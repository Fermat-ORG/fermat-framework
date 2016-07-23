package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.CustomerBrokerSaleNegotiationPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */

public class CustomerBrokerSaleManager implements CustomerBrokerSaleNegotiationManager {

    private CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao;
    private final CustomerBrokerSaleNegotiationPluginRoot pluginRoot;
    
    /*
       Builder
    */

    public CustomerBrokerSaleManager(
            final CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao,
            final CustomerBrokerSaleNegotiationPluginRoot pluginRoot
    ) {
        this.customerBrokerSaleNegotiationDao = customerBrokerSaleNegotiationDao;
        this.pluginRoot = pluginRoot;
    }

    /*
        CustomerBrokerSaleManager Interface implementation.
    */

    @Override
    public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
        try {
            this.customerBrokerSaleNegotiationDao.createCustomerBrokerSaleNegotiation(negotiation);
        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCustomerBrokerSaleNegotiationException(e.getMessage(), e, "", "Cant Create Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.updateCustomerBrokerSaleNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.updateNegotiationNearExpirationDatetime(negotiationId, status);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.cancelNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {

            return this.customerBrokerSaleNegotiationDao.closeNegotiation(negotiation);
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }


    @Override
    public boolean closeNegotiation(UUID negotiationId) throws CantUpdateCustomerBrokerSaleException {
        try {
            System.out.print(new StringBuilder().append("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL").append("\n - NEGOTIATION SALE MANAGER closeNegotiation(").append(negotiationId).append(")\n").toString());
            return this.customerBrokerSaleNegotiationDao.closeNegotiation(negotiationId);
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    @Override
    public void sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.sendToCustomer(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.waitForBroker(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.waitForCustomer(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void waitForClosing(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.waitForClosing(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
        try {
            return this.customerBrokerSaleNegotiationDao.getNegotiations();
        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
        try {
            return this.customerBrokerSaleNegotiationDao.getNegotiationsByNegotiationId(negotiationId);
        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
        try {
            return this.customerBrokerSaleNegotiationDao.getNegotiations(status);
        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListSaleNegotiationsException {
        try {
            return this.customerBrokerSaleNegotiationDao.getNegotiationsBySendAndWaiting(actorType);
        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
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

            case BROKER_PLACE_TO_DELIVER:
                return ClauseType.BROKER_DATE_TIME_TO_DELIVER;

            default:
                throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
        }
    }

    @Override
    public ClauseType getNextClauseTypeByCurrencyType(MoneyType paymentMethod) throws CantGetNextClauseTypeException {
        switch (paymentMethod) {
            case CRYPTO:
                return ClauseType.BROKER_CRYPTO_ADDRESS;

            case BANK:
                return ClauseType.BROKER_BANK_ACCOUNT;

            case CASH_ON_HAND:
                return ClauseType.BROKER_PLACE_TO_DELIVER;

            case CASH_DELIVERY:
                return ClauseType.BROKER_PLACE_TO_DELIVER;

            default:
                throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
        }
    }

    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.createNewLocation(location, uri);
        } catch (CantCreateLocationSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateLocationSaleException(e.getMessage(), e, "", "Cant Create Location Sale");
        }
    }

    @Override
    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.updateLocation(location);
        } catch (CantUpdateLocationSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateLocationSaleException(e.getMessage(), e, "", "Cant Update Location Sale");
        }
    }

    @Override
    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
        try {
            this.customerBrokerSaleNegotiationDao.deleteLocation(location);
        } catch (CantDeleteLocationSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteLocationSaleException(e.getMessage(), e, "", "Cant Delete Location Sale");
        }
    }

    @Override
    public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException {
        try {
            return this.customerBrokerSaleNegotiationDao.getAllLocations();
        } catch (CantGetListLocationsSaleException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListLocationsSaleException(e.getMessage(), e, "", "Cant Get List Locations Sale");
        }
    }

}
