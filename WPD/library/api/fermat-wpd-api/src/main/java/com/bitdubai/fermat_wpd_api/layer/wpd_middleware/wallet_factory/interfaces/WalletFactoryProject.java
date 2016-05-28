package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_wpd_api.all_definition.enums.WalletFactoryProjectState;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {
    // project info
    String getProjectPublicKey();
    void setProjectPublicKey(String publickKey);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    WalletType getWalletType();
    void setWalletType(WalletType walletType);

    WalletCategory getWalletCategory();
    void setWalletCategory(WalletCategory walletCategory);

    FactoryProjectType getFactoryProjectType();
    void setFactoryProjectType (FactoryProjectType factoryProjectType);

    WalletFactoryProjectState getProjectState();
    void setProjectState(WalletFactoryProjectState projectState);

    Timestamp getCreationTimestamp();
    void setCreationTimestamp(Timestamp timestamp);

    Timestamp getLastModificationTimestamp();
    void setLastModificationTimeststamp(Timestamp timestamp);

    int getSize();
    void setSize(int size);



    //skin
    Skin getDefaultSkin();
    void setDefaultSkin(Skin skin);
    List<Skin> getSkins();
    void setSkins(List<Skin> skins);

    //Language getters
    Language getDefaultLanguage();
    void setDefaultLanguage(Language language);
    List<Language> getLanguages();
    void setLanguages (List<Language> languages);

    //Navigation Structure
    AppNavigationStructure getNavigationStructure();
    void setNavigationStructure (AppNavigationStructure navigationStructure);
}
