package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import org.junit.Assert;
import org.junit.Test;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;

/**
 * Created by rodrigo on 2015.07.14..
 * Edited by Nerio on 2015-07-23
 */
public class serviceStatusTest {

    @Test
    public void pausedTest(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        try {
            root.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception){
            Assert.assertNotNull(exception);
        }
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void resumeTest(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        root.stop();
        Assert.assertEquals(STOPPED, root.getStatus());
    }
}
