package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinPlatformCryptoVaultPluginRoot;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 26/08/15.
 */
public class DeveloperBitDubaiTest {

    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void GetPluging() {
        assertThat(developTest.getPlugin()).isInstanceOf(BitcoinPlatformCryptoVaultPluginRoot.class);
    }
}
