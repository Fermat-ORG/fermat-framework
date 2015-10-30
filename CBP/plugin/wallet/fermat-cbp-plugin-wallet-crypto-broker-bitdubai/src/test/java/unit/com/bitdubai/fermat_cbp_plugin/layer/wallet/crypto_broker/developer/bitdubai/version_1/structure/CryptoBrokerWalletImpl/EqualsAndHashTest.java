package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerStockTransactionRecordImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 23.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class EqualsAndHashTest {

    private static final String TEST_PRIVATE_KEY = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
    private static final String TEST_PUBLIC_KEY = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";

    private UUID transactionId = UUID.randomUUID();
    private KeyPair testkeyPairWallet;
    private KeyPair testkeyPairBroker;
    private KeyPair testkeyPairCustomer;
    private BalanceType balanceType = BalanceType.BOOK;
    private TransactionType transactionType = TransactionType.DEBIT;
    private float amount = 300;
    private CurrencyType currencyType = CurrencyType.CRYPTO_MONEY;
    private float runningBookBalance = 3000;
    private float runningAvailableBalance = 3000;
    private long timeStamp = 0;
    private String memo = "TEST";

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    private CryptoBrokerStockTransactionRecord testIdentity1, testIdentity2;

    @Before
    public void setUpWallet() {
        testkeyPairWallet = AsymmetricCryptography.createKeyPair(TEST_PRIVATE_KEY);
        testkeyPairBroker = AsymmetricCryptography.createKeyPair(TEST_PRIVATE_KEY);
        testkeyPairCustomer = AsymmetricCryptography.createKeyPair(TEST_PRIVATE_KEY);
        testIdentity1 = new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                testkeyPairWallet,
                testkeyPairBroker,
                testkeyPairCustomer,
                balanceType,
                transactionType,
                currencyType,
                amount,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo
        );
    }

    @Test
    public void Equals_SameValues_True() {
        testIdentity2 = new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                testkeyPairWallet,
                testkeyPairBroker,
                testkeyPairCustomer,
                balanceType,
                transactionType,
                currencyType,
                amount,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo
        );
        assertThat(testIdentity1).isEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isEqualTo(testIdentity2.hashCode());
    }

    @Test
    public void Equals_DifferentKeyPairWallet_False() {
        testIdentity2 = new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                AsymmetricCryptography.generateECCKeyPair(),
                testkeyPairBroker,
                testkeyPairCustomer,
                balanceType,
                transactionType,
                currencyType,
                amount,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo
        );
        assertThat(testIdentity1).isNotEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isNotEqualTo(testIdentity2.hashCode());
    }

    @Test
    public void Equals_DifferentKeyPairBroker_False() {
        testIdentity2 = new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                testkeyPairWallet,
                AsymmetricCryptography.generateECCKeyPair(),
                testkeyPairCustomer,
                balanceType,
                transactionType,
                currencyType,
                amount,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo
        );
        assertThat(testIdentity1).isNotEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isNotEqualTo(testIdentity2.hashCode());
    }

}