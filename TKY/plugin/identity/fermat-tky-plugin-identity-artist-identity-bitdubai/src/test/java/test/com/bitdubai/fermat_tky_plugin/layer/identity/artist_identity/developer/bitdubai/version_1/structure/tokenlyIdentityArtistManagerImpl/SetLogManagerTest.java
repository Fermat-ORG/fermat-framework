package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyIdentityArtistManagerImpl;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyIdentityArtistManagerImpl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetLogManagerTest {
    @Mock
    LogManager logManager;
    @Test
    public void setLogManagerTest(){
        TokenlyIdentityArtistManagerImpl tokenlyIdentityArtistManager = Mockito.mock(TokenlyIdentityArtistManagerImpl.class);
        doCallRealMethod().when(tokenlyIdentityArtistManager).setLogManager(logManager);
    }
}
