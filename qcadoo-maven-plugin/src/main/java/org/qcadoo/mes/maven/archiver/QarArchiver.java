package org.qcadoo.mes.maven.archiver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.jar.ManifestException;
import org.codehaus.plexus.util.FileUtils;

/**
 * 
 * @goal package
 * @phase package
 * 
 */
public class QarArchiver extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     * @readonly
     * 
     */
    private File classesDirectory;

    /**
     * @parameter expression="${project.build.directory}"
     * @required
     * @readonly
     * 
     */
    private File buildDirectory;

    /**
     * 
     * @parameter expression="${component.org.codehaus.plexus.archiver.Archiver#jar}"
     * @required
     * 
     */
    private JarArchiver jarArchiver;

    /**
     * @parameter
     */

    protected MavenArchiveConfiguration archive = new MavenArchiveConfiguration();

    /**
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File libDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        String archiveName = project.getBuild().getFinalName() + ".qar";
        File custFile = new File(buildDirectory, archiveName);

        MavenArchiver archiver = new MavenArchiver();
        archiver.setArchiver(jarArchiver);
        archiver.setOutputFile(custFile);

        // copyLibs();
        try {
            // archive classes
            archiver.getArchiver().addDirectory(classesDirectory, "classes/");
            // archive libs
            // archiver.getArchiver().addDirectory(libDirectory, "lib/");
            // create archive
            archiver.createArchive(project, archive);
            // set archive as artifact
            project.getArtifact().setFile(custFile);
        } catch (ArchiverException e) {
            throw new MojoExecutionException("Exception while packaging", e);
        } catch (ManifestException e) {
            throw new MojoExecutionException("Exception while packaging", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Exception while packaging", e);
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Exception while packaging", e);
        }
    }

    @SuppressWarnings("rawtypes")
    protected void copyLibs() throws MojoExecutionException {
        try {
            Set artifacts = project.getArtifacts();
            for (Iterator iter = artifacts.iterator(); iter.hasNext();) {
                Artifact artifact = (Artifact) iter.next();
                ScopeArtifactFilter filter = new ScopeArtifactFilter(Artifact.SCOPE_RUNTIME);
                if (!artifact.isOptional() && filter.include(artifact) && "jar".equals(artifact.getType())) {
                    FileUtils.copyFileToDirectory(artifact.getFile(), libDirectory);
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Error copying libs", e);
        }
    }
}
