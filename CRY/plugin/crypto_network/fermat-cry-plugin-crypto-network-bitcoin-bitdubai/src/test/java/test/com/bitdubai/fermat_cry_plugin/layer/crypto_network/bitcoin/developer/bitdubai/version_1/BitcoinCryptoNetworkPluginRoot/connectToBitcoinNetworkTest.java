package test.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.05..
 */
public class connectToBitcoinNetworkTest {

    @Test
    public void connectTest(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        try {
            root.connectToBitcoinNetwork();
        } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
            cantConnectToBitcoinNetwork.printStackTrace();
        }
    }


}
