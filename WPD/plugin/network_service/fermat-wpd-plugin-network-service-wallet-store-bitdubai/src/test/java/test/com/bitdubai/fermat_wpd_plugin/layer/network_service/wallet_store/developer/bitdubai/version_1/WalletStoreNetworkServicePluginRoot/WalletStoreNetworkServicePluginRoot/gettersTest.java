package test.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot.WalletStoreNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.InvalidResultReturnedByDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseDao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 22/08/2015..
 */
@RunWith(MockitoJUnitRunner.class)
public class gettersTest extends TestCase {

    @Mock
    Developer developer;
    @Mock
    Skin skin;
    @Mock
    WalletStoreManager walletStoreManager;
    @Mock
    CatalogItemImpl catalogItemImpl;
    @Mock
    DetailedCatalogItemImpl detailedCatalogItemImpl;
    @Mock
    LogManager logManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    PluginFileSystem pluginFileSystem;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    Database database;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    DatabaseTableRecord databaseTablerecord;
    final char DOT = '.';
    final char SLASH = '/';
    final String CLASS_SUFFIX = ".class";
    final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    CatalogItem catalogItem;
    private WalletStoreNetworkServicePluginRoot walletStoreNetworkServicePluginRoot;
    @Mock
    WalletStoreCatalogDatabaseDao walletStoreCatalogDatabaseDao;

    private UUID testPluginId;

    @Before
    public void setUp() {
        testPluginId = UUID.randomUUID();
        walletStoreNetworkServicePluginRoot = new WalletStoreNetworkServicePluginRoot();
        walletStoreNetworkServicePluginRoot.setId(testPluginId);
        walletStoreNetworkServicePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        walletStoreNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletStoreNetworkServicePluginRoot.setErrorManager(errorManager);
        walletStoreNetworkServicePluginRoot.setLogManager(logManager);
/*        try {
            walletStoreCatalogDatabaseDao = new WalletStoreCatalogDatabaseDao(errorManager,logManager,pluginDatabaseSystem,testPluginId,testPluginId.toString());
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
        walletStoreCatalogDatabaseDao.setLogManager(logManager);
        walletStoreCatalogDatabaseDao.setPluginDatabaseSystem(pluginDatabaseSystem);
        walletStoreCatalogDatabaseDao.setErrorManager(errorManager);*/
    }

    @Test
    public void idTest() {
        walletStoreNetworkServicePluginRoot.getId();
    }

    @Ignore
    @Test
    public void publishWalletTest() {
        try {
            //when(walletStoreCatalogDatabaseDao.getCatalogItems()).thenReturn(database);
            //catalogItem = walletStoreNetworkServicePluginRoot.constructEmptyCatalogItem();
            walletStoreNetworkServicePluginRoot.publishWallet(catalogItem);
        } catch (CantPublishWalletInCatalogException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void getSkinTest() throws CantGetSkinException, InvalidResultReturnedByDatabaseException, CantExecuteDatabaseOperationException {
        //when(walletStoreCatalogDatabaseDao.getSkinFromDatabase(testPluginId)).thenReturn(skin);
        walletStoreNetworkServicePluginRoot.getSkin(testPluginId);
    }

    @Ignore
    @Test
    public void getDeveloperTest() throws CantGetDeveloperException, CantExecuteDatabaseOperationException {
        when(walletStoreCatalogDatabaseDao.getDeveloper(testPluginId)).thenReturn(developer);
        walletStoreNetworkServicePluginRoot.getDeveloper(testPluginId);
    }

    @Test
    public void generateClassesTree() throws ClassNotFoundException {

        List<Class<?>> classes = find(WalletStoreNetworkServicePluginRoot.class.getPackage().getName());

        for (String myClass : walletStoreNetworkServicePluginRoot.getClassesFullPath()) {
            /**
             * True if it exists
             */
            assertTrue(classes.contains(Class.forName(myClass)));
        }

    }

    private List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());

        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
