package unit.FiatIndexImpl;

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
public class GetSalePurchaseUpSpreadTest {

    @Test
    public void getSalePriceUpSpread() {
        FiatIndexImpl fiatIndex = mock(FiatIndexImpl.class);
        when(fiatIndex.getSalePriceUpSpread()).thenReturn(1f);
        assertThat(fiatIndex.getSalePriceUpSpread()).isNotNull();
    }

}
