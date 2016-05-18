package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDeveloperDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetPluginDatabaseSystemTest {
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Test
    public void setPluginDatabaseSystemTest(){
        TokenlyArtistIdentityDeveloperDatabaseFactory tokenlyArtistIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDeveloperDatabaseFactory.class);
        doCallRealMethod().when(tokenlyArtistIdentityDeveloperDatabaseFactory).setPluginDatabaseSystem(pluginDatabaseSystem);
    }
}
