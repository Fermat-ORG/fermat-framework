package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetWalletContactsRegistryTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private ErrorManager mockErrorManager;
    @Mock
    private Database mockDatabase;

    private UUID testPluginId;

    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewarePluginRoot walletContactsMiddlewarePluginRoot;


    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        walletContactsMiddlewarePluginRoot = new WalletContactsMiddlewarePluginRoot();
        walletContactsMiddlewarePluginRoot.setId(testPluginId);
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
    }

    @Ignore
    @Test
    public void getWalletContactsRegistry_setValid_WalletContactsRegistry() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        //walletContactsMiddlewareDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        walletContactsMiddlewarePluginRoot.getWalletContactsRegistry();
    }
}
