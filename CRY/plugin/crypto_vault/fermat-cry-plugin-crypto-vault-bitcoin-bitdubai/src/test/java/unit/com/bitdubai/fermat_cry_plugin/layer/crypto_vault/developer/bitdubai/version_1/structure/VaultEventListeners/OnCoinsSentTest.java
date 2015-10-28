package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by rodrigo on 2015.07.16..
 */
@RunWith(MockitoJUnitRunner.class)
public class OnCoinsSentTest {
    @Mock
    EventManager eventManager;

    @Mock
    LogManager logManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    Database database;

    @Mock
    Wallet wallet;

    @Mock
    Transaction tx;

    @Test
    public void raiseEventTest(){
        VaultEventListeners eventListeners = new VaultEventListeners(database,errorManager, eventManager, logManager);
        eventListeners.setEventManager(eventManager);
        eventListeners.setErrorManager(errorManager);
        eventListeners.setLogManager(logManager);

        eventListeners.onCoinsSent(wallet, tx, Coin.CENT, Coin.COIN);
    }
}
