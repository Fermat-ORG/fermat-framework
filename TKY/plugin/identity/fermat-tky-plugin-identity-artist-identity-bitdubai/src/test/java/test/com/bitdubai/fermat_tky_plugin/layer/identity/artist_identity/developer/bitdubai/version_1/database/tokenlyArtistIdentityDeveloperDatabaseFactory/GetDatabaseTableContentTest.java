package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
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
public class GetDatabaseTableContentTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabaseTable developerDatabaseTable;
    @Mock
    List<DeveloperDatabaseTableRecord> returnedRecords;
    @Test
    public void getDatabaseTableContentTest() {
        TokenlyArtistIdentityDeveloperDatabaseFactory tokenlyArtistIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyArtistIdentityDeveloperDatabaseFactory.class);
        when(tokenlyArtistIdentityDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory,
                                                                                    developerDatabaseTable)).thenReturn(returnedRecords);
    }
}
