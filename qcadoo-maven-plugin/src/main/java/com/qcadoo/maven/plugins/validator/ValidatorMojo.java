package com.qcadoo.maven.plugins.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

import com.qcadoo.plugin.api.ValidationErrorHandler;

/**
 * 
 * @author mady
 * @goal validate
 * @phase validate
 */
public class ValidatorMojo extends AbstractMojo {

    /**
     * @parameter expression="${validate.javaSourcePath}" default-value="/src/main/java/"
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

    private final String[] forbiddenPackages = new String[] { "com.qcadoo.model.internal.", "com.qcadoo.view.internal.",
            "com.qcadoo.localization.internal.", "com.qcadoo.plugin.internal.", "com.qcadoo.report.internal.",
            "com.qcadoo.security.internal." };

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        validateJavaClasses();
        validateSchemas();
    }

    private void validateSchemas() throws MojoFailureException {
        String pluginDescriptor = basedir + resourcePath + "qcadoo-plugin.xml";

        validateSchema(basedir + resourcePath + "qcadoo-plugin.xml");

        for (String file : getModelResources(pluginDescriptor)) {
            validateSchema(file);
        }
        for (String file : getViewResources(pluginDescriptor, "view")) {
            validateSchema(file);
        }
        for (String file : getViewResources(pluginDescriptor, "view-ribbon-group")) {
            validateSchema(file);
        }
        for (String file : getViewResources(pluginDescriptor, "view-tab")) {
            validateSchema(file);
        }
    }

    @SuppressWarnings("unchecked")
    private Set<String> getViewResources(final String pluginDescriptor, final String type) {
        Set<String> resources = new HashSet<String>();

        try {
            Document document = new SAXBuilder().build(new File(pluginDescriptor));

            String pluginName = document.getRootElement().getAttributeValue("plugin");

            if (document.getRootElement().getChild("modules", null) != null) {
                List<Element> elements = document.getRootElement().getChild("modules", null).getChildren(type, null);

                for (Element element : elements) {
                    for (Element resource : (List<Element>) element.getChildren()) {
                        resources.add(basedir + resourcePath + pluginName + "/" + resource.getTextTrim());
                    }
                }
            }
        } catch (IOException e) {
            getLog().error(e.getMessage());
        } catch (JDOMException e) {
            getLog().error(e.getMessage());
        }

        return resources;
    }

    @SuppressWarnings("unchecked")
    private Set<String> getModelResources(final String pluginDescriptor) {
        Set<String> resources = new HashSet<String>();

        try {
            Document document = new SAXBuilder().build(new File(pluginDescriptor));

            String pluginName = document.getRootElement().getAttributeValue("plugin");

            if (document.getRootElement().getChild("modules", null) != null) {
                List<Element> elements = document.getRootElement().getChild("modules", null).getChildren("model", null);

                for (Element element : elements) {
                    resources.add(basedir + resourcePath + pluginName + "/" + element.getAttributeValue("resource"));
                }
            }
        } catch (IOException e) {
            getLog().error(e.getMessage());
        } catch (JDOMException e) {
            getLog().error(e.getMessage());
        }

        return resources;
    }

    private void validateSchema(final String file) throws MojoFailureException {
        try {
            getLog().info("Validating file: " + file);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            factory.setValidating(true);
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setErrorHandler(new ValidationErrorHandler());
            parser.parse(new File(file));

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
            for (String firbiddenPackage : forbiddenPackages) {
                grepFile(fileToGrep, "import " + firbiddenPackage);
            }
        }
    }

    private List<File> getFileListRecursively(final File startingDir) {
        List<File> filesFound = new ArrayList<File>();
        File[] currentFilesAndDirs = startingDir.listFiles();

        if (currentFilesAndDirs != null) {

            for (File file : currentFilesAndDirs) {
                if (file.getName().contains("java")) {
                    filesFound.add(file);
                } else if (file.isDirectory()) {
                    filesFound.addAll(getFileListRecursively(file));
                }
            }
        }

        return filesFound;
    }

    public void grepFile(final File file, final String re) throws MojoFailureException {
        getLog().info("Validating file " + file + " with pattern '" + re + "'");

        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader data = null;

        try {
            in = new FileInputStream(file);
            isr = new InputStreamReader(in);
            data = new BufferedReader(isr);
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
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(data);
        }
    }
}
