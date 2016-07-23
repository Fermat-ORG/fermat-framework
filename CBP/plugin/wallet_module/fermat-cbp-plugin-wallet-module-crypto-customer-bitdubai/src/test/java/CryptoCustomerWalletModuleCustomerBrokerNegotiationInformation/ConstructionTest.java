package CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private static final Random random = new Random(321515131);
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private static final Calendar caLendar = Calendar.getInstance();

    String brokerAlias = new String();
    String merchandise = new String();
    String paymentMethod = new String();
    String paymentCurrency = new String();
    NegotiationStatus status = NegotiationStatus.WAITING_FOR_BROKER;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = new CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
                this.brokerAlias,
                this.merchandise,
                this.paymentMethod,
                this.paymentCurrency,
                this.status
        );
        assertThat(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation).isNotNull();
    }

}
