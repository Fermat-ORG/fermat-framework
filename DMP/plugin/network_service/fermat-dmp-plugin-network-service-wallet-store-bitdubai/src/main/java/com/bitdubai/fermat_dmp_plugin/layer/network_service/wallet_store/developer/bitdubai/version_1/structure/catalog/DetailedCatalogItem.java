package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;


import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class DetailedCatalogItem implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem {

    Language language;
    List<Language> languages;
    Skin defaultSkin;
    List<Skin> skins;
    Version version;
    Version platformInitialVersion;
    Version platformFinalVersion;
    UUID developerId;

    /**
     * Default constructor
     */
    public DetailedCatalogItem() {
    }

    /**
     * Overloaded constructor
     * @param language
     * @param languages
     * @param defaultSkin
     * @param skins
     * @param version
     * @param platformInitialVersion
     * @param platformFinalVersion
     */
    public DetailedCatalogItem(Language language, List<Language> languages, Skin defaultSkin, List<Skin> skins, Version version, Version platformInitialVersion, Version platformFinalVersion, UUID developerId) {
        this.language = language;
        this.languages = languages;
        this.defaultSkin = defaultSkin;
        this.skins = skins;
        this.version = version;
        this.platformInitialVersion = platformInitialVersion;
        this.platformFinalVersion = platformFinalVersion;
        this.developerId = developerId;
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
    public UUID getDeveloperId() {
        return developerId;
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

    public void setDeveloperId(UUID developerId) {
        this.developerId = developerId;
    }
}
