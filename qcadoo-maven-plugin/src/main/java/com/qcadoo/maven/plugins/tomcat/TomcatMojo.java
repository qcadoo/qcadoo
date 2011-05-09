package com.qcadoo.maven.plugins.tomcat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.assembly.archive.ArchiveExpansionException;
import org.apache.maven.plugin.assembly.utils.AssemblyFileUtils;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;
import org.codehaus.plexus.archiver.util.DefaultFileSet;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.io.InputStreamFacade;
import org.codehaus.plexus.util.io.RawInputStreamFacade;
import org.springframework.core.io.ClassPathResource;

/**
 * @goal tomcat
 * @execute phase="package"
 */
public class TomcatMojo extends AbstractMojo {

    /**
     * @component
     */
    private org.apache.maven.artifact.factory.ArtifactFactory artifactFactory;

    /**
     * @component
     */
    private org.apache.maven.artifact.resolver.ArtifactResolver resolver;

    /**
     * @parameter default-value="${localRepository}"
     */
    private org.apache.maven.artifact.repository.ArtifactRepository localRepository;

    /**
     * @parameter default-value="${project.remoteArtifactRepositories}"
     */
    @SuppressWarnings("rawtypes")
    private java.util.List remoteRepositories;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/"
     * @readonly
     */
    private File workingDirectory;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/${project.artifactId}"
     * @readonly
     */
    private File rootDirectory;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/${project.artifactId}/webapps/ROOT"
     * @readonly
     */
    private File webappDirectory;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/${project.artifactId}/lib/"
     * @readonly
     */
    private File libDirectory;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/${project.artifactId}/bin/"
     * @readonly
     */
    private File binDirectory;

    /**
     * @parameter expression="${basedir}/target/tomcat-archiver/${project.artifactId}/qcadoo/"
     * @readonly
     */
    private File configurationDirectory;

    /**
     * @parameter expression="${tomcat.target}" default-value="${basedir}/target/${project.artifactId}.zip"
     * @readonly
     */
    private File target;

    /**
     * @parameter expression="${tomcat.source}" expression="${basedir}/target/${project.artifactId}-${project.version}.war"
     * @readonly
     */
    private File source;

    /**
     * @parameter
     * @required
     */
    private File configuration;

    /**
     * @parameter
     * @required
     */
    private File jdbcDriver;

    /**
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * @component
     */
    private ArchiverManager archiverManager;

    /**
     * @component role="org.codehaus.plexus.archiver.Archiver" roleHint="zip"
     */
    private ZipArchiver zipArchiver;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            prepareWorkingDirectory();
            copyClassPathResources();

            if (isSaasProfileActive()) {
                updateSetenvShForSaas();
                updateSetenvBatForSaas();
            }

            unpackWar();
            copyConfiguration();
            copyJdbcDriver();
            copyDependencies();
            createArchive();
            registerArtifact();
        } catch (ArchiverException e) {
            throw new MojoExecutionException("Exception while creating zip", e);
        } catch (ArtifactResolutionException e) {
            throw new MojoExecutionException("Exception while copying dependencies", e);
        } catch (ArtifactNotFoundException e) {
            throw new MojoExecutionException("Exception while copying dependencies", e);
        } catch (ArchiveExpansionException e) {
            throw new MojoExecutionException("Exception while unpaking war", e);
        } catch (NoSuchArchiverException e) {
            throw new MojoExecutionException("Exception while unpaking war", e);
        } catch (IOException e) {
            throw new MojoExecutionException("Exception while creating zip", e);
        }
    }

    private void updateSetenvBatForSaas() throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(new File(rootDirectory, "bin/setenv.bat"), true));
            writer.append("set \"JAVA_OPTS=%JAVA_OPTS% -Dspring.profiles.active=saas\"\n");
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    private void updateSetenvShForSaas() throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(new File(rootDirectory, "bin/setenv.sh"), true));
            writer.append("JAVA_OPTS=\"$JAVA_OPTS -Dspring.profiles.active=saas\"\n");
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isSaasProfileActive() {
        for (Profile profile : ((List<Profile>) project.getActiveProfiles())) {
            if ("saas".equals(profile.getId())) {
                return true;
            }
        }
        return false;
    }

    private void registerArtifact() {
        project.getArtifact().setFile(target);
    }

    private void createArchive() throws ArchiverException, IOException {
        zipArchiver.setDestFile(target);
        zipArchiver.setDirectoryMode(493);
        zipArchiver.setFileMode(420);

        DefaultFileSet fileSet = new DefaultFileSet();
        fileSet.setDirectory(workingDirectory);
        fileSet.setIncludingEmptyDirectories(true);
        fileSet.setExcludes(new String[] { "**/*.sh", "**/*.bat" });

        zipArchiver.addFileSet(fileSet);
        zipArchiver.setFileMode(492);

        DefaultFileSet executableFileSet = new DefaultFileSet();
        executableFileSet.setDirectory(workingDirectory);
        executableFileSet.setIncludingEmptyDirectories(true);
        executableFileSet.setIncludes(new String[] { "**/*.sh", "**/*.bat" });

        zipArchiver.addFileSet(executableFileSet);
        zipArchiver.createArchive();
    }

    private void unpackWar() throws IOException, ArchiveExpansionException, NoSuchArchiverException {
        AssemblyFileUtils.unpack(source, webappDirectory, archiverManager);
    }

    private void copyJdbcDriver() throws IOException {
        FileUtils.copyFileToDirectory(jdbcDriver, libDirectory);
    }

    private void copyConfiguration() throws IOException {
        FileUtils.copyDirectory(configuration, configurationDirectory);
    }

    private void copyDependencies() throws ArtifactResolutionException, ArtifactNotFoundException, IOException {
        copyDependency(binDirectory, "commons-daemon", "commons-daemon", "1.0.3", "jar", "commons-daemon.jar");
        copyDependency(binDirectory, "org.apache.tomcat", "juli", "6.0.29", "jar", "tomcat-juli.jar");
        copyDependency(binDirectory, "org.apache.tomcat", "bootstrap", "6.0.29", "jar", "bootstrap.jar");
        copyDependency(libDirectory, "org.apache.tomcat", "annotations-api", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "catalina", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "tribes", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "catalina-ha", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "el-api", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "jasper-jdt", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "jasper-el", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "jasper", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "jsp-api", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "servlet-api", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "coyote", "6.0.29", "jar");
        copyDependency(libDirectory, "org.apache.tomcat", "dbcp", "6.0.29", "jar");
    }

    private void copyClassPathResources() throws IOException {
        copyClassPathResource("LICENSE");
        copyClassPathResource("bin/callRestart.bat");
        copyClassPathResource("bin/catalina-tasks.xml");
        copyClassPathResource("bin/catalina.sh");
        copyClassPathResource("bin/catalina.bat");
        copyClassPathResource("bin/cpappend.bat");
        copyClassPathResource("bin/digest.bat");
        copyClassPathResource("bin/digest.sh");
        copyClassPathResource("bin/restart.bat");
        copyClassPathResource("bin/restart.sh");
        copyClassPathResource("bin/setclasspath.bat");
        copyClassPathResource("bin/setclasspath.sh");
        copyClassPathResource("bin/setenv.bat");
        copyClassPathResource("bin/setenv.sh");
        copyClassPathResource("bin/shutdown.bat");
        copyClassPathResource("bin/shutdown.sh");
        copyClassPathResource("bin/startup.bat");
        copyClassPathResource("bin/startup.sh");
        copyClassPathResource("bin/tool-wrapper.bat");
        copyClassPathResource("bin/tool-wrapper.sh");
        copyClassPathResource("bin/version.bat");
        copyClassPathResource("bin/version.sh");
        copyClassPathResource("conf/catalina.policy");
        copyClassPathResource("conf/catalina.properties");
        copyClassPathResource("conf/context.xml");
        copyClassPathResource("conf/logging.properties");
        copyClassPathResource("conf/server.xml");
        copyClassPathResource("conf/tomcat-users.xml");
        copyClassPathResource("conf/web.xml");
        copyClassPathResource("logs/IGNOREME");
        copyClassPathResource("temp/IGNOREME");
        copyClassPathResource("work/IGNOREME");
    }

    private void prepareWorkingDirectory() throws IOException {
        FileUtils.forceMkdir(workingDirectory);

        FileUtils.cleanDirectory(workingDirectory);

        FileUtils.forceMkdir(webappDirectory);
        FileUtils.forceMkdir(libDirectory);
        FileUtils.forceMkdir(configurationDirectory);
        FileUtils.forceMkdir(binDirectory);
    }

    private void copyClassPathResource(final String resourceName) throws IOException {
        InputStreamFacade resource = new RawInputStreamFacade(new ClassPathResource("/tomcat/" + resourceName).getInputStream());
        FileUtils.copyStreamToFile(resource, new File(rootDirectory, resourceName));
    }

    private void copyDependency(final File target, final String groupId, final String artifactId, final String version,
            final String type) throws ArtifactResolutionException, ArtifactNotFoundException, IOException {
        copyDependency(target, groupId, artifactId, version, type, null);
    }

    private void copyDependency(final File target, final String groupId, final String artifactId, final String version,
            final String type, final String finalName) throws ArtifactResolutionException, ArtifactNotFoundException, IOException {
        Artifact artifact = artifactFactory.createArtifactWithClassifier(groupId, artifactId, version, type, null);
        resolver.resolve(artifact, remoteRepositories, localRepository);
        FileUtils.copyFileToDirectory(artifact.getFile(), target);
        if (finalName != null) {
            FileUtils.rename(new File(target, artifact.getFile().getName()), new File(target, finalName));
        }
    }
}
