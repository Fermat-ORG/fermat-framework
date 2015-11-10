//package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
//
//import com.bitdubai.fermat_api.CantStartPluginException;
//import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.CREATED;
//import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.PAUSED;
//import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;
//import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STOPPED;
//
///**
// * Created by Nerio on 18/07/15.
// */
//public class serviceStatusTest {
//
//    private WalletStoreModulePluginRoot walletStoreModulePluginRoot;
//
//    @Before
//    public void setUp() {
//        walletStoreModulePluginRoot = new WalletStoreModulePluginRoot();
//    }
//
//    @Test
//    public void createdTest() {
//        Assert.assertEquals(CREATED, walletStoreModulePluginRoot.getStatus());
//    }
//
//    @Test
//    public void startedTest() throws CantStartPluginException {
//        try {
//            walletStoreModulePluginRoot.start();
//        } catch (CantStartPluginException exception) {
//            Assert.assertNotNull(exception);
//        } catch (Exception exception) {
//            Assert.assertNotNull(exception);
//        }
//        Assert.assertEquals(STARTED, walletStoreModulePluginRoot.getStatus());
//    }
//
//    @Test
//    public void pausedTest() {
//        walletStoreModulePluginRoot.pause();
//        Assert.assertEquals(PAUSED, walletStoreModulePluginRoot.getStatus());
//    }
//
//    @Test
//    public void resumeTest() {
//        walletStoreModulePluginRoot.resume();
//        Assert.assertEquals(STARTED, walletStoreModulePluginRoot.getStatus());
//    }
//
//    @Test
//    public void stopTest() {
//        walletStoreModulePluginRoot.stop();
//        Assert.assertEquals(STOPPED, walletStoreModulePluginRoot.getStatus());
//    }
//}