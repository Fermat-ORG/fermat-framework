package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.exceptions.GitHubCredentialsExpectedException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.exceptions.GitHubRepositoryNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p/>
 * Created by Leon Acosta on 31/07/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RepositoryManager {

    public GHRepository getRepository(String login, String password, String repository) throws GitHubNotAuthorizedException, GitHubRepositoryNotFoundException, GitHubCredentialsExpectedException {

        if (login == null || password == null)
            throw new GitHubCredentialsExpectedException(GitHubCredentialsExpectedException.DEFAULT_MESSAGE, null, "Enter the credentials correctly, login or password cannot be null.", "");

        Properties properties = new Properties();
        properties.setProperty("login", login);
        properties.setProperty("password", password);

        try {
            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            return gitHub.getRepository(repository);
        } catch (java.io.FileNotFoundException e) {
            throw new GitHubRepositoryNotFoundException(GitHubRepositoryNotFoundException.DEFAULT_MESSAGE, e, "Check the name of the repository.", "");
        } catch (IOException e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }

    public void updateGitHubFile(GHRepository ghRepository, String file, String commitContent, String commitComment) {
        try {
            GHContent ghContent = ghRepository.getFileContent(file);
            ghContent.update(commitContent, commitComment);
        } catch (IOException e) {
            System.out.println(getJsonMessage(e.getMessage()));
        }
    }

    public void createGitHubFile(GHRepository ghRepository, String file, String commitContent, String commitComment) {
        try {
            ghRepository.createContent(commitContent,commitComment, file);
        } catch (IOException e) {
            System.out.println(getJsonMessage(e.getMessage()));
        }
    }

    public void updateGitHubFile(GHRepository ghRepository, String file, byte[] commitContent, String commitComment) {
        try {
            GHContent ghContent = ghRepository.getFileContent(file);
            ghContent.update(commitContent, commitComment);
        } catch (IOException e) {
            System.out.println(getJsonMessage(e.getMessage()));
        }
    }

    public void createGitHubFile(GHRepository ghRepository, String file, byte[] commitContent, String commitComment) {
        try {
            ghRepository.createContent(commitContent,commitComment, file);
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

   /* public void uploadFileStructure() {
        Properties properties = new Properties();
        properties.setProperty("login", "lnacosta");
        properties.setProperty("password", "Github91");

        GitHub gitHub;

        try {
            gitHub = GitHubBuilder.fromProperties(properties).build();
            GHRepository repository = gitHub.getRepository("fermat");
            repository.createContent();
        } catch (Exception e) {

        }
    }*/

    /**
     * <p>This method connects to the repository and download resource file on string
     *
     * @param repositoryLink
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private String getRepositoryStringFile(String repositoryLink) throws IOException, FileNotFoundException {
        URL url = new URL(repositoryLink);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        Map<String, List<String>> headerFields = http.getHeaderFields();
        for (String header : headerFields.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                repositoryLink = headerFields.get("Location").get(0);
                url = new URL(repositoryLink);
                http = (HttpURLConnection) url.openConnection();
                headerFields = http.getHeaderFields();
            }
        }

        InputStream crunchifyStream = http.getInputStream();
        return getStringFromStream(crunchifyStream);
    }

    /**
     * <p>This method connects to the repository and download resource file on byte
     *
     * @param repositoryLink path on repository of the file we're trying to get
     * @return byte image object
     * @throws IOException
     * @throws FileNotFoundException
     */
    private byte[] getRepositoryBiteArrayFile(String repositoryLink) throws IOException, FileNotFoundException {
        URL url = new URL(repositoryLink);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int c;
        while ((c = in.read()) != -1) {
            byteArrayOut.write(c);
        }

        in.close();
        return byteArrayOut.toByteArray();
    }

    /**
     * <p> Return the string content from a Stream
     *
     * @param stream
     * @return String Stream Object
     * @throws IOException
     */
    private String getStringFromStream(InputStream stream) throws IOException {
        if (stream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                int counter;
                while ((counter = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, counter);
                }
            } finally {
                stream.close();
            }
            return writer.toString();
        } else {
            return "No Contents";
        }
    }
}
