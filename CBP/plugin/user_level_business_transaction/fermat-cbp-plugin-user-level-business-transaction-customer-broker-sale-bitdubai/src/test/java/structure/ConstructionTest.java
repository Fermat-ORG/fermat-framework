package structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.UserLevelBusinessTransactionCustomerBrokerSalePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure.UserLevelBusinessTransactionCustomerBrokerSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure.events.UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Lozadaa on 23.10.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent testMonitorAgent;

    @Mock
    private EventManager eventManager;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao databaseDao;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao databaseConnectionsDao;
    private UserLevelBusinessTransactionCustomerBrokerSaleManager userLevelBusinessTransactionCustomerBrokerPurchaseManager;
    private Broadcaster broadcaster;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerSalePluginRoot pluginRoot;
    private UUID testId;
    private OpenContractManager openContractManager;
    private CloseContractManager closeContractManager;
    private CurrencyExchangeProviderFilterManager fiatIndexManager;
    private NotificationManagerMiddleware notificationManagerMiddleware;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private CryptoMoneyRestockManager cryptoMoneyRestockManager;
    private BankMoneyRestockManager bankMoneyRestockManager;
    private CashMoneyRestockManager cashMoneyRestockManager;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        ECCKeyPair identity = new ECCKeyPair();
        testMonitorAgent = new UserLevelBusinessTransactionCustomerBrokerSaleMonitorAgent(
                errorManager,
                customerBrokerSaleNegotiationManager,
                pluginDatabaseSystem,
                UUID.randomUUID(),
                openContractManager,
                closeContractManager,
                customerBrokerContractSaleManager,
                fiatIndexManager,
                cryptoBrokerWalletManager,
                bankMoneyRestockManager,
                cashMoneyRestockManager,
                cryptoMoneyRestockManager,
                notificationManagerMiddleware,
                userLevelBusinessTransactionCustomerBrokerPurchaseManager,
                broadcaster
        );
        assertThat(testMonitorAgent).isNotNull();
    }
}