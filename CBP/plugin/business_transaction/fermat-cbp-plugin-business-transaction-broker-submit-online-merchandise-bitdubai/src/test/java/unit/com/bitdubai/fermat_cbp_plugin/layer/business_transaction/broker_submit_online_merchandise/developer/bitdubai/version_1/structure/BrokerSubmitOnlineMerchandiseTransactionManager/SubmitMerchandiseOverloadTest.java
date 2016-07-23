package unit.com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure.BrokerSubmitOnlineMerchandiseTransactionManager;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CryptoBrokerWalletMock;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure.BrokerSubmitOnlineMerchandiseTransactionManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class SubmitMerchandiseOverloadTest {

    //I expect an exception, because the mocks are incomplete
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * This test method not implement all the interfaces needed for a correct behavior, is only for
     * test the way how this method search the wallet public key from wallet settings.
     *
     * @throws Exception
     */
    @Test
    public void submitMerchandiseOverloadMethodWithValidArguments() throws Exception {
        //This test is only to check the overload method.
        thrown.expect(Exception.class);
        CryptoBrokerWalletManager cryptoBrokerWalletManagerMock = new CryptoBrokerWalletManager() {
            @Override
            public void createCryptoBrokerWallet(String walletPublicKey) throws CantCreateCryptoBrokerWalletException {
                //Not implemented
            }

            @Override
            public CryptoBrokerWallet loadCryptoBrokerWallet(String walletPublicKey) throws CryptoBrokerWalletNotFoundException {
                return new CryptoBrokerWalletMock();
            }
        };
        BrokerSubmitOnlineMerchandiseTransactionManager brokerSubmitOnlineMerchandiseTransactionManager =
                new BrokerSubmitOnlineMerchandiseTransactionManager(null, null, null, cryptoBrokerWalletManagerMock, null);
        BigDecimal referencePrice = BigDecimal.TEN;
        brokerSubmitOnlineMerchandiseTransactionManager.submitMerchandise(referencePrice, "CBPWalletPublicKey", "ContractHash", merchandiseCurrency);

    }

}
