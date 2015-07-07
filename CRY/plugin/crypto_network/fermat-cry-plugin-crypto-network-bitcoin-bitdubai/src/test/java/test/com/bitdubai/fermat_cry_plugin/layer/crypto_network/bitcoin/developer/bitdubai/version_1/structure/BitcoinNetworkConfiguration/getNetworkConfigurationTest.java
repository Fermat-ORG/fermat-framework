package test.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.07..
 */
public class getNetworkConfigurationTest {
    static final NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();

    @Test
    public void test(){

        assertEquals(NETWORK_PARAMETERS, BitcoinNetworkConfiguration.getNetworkConfiguration());
    }
}
