package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.CryptoVaultBitcoinCurrencyPluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceStatusTest {

    @Mock
    EventManager eventManager;

    @Test
    public void pauseTest(){
        CryptoVaultBitcoinCurrencyPluginRoot root = new CryptoVaultBitcoinCurrencyPluginRoot();
        root.pause();
        org.junit.Assert.assertEquals(root.getStatus(), ServiceStatus.PAUSED);
    }

    @Test
    public void resumeTest(){
        CryptoVaultBitcoinCurrencyPluginRoot root = new CryptoVaultBitcoinCurrencyPluginRoot();
        root.resume();
        org.junit.Assert.assertEquals(root.getStatus(), ServiceStatus.STARTED);
    }
}
