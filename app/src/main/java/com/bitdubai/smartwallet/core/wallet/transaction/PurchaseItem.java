package com.bitdubai.smartwallet.core.wallet.transaction;

import com.bitdubai.smartwallet.core.shop.Product;
import com.bitdubai.smartwallet.core.shop.Service;
import com.bitdubai.smartwallet.core.system.money.CryptoMoney;
import com.bitdubai.smartwallet.core.system.money.FiatMoney;

/**
 * Created by ciencias on 22.12.14.
 */
public class PurchaseItem {
    private Product mProduct;
    private Service mService;
    private int mQuantity;
    private FiatMoney mUnitFiatQuotedPrice;
    private CryptoMoney mUnitCryptoQuotedPrice;
}
