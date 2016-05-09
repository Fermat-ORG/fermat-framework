package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDeveloperDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetPluginIdTest {
    @Mock
    UUID pluginId;
    @Test
    public void setPluginIdTest(){
        TokenlyArtistIdentityDeveloperDatabaseFactory tokenlyArtistIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDeveloperDatabaseFactory.class);
        doCallRealMethod().when(tokenlyArtistIdentityDeveloperDatabaseFactory).setPluginId(pluginId);
    }
}
