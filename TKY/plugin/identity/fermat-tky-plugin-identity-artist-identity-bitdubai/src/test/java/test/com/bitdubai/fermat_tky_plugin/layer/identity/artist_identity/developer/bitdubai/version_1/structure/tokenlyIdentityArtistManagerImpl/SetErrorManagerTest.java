package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyIdentityArtistManagerImpl;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyIdentityArtistManagerImpl;

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
    public void setErrorManagerTest(){
        TokenlyIdentityArtistManagerImpl tokenlyIdentityArtistManager = Mockito.mock(TokenlyIdentityArtistManagerImpl.class);
        doCallRealMethod().when(tokenlyIdentityArtistManager).setErrorManager(errorManager);
    }
}
