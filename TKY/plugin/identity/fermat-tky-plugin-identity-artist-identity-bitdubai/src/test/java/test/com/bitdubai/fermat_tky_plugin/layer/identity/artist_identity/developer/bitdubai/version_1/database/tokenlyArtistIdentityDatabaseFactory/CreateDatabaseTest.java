package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class CreateDatabaseTest {
    @Mock
    UUID pluginId;
    @Mock
    Database database;
    @Test
    public void createDatabaseTest() throws CantCreateDatabaseException {
        TokenlyArtistIdentityDatabaseFactory tokenlyArtistIdentityDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDatabaseFactory.class);
        when(tokenlyArtistIdentityDatabaseFactory.createDatabase(pluginId)).thenReturn(database);
    }
}
