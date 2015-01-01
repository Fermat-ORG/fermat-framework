package com.bitdubai.smartwallet.core.platform.layer.top.module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.shop.version_1.Product;
import com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.shop.version_1.Service;
import com.bitdubai.smartwallet.core.platform.global.definitions.money.CryptoMoney;
import com.bitdubai.smartwallet.core.platform.global.definitions.money.FiatMoney;

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
