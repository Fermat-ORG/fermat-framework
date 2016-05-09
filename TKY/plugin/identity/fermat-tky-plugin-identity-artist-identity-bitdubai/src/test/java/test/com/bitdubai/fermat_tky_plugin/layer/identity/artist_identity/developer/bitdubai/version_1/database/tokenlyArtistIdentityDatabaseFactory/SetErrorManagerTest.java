package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetErrorManagerTest {
    @Mock
    ErrorManager errorManager;
    @Test
    public void setErrorManagerTest() {
        TokenlyArtistIdentityDatabaseFactory tokenlyArtistIdentityDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDatabaseFactory.class);
        doCallRealMethod().when(tokenlyArtistIdentityDatabaseFactory).setErrorManager(errorManager);
    }
}
