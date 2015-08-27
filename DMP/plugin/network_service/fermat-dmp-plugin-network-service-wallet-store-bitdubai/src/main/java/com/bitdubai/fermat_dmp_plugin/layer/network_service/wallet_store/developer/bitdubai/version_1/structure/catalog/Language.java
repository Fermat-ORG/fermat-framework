package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.net.URL;
import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class Language implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language {

    UUID id;
    UUID walletId;
    Languages languageName;
    String languageLabel;
    int languagePackageSizeInBytes;
    URL url;
    Version version;
    Version initialWalletVersion;
    Version finalWalletVersion;
    com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.Translator translator;
    boolean isDefault;

    /**
     * default constructor
     */
    public Language() {
    }

    /**
     * overloaded constructor
     *
     * @param id
     * @param walletId
     * @param languageName
     * @param languageLabel
     * @param languagePackageSizeInBytes
     * @param url
     * @param version
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param translator
     */
    public Language(UUID id, UUID walletId, Languages languageName, String languageLabel, int languagePackageSizeInBytes, URL url, Version version, Version initialWalletVersion, Version finalWalletVersion, Translator translator, boolean isDefault) {
        this.id = id;
        this.walletId = walletId;
        this.languageName = languageName;
        this.languageLabel = languageLabel;
        this.languagePackageSizeInBytes = languagePackageSizeInBytes;
        this.url = url;
        this.version = version;
        this.initialWalletVersion = initialWalletVersion;
        this.finalWalletVersion = finalWalletVersion;
        this.translator = translator;
        this.isDefault = isDefault;
    }

    @Override
    public UUID getLanguageId() {
        return id;
    }

    @Override
    public UUID getWalletId() {
        return walletId;
    }

    @Override
    public Languages getLanguageName() {
        return languageName;
    }

    @Override
    public String getLanguageLabel() {
        return languageLabel;
    }

    @Override
    public int getLanguagePackageSizeInBytes() {
        return languagePackageSizeInBytes;
    }


    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public Version getInitialWalletVersion() {
        return initialWalletVersion;
    }

    @Override
    public Version getFinalWalletVersion() {
        return finalWalletVersion;
    }

    @Override
    public com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.Translator getTranslator() {
        return translator;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public void setLanguageName(Languages languageName) {
        this.languageName = languageName;
    }

    public void setLanguageLabel(String languageLabel) {
        this.languageLabel = languageLabel;
    }

    public void setLanguagePackageSizeInBytes(int languagePackageSizeInBytes) {
        this.languagePackageSizeInBytes = languagePackageSizeInBytes;
    }


    public void setVersion(Version version) {
        this.version = version;
    }

    public void setInitialWalletVersion(Version initialWalletVersion) {
        this.initialWalletVersion = initialWalletVersion;
    }

    public void setFinalWalletVersion(Version finalWalletVersion) {
        this.finalWalletVersion = finalWalletVersion;
    }

    public void setTranslator(com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.Translator translator) {
        this.translator = translator;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: ");
        stringBuilder.append(this.id);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("languageName: ");
        stringBuilder.append(this.languageName);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("walletId: ");
        stringBuilder.append(this.walletId.toString());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("IsDefault: ");
        stringBuilder.append(this.isDefault);
        return stringBuilder.toString();
    }
}
