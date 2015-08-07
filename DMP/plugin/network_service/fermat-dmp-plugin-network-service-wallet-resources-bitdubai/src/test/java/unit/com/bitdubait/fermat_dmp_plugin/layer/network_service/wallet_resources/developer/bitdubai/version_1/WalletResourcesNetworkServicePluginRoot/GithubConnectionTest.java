package unit.com.bitdubait.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubCredentialsExpectedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

import org.junit.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Matias Furszyfer on 2015.08.07..
 */
public class GithubConnectionTest {


    @Test
    public void getRepository() throws GitHubNotAuthorizedException, GitHubRepositoryNotFoundException, GitHubCredentialsExpectedException {


        Properties properties = new Properties();
        properties.setProperty("login", "furszy");
        properties.setProperty("password", "");

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
    public void testForkRepo() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("login", "furszy");
        properties.setProperty("password", "sausages1");


        GitHub gitHub = GitHubBuilder.fromProperties(properties).build();
        GHRepository ghRepository = gitHub.getRepository("furszy/fermat");


        GHUser hub=ghRepository.getOwner();

        //ghRepository.getFileContent()
        //GHRepository ghRepository=hub.getRepository("fermat");
        ghRepository.createIssue("Mati testing github api").body("Estoy creando un issue desde mi celular").assignee(hub).label("bug").create();
        //GHRepository r=hub.getOrganizations("bitDubai")getRepository("fermat");
        //GHRepository r=hub.createRepository("github-api-test","a test repository","http://github-api.kohsuke.org/",true);

    }
}
