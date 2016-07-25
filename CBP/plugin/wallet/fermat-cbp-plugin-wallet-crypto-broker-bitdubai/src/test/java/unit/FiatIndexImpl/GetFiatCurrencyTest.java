package unit.FiatIndexImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.FiatIndexImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetFiatCurrencyTest {

    @Test
    public void getFiatCurrency() {
        FiatIndexImpl fiatIndex = mock(FiatIndexImpl.class);
        when(fiatIndex.getFiatCurrency()).thenReturn(FiatCurrency.ARGENTINE_PESO);
        assertThat(fiatIndex.getFiatCurrency()).isNotNull();
    }

}
