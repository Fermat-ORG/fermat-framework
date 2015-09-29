package unit.com.bitdubai.fermat_api.layer.all_definition.github;

import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;

import org.junit.Test;

/**
 * Created by rodrigo on 8/22/15.
 */
public class githubTest {
    final String USERNAME="acostarodrigo";
    final String PASSWORD ="";
    final String REPOSITORY ="acostarodrigo/testFermat";

    @Test
    public void testCreateFile() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        GithubConnection githubConnection = new GithubConnection(REPOSITORY, USERNAME, PASSWORD);
        githubConnection.createGitHubTextFile("bitdubai/reference_wallet/testNuevoString-Path", "hola content", "test commit message");
    }

    @Test
    public void testCreateImage() throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        GithubConnection githubConnection = new GithubConnection(REPOSITORY, USERNAME, PASSWORD);
        githubConnection.createGitHubImageFile("NuevaImage", new byte[]{3,4}, "commit message");
    }
}
