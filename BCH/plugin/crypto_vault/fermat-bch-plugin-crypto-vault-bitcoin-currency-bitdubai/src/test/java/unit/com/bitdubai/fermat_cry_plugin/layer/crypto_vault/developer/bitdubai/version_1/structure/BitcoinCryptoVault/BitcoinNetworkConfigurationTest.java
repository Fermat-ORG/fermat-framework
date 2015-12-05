package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.10..
 */
public class BitcoinNetworkConfigurationTest {
    static final NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();

    @Test
    public void test(){

        Assert.assertEquals(NETWORK_PARAMETERS, BitcoinNetworkConfiguration.getNetworkConfiguration());
    }
}
