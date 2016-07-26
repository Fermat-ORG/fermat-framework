package CryptoBrokerWalletModuleContractBasicInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleContractBasicInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by roy on 2/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private static Random random = new Random(321515131);
    private static Calendar instance = Calendar.getInstance();


    private String customerAlias = new String();
    private String merchandise = new String();
    private String typeOfPayment = new String();
    private String paymentCurrency = new String();
    private ContractStatus status = ContractStatus.CANCELLED;
    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = null;


    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoBrokerWalletModuleContractBasicInformation cryptoBrokerWalletModuleContractBasicInformation = new CryptoBrokerWalletModuleContractBasicInformation(
                this.customerAlias,
                this.merchandise,
                this.typeOfPayment,
                this.paymentCurrency,
                this.status,
                this.customerBrokerPurchaseNegotiation
        );
        assertThat(cryptoBrokerWalletModuleContractBasicInformation).isNotNull();
    }

}
