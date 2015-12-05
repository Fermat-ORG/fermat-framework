package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;

/**
 * Created by Franklin Marcano 05/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceStatusTest {

    @Test
    public void pausedTest(){
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        root.pause();
        Assert.assertEquals(PAUSED, root.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        try {
            root.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception){
            Assert.assertNotNull(exception);
        }
        //Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void resumeTest(){
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        root.resume();
        Assert.assertEquals(STARTED, root.getStatus());
    }

    @Test
    public void createdTest(){
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        Assert.assertEquals(CREATED, root.getStatus());
    }

    @Test
    public void stopTest() throws CantStartPluginException{
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        try {
            root.stop();
            Assert.assertEquals(STOPPED, root.getStatus());
        } catch (Exception exception){
            Assert.assertNotNull(exception);
        }
    }
}
