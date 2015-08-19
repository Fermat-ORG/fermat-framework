package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1;

/**
 * Created by Matias Furszyfer on 2015.08.17..
 */

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

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
 *  This class will be used until the main repository is open source
 */

public class GithubConnection {


    Properties properties;
    GHRepository ghRepository;

    public GithubConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        setUpConnection();
    }


    private void setUpConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");
        try {

            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            ghRepository = gitHub.getRepository("furszy/fermat");


        } catch (java.io.FileNotFoundException e) {
            throw new GitHubRepositoryNotFoundException(GitHubRepositoryNotFoundException.DEFAULT_MESSAGE, e, "Check the name of the repository.", "");
        } catch (IOException e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }


    public String getFile(String path) throws IOException {

        GHContent ghContent= ghRepository.getFileContent(path);

        InputStream inputStream = ghContent.read();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();

        return theString;
    }

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


}
