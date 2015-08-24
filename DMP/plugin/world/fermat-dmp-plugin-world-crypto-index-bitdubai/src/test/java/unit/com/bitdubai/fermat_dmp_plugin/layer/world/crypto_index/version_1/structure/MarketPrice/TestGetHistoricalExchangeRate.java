package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.MarketPrice;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 21/08/15.
 */
public class TestGetHistoricalExchangeRate {

    MarketPrice marketPrice = new MarketPrice();
    private String url="http://api.cryptocoincharts.info/tradingPair/btc_usd";
    private double marketExchangeRate=0;
    private CryptoCurrency c=null;
    private FiatCurrency f=null;
    private long time;
    private String x=null;

    public void setValue() throws InvalidParameterException {
        c= CryptoCurrency.getByCode(c.getByCode("BTC").getCode().toString());
        f= FiatCurrency.getByCode(f.getByCode("USD").getCode().toString());
    }
    @Test
    public void getHistoricalExchangeRateTest() throws InvalidParameterException {
        setValue();
       marketExchangeRate=marketPrice.getHistoricalExchangeRate(c,f,time);
        Assertions.assertThat(marketExchangeRate).isNotEqualTo(null);
    }
}
