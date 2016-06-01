package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyArtistIdentityImp;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyArtistIdentityImp;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetPluginFileSystemTest {
    @Mock
    PluginFileSystem pluginFileSystem;
    @Test
    public void setPluginFileSystemTest(){
        TokenlyArtistIdentityImp tokenlyArtistIdentityImp = Mockito.mock(TokenlyArtistIdentityImp.class);
        doCallRealMethod().when(tokenlyArtistIdentityImp).setPluginFileSystem(pluginFileSystem);
    }
}
