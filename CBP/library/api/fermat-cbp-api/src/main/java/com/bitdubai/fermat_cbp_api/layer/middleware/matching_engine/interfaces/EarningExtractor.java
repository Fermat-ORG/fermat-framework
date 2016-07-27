package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.util.List;


/**
 * Created by nelsonalfo on 19/04/16.
 */
public interface EarningExtractor {
    void applyEarningExtraction(EarningsPair earningsPair, float amount, String earningWalletPublicKey, String brokerWalletPublicKey, long fee, FeeOrigin feeOrigin) throws CantExtractEarningsException;

    Platforms getPlatform();

    void setAssociatedWallets(List<CryptoBrokerWalletAssociatedSetting> associatedWallets);
}
