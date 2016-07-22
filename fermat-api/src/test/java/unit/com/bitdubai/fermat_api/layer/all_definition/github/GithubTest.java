package unit.com.bitdubai.fermat_api.layer.all_definition.github;

import com.bitdubai.fermat_api.layer.all_definition.github.GitHubConnection;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by rodrigo on 8/22/15.
 */
public class GithubTest {
    final String USERNAME = "guillermo20";
    final String PASSWORD = "";
    final String REPOSITORY = "guillermo20/testFermat";

    @Ignore
    @Test
    public void testCreateFile() throws com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException, com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException {
        GitHubConnection gitHubConnection = new GitHubConnection(REPOSITORY, USERNAME, PASSWORD);
        gitHubConnection.createGitHubTextFile("bitdubai/reference_wallet/testNuevoString-PathE", "test2 content", "test2 commit message");
    }

    @Ignore
    @Test
    public void testCreateImage() throws com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException, com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException {
        GitHubConnection gitHubConnection = new GitHubConnection(REPOSITORY, USERNAME, PASSWORD);
        gitHubConnection.createGitHubImageFile("NuevaImage3", new byte[]{3, 4}, "commit message");
    }

    @Ignore
    @Test
    public void getFile() throws IOException, com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException, com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException {
        GitHubConnection gitHubConnection = new GitHubConnection(REPOSITORY, USERNAME, PASSWORD);
        String result = gitHubConnection.getFile("bitdubai/reference_wallet/testNuevoString-PathE");
        System.out.println(result);
    }
}
