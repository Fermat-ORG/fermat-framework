package unit.QuoteImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.QuoteImpl;

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
public class GetMerchandiseTest {

    @Test
    public void getMerchandise() {
        QuoteImpl quote = mock(QuoteImpl.class);
        when(quote.getMerchandise()).thenReturn(FiatCurrency.ARGENTINE_PESO);
        assertThat(quote.getMerchandise()).isNotNull();
    }

}
