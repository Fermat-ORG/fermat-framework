package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by nelsonalfo on 20/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CryptoBrokerIdentityExtraDataTest {

    private CryptoBrokerIdentityExtraData extraData;
    private String jsonString;


    @Test
    public void testToJson() throws Exception {

        extraData = new CryptoBrokerIdentityExtraData(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, "Selling Bitcoin");
        jsonString = extraData.toJson();
        assertThat(jsonString).isEqualTo("{" +
                "\"merchandise\":{\"type\":\"CRYPTO\",\"code\":\"BTC\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"extraText\":\"Selling Bitcoin\"" +
                "}");

        extraData = new CryptoBrokerIdentityExtraData(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, "Merchandises: BTC, USD");
        jsonString = extraData.toJson();
        assertThat(jsonString).isEqualTo("{" +
                "\"merchandise\":{\"type\":\"CRYPTO\",\"code\":\"BTC\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"extraText\":\"Merchandises: BTC, USD\"" +
                "}");

        extraData = new CryptoBrokerIdentityExtraData(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR, "Merchandises: USD, VEF");
        jsonString = extraData.toJson();
        assertThat(jsonString).isEqualTo("{" +
                "\"merchandise\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"VEF\"}," +
                "\"extraText\":\"Merchandises: USD, VEF\"" +
                "}");

        extraData = new CryptoBrokerIdentityExtraData(null, null, "Selling Bitcoin");
        jsonString = extraData.toJson();
        assertThat(jsonString).isEqualTo("{" +
                "\"extraText\":\"Selling Bitcoin\"" +
                "}");
    }

    @Test
    public void testFromJson() throws Exception {

        jsonString = "{" +
                "\"merchandise\":{\"type\":\"CRYPTO\",\"code\":\"BTC\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"extraText\":\"Selling Bitcoin\"" +
                "}";
        extraData = CryptoBrokerIdentityExtraData.fromJson(jsonString);
        assertThat(extraData).isNotNull();
        assertThat(extraData.getMerchandise()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(extraData.getPaymentCurrency()).isEqualTo(FiatCurrency.US_DOLLAR);
        assertThat(extraData.getExtraText()).isEqualTo("Selling Bitcoin");

        jsonString = "{" +
                "\"merchandise\":{\"type\":\"CRYPTO\",\"code\":\"BTC\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"extraText\":\"Merchandises: BTC, USD\"" +
                "}";
        extraData = CryptoBrokerIdentityExtraData.fromJson(jsonString);
        assertThat(extraData).isNotNull();
        assertThat(extraData.getMerchandise()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(extraData.getPaymentCurrency()).isEqualTo(FiatCurrency.US_DOLLAR);
        assertThat(extraData.getExtraText()).isEqualTo("Merchandises: BTC, USD");

        jsonString = "{" +
                "\"merchandise\":{\"type\":\"FIAT\",\"code\":\"USD\"}," +
                "\"paymentCurrency\":{\"type\":\"FIAT\",\"code\":\"VEF\"}," +
                "\"extraText\":\"Merchandises: USD, VEF\"" +
                "}";
        extraData = CryptoBrokerIdentityExtraData.fromJson(jsonString);
        assertThat(extraData).isNotNull();
        assertThat(extraData.getMerchandise()).isEqualTo(FiatCurrency.US_DOLLAR);
        assertThat(extraData.getPaymentCurrency()).isEqualTo(FiatCurrency.VENEZUELAN_BOLIVAR);
        assertThat(extraData.getExtraText()).isEqualTo("Merchandises: USD, VEF");

        jsonString = "{" +
                "\"merchandise\":\"BITCOIN\"," +
                "\"paymentCurrency\":\"US_DOLLAR\"," +
                "\"extraText\":\"Selling Bitcoin\"" +
                "}";
        extraData = CryptoBrokerIdentityExtraData.fromJson(jsonString);
        assertThat(extraData).isNull();

        jsonString = "{\"extraText\":\"Merchandises: USD, VEF\"}";
        extraData = CryptoBrokerIdentityExtraData.fromJson(jsonString);
        assertThat(extraData).isNotNull();
        assertThat(extraData.getMerchandise()).isNull();
        assertThat(extraData.getPaymentCurrency()).isNull();
        assertThat(extraData.getExtraText()).isEqualTo("Merchandises: USD, VEF");
    }
}