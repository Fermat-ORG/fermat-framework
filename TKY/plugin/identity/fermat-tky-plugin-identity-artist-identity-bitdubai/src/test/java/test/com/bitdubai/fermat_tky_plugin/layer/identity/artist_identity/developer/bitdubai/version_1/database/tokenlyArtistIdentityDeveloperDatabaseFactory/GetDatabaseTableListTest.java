package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
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
public class GetDatabaseTableListTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    List<DeveloperDatabaseTable> tables;
    @Test
    public void getDatabaseTableListTest() {
        TokenlyArtistIdentityDeveloperDatabaseFactory tokenlyArtistIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDeveloperDatabaseFactory.class);
        when(tokenlyArtistIdentityDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory)).thenReturn(tables);
    }
}
