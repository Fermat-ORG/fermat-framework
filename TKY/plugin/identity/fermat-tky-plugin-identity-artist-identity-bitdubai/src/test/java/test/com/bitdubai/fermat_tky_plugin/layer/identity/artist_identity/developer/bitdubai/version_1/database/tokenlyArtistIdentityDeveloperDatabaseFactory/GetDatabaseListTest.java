package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDeveloperDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class GetDatabaseListTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    List<DeveloperDatabase> databases;
    @Test
    public void getDatabaseListTest() {
        TokenlyArtistIdentityDeveloperDatabaseFactory tokenlyArtistIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDeveloperDatabaseFactory.class);
        when(tokenlyArtistIdentityDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory)).thenReturn(databases);
    }
}
