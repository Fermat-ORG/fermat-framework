package com.bitdubai.smartwallet.core.transaction;

import com.bitdubai.smartwallet.core.shop.Product;
import com.bitdubai.smartwallet.core.shop.Service;

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
