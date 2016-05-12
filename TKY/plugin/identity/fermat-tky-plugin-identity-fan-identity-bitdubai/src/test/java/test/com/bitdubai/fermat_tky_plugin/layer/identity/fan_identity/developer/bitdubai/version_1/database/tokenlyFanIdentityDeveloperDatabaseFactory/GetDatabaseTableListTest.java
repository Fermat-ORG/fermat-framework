package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDeveloperDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetDatabaseTableListTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    List<DeveloperDatabaseTable> developerDatabaseTables;
    @Test
    public void getDatabaseTableListTest() {
        TokenlyFanIdentityDeveloperDatabaseFactory tokenlyFanIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyFanIdentityDeveloperDatabaseFactory.class);

        when(tokenlyFanIdentityDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory)).thenReturn(developerDatabaseTables);

    }
}
