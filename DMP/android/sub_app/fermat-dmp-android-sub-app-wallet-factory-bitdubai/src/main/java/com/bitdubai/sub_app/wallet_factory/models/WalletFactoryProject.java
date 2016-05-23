package com.bitdubai.sub_app.wallet_factory.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.all_definition.enums.WalletFactoryProjectState;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Wallet Factory Project Module
 */
public class WalletFactoryProject implements com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject {

    private String projectPublicKey;
    private String name;
    private String description;
    private WalletType walletType;
    private WalletCategory walletCategory;
    private FactoryProjectType projectType;
    private WalletFactoryProjectState projectState;
    private Timestamp creationTimeStamp;
    private Timestamp lastModificationTimeStamp;
    private int size;

    private Skin defaultSkin;
    private ArrayList<Skin> skins;

    private Language defaultLanguage;
    private ArrayList<Language> languages;

    private AppNavigationStructure navStructure;


    @Override
    public String getProjectPublicKey() {
        return projectPublicKey;
    }

    @Override
    public void setProjectPublicKey(String publicKey) {
        this.projectPublicKey = publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public WalletType getWalletType() {
        return walletType;
    }

    @Override
    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    @Override
    public WalletCategory getWalletCategory() {
        return walletCategory;
    }

    @Override
    public void setWalletCategory(WalletCategory walletCategory) {
        this.walletCategory = walletCategory;
    }

    @Override
    public FactoryProjectType getFactoryProjectType() {
        return projectType;
    }

    @Override
    public void setFactoryProjectType(FactoryProjectType factoryProjectType) {
        this.projectType = factoryProjectType;
    }

    @Override
    public WalletFactoryProjectState getProjectState() {
        return projectState;
    }

    @Override
    public void setProjectState(WalletFactoryProjectState projectState) {
        this.projectState = projectState;
    }

    @Override
    public Timestamp getCreationTimestamp() {
        return creationTimeStamp;
    }

    @Override
    public void setCreationTimestamp(Timestamp timestamp) {
        this.creationTimeStamp = timestamp;
    }

    @Override
    public Timestamp getLastModificationTimestamp() {
        return lastModificationTimeStamp;
    }

    @Override
    public void setLastModificationTimeststamp(Timestamp timestamp) {
        this.lastModificationTimeStamp = timestamp;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Skin getDefaultSkin() {
        return defaultSkin;
    }

    @Override
    public void setDefaultSkin(Skin skin) {
        this.defaultSkin = skin;
    }

    @Override
    public List<Skin> getSkins() {
        return skins;
    }

    @Override
    public void setSkins(List<Skin> skins) {
        this.skins = (ArrayList<Skin>) skins;
    }

    @Override
    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    @Override
    public void setDefaultLanguage(Language language) {
        this.defaultLanguage = language;
    }

    @Override
    public List<Language> getLanguages() {
        return languages;
    }

    @Override
    public void setLanguages(List<Language> languages) {
        this.languages = (ArrayList<Language>) languages;
    }

    @Override
    public AppNavigationStructure getNavigationStructure() {
        return navStructure;
    }

    @Override
    public void setNavigationStructure(AppNavigationStructure navigationStructure) {
        this.navStructure = navigationStructure;
    }
}
