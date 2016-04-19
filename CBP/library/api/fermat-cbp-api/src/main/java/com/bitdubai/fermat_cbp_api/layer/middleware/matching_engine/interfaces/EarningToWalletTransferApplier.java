package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.List;


/**
 * Created by nelsonalfo on 19/04/16.
 */
public interface EarningToWalletTransferApplier {
    void applyTransference(EarningsPair earningsPair, EarningTransaction earningTransaction, String earningWalletPublicKey, String brokerWalletPublicKey) throws CantTransferEarningsToWalletException;

    Platforms getPlatform();

    void setAssociatedWallets(List<CryptoBrokerWalletAssociatedSetting> associatedWallets);
}
