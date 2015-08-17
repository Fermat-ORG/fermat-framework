package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 8/17/15.
 */
public class MiddlewareWalletFactoryProject implements WalletFactoryProject {
    String projectPublicKey;
    String name;
    String description;
    WalletType walletType;
    WalletFactoryProjectState projectState;
    Timestamp creationTimestamp;
    Timestamp lastModificationTimestamp;
    Skin defaultSkin;
    List<Skin> skins;
    Language defaultLanguage;
    List<Language> languages;
    WalletNavigationStructure navigationStructure;



    @Override
    public String getProjectPublicKey() {
        return projectPublicKey;
    }

    @Override
    public void setProjectPublickKey(String publickKey) {
        this.projectPublicKey = publickKey;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description= description;
    }

    @Override
    public WalletType getWalletType() {
        return this.walletType;
    }

    @Override
    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    @Override
    public WalletFactoryProjectState getProjectState() {
        return this.projectState;
    }

    @Override
    public void setProjectState(WalletFactoryProjectState projectState) {
        this.projectState = projectState;
    }

    @Override
    public Timestamp getCreationTimestamp() {
        return this.creationTimestamp;
    }

    @Override
    public void setCreationTimestamp(Timestamp timestamp) {
        this.creationTimestamp = timestamp;
    }

    @Override
    public Timestamp getLastModificationTimestamp() {
        return this.lastModificationTimestamp;
    }

    @Override
    public void setLastModificationTimeststamp(Timestamp timeststamp) {
        this.lastModificationTimestamp = timeststamp;
    }

    @Override
    public Skin getDefaultSkin() throws CantGetWalletFactoryProjectSkinException {
        return this.defaultSkin;
    }

    @Override
    public void setDefaultSkin(Skin skin) {
        this.defaultSkin = skin;
    }

    @Override
    public List<Skin> getSkins() throws CantGetWalletFactoryProjectSkinException {
        return this.skins;
    }

    @Override
    public Skin getEmptySkin() throws CantGetWalletFactoryProjectSkinException {
        Skin emptySkin = new Skin();
        return emptySkin;
    }

    @Override
    public void deleteSkin(Skin skin) throws CantDeleteWalletFactoryProjectSkinException {

    }

    @Override
    public Language getDefaultLanguage() throws CantGetWalletFactoryProjectLanguageException {
        return this.getDefaultLanguage();
    }

    @Override
    public void setDefaultLanguage(Language language) {
        this.defaultLanguage = language;
    }

    @Override
    public List<Language> getLanguages() throws CantGetWalletFactoryProjectLanguageException {
        return this.languages;
    }

    @Override
    public Language getEmptyLanguage() throws CantGetWalletFactoryProjectLanguageException {
        Language emptyLanguage = new Language();
        return emptyLanguage;
    }

    @Override
    public void deleteLanguage(Language language) throws CantDeleteWalletFactoryProjectLanguageException {

    }

    @Override
    public WalletNavigationStructure getNavigationStructure(UUID id) {
        return this.navigationStructure;
    }

    @Override
    public void setNavigationStructure(WalletNavigationStructure navigationStructure) {
        this.navigationStructure = navigationStructure;
    }
}
