package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.07..
 */

public interface FactoryProject {

    UUID getId();

    String getName();

    String getDeveloperPublicKey();

    WalletCategory getWalletCategory();

    WalletType getWalletType();

    Map<UUID, String> getMapSkins();

    Map<UUID, String> getMapLanguages();

}
