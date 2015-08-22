package unit.com.bitdubai.fermat_api.layer.all_definition.github;

import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

import org.junit.Test;

/**
 * Created by rodrigo on 8/22/15.
 */
public class githubTest {
    final String USERNAME="";
    final String PASSWORD ="";
    @Test
    public void testConnection() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        GithubConnection githubConnection = new GithubConnection(USERNAME, PASSWORD);
        githubConnection.createGitHubTextFile("testNuevoStrin", "hola", "test");
    }
}
