package com.bitdubai.wallet_platform_core.layer._11_module.wallet_runtime.developer.bitdubai.version_1.transaction;

import com.bitdubai.wallet_platform_core.layer._9_network_service.shop.version_1.Product;
import com.bitdubai.wallet_platform_core.layer._9_network_service.shop.version_1.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.money.CryptoMoney;
import com.bitdubai.wallet_platform_api.layer._1_definition.money.FiatMoney;

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
