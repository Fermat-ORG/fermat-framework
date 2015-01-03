package com.bitdubai.smartwallet.platform.layer._9_module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.platform.layer._7_network_service.shop.version_1.Product;
import com.bitdubai.smartwallet.platform.layer._7_network_service.shop.version_1.Service;
import com.bitdubai.smartwallet.platform.layer._2_definition.money.CryptoMoney;
import com.bitdubai.smartwallet.platform.layer._2_definition.money.FiatMoney;

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
