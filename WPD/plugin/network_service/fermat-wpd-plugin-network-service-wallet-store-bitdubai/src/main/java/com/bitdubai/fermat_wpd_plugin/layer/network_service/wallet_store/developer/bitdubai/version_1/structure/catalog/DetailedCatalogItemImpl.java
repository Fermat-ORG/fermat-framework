package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;


import java.util.List;

/**
 * Created by rodrigo on 7/23/15.
 */
public class DetailedCatalogItemImpl implements DetailedCatalogItem {

    Language language;
    List<Language> languages;
    Skin defaultSkin;
    List<Skin> skins;
    Version version;
    Version platformInitialVersion;
    Version platformFinalVersion;
    DeveloperIdentity developer;
    Designer designer;

    /**
     * Default constructor
     */
    public DetailedCatalogItemImpl() {
    }

    /**
     * Overloaded constructor
     *
     * @param language
     * @param languages
     * @param defaultSkin
     * @param skins
     * @param version
     * @param platformInitialVersion
     * @param platformFinalVersion
     */
    public DetailedCatalogItemImpl(Language language, List<Language> languages, Skin defaultSkin, List<Skin> skins, Version version, Version platformInitialVersion, Version platformFinalVersion, DeveloperIdentity developer, Designer designer) {
        this.language = language;
        this.languages = languages;
        this.defaultSkin = defaultSkin;
        this.skins = skins;
        this.version = version;
        this.platformInitialVersion = platformInitialVersion;
        this.platformFinalVersion = platformFinalVersion;
        this.developer = developer;
        this.designer = designer;
    }

    @Override
    public Language getDefaultLanguage() throws CantGetLanguageException {
        return language;
    }

    @Override
    public List<Language> getLanguages() throws CantGetLanguagesException {
        return languages;
    }

    @Override
    public Skin getDefaultSkin() throws CantGetSkinException {
        return defaultSkin;
    }

    @Override
    public List<Skin> getSkins() throws CantGetSkinsException {
        return skins;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public Version getPlatformInitialVersion() {
        return platformInitialVersion;
    }

    @Override
    public Version getPlatformFinalVersion() {
        return platformFinalVersion;
    }

    @Override
    public DeveloperIdentity getDeveloper() {
        return developer;
    }

    @Override
    public Designer getDesigner() {
        return designer;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public void setDefaultSkin(Skin defaultSkin) {
        this.defaultSkin = defaultSkin;
    }

    public void setSkins(List<Skin> skins) {
        this.skins = skins;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setPlatformInitialVersion(Version platformInitialVersion) {
        this.platformInitialVersion = platformInitialVersion;
    }

    public void setPlatformFinalVersion(Version platformFinalVersion) {
        this.platformFinalVersion = platformFinalVersion;
    }

    public void setDeveloper(DeveloperIdentity developer) {
        this.developer = developer;
    }


}
