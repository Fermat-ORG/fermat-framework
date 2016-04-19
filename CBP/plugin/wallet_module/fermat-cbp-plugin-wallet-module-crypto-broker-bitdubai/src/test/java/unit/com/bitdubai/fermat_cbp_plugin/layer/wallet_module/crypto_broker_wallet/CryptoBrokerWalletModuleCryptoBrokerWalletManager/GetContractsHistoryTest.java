package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 21/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetContractsHistoryTest {

    @Mock
    CryptoBrokerWalletModuleManager walletManager;

    @Test
    public void passNullAsParameterShouldReturnAllItems() throws Exception {
        int expectedItems = 20;
        Collection<ContractBasicInformation> contractsHistory = walletManager.getContractsHistory(null, 0, 20);

        assertThat(contractsHistory).isNotNull();
        assertThat(contractsHistory).isNotEmpty();
        assertThat(contractsHistory).hasSize(expectedItems);
    }

    @Test
    public void passCompletedAsParameterShouldReturnCompletedItems() throws Exception {
        int expectedItems = 10;
        Collection<ContractBasicInformation> contractsHistory = walletManager.getContractsHistory(ContractStatus.COMPLETED, 0, 20);

        assertThat(contractsHistory).isNotNull();
        assertThat(contractsHistory).isNotEmpty();
        assertThat(contractsHistory).hasSize(expectedItems);
    }

    @Test
    public void passCanceledAsParameterShouldReturnCanceledItems() throws Exception {
        int expectedItems = 6;
        Collection<ContractBasicInformation> contractsHistory = walletManager.getContractsHistory(ContractStatus.CANCELLED, 0, 20);

        assertThat(contractsHistory).isNotNull();
        assertThat(contractsHistory).isNotEmpty();
        assertThat(contractsHistory).hasSize(expectedItems);
    }
}