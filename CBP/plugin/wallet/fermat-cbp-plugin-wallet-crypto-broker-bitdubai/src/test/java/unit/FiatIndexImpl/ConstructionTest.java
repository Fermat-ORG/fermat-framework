package unit.FiatIndexImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.FiatIndexImpl;

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
    float salePrice = 1f;
    float purchasePrice = 1f;
    float salePriceUpSpread = 1f;
    float salePriceDownSpread = 1f;
    float purchasePriceUpSpread = 1f;
    float purchasePriceDownSpread = 1f;
    float priceReference = 1f;
    FiatCurrency fiatCurrency = FiatCurrency.ARGENTINE_PESO;
    float volatility = 1f;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        FiatIndexImpl fiatIndex = new FiatIndexImpl(
                this.merchandise,
                this.salePrice,
                this.purchasePrice,
                this.salePriceUpSpread,
                this.salePriceDownSpread,
                this.purchasePriceUpSpread,
                this.purchasePriceDownSpread,
                this.priceReference,
                this.volatility,
                this.fiatCurrency
        );

        assertThat(fiatIndex).isNotNull();
    }
}
