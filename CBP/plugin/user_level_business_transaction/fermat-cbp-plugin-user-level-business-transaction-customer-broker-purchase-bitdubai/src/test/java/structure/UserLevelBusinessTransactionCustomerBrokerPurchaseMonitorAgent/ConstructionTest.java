package structure.UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database.UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure.UserLevelBusinessTransactionCustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure.events.UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent;
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
    @Mock
    private EventManager eventManager;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao databaseDao;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerPurchaseDatabaseDao databaseConnectionsDao;
    private UserLevelBusinessTransactionCustomerBrokerPurchaseManager userLevelBusinessTransactionCustomerBrokerPurchaseManager;

    @Mock
    private UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot pluginRoot;
    private UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent testMonitorAgent;
    private UUID testId;
    private OpenContractManager openContractManager;
    private CloseContractManager closeContractManager;
    private FiatIndexManager fiatIndexManager;
    private NotificationManagerMiddleware notificationManagerMiddleware;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;


    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        ECCKeyPair identity = new ECCKeyPair();
        testMonitorAgent = new UserLevelBusinessTransactionCustomerBrokerPurchaseMonitorAgent(
                errorManager,
                customerBrokerPurchaseNegotiationManager,
                pluginDatabaseSystem,
                testId,
                openContractManager,
                closeContractManager,
                customerBrokerContractPurchaseManager,
                fiatIndexManager,
                notificationManagerMiddleware,
                userLevelBusinessTransactionCustomerBrokerPurchaseManager

        );
        assertThat(testMonitorAgent).isNotNull();
    }
}