package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanIdentityManagerModule;

import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class FanIdentityManager implements TokenlyFanIdentityManagerModule{
    private final ErrorManager errorManager;
    private final TokenlyFanIdentityManager tokenlyFanIdentityManager;

    public FanIdentityManager(ErrorManager errorManager,TokenlyFanIdentityManager tokenlyFanIdentityManager) {
        this.errorManager = errorManager;
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
    }

    @Override
    public Fan createFanIdentity(String alias, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        return tokenlyFanIdentityManager.createFanIdentity(
                alias,
                profileImage,
                externalUserName,
                externalAccessToken,
                externalPlatform);
    }

    @Override
    public void updateFanIdentity(String alias, UUID id, String publicKey, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {
        tokenlyFanIdentityManager.updateFanIdentity(
                alias,
                id,
                publicKey,
                profileImage,
                externalUserName,
                externalAccessToken,
                externalPlatform);
    }

    @Override
    public Fan getFanIdentity(UUID publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return tokenlyFanIdentityManager.getFanIdentity(publicKey);
    }
}
