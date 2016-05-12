package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class SetPluginDatabaseSystemTest {
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Test
    public void setPluginDatabaseSystemTest() {
        TokenlyArtistIdentityDatabaseFactory tokenlyArtistIdentityDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDatabaseFactory.class);
        doCallRealMethod().when(tokenlyArtistIdentityDatabaseFactory).setPluginDatabaseSystem(pluginDatabaseSystem);
    }
}
