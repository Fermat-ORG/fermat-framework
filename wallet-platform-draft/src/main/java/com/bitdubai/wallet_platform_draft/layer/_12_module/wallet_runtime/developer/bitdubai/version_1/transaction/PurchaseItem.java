package com.bitdubai.wallet_platform_draft.layer._12_module.wallet_runtime.developer.bitdubai.version_1.transaction;

import com.bitdubai.wallet_platform_api.layer._1_definition.money.CryptoMoney;
import com.bitdubai.wallet_platform_api.layer._1_definition.money.FiatMoney;

/**
 * Created by ciencias on 22.12.14.
 */
public class PurchaseItem {
    private int mQuantity;
    private FiatMoney mUnitFiatQuotedPrice;
    private CryptoMoney mUnitCryptoQuotedPrice;
}
