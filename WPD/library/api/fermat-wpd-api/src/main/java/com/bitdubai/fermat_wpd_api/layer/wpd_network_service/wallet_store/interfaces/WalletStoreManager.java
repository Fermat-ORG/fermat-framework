package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;
import com.bitdubai.fermat_wpd_api.all_definition.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
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

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager extends FermatManager {
    void publishWallet(CatalogItem catalogItem) throws CantPublishWalletInCatalogException;

    void publishSkin(Skin skin) throws CantPublishSkinInCatalogException;

    void publishLanguage(Language language) throws CantPublishLanguageInCatalogException;

    void publishDesigner(Designer designer) throws CantPublishDesignerInCatalogException;

    void publishDeveloper(Developer developer) throws CantPublishDeveloperInCatalogException;

    void publishTranslator(Translator translator) throws CantPublishTranslatorInCatalogException, CantPublishLanguageInCatalogException;


    WalletCatalog getWalletCatalogue() throws CantGetWalletsCatalogException;

    CatalogItem getCatalogItem(UUID walletId) throws CantGetCatalogItemException;

    DetailedCatalogItem getDetailedCatalogItem(UUID walletId) throws CantGetCatalogItemException;

    Language getLanguage(UUID walletId) throws CantGetWalletLanguageException;

    Skin getSkin(UUID walletId) throws CantGetSkinException;

    DeveloperIdentity getDeveloper(UUID developerId) throws CantGetDeveloperException;

    DesignerIdentity getDesigner(UUID designerId) throws CantGetDesignerException;

    TranslatorIdentity getTranslator(UUID translatorId) throws CantGetTranslatorException;


    /**
     * Method returns an empty new instance of a CatalogItem
     *
     * @return CatalogItem
     */
    CatalogItem constructEmptyCatalogItem();

    Language constructLanguage(UUID languageId,
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

    Skin constructSkin(UUID skinId,
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

    CatalogItem constructCatalogItem(UUID walletId, //Todo: Refactor a String para que acepte PublicKey
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


