package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDesignerException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetTranslatorException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishDesignerInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishDeveloperInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishLanguageInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishSkinInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishTranslatorInCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager extends FermatManager {
    public void publishWallet(CatalogItem catalogItem) throws CantPublishWalletInCatalogException;

    public void publishSkin(Skin skin) throws CantPublishSkinInCatalogException;

    public void publishLanguage(Language language) throws CantPublishLanguageInCatalogException;

    public void publishDesigner(Designer designer) throws CantPublishDesignerInCatalogException;

    public void publishDeveloper(Developer developer) throws CantPublishDeveloperInCatalogException;

    public void publishTranslator(Translator translator) throws CantPublishTranslatorInCatalogException, CantPublishLanguageInCatalogException;


    public WalletCatalog getWalletCatalogue() throws CantGetWalletsCatalogException;

    public CatalogItem getCatalogItem(UUID walletId) throws CantGetCatalogItemException;

    public DetailedCatalogItem getDetailedCatalogItem(UUID walletId) throws CantGetCatalogItemException;

    public Language getLanguage(UUID walletId) throws CantGetWalletLanguageException;

    public Skin getSkin(UUID walletId) throws CantGetSkinException;

    public DeveloperIdentity getDeveloper(UUID developerId) throws CantGetDeveloperException;

    public DesignerIdentity getDesigner(UUID designerId) throws CantGetDesignerException;

    public TranslatorIdentity getTranslator(UUID translatorId) throws CantGetTranslatorException;


    /**
     * Method returns an empty new instance of a CatalogItem
     *
     * @return CatalogItem
     */
    public CatalogItem constructEmptyCatalogItem();

    public Language constructLanguage(UUID languageId,
                                      Languages nameLanguage,
                                      String languageLabel,
                                      UUID walletId, //Todo: Refactor a String para que acepte PublicKey
                                      Version version,
                                      Version initialWalletVersion,
                                      Version finalWalletVersion,
                                      List<URL> videoPreviews,
                                      long languageSizeInBytes,
                                      TranslatorIdentity translator,
                                      boolean isDefault);

    public Skin constructSkin(UUID skinId,
                              String nameSkin,
                              UUID walletId, //Todo: Refactor a String para que acepte PublicKey
                              ScreenSize screenSize,
                              Version version,
                              Version initialWalletVersion,
                              Version finalWalletVersion,
                              byte[] presentationImage,
                              List<byte[]> previewImageList,
                              boolean hasVideoPreview,
                              List<URL> videoPreviews,
                              long skinSizeInBytes,
                              com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity designer,
                              boolean isDefault);

    public CatalogItem constructCatalogItem(UUID walletId, //Todo: Refactor a String para que acepte PublicKey
                                            int defaultSizeInBytes,
                                            String name, String description,
                                            WalletCategory walletCategory,
                                            byte[] icon,
                                            Version version,
                                            Version platformInitialVersion,
                                            Version platformFinalVersion,
                                            List<Skin> skins,
                                            Skin skin,
                                            Language language,
                                            DeveloperIdentity developer,
                                            List<Language> languages,
                                            URL publisherWebsiteUrl) throws CantGetWalletIconException;

}


