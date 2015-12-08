package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationPaymentCurrency;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreatePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeletePaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListBankAccountsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListPaymentCurrencySaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateBankAccountSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class CustomerBrokerSaleManager implements CustomerBrokerSaleNegotiationManager{

    private CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao;

    /*
       Builder
    */

    public CustomerBrokerSaleManager(CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao){
        this.customerBrokerSaleNegotiationDao = customerBrokerSaleNegotiationDao;
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
    public CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
        return this.customerBrokerSaleNegotiationDao.getNegotiationsByNegotiationId(negotiationId);
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

    @Override
    public void createNewLocation(String location, String uri) throws CantCreateLocationSaleException {
        this.customerBrokerSaleNegotiationDao.createNewLocation(location, uri);
    }

    @Override
    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
        this.customerBrokerSaleNegotiationDao.updateLocation(location);
    }

    @Override
    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
        this.customerBrokerSaleNegotiationDao.deleteLocation(location);
    }

    @Override
    public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException {
        return this.customerBrokerSaleNegotiationDao.getAllLocations();
    }

    @Override
    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException {
        this.customerBrokerSaleNegotiationDao.createNewBankAccount(bankAccount);
    }

    @Override
    public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountSaleException {
        this.customerBrokerSaleNegotiationDao.updateBankAccount(bankAccount);
    }

    @Override
    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException {
        this.customerBrokerSaleNegotiationDao.deleteBankAccount(bankAccount);
    }

    @Override
    public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsSaleException {
        return this.customerBrokerSaleNegotiationDao.getBankAccountByCurrencyType(currency);
    }

    @Override
    public void createNewPaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantCreatePaymentCurrencySaleException {
        this.customerBrokerSaleNegotiationDao.createNewPaymentCurrency(paymentCurrency);
    }

    @Override
    public void deletePaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantDeletePaymentCurrencySaleException {
        this.customerBrokerSaleNegotiationDao.deletePaymentCurrency(paymentCurrency);
    }

    @Override
    public Collection<NegotiationPaymentCurrency> getAllPaymentCurrencies() throws CantGetListPaymentCurrencySaleException {
        return this.customerBrokerSaleNegotiationDao.getAllPaymentCurrencies();
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
