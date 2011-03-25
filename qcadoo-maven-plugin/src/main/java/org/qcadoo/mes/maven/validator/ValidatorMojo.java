package org.qcadoo.mes.maven.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.qcadoo.mes.maven.SimpleErrorHandler;
import org.xml.sax.SAXException;

/**
 * 
 * @author mady
 * @goal validate
 * @phase compile
 */
public class ValidatorMojo extends AbstractMojo {

    /**
     * @parameter expression="${validate.viewXmlPath}" default-value="/src/main/java/"
     * @required
     * @readonly
     */
    private String javaSourcePath;

    /**
     * @parameter expression="${validate.viewXmlPath}" default-value="/src/main/resources/"
     * @required
     * @readonly
     */
    private String resourcePath;

    /**
     * @parameter default-value="${basedir}"
     * @required
     * @readonly
     */
    private String basedir;

    /**
     * @parameter default-value="${mes.plugin.identifier}"
     * @required
     * @readonly
     */
    private String pluginId;

    // private static final String VIEW_SCHEMA =
    // "file:/Users/mady/qcadoo/mes/mes-core/src/main/resources/com/qcadoo/mes/core/view.xsd";

    private static final String MODEL_SCHEMA = "../resources/schemas/model.xsd";

    private static final String PLUGIN_SCHEMA = "../resources/schemas/plugin.xsd";

    public void execute() throws MojoExecutionException, MojoFailureException {
        /**
         * PLUGIN XML VALIDATION
         */
        // String fullViewDir = basedir + xmlPath + pluginId + "/view";
        String fullModelDir = basedir + resourcePath + pluginId + "/model";

        // URL myurl = this.getClass().getResource("/validator-maven-plugin/src/main/resources/schemas/model.xsd");
        Map<String, String> schemas = new HashMap<String, String>();
        URL model = getClass().getResource("model.xsd");
        System.out.println(model);
        schemas.put(fullModelDir, MODEL_SCHEMA);
        // schemas.put(fullViewDir, VIEW_SCHEMA);

        for (Entry<String, String> entry : schemas.entrySet()) {
            validateXmlFilesInALocation(entry);
        }

        /**
         * PLUGIN XML VALIDATION
         */
        String fullPluginDir = basedir + resourcePath + "qcadoo-plugin.xml";
        validateFile(PLUGIN_SCHEMA, new File(fullPluginDir));

        /**
         * JAVA SOURCE CODE VALIDATION
         */
        validateJavaClasses();

    }

    private void validateXmlFilesInALocation(final Entry<String, String> entry) throws MojoFailureException {
        File file = new File(entry.getKey());

        File[] files = file.listFiles(new FilenameFilter() {

            public boolean accept(File d, String n) {
                return n.endsWith(".xml");
            }
        });

        for (int i = 0; i < files.length; i++) {
            validateFile(entry.getValue(), files[i]);
        }
    }

    private void validateFile(final String schema, final File file) throws MojoFailureException {
        try {
            getLog().info("Validating file: " + file);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            factory.setValidating(true);
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", schema);

            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setErrorHandler(new SimpleErrorHandler());
            parser.parse(file);

        } catch (ParserConfigurationException e) {
            throw new MojoFailureException("We couldn't parse the file: " + file);
        } catch (SAXException e) {
            throw new MojoFailureException("We couldn't parse the file: " + file);
        } catch (IOException e) {
            throw new MojoFailureException("We couldn't parse the file: " + file);
        }
    }

    private void validateJavaClasses() throws MojoFailureException {
        File file = new File(basedir + javaSourcePath);

        List<File> files = getFileListRecursively(file);

        for (File fileToGrep : files) {
            grepFile(fileToGrep, "import com.qcadoo.mes.internal.");
        }
    }

    private List<File> getFileListRecursively(final File startingDir) {
        List<File> filesFound = new ArrayList<File>();
        File[] currentFilesAndDirs = startingDir.listFiles();

        for (File file : currentFilesAndDirs) {
            if (file.getName().contains("java")) {
                filesFound.add(file);
            } else if (file.isDirectory()) {
                filesFound.addAll(getFileListRecursively(file));
            }
        }

        return filesFound;
    }

    public void grepFile(final File file, final String re) throws MojoFailureException {

        getLog().info("Validating file: " + file);

        InputStream in = null;
        InputStreamReader isr = null;

        try {
            in = new FileInputStream(file);
            isr = new InputStreamReader(in);
            BufferedReader data = new BufferedReader(isr);
            String line = null;

            while ((line = data.readLine()) != null) {
                if (line.contains(re)) {
                    throw new MojoFailureException("File: " + file
                            + " contains a com.qcadoo.mes.internal import which is not permitted. Please use the API instead.");
                }
            }

        } catch (FileNotFoundException e) {
            getLog().error(e.getMessage());
        } catch (IOException e) {
            getLog().error(e.getMessage());
        } finally {
            try {
                isr.close();
                in.close();
            } catch (IOException e) {
                getLog().error(e.getMessage());
            }
        }

    }
}
