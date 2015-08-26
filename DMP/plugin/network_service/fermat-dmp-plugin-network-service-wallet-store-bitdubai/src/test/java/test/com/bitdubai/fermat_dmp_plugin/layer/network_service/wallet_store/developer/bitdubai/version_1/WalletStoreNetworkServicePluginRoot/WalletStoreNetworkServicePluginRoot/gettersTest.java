package test.com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot.WalletStoreNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;

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

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 22/08/2015..
 */
@RunWith(MockitoJUnitRunner.class)
public class gettersTest extends TestCase {

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
WalletStoreCatalogDatabaseDao walletStoreCatalogDatabaseDao;
    @Mock
    Database database;
    final char DOT = '.';
    final char SLASH = '/';
    final String CLASS_SUFFIX = ".class";
    final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    CatalogItem catalogItem;
    private WalletStoreNetworkServicePluginRoot walletStoreNetworkServicePluginRoot;
    private UUID testPluginId;
    private UUID languageId;
    private UUID walletId;
    private Version version;
    private Version initialWalletVersion;
    private Version finalWalletVersion;
    List<URL> videoPreviews;
    long languageSizeInBytes;

    com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator translator;

    @Before
    public void setUp() {
        testPluginId = UUID.randomUUID();
        languageId = UUID.randomUUID();
        walletId = UUID.randomUUID();
        version = new Version("1.0.0");
        initialWalletVersion = new Version("1.0.0");
        finalWalletVersion = new Version("1.0.0");
        languageSizeInBytes = 100;
        translator = new com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator();
        translator.setId(UUID.randomUUID());
        translator.setName("Traductor");
        translator.setPublicKey("SDSDFSDFskdmfskdjfsdkjf");
        //language.setTranslator(translator);
        walletStoreNetworkServicePluginRoot = new WalletStoreNetworkServicePluginRoot();
        walletStoreNetworkServicePluginRoot.setId(testPluginId);
        walletStoreNetworkServicePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        walletStoreNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletStoreNetworkServicePluginRoot.setErrorManager(errorManager);
        walletStoreNetworkServicePluginRoot.setLogManager(logManager);

    }

    @Test
    public void idTest() {
        walletStoreNetworkServicePluginRoot.getId();
    }

    @Test
    public void publishWalletTest() {
        try {
            walletStoreNetworkServicePluginRoot.publishWallet(catalogItem);
        } catch (CantPublishWalletInCatalogException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void constructEmptyCatalogItemTest()  throws CantGetWalletsCatalogException {
        //when(walletStoreCatalogDatabaseDao.getCatalogItems()).thenReturn(database);
        walletStoreNetworkServicePluginRoot.constructEmptyCatalogItem();
    }

    @Test
    public void constructLanguageTest()  throws CantGetWalletsCatalogException {
        walletStoreNetworkServicePluginRoot.constructLanguage(languageId,
                Languages.SPANISH,"Espanol",walletId,version,initialWalletVersion,finalWalletVersion,
                videoPreviews,languageSizeInBytes,translator,true);
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
