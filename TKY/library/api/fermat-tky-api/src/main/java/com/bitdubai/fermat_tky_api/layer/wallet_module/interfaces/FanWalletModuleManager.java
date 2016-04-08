package com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;

/**
 * Created by Miguel Payarez on 30/03/16.
 */
public interface FanWalletModuleManager extends ModuleManager<FanWalletPreferenceSettings, ActiveActorIdentityInformation> {
    //TODO: Implementar y Documentar

    FanWalletModule getFanWalletModule();
}
