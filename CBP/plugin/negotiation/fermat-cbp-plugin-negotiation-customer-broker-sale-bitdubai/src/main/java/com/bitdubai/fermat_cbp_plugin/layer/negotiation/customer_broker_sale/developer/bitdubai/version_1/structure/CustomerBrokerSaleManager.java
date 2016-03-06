package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class CustomerBrokerSaleManager implements CustomerBrokerSaleNegotiationManager{

    private CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    /*
       Builder
    */

    public CustomerBrokerSaleManager(
            final CustomerBrokerSaleNegotiationDao customerBrokerSaleNegotiationDao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
    ){
        this.customerBrokerSaleNegotiationDao = customerBrokerSaleNegotiationDao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    /*
        CustomerBrokerSaleManager Interface implementation.
    */

    @Override
    public void createCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantCreateCustomerBrokerSaleNegotiationException {
        try{
            this.customerBrokerSaleNegotiationDao.createCustomerBrokerSaleNegotiation(negotiation);
        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCustomerBrokerSaleNegotiationException(e.getMessage(), e, "", "Cant Create Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void updateCustomerBrokerSaleNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.updateCustomerBrokerSaleNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.updateNegotiationNearExpirationDatetime(negotiationId, status);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void cancelNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.cancelNegotiation(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public boolean closeNegotiation(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
//        try {
//            if(verifyStatusClause(negotiation.getClauses())) {
                this.customerBrokerSaleNegotiationDao.closeNegotiation(negotiation);
                return true;
//            }
//            return false;
//        } catch (CantGetListClauseException e) {
//            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantUpdateCustomerBrokerSaleException(CantUpdateCustomerBrokerSaleException.DEFAULT_MESSAGE, e, "", "");
//        }
    }

    @Override
    public void sendToCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.sendToCustomer(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void waitForCustomer(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.waitForCustomer(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public void sendToBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {

    }

    @Override
    public void waitForBroker(CustomerBrokerSaleNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.waitForBroker(negotiation);
        } catch (CantUpdateCustomerBrokerSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateCustomerBrokerSaleException(e.getMessage(), e, "", "Cant Update Customer Broker Sale Negotiation");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiations() throws CantGetListSaleNegotiationsException {
        try{
            return this.customerBrokerSaleNegotiationDao.getNegotiations();
        } catch (CantGetListSaleNegotiationsException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public CustomerBrokerSaleNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListSaleNegotiationsException {
        try{
            return this.customerBrokerSaleNegotiationDao.getNegotiationsByNegotiationId(negotiationId);
        } catch (CantGetListSaleNegotiationsException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListSaleNegotiationsException {
        try{
            return this.customerBrokerSaleNegotiationDao.getNegotiations(status);
        } catch (CantGetListSaleNegotiationsException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListSaleNegotiationsException(e.getMessage(), e, "", "Cant Get List Sale Negotiations");
        }
    }

    @Override
    public Collection<CustomerBrokerSaleNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListSaleNegotiationsException {
        try{
            return this.customerBrokerSaleNegotiationDao.getNegotiationsBySendAndWaiting(actorType);
        } catch (CantGetListSaleNegotiationsException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        try{
            this.customerBrokerSaleNegotiationDao.createNewLocation(location, uri);
        } catch (CantCreateLocationSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateLocationSaleException(e.getMessage(), e, "", "Cant Create Location Sale");
        }
    }

    @Override
    public void updateLocation(NegotiationLocations location) throws CantUpdateLocationSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.updateLocation(location);
        } catch (CantUpdateLocationSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateLocationSaleException(e.getMessage(), e, "", "Cant Update Location Sale");
        }
    }

    @Override
    public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.deleteLocation(location);
        } catch (CantDeleteLocationSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteLocationSaleException(e.getMessage(), e, "", "Cant Delete Location Sale");
        }
    }

    @Override
    public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsSaleException {
        try{
            return this.customerBrokerSaleNegotiationDao.getAllLocations();
        } catch (CantGetListLocationsSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListLocationsSaleException(e.getMessage(), e, "", "Cant Get List Locations Sale");
        }
    }

    @Override
    public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.createNewBankAccount(bankAccount);
        } catch (CantCreateBankAccountSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateBankAccountSaleException(e.getMessage(), e, "", "Cant Create Bank Account Sale");
        }
    }

    @Override
    public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.updateBankAccount(bankAccount);
        } catch (CantUpdateBankAccountSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateBankAccountSaleException(e.getMessage(), e, "", "Cant Update Bank Account Sale");
        }
    }

    @Override
    public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountSaleException {
        try{
            this.customerBrokerSaleNegotiationDao.deleteBankAccount(bankAccount);
        } catch (CantDeleteBankAccountSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteBankAccountSaleException(e.getMessage(), e, "", "Cant Delete Bank Account Sale");
        }
    }

    @Override
    public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsSaleException {
        try{
            return this.customerBrokerSaleNegotiationDao.getBankAccountByCurrencyType(currency);
        } catch (CantGetListBankAccountsSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListBankAccountsSaleException(e.getMessage(), e, "", "Cant Get List Bank Accounts Sale");
        }
    }

    @Override
    public void createNewPaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantCreatePaymentCurrencySaleException {
        try{
            this.customerBrokerSaleNegotiationDao.createNewPaymentCurrency(paymentCurrency);
        } catch (CantCreatePaymentCurrencySaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreatePaymentCurrencySaleException(e.getMessage(), e, "", "Cant Create Payment Currency Sale");
        }
    }

    @Override
    public void deletePaymentCurrency(NegotiationPaymentCurrency paymentCurrency) throws CantDeletePaymentCurrencySaleException {
        try{
            this.customerBrokerSaleNegotiationDao.deletePaymentCurrency(paymentCurrency);
        } catch (CantDeletePaymentCurrencySaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeletePaymentCurrencySaleException(e.getMessage(), e, "", "Cant Delete Payment Currency Sale");
        }
    }

    @Override
    public Collection<NegotiationPaymentCurrency> getAllPaymentCurrencies() throws CantGetListPaymentCurrencySaleException {
        try{
            return this.customerBrokerSaleNegotiationDao.getAllPaymentCurrencies();
        } catch (CantGetListPaymentCurrencySaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListPaymentCurrencySaleException(e.getMessage(), e, "", "Cant Get List Payment Currency Sale");
        }
    }

    @Override
    public Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsSaleException {
        try{
            return this.customerBrokerSaleNegotiationDao.getCurrencyTypeAvailableBankAccount();
        } catch (CantGetListBankAccountsSaleException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListBankAccountsSaleException(e.getMessage(), e, "", "Cant Get List Bank Accounts Sale");
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

        if( clausesAgreed.containsValue(MoneyType.CRYPTO.getCode()) ){
            if( !clausesAgreed.containsKey(ClauseType.BROKER_CRYPTO_ADDRESS) ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(MoneyType.BANK.getCode()) ){
            if( !clausesAgreed.containsKey(ClauseType.BROKER_BANK_ACCOUNT) ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(MoneyType.CASH_ON_HAND.getCode()) ){
            if(
                    ( !clausesAgreed.containsKey(ClauseType.BROKER_PLACE_TO_DELIVER) ) &&
                            ( !clausesAgreed.containsKey(ClauseType.BROKER_DATE_TIME_TO_DELIVER) )
                    ){
                return false;
            }
        }

        if( clausesAgreed.containsValue(MoneyType.CASH_DELIVERY.getCode()) ){
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
