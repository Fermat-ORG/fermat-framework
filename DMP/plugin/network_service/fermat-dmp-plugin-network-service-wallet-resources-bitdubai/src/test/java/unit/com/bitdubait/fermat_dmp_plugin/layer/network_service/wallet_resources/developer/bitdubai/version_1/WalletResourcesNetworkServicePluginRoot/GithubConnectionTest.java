package unit.com.bitdubait.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubCredentialsExpectedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Created by Matias Furszyfer on 2015.08.07..
 */


public class GithubConnectionTest {


    @Test
    public void getRepository() throws GitHubNotAuthorizedException, GitHubRepositoryNotFoundException, GitHubCredentialsExpectedException {


        Properties properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");

        try {
            GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
            GHRepository ghRepository = gitHub.getRepository("furszy/fermat");
            System.out.println(ghRepository.toString());

        } catch (java.io.FileNotFoundException e) {
            throw new GitHubRepositoryNotFoundException(GitHubRepositoryNotFoundException.DEFAULT_MESSAGE, e, "Check the name of the repository.", "");
        } catch (IOException e) {
            throw new GitHubNotAuthorizedException(GitHubNotAuthorizedException.DEFAULT_MESSAGE, e, "Check your credentials or access to this repository.", "");
        }
    }

    @Test
    public void testDownloadFilesFromRepo() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");


        GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
        GHRepository ghRepository = gitHub.getRepository("furszy/fermat");


        GHUser hub=ghRepository.getOwner();

        GHContent ghContent= ghRepository.getFileContent("seed-resources/wallet_resources/bitDubai/reference_wallet/bitcoin_wallet/navigation_structure/1.0.0/navigation_structure.xml");

        InputStream inputStream = ghContent.read();

        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();

        System.out.println(theString);

    }

    @Ignore
    @Test
    public void testuploadFilesFromRepo() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("login", "MALOTeam");
        properties.setProperty("password", "fermat123456");


        GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
        GHRepository ghRepository = gitHub.getRepository("furszy/fermat");


        GHUser hub=ghRepository.getOwner();

        ghRepository.createContent("mati", "testeando", "seed-resources/wallet_resources/bitDubai/reference_wallet/bitcoin_wallet/navigation_structure/text.txt");


    }




}
