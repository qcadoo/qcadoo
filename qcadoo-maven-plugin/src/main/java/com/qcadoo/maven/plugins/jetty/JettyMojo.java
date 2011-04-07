package com.qcadoo.maven.plugins.jetty;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.mortbay.jetty.plugin.JettyRunMojo;
import org.mortbay.jetty.plugin.JettyWebAppContext;
import org.mortbay.jetty.plugin.SystemProperties;
import org.mortbay.jetty.plugin.SystemProperty;
import org.springframework.util.ReflectionUtils;

/**
 * @goal jetty
 * @execute phase="test-compile"
 */
public class JettyMojo extends JettyRunMojo {

    /**
     * @parameter
     * @required
     */
    private File jdbcDriver;

    /**
     * @parameter
     * @required
     */
    private String profile;

    /**
     * @parameter
     * @required
     */
    private File configuration;

    /**
     * @parameter
     * @required
     */
    private File logs;

    /**
     * @parameter
     * @required
     */
    private File temporaryPlugins;

    /**
     * @parameter
     * @required
     */
    private File plugins;

    /**
     * @parameter
     * @required
     */
    private File webapp;

    /**
     * @parameter
     * @required
     */
    private String restartCommand;

    /**
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project2;

    /**
     * @parameter expression="${basedir}"
     * @readonly
     */
    private String basedir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        setField("scanTargets", new File[] { jdbcDriver });
        setField("project", project2);
        setField("reload", "automatic");
        setField("scanIntervalSeconds", 0);

        logs.mkdirs();
        temporaryPlugins.mkdirs();
        plugins.mkdirs();

        SystemProperties systemProperties = new SystemProperties();
        systemProperties.setSystemProperty(getSystemProperty("spring.profiles.default", profile));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_CONF", configuration.getAbsolutePath()));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_LOG", logs.getAbsolutePath()));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_PLUGINS_TMP_PATH", temporaryPlugins.getAbsolutePath()));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_PLUGINS_PATH", plugins.getAbsolutePath()));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_WEBAPP_PATH", webapp.getAbsolutePath()));
        systemProperties.setSystemProperty(getSystemProperty("QCADOO_RESTART_CMD", restartCommand));
        setSystemProperties(systemProperties);

        try {
            JettyWebAppContext webAppConfig = new JettyWebAppContext();
            webAppConfig.setContextPath("/");

            List<Resource> resources = new ArrayList<Resource>();
            resources.add(Resource.newResource(webapp.getAbsolutePath()));
            resources.add(Resource.newResource("${basedir}/../../../qcadoo/qcadoo-view/src/main/resources"));
            resources.add(Resource.newResource("${basedir}/../../mes-plugins/mes-plugins-plugin-management/src/main/resources"));
            resources.add(Resource.newResource("${basedir}/../../mes-plugins/mes-plugins-products/src/main/resources"));
            webAppConfig.setBaseResource(new ResourceCollection(resources.toArray(new Resource[resources.size()])));
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        super.execute();
    }

    private SystemProperty getSystemProperty(final String name, final String value) {
        SystemProperty systemProperty = new SystemProperty();
        systemProperty.setKey(name);
        systemProperty.setName(name);
        systemProperty.setValue(value);
        return systemProperty;
    }

    private void setField(final String name, final Object value) {
        Field field = ReflectionUtils.findField(getClass(), name);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, this, value);
    }

}
