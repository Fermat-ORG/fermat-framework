package CryptoCustomerWalletModuleClauseInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleClauseInformation;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 7/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetValueTest {
    @Test
    public void getValue(){
        CryptoCustomerWalletModuleClauseInformation cryptoCustomerWalletModuleClauseInformation = mock(CryptoCustomerWalletModuleClauseInformation.class);
        when(cryptoCustomerWalletModuleClauseInformation.getValue()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleClauseInformation.getValue()).isNotNull();
    }
}
