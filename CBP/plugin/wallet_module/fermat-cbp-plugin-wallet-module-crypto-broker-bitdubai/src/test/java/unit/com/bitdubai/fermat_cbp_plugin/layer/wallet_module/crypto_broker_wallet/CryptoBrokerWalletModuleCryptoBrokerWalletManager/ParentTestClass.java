package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.mockito.Mock;

import static org.mockito.Mockito.spy;


/**
 * Created by nelsonalfo on 21/07/16.
 */
public class ParentTestClass {
    protected CryptoBrokerWalletModuleManager moduleManagerSpy;

    protected void setUp() throws Exception {
        moduleManagerSpy = spy(new CryptoBrokerWalletModuleCryptoBrokerWalletManager(
                walletManagerManager,
                cryptoBrokerWalletManager,
                bankMoneyWalletManager,
                customerBrokerSaleNegotiationManager,
                bankMoneyRestockManager,
                cashMoneyRestockManager,
                cryptoMoneyRestockManager,
                cashMoneyWalletManager,
                bankMoneyDestockManager,
                cashMoneyDestockManager,
                cryptoMoneyDestockManager,
                customerBrokerContractSaleManager,
                currencyExchangeProviderFilterManager,
                cryptoBrokerIdentityManager,
                customerBrokerUpdateManager,
                cryptoWalletManager,
                cryptoBrokerActorManager,
                customerOnlinePaymentManager,
                customerOfflinePaymentManager,
                customerAckOnlineMerchandiseManager,
                customerAckOfflineMerchandiseManager,
                brokerAckOfflinePaymentManager,
                brokerAckOnlinePaymentManager,
                brokerSubmitOfflineMerchandiseManager,
                brokerSubmitOnlineMerchandiseManager,
                intraWalletUserIdentityManager,
                matchingEngineManager,
                customerBrokerCloseManager,
                cryptoCustomerActorConnectionManager,
                pluginFileSystem,
                null
        ));
    }

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    WalletManagerManager walletManagerManager;

    @Mock
    com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @Mock
    BankMoneyWalletManager bankMoneyWalletManager;

    @Mock
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @Mock
    BankMoneyRestockManager bankMoneyRestockManager;

    @Mock
    CashMoneyRestockManager cashMoneyRestockManager;

    @Mock
    CryptoMoneyRestockManager cryptoMoneyRestockManager;

    @Mock
    CashMoneyWalletManager cashMoneyWalletManager;

    @Mock
    BankMoneyDestockManager bankMoneyDestockManager;

    @Mock
    CashMoneyDestockManager cashMoneyDestockManager;

    @Mock
    CryptoMoneyDestockManager cryptoMoneyDestockManager;

    @Mock
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @Mock
    CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;

    @Mock
    CustomerBrokerUpdateManager customerBrokerUpdateManager;

    @Mock
    CryptoWalletManager cryptoWalletManager;

    @Mock
    CryptoBrokerActorManager cryptoBrokerActorManager;

    @Mock
    CustomerOnlinePaymentManager customerOnlinePaymentManager;

    @Mock
    CustomerOfflinePaymentManager customerOfflinePaymentManager;

    @Mock
    CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;

    @Mock
    CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;

    @Mock
    BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;

    @Mock
    BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;

    @Mock
    BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;

    @Mock
    BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;

    @Mock
    MatchingEngineManager matchingEngineManager;

    @Mock
    CustomerBrokerCloseManager customerBrokerCloseManager;

    @Mock
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @Mock
    CryptoBrokerIdentityManager cryptoBrokerIdentityManager;

    @Mock
    CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;
}
