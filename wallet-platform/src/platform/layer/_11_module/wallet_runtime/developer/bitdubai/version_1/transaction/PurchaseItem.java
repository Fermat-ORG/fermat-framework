package platform.layer._11_module.wallet_runtime.developer.bitdubai.version_1.transaction;

import platform.layer._9_network_service.shop.version_1.Product;
import platform.layer._9_network_service.shop.version_1.Service;
import platform.layer._1_definition.money.CryptoMoney;
import platform.layer._1_definition.money.FiatMoney;

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
