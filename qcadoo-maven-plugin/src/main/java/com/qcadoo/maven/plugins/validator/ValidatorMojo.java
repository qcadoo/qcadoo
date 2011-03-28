package com.qcadoo.maven.plugins.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.qcadoo.maven.plugin.SimpleErrorHandler;

/**
 * 
 * @author mady
 * @goal validate
 * @phase validate
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

    // private static final String VIEW_SCHEMA =
    // "file:/Users/mady/qcadoo/mes/mes-core/src/main/resources/com/qcadoo/mes/core/view.xsd";

    private static final String MODEL_SCHEMA = "http://schema.qcadoo.org/model.xsd";

    private static final String PLUGIN_SCHEMA = "http://schema.qcadoo.org/plugin.xsd";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        /**
         * JAVA SOURCE CODE VALIDATION
         */
        validateJavaClasses();

        /**
         * PLUGIN XML VALIDATION
         */
        String fullPluginDir = basedir + resourcePath + "qcadoo-plugin.xml";
        validateFile(PLUGIN_SCHEMA, new File(fullPluginDir));

        /**
         * PLUGIN XML VALIDATION
         */

        Map<List<String>, String> schemas = new HashMap<List<String>, String>();
        schemas.put(getFileList("model:model", fullPluginDir), MODEL_SCHEMA);
        // schemas.put(getFileList("view:view"), VIEW_SCHEMA);

        for (Entry<List<String>, String> entry : schemas.entrySet()) {
            validateXmlFiles(entry);
        }
    }

    private List<String> getFileList(final String type, final String pluginDir) {
        List<String> fileList = new ArrayList<String>();

        try {
            File plugin = new File(pluginDir);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(plugin);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(type);

            for (int i = 0; i < nList.getLength(); i++) {

                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    fileList.add(basedir + resourcePath + eElement.getAttribute("resource"));
                }
            }

        } catch (ParserConfigurationException e) {
            getLog().error(e.getMessage());
        } catch (SAXException e) {
            getLog().error(e.getMessage());
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }

        return fileList;
    }

    private void validateXmlFiles(final Entry<List<String>, String> entry) throws MojoFailureException {
        for (String filePath : entry.getKey()) {
            validateFile(entry.getValue(), new File(filePath));
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
            getLog().error(e.getMessage());
            throw (MojoFailureException) new MojoFailureException("We couldn't parse the file: " + file).initCause(e);
        } catch (SAXException e) {
            getLog().error(e.getMessage());
            throw (MojoFailureException) new MojoFailureException("We couldn't parse the file: " + file).initCause(e);
        } catch (IOException e) {
            getLog().error(e.getMessage());
            throw (MojoFailureException) new MojoFailureException("We couldn't parse the file: " + file).initCause(e);
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

            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(data);

        } catch (FileNotFoundException e) {
            getLog().error(e.getMessage());
        } catch (IOException e) {
            getLog().error(e.getMessage());
        }
    }
}
