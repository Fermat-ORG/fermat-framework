package com.bitdubai.smartwallet.core.system.wallet.transaction;

import com.bitdubai.smartwallet.core.system.money.Discount;
import com.bitdubai.smartwallet.core.system.money.MoneySource;

/**
 * Created by ciencias on 21.12.14.
 */
public  class InSystemMoneyOut extends FiatCryptoWithSystemUser {

    private MoneySource[] mMoneySource;
    private Discount mDiscount;
}
