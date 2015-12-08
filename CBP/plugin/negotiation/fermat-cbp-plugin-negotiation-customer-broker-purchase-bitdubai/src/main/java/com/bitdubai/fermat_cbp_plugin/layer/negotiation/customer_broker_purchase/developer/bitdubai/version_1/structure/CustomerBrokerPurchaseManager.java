package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class CustomerBrokerPurchaseManager implements CustomerBrokerPurchaseNegotiationManager {

    private CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;

    /*
       Builder
    */

        public CustomerBrokerPurchaseManager(CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao){
            this.customerBrokerPurchaseNegotiationDao = customerBrokerPurchaseNegotiationDao;
        }

    /*
        CustomerBrokerPurchaseManager Interface implementation.
    */

        @Override
        public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {
            this.customerBrokerPurchaseNegotiationDao.createCustomerBrokerPurchaseNegotiation(negotiation);
        }

        @Override
        public void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            this.customerBrokerPurchaseNegotiationDao.updateCustomerBrokerPurchaseNegotiation(negotiation);
        }

        @Override
        public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            this.customerBrokerPurchaseNegotiationDao.cancelNegotiation(negotiation);
        }

        @Override
        public boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                if(verifyStatusClause(negotiation.getClauses())) {
                    this.customerBrokerPurchaseNegotiationDao.closeNegotiation(negotiation);
                    return true;
                }
                return false;
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            this.customerBrokerPurchaseNegotiationDao.sendToBroker(negotiation);
        }

        @Override
        public void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            this.customerBrokerPurchaseNegotiationDao.waitForBroker(negotiation);
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException {
            return this.customerBrokerPurchaseNegotiationDao.getNegotiations();
        }

        @Override
        public CustomerBrokerPurchaseNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException {
            return this.customerBrokerPurchaseNegotiationDao.getNegotiationsByNegotiationId(negotiationId);
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
            return this.customerBrokerPurchaseNegotiationDao.getNegotiations(status);
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

                case CUSTOMER_BANK:
                    return ClauseType.CUSTOMER_BANK_ACCOUNT;

                case PLACE_TO_MEET:
                    return ClauseType.DATE_TIME_TO_MEET;

                case CUSTOMER_PLACE_TO_DELIVER:
                    return ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        @Override
        public ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException {
            switch (paymentMethod) {
                case CRYPTO_MONEY:
                    return ClauseType.CUSTOMER_CRYPTO_ADDRESS;

                case BANK_MONEY:
                    return ClauseType.CUSTOMER_BANK;

                case CASH_ON_HAND_MONEY:
                    return ClauseType.PLACE_TO_MEET;

                case CASH_DELIVERY_MONEY:
                    return ClauseType.CUSTOMER_PLACE_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        }

        @Override
        public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.createNewLocation(location, uri);
        }

        @Override
        public void updateLocation(NegotiationLocations location) throws CantUpdateLocationPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.updateLocation(location);
        }

        @Override
        public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.deleteLocation(location);
        }

        @Override
        public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsPurchaseException {
            return this.customerBrokerPurchaseNegotiationDao.getAllLocations();
        }

        @Override
        public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.createNewBankAccount(bankAccount);
        }

        @Override
        public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.updateBankAccount(bankAccount);
        }

        @Override
        public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {
            this.customerBrokerPurchaseNegotiationDao.deleteBankAccount(bankAccount);
        }

        @Override
        public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsPurchaseException {
            return this.customerBrokerPurchaseNegotiationDao.getBankAccountByCurrencyType(currency);
        }

    /*
    *   Private Methods
    * */

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

            if( clausesAgreed.containsValue(CurrencyType.CRYPTO_MONEY.getCode()) ){
                if( !clausesAgreed.containsKey(ClauseType.CUSTOMER_CRYPTO_ADDRESS) ){
                    return false;
                }
            }

            if( clausesAgreed.containsValue(CurrencyType.BANK_MONEY.getCode()) ){
                if(
                        ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_BANK) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_BANK_ACCOUNT) )
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
                        ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_PLACE_TO_DELIVER) ) &&
                                ( !clausesAgreed.containsKey(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) )
                        ){
                    return false;
                }
            }

            return true;
        }
}
