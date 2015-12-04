package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 12-10-2015.
 * Update by Angel on 30/11/2015
 */
public class CustomerBrokerSaleNegotiationPluginRoot extends AbstractPlugin implements CustomerBrokerSaleNegotiationManager, DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao;

    /*
        Builder
    */

        public CustomerBrokerSaleNegotiationPluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    /*
        Plugin Interface implementation.
    */

        @Override
        public void start() throws CantStartPluginException {
            this.serviceStatus = ServiceStatus.STARTED;
            try {
                this.customerBrokerSaleNegotiationDao = new CustomerBrokerSaleNegotiationDao(pluginDatabaseSystem, pluginId);
                this.customerBrokerSaleNegotiationDao.initializeDatabase();
            } catch (CantInitializeCustomerBrokerSaleNegotiationDatabaseException e) {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException();
            }
        }

    /*
        DatabaseManagerForDevelopers Interface implementation.
    */

        @Override
        public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
            CustomerBrokerSaleNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerSaleNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
            CustomerBrokerSaleNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerSaleNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
            try {
                CustomerBrokerSaleNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerSaleNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                dbFactory.initializeDatabase();
                return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
            } catch (CantInitializeCustomerBrokerSaleNegotiationDatabaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
            return new ArrayList<>();
        }

    /*
        CustomerBrokerSaleManager Interface implementation.
    */

        @Override
        public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
            this.customerBrokerSaleNegotiationDao.createCustomerBrokerSaleNegotiation(negotiation);
        }

        @Override
        public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            this.customerBrokerSaleNegotiationDao.updateCustomerBrokerSaleNegotiation(negotiation);
        }

        @Override
        public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            this.customerBrokerSaleNegotiationDao.cancelNegotiation(negotiation);
        }

        @Override
        public boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            try {
                if(verifyStatusClause(negotiation.getClauses())) {
                    this.customerBrokerSaleNegotiationDao.closeNegotiation(negotiation);
                    return true;
                }
                return false;
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            this.customerBrokerSaleNegotiationDao.sendToBroker(negotiation);
        }

        @Override
        public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
            this.customerBrokerSaleNegotiationDao.waitForBroker(negotiation);
        }

        @Override
        public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
            return this.customerBrokerSaleNegotiationDao.getNegotiations();
        }

        @Override
        public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByContractId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
            return this.customerBrokerSaleNegotiationDao.getNegotiationsByContractId(negotiationId);
        }

        @Override
        public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
            return this.customerBrokerSaleNegotiationDao.getNegotiations(status);
        }

        @Override
        public ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException {
            switch (type) {
                case BROKER_CURRENCY:
                    return ClauseType.EXCHANGE_RATE;

                case EXCHANGE_RATE:
                    return ClauseType.BROKER_CURRENCY_QUANTITY;

                case BROKER_CURRENCY_QUANTITY:
                    return ClauseType.BROKER_PAYMENT_METHOD;

                case BROKER_BANK:
                    return ClauseType.BROKER_BANK_ACCOUNT;

                case PLACE_TO_MEET:
                    return ClauseType.DATE_TIME_TO_MEET;

                case BROKER_PLACE_TO_DELIVER:
                    return ClauseType.BROKER_DATE_TIME_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        @Override
        public ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException {
            switch (paymentMethod) {
                case CRYPTO_MONEY:
                    return ClauseType.BROKER_CRYPTO_ADDRESS;

                case BANK_MONEY:
                    return ClauseType.BROKER_BANK;

                case CASH_ON_HAND_MONEY:
                    return ClauseType.PLACE_TO_MEET;

                case CASH_DELIVERY_MONEY:
                    return ClauseType.BROKER_PLACE_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

    /*
    *   Private Methods
    * */

        private boolean verifyStatusClause(Collection<Clause> clausules) throws CantUpdateCustomerBrokerSaleException {
            Map<ClauseType, String> clausesAgreed = new HashMap<ClauseType, String>();

            for(Clause clause : clausules) {
                if(clause.getStatus() == ClauseStatus.AGREED){
                    clausesAgreed.put(clause.getType(), clause.getValue());
                }
            }

            if(
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_CURRENCY) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.EXCHANGE_RATE) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.BROKER_CURRENCY_QUANTITY) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.BROKER_PAYMENT_METHOD) )
                    ){
                return false;
            }

            if( clausesAgreed.containsValue(CurrencyType.CRYPTO_MONEY.getCode()) ){
                if( !clausesAgreed.containsKey(ClauseType.BROKER_CRYPTO_ADDRESS) ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(CurrencyType.BANK_MONEY.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.BROKER_BANK) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.BROKER_BANK_ACCOUNT) )
                        ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(CurrencyType.CASH_ON_HAND_MONEY.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.PLACE_TO_MEET) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.DATE_TIME_TO_MEET) )
                        ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(CurrencyType.CASH_DELIVERY_MONEY.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.BROKER_PLACE_TO_DELIVER) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.BROKER_DATE_TIME_TO_DELIVER) )
                        ){
                    return false;
                }
            }

            return true;
        }
}
