package CryptoCustomerWalletModuleClausesImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleClausesImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 7/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private final UUID clauseId = UUID.randomUUID();
    private final ClauseType type = ClauseType.BROKER_CRYPTO_ADDRESS;
    private final String value = new String();
    private final ClauseStatus status = ClauseStatus.AGREED;
    private final String proposedBy = new String();
    private final short indexOrder = 12;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleClausesImpl cryptoCustomerWalletModuleClauses = new CryptoCustomerWalletModuleClausesImpl(
                this.clauseId,
                this.type,
                this.value,
                this.status,
                this.proposedBy,
                this.indexOrder
        );
        assertThat(cryptoCustomerWalletModuleClauses).isNotNull();
    }
}
