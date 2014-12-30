package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.Discount;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.MoneySource;

/**
 * Created by ciencias on 21.12.14.
 */
public  class InSystemMoneyOut extends FiatCryptoWithSystemUser {

    private MoneySource[] mMoneySource;
    private Discount mDiscount;
}
