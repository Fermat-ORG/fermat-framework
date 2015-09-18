package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.MarketPrice;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by francisco on 21/08/15.
 */
public class GetMarketPriceTest {

    MarketPrice marketPrice = new MarketPrice();
    private double marketExchangeRate=0;
    private CryptoCurrency c=null;
    private FiatCurrency f=null;
    private long time;

    @Before
    public void setValue() throws InvalidParameterException {
        c= CryptoCurrency.getByCode("BTC");
        f= FiatCurrency.getByCode("USD");
    }
    @Test
    public void TestGetMarketPrice() throws Exception{
        marketExchangeRate=marketPrice.getMarketPrice(f,c,time);
        System.out.println("El precio: " + marketExchangeRate);
        Assertions.assertThat(marketExchangeRate).isNotEqualTo(null);
    }
}
