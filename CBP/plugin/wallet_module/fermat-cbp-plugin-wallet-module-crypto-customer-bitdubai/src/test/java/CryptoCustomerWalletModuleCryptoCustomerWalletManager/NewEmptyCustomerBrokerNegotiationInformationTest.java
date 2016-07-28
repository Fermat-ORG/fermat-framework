package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewEmptyCustomerBrokerNegotiationInformationTest {
    @Test
    public void newEmptyCustomerBrokerNegotiationInformation() throws CantNewEmptyCustomerBrokerNegotiationInformationException {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class);
        when(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCustomerBrokerNegotiationInformation()).thenReturn(new CustomerBrokerNegotiationInformation() {
            @Override
            public ActorIdentity getCustomer() {
                return null;
            }

            @Override
            public ActorIdentity getBroker() {
                return null;
            }

            @Override
            public Map<ClauseType, String> getNegotiationSummary() {
                return null;
            }

            @Override
            public Map<ClauseType, ClauseInformation> getClauses() {
                return null;
            }

            @Override
            public NegotiationStatus getStatus() {
                return null;
            }

            @Override
            public String getMemo() {
                return null;
            }

            @Override
            public void setMemo(String memo) {

            }

            @Override
            public long getLastNegotiationUpdateDate() {
                return 0;
            }

            @Override
            public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {

            }

            @Override
            public long getNegotiationExpirationDate() {
                return 0;
            }

            @Override
            public void setNegotiationExpirationDate(long negotiationExpirationDate) {

            }

            @Override
            public UUID getNegotiationId() {
                return null;
            }

            @Override
            public void setCancelReason(String cancelReason) {

            }

            @Override
            public String getCancelReason() {
                return null;
            }
        });
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCustomerBrokerNegotiationInformation()).isNotNull();
    }

}
