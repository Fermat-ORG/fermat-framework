package com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantFindProcessException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantGetItemInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantSetInstallationStatusException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDesignerException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletCatalog;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetSkinVideoPreviewException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetWalletsFromCatalogueException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartLanguageInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartSkinInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletCatalogueFilter;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreDetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreLanguage;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreSkin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by rodrigo on 7/29/15.
 */
public class WalletStoreModuleManager {

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithDeviceUser interface variables and implementation
     */
    DeviceUserManager deviceUserManager;

    /**
     * DealsWithjLogger interface member variable
     */
    LogManager logManager;


    /**
     * DealsWithWalletStoreMiddleware interface member variable
     */
    WalletStoreManager walletStoreManagerMiddleware;

    /**
     * DealsWithWalletManager interface variable and implementation
     */
    WalletManagerManager walletManagerManager;

    /**
     * DealsWithWalletStoreNetworkService interface member variable
     */
    com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager walletStoreManagerNetworkService;

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param walletStoreManagerMiddleware
     * @param walletStoreManagerNetworkService
     */
    public WalletStoreModuleManager(ErrorManager errorManager, LogManager logManager, WalletStoreManager walletStoreManagerMiddleware,
                                    com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager walletStoreManagerNetworkService,
                                    WalletManagerManager walletManagerManager) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.walletStoreManagerMiddleware = walletStoreManagerMiddleware;
        this.walletStoreManagerNetworkService = walletStoreManagerNetworkService;
        this.walletManagerManager = walletManagerManager;
    }


    private InstallationStatus getWalletInstallationStatus(CatalogItem catalogItem) throws CantGetItemInformationException {
        return walletStoreManagerMiddleware.getInstallationStatus(CatalogItems.WALLET, catalogItem.getId());
    }

    private InstallationStatus getSkinInstallationStatus(UUID skinId) throws CantGetItemInformationException {
        return walletStoreManagerMiddleware.getInstallationStatus(CatalogItems.SKIN, skinId);
    }

    private InstallationStatus getLanguageInstallationStatus(UUID languageId) throws CantGetItemInformationException {
        return walletStoreManagerMiddleware.getInstallationStatus(CatalogItems.LANGUAGE, languageId);
    }

    private WalletStoreCatalogueItem getWalletCatalogueItem(final CatalogItem catalogItem, final InstallationStatus installationStatus) {
        WalletStoreCatalogueItem walletStoreCatalogueItem = new WalletStoreCatalogueItem() {

            @Override
            public InstallationStatus getInstallationStatus() {
                return installationStatus;
            }

            @Override
            public WalletStoreDetailedCatalogItem getWalletDetailedCatalogItem() throws DatailedInformationNotFoundException {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            }

            @Override
            public UUID getId() {
                return catalogItem.getId();
            }

            @Override
            public String getName() {
                return catalogItem.getName();
            }

            @Override
            public WalletCategory getCategory() {
                return catalogItem.getCategory();
            }

            @Override
            public String getDescription() {
                return catalogItem.getDescription();
            }

            @Override
            public int getDefaultSizeInBytes() {
                return catalogItem.getDefaultSizeInBytes();
            }

            @Override
            public byte[] getIcon() throws CantGetWalletIconException {
                return catalogItem.getIcon();
            }

            @Override
            public URL getpublisherWebsiteUrl() {
                return catalogItem.getpublisherWebsiteUrl();
            }

            @Override
            public DetailedCatalogItem getDetailedCatalogItemImpl() throws CantGetWalletDetailsException {
                return catalogItem.getDetailedCatalogItemImpl();
            }
        };

        return walletStoreCatalogueItem;
    }

    private com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity getDesigner(UUID designerId) throws CantGetDesignerException {
        return walletStoreManagerNetworkService.getDesigner(designerId);
    }

    private WalletStoreSkin getWalletStoreSkin(final Skin skin, final InstallationStatus installationStatus) {
        WalletStoreSkin walletStoreSkin = new WalletStoreSkin() {
            @Override
            public InstallationStatus getInstallationStatus() {
                return installationStatus;
            }

            @Override
            public UUID getSkinId() {
                return skin.getSkinId();
            }

            @Override
            public String getSkinName() {
                return skin.getSkinName();
            }

            @Override
            public UUID getWalletId() {
                return skin.getWalletId();
            }

            @Override
            public Version getVersion() {
                return skin.getVersion();
            }

            @Override
            public Version getInitialWalletVersion() {
                return skin.getInitialWalletVersion();
            }

            @Override
            public Version getFinalWalletVersion() {
                return skin.getFinalWalletVersion();
            }

            @Override
            public byte[] getPresentationImage() throws CantGetWalletIconException {
                return skin.getPresentationImage();
            }

            @Override
            public List<byte[]> getPreviewImageList() throws CantGetWalletIconException {
                return skin.getPreviewImageList();
            }

            @Override
            public boolean hasVideoPreview() {
                return skin.hasVideoPreview();
            }

            @Override
            public List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException {
                return skin.getVideoPreviews();
            }

            @Override
            public long getSkinSizeInBytes() {
                return skin.getSkinSizeInBytes();
            }

            @Override
            public com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity getDesigner() {
                return skin.getDesigner();
            }

            @Override
            public boolean isDefault() {
                return skin.isDefault();
            }

            @Override
            public ScreenSize getScreenSize() {
                return skin.getScreenSize();
            }

        };

        return walletStoreSkin;

    }

    private WalletStoreLanguage getWalletStoreLanguage(final Language language, final InstallationStatus installationStatus) {
        WalletStoreLanguage walletStoreLanguage = new WalletStoreLanguage() {
            @Override
            public InstallationStatus getInstallationStatus() {
                return installationStatus;
            }

            @Override
            public UUID getLanguageId() {
                return language.getLanguageId();
            }

            @Override
            public UUID getWalletId() {
                return language.getWalletId();
            }

            @Override
            public Languages getLanguageName() {
                return language.getLanguageName();
            }

            @Override
            public String getLanguageLabel() {
                return language.getLanguageLabel();
            }

            @Override
            public int getLanguagePackageSizeInBytes() {
                return language.getLanguagePackageSizeInBytes();
            }

            @Override
            public Version getVersion() {
                return language.getVersion();
            }

            @Override
            public Version getInitialWalletVersion() {
                return language.getInitialWalletVersion();
            }

            @Override
            public Version getFinalWalletVersion() {
                return language.getFinalWalletVersion();
            }

            @Override
            public TranslatorIdentity getTranslator() {
                return language.getTranslator();
            }

            @Override
            public boolean isDefault() {
                return language.isDefault();
            }
        };
        return walletStoreLanguage;
    }

    private WalletStoreDetailedCatalogItem getWalletStoreDetailedCatalogItem(final DetailedCatalogItem detailedCatalogItem) throws CantGetDeveloperException {

        WalletStoreDetailedCatalogItem walletStoreDetailedCatalogItem = new WalletStoreDetailedCatalogItem() {
            @Override
            public Language getDefaultLanguage() throws CantGetLanguageException {
                return detailedCatalogItem.getDefaultLanguage();
            }

            @Override
            public List<Language> getLanguages() throws CantGetLanguagesException {
                return detailedCatalogItem.getLanguages();
            }

            @Override
            public Skin getDefaultSkin() throws CantGetSkinException {
                return detailedCatalogItem.getDefaultSkin();
            }

            @Override
            public List<Skin> getSkins() throws CantGetSkinsException {
                return detailedCatalogItem.getSkins();
            }

            @Override
            public Version getVersion() {
                return detailedCatalogItem.getVersion();
            }

            @Override
            public Version getPlatformInitialVersion() {
                return detailedCatalogItem.getPlatformInitialVersion();
            }

            @Override
            public Version getPlatformFinalVersion() {
                return detailedCatalogItem.getPlatformFinalVersion();
            }

            @Override
            public DeveloperIdentity getDeveloper() {
                return detailedCatalogItem.getDeveloper();
            }

            @Override
            public com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity getDesigner() {
                return detailedCatalogItem.getDesigner();
            }
        };

        return walletStoreDetailedCatalogItem;
    }

    private DeveloperIdentity getDeveloper(UUID developerId) throws CantGetDeveloperException {
        return walletStoreManagerNetworkService.getDeveloper(developerId);
    }


    /**
     * Puts to installing status the specified language and its wallet.
     *
     * @param walletCatalogueId
     * @param languageId
     * @throws CantStartLanguageInstallationException
     */
    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartLanguageInstallationException {
        try {
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.INSTALLING);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.INSTALLING);
            Language installingLanguage = getWalletLanguageFromWalletCatalogueId(walletCatalogueId);
            walletManagerManager.installLanguage(walletCatalogueId, languageId, installingLanguage.getLanguageName(), installingLanguage.getLanguageLabel(), installingLanguage.getVersion());
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.INSTALLED);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.INSTALLED);
        } catch (Exception exception) {
            try {
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.NOT_INSTALLED);
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.NOT_INSTALLED);
            } catch (CantSetInstallationStatusException e) {
                throw new CantStartLanguageInstallationException(CantStartLanguageInstallationException.DEFAULT_MESSAGE, e, null, null);
            }
            throw new CantStartLanguageInstallationException(CantStartLanguageInstallationException.DEFAULT_MESSAGE, exception, null, null);
        }
    }


    /**
     * Puts to installing status the specified skin and its wallet.
     *
     * @param walletCatalogueId
     * @param skinId
     * @throws CantStartSkinInstallationException
     */
    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartSkinInstallationException {
        try {
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.INSTALLING);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.INSTALLING);
            Skin installingSkin = getWalletSkinFromWalletCatalogueId(walletCatalogueId);
            //We send null preview for now.
            walletManagerManager.installSkin(walletCatalogueId, skinId, installingSkin.getSkinName(), null, installingSkin.getVersion());
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.INSTALLED);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.INSTALLED);
        } catch (Exception exception) {
            try {
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.NOT_INSTALLED);
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.NOT_INSTALLED);
            } catch (CantSetInstallationStatusException e) {
                throw new CantStartSkinInstallationException(CantStartSkinInstallationException.DEFAULT_MESSAGE, e, null, null);
            }
            throw new CantStartSkinInstallationException(CantStartSkinInstallationException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    private Language getWalletLanguageFromWalletCatalogueId(UUID walletCatalogueId) throws CantGetLanguageException {

        try {

            DetailedCatalogItem detailedCatalogItem = walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogueId);
            Logger LOG = Logger.getGlobal();
            LOG.info("MAP_detailedCatalogItem:" + detailedCatalogItem);
            return detailedCatalogItem.getDefaultLanguage();

        } catch (CantGetCatalogItemException exception) {
            throw new CantGetLanguageException(CantGetCatalogItemException.DEFAULT_MESSAGE, exception, "Cannot get the wallet language", "Please, check the cause");
        }

    }

    private Skin getWalletSkinFromWalletCatalogueId(UUID walletCatalogueId) throws CantGetSkinException {

        DetailedCatalogItem detailedCatalogItem = null;
        try {
            detailedCatalogItem = walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogueId);
            return detailedCatalogItem.getDefaultSkin();
        } catch (CantGetCatalogItemException exception) {
            throw new CantGetSkinException(CantGetCatalogItemException.DEFAULT_MESSAGE, exception, "Cannot get the wallet Skin", "Please, check the cause");
        }

    }

    private String checkDeveloperAlias(DetailedCatalogItem detailedCatalogItem){
        String developerAlias="DefaultDeveloperAlias";
        if(detailedCatalogItem.getDeveloper()!=null){
            developerAlias=detailedCatalogItem.getDeveloper().getAlias();
        }
        return developerAlias;
    }

    private Languages checkLanguages(Languages languages){
        Languages checkingLanguages=languages;
        if(languages==null){
            checkingLanguages=Languages.AMERICAN_ENGLISH;
        }
        return checkingLanguages;
    }

    /**
     * start the installation of the passed wallet.
     *
     * @param walletCategory
     * @param skinId
     * @param languageId
     * @param walletCatalogueId
     * @param version
     * @throws CantStartInstallationException
     */
    public void installWallet(WalletCategory walletCategory, UUID skinId, UUID languageId, UUID walletCatalogueId, Version version) throws CantStartInstallationException {
        try {
            Logger LOG = Logger.getGlobal();

            LOG.info("MAP_CATALOGUE:"+walletCatalogueId);
            LOG.info("MAP_WMNS:"+walletStoreManagerNetworkService);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.INSTALLING);
            CatalogItem catalogItem = walletStoreManagerNetworkService.getCatalogItem(walletCatalogueId);
            DetailedCatalogItem detailedCatalogItem = walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogueId);
            LOG.info("MAP_WMM:"+walletManagerManager);
            WalletInstallationProcess walletInstallationProcess = walletManagerManager.installWallet(walletCategory, walletCatalogueId.toString());
            LOG.info("MAP_DUM:"+deviceUserManager);
            //DeviceUser deviceUser = deviceUserManager.getLoggedInDeviceUser();
            Skin skin = getWalletSkinFromWalletCatalogueId(walletCatalogueId);
            Language language = getWalletLanguageFromWalletCatalogueId(walletCatalogueId);
            //TODO: when we fix the publisher, delete this, please.
            Languages languageName=checkLanguages(language.getLanguageName());
            String developerAlias=checkDeveloperAlias(detailedCatalogItem);

            /*
            For now, we'll pass null to the  walletPrivateKey, walletIconName, skinPreview method arguments
            TODO: Get the real values for this null objects.
            */

            LOG.info("MAP_STORE_MODULE:"+walletInstallationProcess);
            LOG.info("MAP_NAME:"+catalogItem.getName());
            LOG.info("MAP_ID:"+catalogItem.getId());
            LOG.info("MAP_VERSION:"+detailedCatalogItem.getVersion());
            LOG.info("MAP_SCREENS:"+skin.getScreenSize().getCode());
            LOG.info("MAP_SKIN_VERSION:"+skin.getVersion());
            LOG.info("MAP_SKIN_NAME:"+skin.getSkinName());
            LOG.info("MAP_LANGUAGE_VERSION:"+language.getVersion());
            LOG.info("MAP_LANGUAGE_NAME:"+language.getLanguageName());
            LOG.info("MAP_FIX_LANGUAGE_NAME:"+languageName);
            LOG.info("MAP_LANGUAGE_LABEL:"+language.getLanguageLabel());
            LOG.info("MAP_DEVELOPER_ALIAS:"+developerAlias);

            LOG.info("MAP_VERSION:"+version);
            walletInstallationProcess.startInstallation(WalletType.NICHE, catalogItem.getName(),
                    catalogItem.getId().toString(), null, /*deviceUser.getPublicKey()*/"testPublicKey", null,
                    walletCatalogueId, detailedCatalogItem.getVersion(), skin.getScreenSize().getCode(),
                    skinId, skin.getVersion(), skin.getSkinName(), null, languageId,
                    language.getVersion(), /*language.getLanguageName()*/languageName, language.getLanguageLabel(),
                    /*detailedCatalogItem.getDeveloper().getAlias()*/developerAlias, version.toString()/*"1.0.0"*/);
        } catch (CantSetInstallationStatusException exception) {
            throw new CantStartInstallationException(CantSetInstallationStatusException.DEFAULT_MESSAGE, exception, "Cannot set the instalation status", "Please, check the cause");
        } catch (CantGetCatalogItemException exception) {
            throw new CantStartInstallationException(CantGetCatalogItemException.DEFAULT_MESSAGE, exception, "Cannot get the catalog items", "Please, check the cause");
        } catch (CantFindProcessException exception) {
            throw new CantStartInstallationException(CantFindProcessException.DEFAULT_MESSAGE, exception, "Cannot get the WalletInstallationProcess", "Please, check the cause");
        } /*catch (CantGetLoggedInDeviceUserException exception) {
            throw new CantStartInstallationException(CantGetLoggedInDeviceUserException.DEFAULT_MESSAGE, exception, "Cannot get the Device user", "Please, check the cause");
        }*/ catch (CantGetSkinException exception) {
            throw new CantStartInstallationException(CantGetSkinException.DEFAULT_MESSAGE, exception, "Cannot get the wallet Skin", "Please, check the cause");
        } catch (CantGetLanguageException exception) {
            throw new CantStartInstallationException(CantGetLanguageException.DEFAULT_MESSAGE, exception, "Cannot get the wallet language", "Please, check the cause");
        } catch (CantInstallWalletException exception) {
            throw new CantStartInstallationException(CantStartInstallationException.DEFAULT_MESSAGE, exception, "Trying to install a new wallet", "Please, check the cause");
        } catch (Exception exception) {
            throw new CantStartInstallationException(CantStartInstallationException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to install a new wallet", "Please, check the cause");
        }
    }


    /**
     * unisntall the specified Language
     *
     * @param walletCatalogueId
     * @param languageId
     * @throws CantStartUninstallLanguageException
     */
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallLanguageException {
        try {
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.UNINSTALLING);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.UNINSTALLING);
            walletManagerManager.uninstallLanguage(walletCatalogueId, languageId);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.UNINSTALLED);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.UNINSTALLED);
        } catch (Exception exception) {
            try {
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.LANGUAGE, languageId, InstallationStatus.NOT_UNINSTALLED);
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.NOT_UNINSTALLED);
            } catch (CantSetInstallationStatusException e) {
                throw new CantStartUninstallLanguageException(CantStartUninstallLanguageException.DEFAULT_MESSAGE, e, null, null);
            }
            throw new CantStartUninstallLanguageException(CantStartUninstallLanguageException.DEFAULT_MESSAGE, exception, null, null);
        }
    }


    /**
     * uninstall the specified skin
     *
     * @param walletCatalogueId
     * @param skinId
     * @throws CantStartUninstallSkinException
     */
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallSkinException {
        try {
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.UNINSTALLING);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.UNINSTALLING);
            walletManagerManager.uninstallSkin(walletCatalogueId, skinId);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.UNINSTALLED);
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.UNINSTALLED);

        } catch (Exception exception) {
            try {
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.SKIN, skinId, InstallationStatus.NOT_UNINSTALLED);
                walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.NOT_UNINSTALLED);
            } catch (CantSetInstallationStatusException e) {
                throw new CantStartUninstallSkinException(CantStartUninstallSkinException.DEFAULT_MESSAGE, e, null, null);
            }
            throw new CantStartUninstallSkinException(CantStartUninstallSkinException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * unisntall the specified wallet
     *
     * @param walletCatalogueId
     * @throws CantStartUninstallWalletException
     */
    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException {
        try {
            walletStoreManagerMiddleware.setInstallationStatus(CatalogItems.WALLET, walletCatalogueId, InstallationStatus.UNINSTALLING);
        } catch (Exception exception) {
            throw new CantStartUninstallWalletException(CantStartUninstallWalletException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * returns the WalletStore Catalag
     *
     * @return
     * @throws CantGetRefinedCatalogException
     */
    public WalletStoreCatalogue getCatalogue() throws CantGetRefinedCatalogException {
        try {
            final List<WalletStoreCatalogueItem> walletStoreCatalogueItemList = new ArrayList<WalletStoreCatalogueItem>();
            WalletCatalog walletCatalog = walletStoreManagerNetworkService.getWalletCatalogue();

            for (CatalogItem catalogItem : walletCatalog.getWalletCatalog(0, 0)) {

                InstallationStatus installationStatus;
                try {
                    installationStatus = getWalletInstallationStatus(catalogItem);
                } catch (Exception e) {
                    installationStatus = InstallationStatus.NOT_INSTALLED;
                }
                WalletStoreCatalogueItem walletStoreCatalogueItem = getWalletCatalogueItem(catalogItem, installationStatus);
                walletStoreCatalogueItemList.add(walletStoreCatalogueItem);
            }

            WalletStoreCatalogue walletStoreCatalogue = new WalletStoreCatalogue() {
                @Override
                public List<WalletStoreCatalogueItem> getWalletCatalogue(int offset, int top) throws CantGetWalletsFromCatalogueException {
                    return walletStoreCatalogueItemList;
                }

                @Override
                public void addFilter(WalletCatalogueFilter walletFilter) {

                }

                @Override
                public void clearFilters() {

                }
            };
            System.out.println("walletStoreCatalogue: " + walletStoreCatalogue);

            return walletStoreCatalogue;
        } catch (Exception exception) {
            throw new CantGetRefinedCatalogException(CantGetRefinedCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }

    }

    /**
     * Gets the WalletStoreDetailed CAtalogItem object for the passes wallet
     *
     * @param walletCatalogId
     * @return
     * @throws CantGetWalletsCatalogException
     */
    public WalletStoreDetailedCatalogItem getCatalogItemDetails(UUID walletCatalogId) throws CantGetWalletsCatalogException {
        try {
            return getWalletStoreDetailedCatalogItem(walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogId));
        } catch (Exception exception) {
            throw new CantGetWalletsCatalogException(CantGetWalletsCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }
    }


}
