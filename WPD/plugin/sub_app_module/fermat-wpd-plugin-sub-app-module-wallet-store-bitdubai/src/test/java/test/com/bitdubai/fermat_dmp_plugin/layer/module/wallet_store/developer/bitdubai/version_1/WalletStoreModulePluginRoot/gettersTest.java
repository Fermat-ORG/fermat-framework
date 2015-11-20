//package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
//
//import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetRefinedCatalogException;
//import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogue;
//import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
//import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletCatalog;
//import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
//import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
//import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
//import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
//import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
//
//import junit.framework.TestCase;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by rodrigo on 2015.07.07..
// */
//@RunWith(MockitoJUnitRunner.class)
//public class gettersTest extends TestCase {
//
//    @Mock
//    WalletStoreManager walletStoreManagerNetworkService;
//    @Mock
//    WalletStoreCatalogue walletStoreCatalogue;
//    @Mock
//    WalletCatalog walletCatalog;
//    @Mock
//    WalletStoreManager walletStoreManager;
//    @Mock
//    ErrorManager errorManager;
//    @Mock
//    LogManager logManager;
//    @Mock
//    com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManagerMiddleware;
//    @Mock
//    PluginDatabaseSystem pluginDatabaseSystem;
//    @Mock
//    PluginFileSystem pluginFileSystem;
//
//    final char DOT = '.';
//    final char SLASH = '/';
//    final String CLASS_SUFFIX = ".class";
//    final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
//
//
//    private WalletStoreModulePluginRoot walletStoreModulePluginRoot;
//
//    WalletStoreModuleManager walletStoreModuleManager;
//
//    @Before
//    public void setUp() {
//        walletStoreModulePluginRoot = new WalletStoreModulePluginRoot();
//        walletStoreModulePluginRoot.setWalletStoreManager(walletStoreManagerNetworkService);
//        //dealsWithWalletStoreNetworkService.setWalletStoreManager(walletStoreManagerNetworkService);
///*        walletStoreModuleManager = new WalletStoreModuleManager(errorManager,logManager,walletStoreManagerMiddleware,walletStoreManagerNetworkService);
//        walletStoreModuleManager.setErrorManager(errorManager);
//        walletStoreModuleManager.setLogManager(logManager);
//        walletStoreModuleManager.setWalletStoreManager(walletStoreManagerMiddleware);
//        walletStoreModuleManager.setWalletStoreManager(walletStoreManagerNetworkService);*/
//        //dealsWithWalletStoreNetworkService.setWalletStoreManager(walletStoreManager);
//    }
//
//    @Ignore
//    @Test
//    public void getCatalogueTest() throws CantGetRefinedCatalogException {
//        try {
//            // when(walletStoreManager.getWalletCatalogue()).thenReturn(walletCatalog);
//
//            //when(walletStoreModuleManager.getCatalogue()).thenReturn(walletStoreCatalogue);
//            //when(walletStoreManagerNetworkService).thenReturn(walletCatalog);
//            walletStoreManagerNetworkService.getWalletCatalogue();
//            walletStoreModulePluginRoot.getWalletStoreModuleManager();
//            walletStoreModulePluginRoot.getCatalogue();
//        } catch (CantGetWalletsCatalogException e) {
//            e.printStackTrace();
//        }
//        System.out.println("paso ");
////Assert.assertNotEquals(walletStoreCatalogue, walletStoreModuleManager.getCatalogue());
//
//    }
//
//    @Test
//    public void generateClassesTree() throws ClassNotFoundException {
//
//        List<Class<?>> classes = find(WalletStoreModulePluginRoot.class.getPackage().getName());
//
//        for (String myClass : walletStoreModulePluginRoot.getClassesFullPath()) {
//            /**
//             * True if it exists
//             */
//            assertTrue(classes.contains(Class.forName(myClass)));
//        }
//
//    }
//
//    private List<Class<?>> find(String scannedPackage) {
//        String scannedPath = scannedPackage.replace(DOT, SLASH);
//        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
//        if (scannedUrl == null) {
//            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
//        }
//        File scannedDir = new File(scannedUrl.getFile());
//
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        for (File file : scannedDir.listFiles()) {
//            classes.addAll(find(file, scannedPackage));
//        }
//        return classes;
//    }
//
//    private List<Class<?>> find(File file, String scannedPackage) {
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        String resource = scannedPackage + DOT + file.getName();
//        if (file.isDirectory()) {
//            for (File child : file.listFiles()) {
//                classes.addAll(find(child, resource));
//            }
//        } else if (resource.endsWith(CLASS_SUFFIX)) {
//            int endIndex = resource.length() - CLASS_SUFFIX.length();
//            String className = resource.substring(0, endIndex);
//            try {
//                classes.add(Class.forName(className));
//            } catch (ClassNotFoundException ignore) {
//            }
//        }
//        return classes;
//    }
//
//    @Test
//    public void getWalletStoreModuleManagerTest_ValidWalletNotNULL() {
//        Assert.assertNotNull(walletStoreModulePluginRoot.getWalletStoreModuleManager());
//    }
//
//
//}
