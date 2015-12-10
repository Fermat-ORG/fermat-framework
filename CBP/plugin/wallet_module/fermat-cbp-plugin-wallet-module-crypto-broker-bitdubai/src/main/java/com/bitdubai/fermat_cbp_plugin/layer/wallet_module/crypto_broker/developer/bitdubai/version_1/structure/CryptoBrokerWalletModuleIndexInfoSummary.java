package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.Index;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nelson on 14/11/15.
 */
public class CryptoBrokerWalletModuleIndexInfoSummary implements IndexInfoSummary {
    private String currencyAndReferenceCurrency;
    private String salePriceAndCurrency;
    private String purchasePriceAndCurrency;

    public CryptoBrokerWalletModuleIndexInfoSummary(Index index) {
        Currency currency = index.getCurrency();
        Currency referenceCurrency = index.getReferenceCurrency();
        currencyAndReferenceCurrency = currency.getCode() + " / " + referenceCurrency.getCode();

        double purchasePrice = index.getPurchasePrice();
        NumberFormat numberFormat = DecimalFormat.getInstance();
        purchasePriceAndCurrency = currency.getCode() + " " + numberFormat.format(purchasePrice);

        double salePrice = index.getSalePrice();
        numberFormat = DecimalFormat.getInstance();
        salePriceAndCurrency = currency.getCode() + " " + numberFormat.format(salePrice);
    }

    public CryptoBrokerWalletModuleIndexInfoSummary(FermatEnum currency, FermatEnum referenceCurrency, double purchasePrice, double salePrice) {
        currencyAndReferenceCurrency = currency.getCode() + " / " + referenceCurrency.getCode();

        NumberFormat numberFormat = DecimalFormat.getInstance();
        purchasePriceAndCurrency = currency.getCode() + " " + numberFormat.format(purchasePrice);

        numberFormat = DecimalFormat.getInstance();
        salePriceAndCurrency = currency.getCode() + " " + numberFormat.format(salePrice);
    }

    @Override
    public String getCurrencyAndReferenceCurrency() {
        return currencyAndReferenceCurrency;
    }

    @Override
    public String getSalePriceAndCurrency() {
        return salePriceAndCurrency;
    }

    @Override
    public String getPurchasePriceAndCurrency() {
        return purchasePriceAndCurrency;
    }
}
