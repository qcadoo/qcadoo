package com.qcadoo.maven.plugins.jetty;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.mortbay.jetty.plugin.JettyRunMojo;

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
    private File log;

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
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("JETTY");
    }

}
