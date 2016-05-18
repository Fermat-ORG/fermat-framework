package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.tokenlyArtistIdentityPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class GetDatabaseTableListTest {
    @Mock
    List<DeveloperDatabaseTable> developerDatabaseTables;
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabase developerDatabase;

    @Test
    public void getDatabaseTableListTest(){
        TokenlyArtistIdentityPluginRoot tokenlyArtistIdentityPluginRoot = Mockito.mock(TokenlyArtistIdentityPluginRoot.class);
        when(tokenlyArtistIdentityPluginRoot.getDatabaseTableList(developerObjectFactory,
                                                                    developerDatabase)).thenReturn(developerDatabaseTables);
    }
}
