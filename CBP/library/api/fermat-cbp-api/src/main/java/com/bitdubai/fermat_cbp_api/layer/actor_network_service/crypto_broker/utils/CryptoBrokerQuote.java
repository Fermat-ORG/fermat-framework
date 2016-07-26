package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;


/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote</code>
 * contain the information about quotes.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/02/2016.
 */
public final class CryptoBrokerQuote implements CryptoBrokerInfo {

    private final Currency merchandise;
    private final Currency paymentCurrency;
    private final Float price;
    private String supportedPlatforms;

    public CryptoBrokerQuote(final Currency merchandise,
                             final Currency paymentCurrency,
                             final Float price) {

        this.merchandise = merchandise;
        this.paymentCurrency = paymentCurrency;
        this.price = price;
    }

    public CryptoBrokerQuote(final Currency merchandise,
                             final Currency paymentCurrency,
                             final Float price,
                             final String supportedPlatforms) {

        this.merchandise = merchandise;
        this.paymentCurrency = paymentCurrency;
        this.price = price;
        this.supportedPlatforms = supportedPlatforms;
    }

    public CryptoBrokerQuote(Quote quote) {
        this.merchandise = (Currency) quote.getMerchandise();
        this.paymentCurrency = quote.getFiatCurrency();
        this.price = quote.getPriceReference();
    }

    public final Currency getMerchandise() {
        return merchandise;
    }

    public final Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    public final Float getPrice() {
        return price;
    }

    public final String getSupportedPlatforms() {
        return supportedPlatforms;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoBrokerQuote{")
                .append("merchandise=").append(merchandise)
                .append(", paymentCurrency=").append(paymentCurrency)
                .append(", price=").append(price)
                .append(", supportedPlatforms=").append(supportedPlatforms)
                .append('}').toString();
    }

}
