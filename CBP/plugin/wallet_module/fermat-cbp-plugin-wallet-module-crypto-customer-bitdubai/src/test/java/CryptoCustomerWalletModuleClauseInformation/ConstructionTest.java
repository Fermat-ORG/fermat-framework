package CryptoCustomerWalletModuleClauseInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.classes.CryptoCustomerWalletModuleClauseInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 7/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private ClauseType clauseType = ClauseType.BROKER_BANK_ACCOUNT;
    private String value = new String();
    private ClauseStatus status = ClauseStatus.AGREED;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleClauseInformation cryptoCustomerWalletModuleClauseInformation = new CryptoCustomerWalletModuleClauseInformation(
                this.clauseType,
                this.value,
                this.status
        );
        assertThat(cryptoCustomerWalletModuleClauseInformation).isNotNull();
    }

}
