package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by nelsonalfo on 26/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateViewHolderTest {
    @Test
    public void test1() throws ParseException {
        final float number = 15.800f;

        final NumberFormat numberFormat = DecimalFormat.getInstance(new Locale("es"));
        final String formattedNumber = numberFormat.format(number);
        assertThat(formattedNumber).isEqualTo("15,8");

        float parsedNumber = numberFormat.parse(formattedNumber).floatValue();
        assertThat(parsedNumber).isEqualTo(number);
    }

    @Test
    public void test2() throws ParseException {
        final float number = 15.800f;
        final NumberFormat numberFormat = DecimalFormat.getInstance(new Locale("en"));

        final String formattedNumber = numberFormat.format(number);
        assertThat(formattedNumber).isEqualTo("15.8");

        float parsedNumber = numberFormat.parse(formattedNumber).floatValue();
        assertThat(parsedNumber).isEqualTo(number);


    }

    @Test
    public void test3() throws ParseException {
        final float number = 15.800f;

        final NumberFormat numberFormat = DecimalFormat.getInstance(new Locale("en"));
        final String formattedNumber = numberFormat.format(number);
        assertThat(formattedNumber).isEqualTo("15.8");

        final String bigDecimalFormat = new BigDecimal(formattedNumber).toString();
        assertThat(bigDecimalFormat).isEqualTo("15.8");

        float parsedNumber = numberFormat.parse(bigDecimalFormat).floatValue();
        assertThat(parsedNumber).isEqualTo(number);
    }

    @Test
    public void test4() throws ParseException {
        final NumberFormat enNumberFormat = DecimalFormat.getInstance(new Locale("en"));
        final NumberFormat esNumberFormat = DecimalFormat.getInstance(new Locale("es"));
        final String baseNumber = "15.8";

        float floatValue = Float.valueOf(baseNumber);
        String enFormattedNumber = enNumberFormat.format(floatValue);
        assertThat(enFormattedNumber).isEqualTo("15.8");

        floatValue = enNumberFormat.parse(enFormattedNumber).floatValue();
        String newBaseStrNumber = Float.toString(floatValue);
        assertThat(newBaseStrNumber).isEqualTo(baseNumber);


        floatValue = Float.valueOf(newBaseStrNumber);
        String esFormattedNumber = esNumberFormat.format(floatValue);
        assertThat(esFormattedNumber).isEqualTo("15,8");

        floatValue = esNumberFormat.parse(esFormattedNumber).floatValue();
        newBaseStrNumber = Float.toString(floatValue);
        assertThat(newBaseStrNumber).isEqualTo(baseNumber);
    }

}