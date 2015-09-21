package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginDatabaseSystem;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class getCryptoStatusTest {
    @Mock
    ErrorManager errorManager;

    @Mock
    EventManager eventManager;

    MockedPluginDatabaseSystem mockedPluginDatabaseSystem;

    @Test
    public void test() throws CantCreateDatabaseException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        mockedPluginDatabaseSystem = new MockedPluginDatabaseSystem();
        Database database = mockedPluginDatabaseSystem.createDatabase(UUID.randomUUID(), "testok");

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.getCryptoStatus("test");
    }
}
