package com.bitdubai.fermat_api.layer.all_definition.github;

/**
 * Created by Matias Furszyfer on 2015.08.17..
 */

import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException;
import com.google.gson.JsonParseException;

import org.apache.commons.io.IOUtils;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Class used for Fermat connection with Github
 */

public class GitHubConnection {

    String mainRepository;

    RepositoryId repo;
    GitHubClient client;

    Properties properties;
    GHRepository ghRepository;

    public GitHubConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        mainRepository = "furszy/fermat";
        setUpConnection();
    }

    public GitHubConnection(String repository, String user, String password) throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        this.mainRepository = repository;
        setUpConnection(user, password);
    }


    private void setUpConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");
        try {
            String user = "MALOTeam";
            String password = "fermat123456";
            String vec[] = mainRepository.split("/");
            repo = new RepositoryId(vec[0], vec[1]);
            client = new GitHubClient();
            client.setCredentials(user, password);
            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            ghRepository = gitHub.getRepository(mainRepository);

        } catch (Exception e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }

    private void setUpConnection(String user, String password) throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        properties = new Properties();
        properties.setProperty("login", user);
        properties.setProperty("password", password);
        try {

            String vec[] = mainRepository.split("/");
            repo = new RepositoryId(vec[0], vec[1]);
            client = new GitHubClient();
            client.setCredentials(user, password);
            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            ghRepository = gitHub.getRepository(mainRepository);

        } catch (Exception e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }

    }

    /**
     * Get file from main repository
     *
     * @param path
     * @return
     * @throws IOException
     */

    public String getFile(String path) throws IOException {

        GHContent ghContent = ghRepository.getFileContent(path);

        InputStream inputStream = ghContent.read();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();

        return theString;
    }

    /**
     * Get image from main repository
     *
     * @param path
     * @return
     * @throws IOException
     */
    public byte[] getImage(String path) throws IOException {

        GHContent ghContent = ghRepository.getFileContent(path);
        InputStream inputStream = ghContent.read();
        BufferedInputStream in = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int c;
        while ((c = in.read()) != -1) {
            byteArrayOut.write(c);
        }

        in.close();
        return byteArrayOut.toByteArray();

    }


    /**
     * Push image file to github repository
     *
     * @param path
     * @param commitContent
     * @param commitMessage
     */

    public void createGitHubImageFile(String path, byte[] commitContent, String commitMessage) {
        try {
            Contents contents = new Contents();
            contents.setEncoding(RepositoryContents.ENCODING_BASE64);
            contents.setContent(org.eclipse.egit.github.core.util.EncodingUtils.toBase64(commitContent));
            contents.setName(commitMessage);
            contents.setMessage(commitMessage);
            contents.setPath(path);
            client.put(new StringBuilder().append("/repos/").append(repo.generateId()).append("/contents/").append(path).toString(), contents, Contents.class);
        } catch (JsonParseException e) {
            System.out.println("creado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Push text file to github repository
     *
     * @param path
     * @param commitContent
     * @param commitMessage
     */

    public void createGitHubTextFile(String path, String commitContent, String commitMessage) {
        try {

            Contents contents = new Contents();
            contents.setEncoding(RepositoryContents.ENCODING_BASE64);
            contents.setContent(org.eclipse.egit.github.core.util.EncodingUtils.toBase64(commitContent));
            contents.setName(commitMessage);
            contents.setMessage(commitMessage);
            contents.setPath(path);
            client.put(new StringBuilder().append("/repos/").append(repo.generateId()).append("/contents/").append(path).toString(), contents, Contents.class);
        } catch (JsonParseException e) {
            System.out.println("creado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMainRepository() {
        return mainRepository;
    }

    public void setMainRepository(String mainRepository) {
        this.mainRepository = mainRepository;
    }
}
