package test.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot.WalletStoreNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetSkinVideoPreviewException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItemImpl;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 26/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class constructTest extends TestCase {

    @Mock
    Skin skin;
    @Mock
    Designer designer;
    @Mock
    Language language;
    @Mock
    Developer developer;
    @Mock
    Translator translator;
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

    private WalletStoreNetworkServicePluginRoot walletStoreNetworkServicePluginRoot;

    private UUID testPluginId;
    List<URL> videoPreviews;
    long languageSizeInBytes;

    @Before
    public void setUp() {
        testPluginId = UUID.randomUUID();
        walletStoreNetworkServicePluginRoot = new WalletStoreNetworkServicePluginRoot();
        walletStoreNetworkServicePluginRoot.setId(testPluginId);
        walletStoreNetworkServicePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        walletStoreNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletStoreNetworkServicePluginRoot.setErrorManager(errorManager);
        walletStoreNetworkServicePluginRoot.setLogManager(logManager);
    }


    @Test
    public void constructEmptyCatalogItemTest() throws CantGetWalletsCatalogException {
        walletStoreNetworkServicePluginRoot.constructEmptyCatalogItem();
    }

    @Test
    public void constructLanguageTest() throws CantGetWalletsCatalogException {
        walletStoreNetworkServicePluginRoot.constructLanguage(language.getLanguageId(),
                Languages.SPANISH, "Espanol", testPluginId, language.getVersion(),
                language.getInitialWalletVersion(), language.getFinalWalletVersion(),
                videoPreviews, languageSizeInBytes, translator, true);
    }

    @Test
    public void constructSkinTest() throws CantGetWalletIconException, CantGetSkinVideoPreviewException {
        walletStoreNetworkServicePluginRoot.constructSkin(testPluginId,
                "Espanol", testPluginId, ScreenSize.MEDIUM, skin.getVersion(),
                skin.getInitialWalletVersion(), skin.getFinalWalletVersion(),
                skin.getPresentationImage(), skin.getPreviewImageList(), false,
                skin.getVideoPreviews(), skin.getSkinSizeInBytes(), skin.getDesigner(), true);

    }

    @Test
    public void constructCatalogItemTest() throws CantGetWalletIconException, CantGetLanguagesException, CantGetSkinsException {
        walletStoreNetworkServicePluginRoot.constructCatalogItem(testPluginId,
                catalogItemImpl.getDefaultSizeInBytes(), catalogItemImpl.getName(),
                catalogItemImpl.getDescription(), catalogItemImpl.getCategory(),
                catalogItemImpl.getIcon(), detailedCatalogItemImpl.getVersion(),
                detailedCatalogItemImpl.getPlatformInitialVersion(),
                detailedCatalogItemImpl.getPlatformFinalVersion(),
                detailedCatalogItemImpl.getSkins(), skin, language, developer,
                detailedCatalogItemImpl.getLanguages(), catalogItemImpl.getpublisherWebsiteUrl());
    }
}
