package com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by memo on 04/12/15.
 */
public interface BankMoneyWalletModuleManager extends ModuleManager<FermatSettings, ActorIdentityInformation> {

    BankingWallet getBankingWallet();
}
