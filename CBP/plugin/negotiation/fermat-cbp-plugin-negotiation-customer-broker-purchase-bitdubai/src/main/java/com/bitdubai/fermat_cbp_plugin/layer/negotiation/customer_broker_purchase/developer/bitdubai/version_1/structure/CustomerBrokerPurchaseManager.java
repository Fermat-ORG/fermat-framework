package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */

public class CustomerBrokerPurchaseManager implements CustomerBrokerPurchaseNegotiationManager {

    private final CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    /*
       Builder
    */

        public CustomerBrokerPurchaseManager(
            final CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
        ){
            this.customerBrokerPurchaseNegotiationDao = customerBrokerPurchaseNegotiationDao;
            this.errorManager = errorManager;
            this.pluginVersionReference = pluginVersionReference;
        }

    /*
        CustomerBrokerPurchaseManager Interface implementation.
    */

        @Override
        public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.createCustomerBrokerPurchaseNegotiation(negotiation);
            } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Create Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.updateCustomerBrokerPurchaseNegotiation(negotiation);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.updateNegotiationNearExpirationDatetime(negotiationId, status);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.cancelNegotiation(negotiation);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                return this.customerBrokerPurchaseNegotiationDao.closeNegotiation(negotiation);
            } catch (Exception e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.sendToBroker(negotiation);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException{
            try{
                this.customerBrokerPurchaseNegotiationDao.waitForBroker(negotiation);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public void waitForCustomer(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try{
                this.customerBrokerPurchaseNegotiationDao.waitForCustomer(negotiation);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(e.getMessage(), e, "", "Cant Update Customer Broker Purchase Negotiation");
            }
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getNegotiations();
            } catch (CantGetListPurchaseNegotiationsException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListPurchaseNegotiationsException(e.getMessage(), e, "", "Cant Get List Purchase Negotiations");
            }
        }

        @Override
        public CustomerBrokerPurchaseNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getNegotiationsByNegotiationId(negotiationId);
            } catch (CantGetListPurchaseNegotiationsException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListPurchaseNegotiationsException(e.getMessage(), e, "", "Cant Get List Purchase Negotiations");
            }
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getNegotiations(status);
            } catch (CantGetListPurchaseNegotiationsException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListPurchaseNegotiationsException(e.getMessage(), e, "", "Cant Get List Purchase Negotiations");
            }
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListPurchaseNegotiationsException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getNegotiationsBySendAndWaiting(actorType);
            } catch (CantGetListPurchaseNegotiationsException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListPurchaseNegotiationsException(e.getMessage(), e, "", "Cant Get List Purchase Negotiations");
            }
        }

        @Override
        public ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException {
            switch (type) {
                case CUSTOMER_CURRENCY:
                    return ClauseType.EXCHANGE_RATE;

                case EXCHANGE_RATE:
                    return ClauseType.CUSTOMER_CURRENCY_QUANTITY;

                case CUSTOMER_CURRENCY_QUANTITY:
                    return ClauseType.CUSTOMER_PAYMENT_METHOD;

                case CUSTOMER_PLACE_TO_DELIVER:
                    return ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        @Override
        public ClauseType getNextClauseTypeByCurrencyType(MoneyType paymentMethod) throws CantGetNextClauseTypeException {
            switch (paymentMethod) {
                case CRYPTO:
                    return ClauseType.CUSTOMER_CRYPTO_ADDRESS;

                case BANK:
                    return ClauseType.BROKER_BANK_ACCOUNT;

                case CASH_ON_HAND:
                    return ClauseType.CUSTOMER_PLACE_TO_DELIVER;

                case CASH_DELIVERY:
                    return ClauseType.CUSTOMER_PLACE_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        @Override
        public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.createNewLocation(location, uri);
            } catch (CantCreateLocationPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateLocationPurchaseException(e.getMessage(), e, "", "Cant Create Location Purchase");
            }
        }

        @Override
        public void updateLocation(NegotiationLocations location) throws CantUpdateLocationPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.updateLocation(location);
            } catch (CantUpdateLocationPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateLocationPurchaseException(e.getMessage(), e, "", "Cant Update Location Purchase");
            }
        }

        @Override
        public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.deleteLocation(location);
            } catch (CantDeleteLocationPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantDeleteLocationPurchaseException(e.getMessage(), e, "", "Cant Delete Location Purchase");
            }
        }

        @Override
        public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsPurchaseException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getAllLocations();
            } catch (CantGetListLocationsPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListLocationsPurchaseException(e.getMessage(), e, "", "Cant Get List Locations Purchase");
            }
        }

        @Override
        public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.createNewBankAccount(bankAccount);
            } catch (CantCreateBankAccountPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateBankAccountPurchaseException(e.getMessage(), e, "", "Cant Create Bank Account Purchase");
            }
        }

        @Override
        public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.updateBankAccount(bankAccount);
            } catch (CantUpdateBankAccountPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateBankAccountPurchaseException(e.getMessage(), e, "", "Cant Update Bank Account Purchase");
            }
        }

        @Override
        public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {
            try{
                this.customerBrokerPurchaseNegotiationDao.deleteBankAccount(bankAccount);
            } catch (CantDeleteBankAccountPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantDeleteBankAccountPurchaseException(e.getMessage(), e, "", "Cant Delete Bank Account Purchase");
            }
        }

        @Override
        public Collection<NegotiationBankAccount> getAllBankAccount() throws CantGetListBankAccountsPurchaseException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getAllBankAccount();
            } catch (CantGetListBankAccountsPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBankAccountsPurchaseException(e.getMessage(), e, "", "Cant Get List Bank Accounts Purchase");
            }
        }

        @Override
        public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsPurchaseException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getBankAccountByCurrencyType(currency);
            } catch (CantGetListBankAccountsPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBankAccountsPurchaseException(e.getMessage(), e, "", "Cant Get List Bank Accounts Purchase");
            }
        }

        @Override
        public Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsPurchaseException {
            try{
                return this.customerBrokerPurchaseNegotiationDao.getCurrencyTypeAvailableBankAccount();
            } catch (CantGetListBankAccountsPurchaseException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBankAccountsPurchaseException(e.getMessage(), e, "", "Cant Get List Bank Accounts Purchase");
            }
        }

    /*
    *   Private Methods
    * */

        @Deprecated
        private boolean verifyStatusClause(Collection<Clause> clausules) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            Map<ClauseType, String> clausesAgreed = new HashMap<ClauseType, String>();

            for(Clause clause : clausules) {
                if(clause.getStatus() == ClauseStatus.AGREED){
                    clausesAgreed.put(clause.getType(), clause.getValue());
                }
            }

            if(
                    ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CURRENCY) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.EXCHANGE_RATE) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CURRENCY_QUANTITY) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PAYMENT_METHOD) )
                    ){
                return false;
            }

            if( clausesAgreed.containsValue(MoneyType.CRYPTO.getCode()) ){
                if( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CRYPTO_ADDRESS) ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(MoneyType.BANK.getCode()) ){
                if( !clausesAgreed.containsKey(ClauseType.CUSTOMER_BANK_ACCOUNT) ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(MoneyType.CASH_ON_HAND.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PLACE_TO_DELIVER) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) )
                        ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(MoneyType.CASH_DELIVERY.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PLACE_TO_DELIVER) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) )
                        ){
                    return false;
                }
            }

            return true;
        }
}
