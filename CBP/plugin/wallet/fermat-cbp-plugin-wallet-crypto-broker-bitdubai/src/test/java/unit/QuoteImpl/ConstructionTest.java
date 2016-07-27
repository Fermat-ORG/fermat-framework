package unit.QuoteImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.QuoteImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    FermatEnum merchandise = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    FiatCurrency fiatCurrency = FiatCurrency.ARGENTINE_PESO;
    float priceReference = 1f;
    float quantity = 1f;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        QuoteImpl quote = new QuoteImpl(
                this.merchandise,
                this.fiatCurrency,
                this.priceReference,
                this.quantity
        );
        assertThat(quote).isNotNull();
    }
}
