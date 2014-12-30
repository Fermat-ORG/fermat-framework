package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.transaction;

import com.bitdubai.smartwallet.core.world.commerce.shop.Product;
import com.bitdubai.smartwallet.core.world.commerce.shop.Service;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.CryptoMoney;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.middleware.money.FiatMoney;

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
