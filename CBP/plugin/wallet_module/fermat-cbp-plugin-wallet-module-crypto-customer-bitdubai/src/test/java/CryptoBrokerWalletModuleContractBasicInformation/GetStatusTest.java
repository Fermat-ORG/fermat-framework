package CryptoBrokerWalletModuleContractBasicInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleContractBasicInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 5/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetStatusTest {
    @Test
    public void getStatus() {
        CryptoBrokerWalletModuleContractBasicInformation cryptoBrokerWalletModuleContractBasicInformation = mock(CryptoBrokerWalletModuleContractBasicInformation.class);
        when(cryptoBrokerWalletModuleContractBasicInformation.getStatus()).thenReturn(ContractStatus.CANCELLED);
        assertThat(cryptoBrokerWalletModuleContractBasicInformation.getStatus()).isNotNull();
    }
}
