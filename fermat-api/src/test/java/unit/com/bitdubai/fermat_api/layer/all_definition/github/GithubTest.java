package unit.com.bitdubai.fermat_api.layer.all_definition.github;

import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by rodrigo on 8/22/15.
 */
public class GithubTest {
    final String USERNAME="guillermo20";
    final String PASSWORD ="";
    final String REPOSITORY ="guillermo20/testFermat";

    @Ignore
    @Test
    public void testCreateFile() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        GithubConnection githubConnection = new GithubConnection(REPOSITORY, USERNAME, PASSWORD);
        githubConnection.createGitHubTextFile("bitdubai/reference_wallet/testNuevoString-PathE", "test2 content", "test2 commit message");
    }

    @Ignore
    @Test
    public void testCreateImage() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        GithubConnection githubConnection = new GithubConnection(REPOSITORY, USERNAME, PASSWORD);
        githubConnection.createGitHubImageFile("NuevaImage3", new byte[]{3,4}, "commit message");
    }
    @Ignore
    @Test
    public void getFile()throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException, IOException{
        GithubConnection githubConnection = new GithubConnection(REPOSITORY, USERNAME, PASSWORD);
        String result =githubConnection.getFile("bitdubai/reference_wallet/testNuevoString-PathE");
        System.out.println(result);
    }
}
