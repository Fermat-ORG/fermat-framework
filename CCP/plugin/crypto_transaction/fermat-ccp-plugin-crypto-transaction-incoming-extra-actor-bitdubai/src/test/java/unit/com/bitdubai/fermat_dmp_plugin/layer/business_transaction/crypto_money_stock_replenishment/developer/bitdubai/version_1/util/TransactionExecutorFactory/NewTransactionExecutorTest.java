//package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
//<<<<<<< HEAD:CCP/plugin/transaction/fermat-ccp-plugin-transaction-incoming-extra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/business_transaction/crypto_money_stock_replenishment/developer/bitdubai/version_1/util/TransactionExecutorFactory/NewTransactionExecutorTest.java
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:DMP/plugin/transaction/fermat-dmp-plugin-transaction-incoming-extra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/transaction/incoming_extra_user/developer/bitdubai/version_1/util/TransactionExecutorFactory/NewTransactionExecutorTest.java
//import CryptoWalletManager;
//import CryptoWalletWallet;
//import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
//import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
//import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors.CryptoBasicWalletTransactionExecutor;
//import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.fest.assertions.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static com.googlecode.catchexception.CatchException.*;
///**
// * Created by jorgegonzalez on 2015.07.08..
// */
//@RunWith(MockitoJUnitRunner.class)
//public class NewTransactionExecutorTest {
//
//    @Mock
//    private ActorAddressBookManager mockActorAddressBookManager;
//    @Mock
//    private CryptoWalletManager mockBitcoinWalletManager;
//    @Mock
//    private CryptoWalletWallet mockBitcoinWallet;
//
//    private TransactionExecutorFactory testExecutorFactory;
//    private TransactionExecutor testExecutor;
//
//
//    @Test
//    public void newTransactionExecutor_PlatformWalletTypeNotSupported_TransactionExecutorCreated() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenReturn(mockBitcoinWallet);
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.COMPOSITE_WALLET_MULTI_ACCOUNT, "replace_by_wallet_public_key");
//        assertThat(testExecutor).isNull();
//    }
//
//    @Test
//    public void newTransactionExecutor_WalletRecognizedByManager_TransactionExecutorCreated() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenReturn(mockBitcoinWallet);
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, "replace_by_wallet_public_key");
//        assertThat(testExecutor)
//                .isNotNull()
//                .isInstanceOf(CryptoBasicWalletTransactionExecutor.class);
//    }
//
//    @Test
//    public void newTransactionExecutor_WalletNotRecognizedByManager_ThrowsCantLoadWalletException() throws Exception{
//        when(mockBitcoinWalletManager.loadWallet(anyString())).thenThrow(new CantLoadWalletException("MOCK", null, null, null));
//
//        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
//        catchException(testExecutorFactory).newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, "replace_by_wallet_public_key");
//
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantLoadWalletException.class);
//    }
//
//}
