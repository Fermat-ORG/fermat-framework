package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructorAndSettersTest {
    @Mock
    PluginDatabaseSystem databaseSystem;

    @Mock
    ErrorManager errorManager;

    @Mock
    EventManager eventManager;

    @Mock
    Database database;

    @Test
    public void constructorTest(){
        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);

    }

    @Test
    public void settersTest(){
        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);
        cryptoVaultDatabaseActions.setErrorManager(errorManager);
        cryptoVaultDatabaseActions.setEventManager(eventManager);
        cryptoVaultDatabaseActions.setVault(new Wallet(TestNet3Params.get()));
    }
}
