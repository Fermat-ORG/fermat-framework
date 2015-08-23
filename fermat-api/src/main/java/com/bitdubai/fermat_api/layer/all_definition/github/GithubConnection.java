package com.bitdubai.fermat_api.layer.all_definition.github;

/**
 * Created by Matias Furszyfer on 2015.08.17..
 */

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
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
 *  Class used for Fermat connection with Github
 */

public class GithubConnection {

    String mainRepository;


    Properties properties;
    GHRepository ghRepository;

    public GithubConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        mainRepository="furszy/fermat";
        setUpConnection();
    }

    public GithubConnection(String repository, String user, String password) throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        this.mainRepository = repository;
        setUpConnection(user, password);
    }


    private void setUpConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");
        try {

            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            ghRepository = gitHub.getRepository(mainRepository);


        } catch (java.io.FileNotFoundException e) {
            throw new GitHubRepositoryNotFoundException(GitHubRepositoryNotFoundException.DEFAULT_MESSAGE, e, "Check the name of the repository.", "");
        } catch (IOException e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }

    private void setUpConnection(String user,String password) throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        properties = new Properties();
        properties.setProperty("login", user);
        properties.setProperty("password", password);
        try {

            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            ghRepository = gitHub.getRepository(mainRepository);


        } catch (java.io.FileNotFoundException e) {
            throw new GitHubRepositoryNotFoundException(GitHubRepositoryNotFoundException.DEFAULT_MESSAGE, e, "Check the name of the repository.", "");
        } catch (IOException e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }


    /**
     *
     *  Get file from main repository
     *
     * @param path
     * @return
     * @throws IOException
     */


    public String getFile(String path) throws IOException {

        GHContent ghContent= ghRepository.getFileContent(path);

        InputStream inputStream = ghContent.read();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();

        return theString;
    }

    /**
     *
     *  Get image from main repository
     *
     * @param path
     * @return
     * @throws IOException
     */

    public byte[] getImage(String path) throws IOException {

        GHContent ghContent= ghRepository.getFileContent(path);

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
     *
     *  Push image file to github repository
     *
     * @param path
     * @param commitContent
     * @param commitMessage
     */

    public void createGitHubImageFile(String path, byte[] commitContent, String commitMessage) {
        try {
            ghRepository.createContent(commitContent,commitMessage, path);
        } catch (IOException e) {
            System.out.println(getJsonMessage(e.getMessage()));
        }
    }

    /**
     *
     *  Push text file to github repository
     *
     *
     * @param path
     * @param commitContent
     * @param commitMessage
     */
    public void createGitHubTextFile(String path, String commitContent, String commitMessage) {
        try {
            ghRepository.createContent(commitContent,commitMessage, path);
        } catch (IOException e) {
            System.out.println(getJsonMessage(e.getMessage()));
        }
    }

    private String getJsonMessage(String jsonMessage) {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(jsonMessage);
            JsonNode nameNode = rootNode.path("message");

            return nameNode.textValue();
        } catch (IOException e) {
            return "Unexpected error.";
        }
    }

    public String getMainRepository() {
        return mainRepository;
    }

    public void setMainRepository(String mainRepository) {
        this.mainRepository = mainRepository;
    }
}
