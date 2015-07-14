package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Test;

import static com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.07..
 */
public class getNetworkConfigurationTest{

    @Test
    public void regTestNet(){
        NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();
        assertEquals(NETWORK_PARAMETERS, BitcoinNetworkConfiguration.getNetworkConfiguration());
    }
}
