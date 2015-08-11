package com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.FactoryProject;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.07..
 */

public class Project implements FactoryProject {


    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private WalletCategory walletCategory;

    private WalletType walletType;

    private String developerPublicKey;

    private Map<UUID,String> mapSkins;

    private Map<UUID,String> mapLanguages;

    public Project(UUID id, String name, String developerPublicKey, Map<UUID, String> mapSkins, Map<UUID, String> mapLanguages) {
        this.id = id;
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.mapSkins = mapSkins;
        this.mapLanguages = mapLanguages;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeveloperPublicKey() {
        return developerPublicKey;
    }

    public WalletCategory getWalletCategory() {
        return walletCategory;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public Map<UUID, String> getMapSkins() {
        return mapSkins;
    }

    public Map<UUID, String> getMapLanguages() {
        return mapLanguages;
    }


}
