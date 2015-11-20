package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.07..
 */

public interface FactoryProject {

    public UUID getId();

    public String getName();

    public String getDeveloperPublicKey();

    public WalletCategory getWalletCategory();

    public WalletType getWalletType();

    public Map<UUID, String> getMapSkins();

    public Map<UUID, String> getMapLanguages();

}
