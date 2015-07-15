package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.junit.Assert;
import org.junit.Test;

import static com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.07..
 */
public class getNetworkConfigurationTest implements BitcoinManager{

    @Test
    public void defaultNetTest(){
        NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();
        assertEquals(NETWORK_PARAMETERS, BitcoinNetworkConfiguration.getNetworkConfiguration(null));
    }

    @Test
    public void constructorTest() {
        BitcoinNetworkConfiguration bitcoinNetworkConfiguration = new BitcoinNetworkConfiguration();

        BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.REGTEST_NET);
    }

    @Test
    public void regTestNetTest() {
        NetworkParameters networkParameters = RegTestParams.get();
        Assert.assertEquals(BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.REGTEST_NET), networkParameters);
    }

    @Test
    public void mainTestNetTest() {
        NetworkParameters networkParameters = MainNetParams.get();
        Assert.assertEquals(BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.MAIN_NET), networkParameters);
    }

    @Test
    public void testNetTest() {
        NetworkParameters networkParameters = TestNet3Params.get();
        Assert.assertEquals(BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.Test3_NET), networkParameters);
    }

    @Test
    public void fermatTestNetTest() {
        Assert.assertNull(BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.FERMAT_TEST));
    }

    @Test
    public void fermatMaintNetTest() {
        Assert.assertNull(BitcoinNetworkConfiguration.getNetworkConfiguration(BitcoinNetworkUsed.FERMAT_MAIN));
    }
}
