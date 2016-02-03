package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Customer and Broker Negotiation Information
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15.
 */
public class CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation implements CustomerBrokerNegotiationInformation {

    // -- for test purposes
    private static final Random random = new Random(321515131);
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private static final Calendar calendar = Calendar.getInstance();
    // for test purposes --

    private ActorIdentity customerIdentity;
    private ActorIdentity brokerIdentity;
    private Map<ClauseType, String> summary;
    private Map<ClauseType, ClauseInformation> clauses;
    private Collection<Clause> negotiationClause;
    private NegotiationStatus status;
    private long date;


//    public CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(String brokerAlias, String merchandise, String paymentMethod, String paymentCurrency, NegotiationStatus status) {
    public CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(CustomerBrokerPurchaseNegotiation negotiation) {

        try {
            negotiationClause = negotiation.getClauses();

            String customerAlias        = negotiation.getCustomerPublicKey();
            String brokerAlias          = negotiation.getBrokerPublicKey();
            NegotiationStatus status    = negotiation.getStatus();


            Clause paymentCurrency  = getClause(ClauseType.BROKER_CURRENCY.getCode());
            Clause exchangeRate     = getClause(ClauseType.EXCHANGE_RATE.getCode());
            Clause customerQty      = getClause(ClauseType.CUSTOMER_CURRENCY_QUANTITY.getCode());
            Clause brokerQty        = getClause(ClauseType.BROKER_CURRENCY_QUANTITY.getCode());
            Clause merchandise      = getClause(ClauseType.CUSTOMER_CURRENCY.getCode());

            Clause receptionMethod  = getClause(ClauseType.CUSTOMER_PAYMENT_METHOD.getCode());
            Clause paymentMethod    = getClause(ClauseType.BROKER_PAYMENT_METHOD.getCode());
            Clause customerDateTime = getClause(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER.getCode());
            Clause brokerDateTime   = getClause(ClauseType.BROKER_DATE_TIME_TO_DELIVER.getCode());

            Clause paymentBank      = getClause(ClauseType.BROKER_BANK_ACCOUNT.getCode());
            Clause paymentCash      = getClause(ClauseType.BROKER_PLACE_TO_DELIVER.getCode());
            Clause paymnetCrypto    = getClause(ClauseType.BROKER_CRYPTO_ADDRESS.getCode());

            Clause receptionBank    = getClause(ClauseType.CUSTOMER_BANK_ACCOUNT.getCode());
            Clause receptionCash    = getClause(ClauseType.CUSTOMER_PLACE_TO_DELIVER.getCode());
            Clause receptionCrypto  = getClause(ClauseType.CUSTOMER_CRYPTO_ADDRESS.getCode());


            String customerQtyValue     = decimalFormat.format(customerQty.getValue());
            String exchangeRateValue    = decimalFormat.format(exchangeRate.getValue());
            String bokerQtyValue        = decimalFormat.format(brokerQty.getValue());

            this.customerIdentity   = new CryptoCustomerWalletModuleActorIdentityImpl(customerAlias, new byte[0]);
            this.brokerIdentity     = new CryptoCustomerWalletModuleActorIdentityImpl(brokerAlias, new byte[0]);

            summary = new HashMap<>();
            summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerQty.getValue());
            summary.put(ClauseType.CUSTOMER_CURRENCY, merchandise.getValue());
            summary.put(ClauseType.EXCHANGE_RATE, exchangeRate.getValue());
            summary.put(ClauseType.BROKER_CURRENCY, paymentCurrency.getValue());
            summary.put(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod.getValue());

            this.status = status;
            date = calendar.getTimeInMillis();

            clauses = new HashMap<>();

            //BASIC CLAUSES
            clauses.put(ClauseType.BROKER_CURRENCY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_CURRENCY, paymentCurrency.getValue(), paymentCurrency.getStatus()));
            clauses.put(ClauseType.EXCHANGE_RATE, new CryptoCustomerWalletModuleClauseInformation(ClauseType.EXCHANGE_RATE, exchangeRateValue, exchangeRate.getStatus()));
            clauses.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY_QUANTITY, customerQtyValue, customerQty.getStatus()));
            clauses.put(ClauseType.BROKER_CURRENCY_QUANTITY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_CURRENCY_QUANTITY, bokerQtyValue, brokerQty.getStatus()));
            clauses.put(ClauseType.CUSTOMER_CURRENCY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY, merchandise.getValue(), merchandise.getStatus()));

            //PAYMENT CLAUSES
            if(receptionMethod != null)
                clauses.put(ClauseType.CUSTOMER_PAYMENT_METHOD, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_PAYMENT_METHOD, receptionMethod.getValue(), receptionMethod.getStatus()));
            if(paymentMethod != null)
                clauses.put(ClauseType.BROKER_PAYMENT_METHOD, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod.getValue(), paymentMethod.getStatus()));

            //DATATIME CLAUSES
            if(customerDateTime != null)
                clauses.put(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, brokerDateTime.getValue(), brokerDateTime.getStatus()));
            if(brokerDateTime != null)
                clauses.put(ClauseType.BROKER_DATE_TIME_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_DATE_TIME_TO_DELIVER, customerDateTime.getValue(), customerDateTime.getStatus()));

            //PAYMENT INFO CLAUSES
            if(paymentBank != null)
                clauses.put(ClauseType.BROKER_BANK_ACCOUNT, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_BANK_ACCOUNT, paymentBank.getValue(), paymentBank.getStatus()));
            if(paymentCash != null)
                clauses.put(ClauseType.BROKER_PLACE_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_PLACE_TO_DELIVER, paymentCash.getValue(), paymentCash.getStatus()));
            if(paymnetCrypto != null)
                clauses.put(ClauseType.BROKER_CRYPTO_ADDRESS, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_CRYPTO_ADDRESS, paymnetCrypto.getValue(), paymnetCrypto.getStatus()));

            //RECEPTION INFO CLAUSES
            if(receptionBank != null)
                clauses.put(ClauseType.CUSTOMER_BANK_ACCOUNT, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_BANK_ACCOUNT, receptionBank.getValue(), receptionBank.getStatus()));
            if(receptionCash != null)
                clauses.put(ClauseType.CUSTOMER_PLACE_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_PLACE_TO_DELIVER, receptionCash.getValue(), receptionCash.getStatus()));
            if(receptionCrypto != null)
                clauses.put(ClauseType.CUSTOMER_CRYPTO_ADDRESS, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_CRYPTO_ADDRESS, receptionCrypto.getValue(), receptionCrypto.getStatus()));

        } catch (CantGetListClauseException e) {

        }
        /*this.customerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl("CustomerAlias", new byte[0]);
        this.brokerIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(brokerAlias, new byte[0]);

        String currencyQty = decimalFormat.format(random.nextFloat() * 100);
        String exchangeRate = decimalFormat.format(random.nextFloat());

        summary = new HashMap<>();
        summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty);
        summary.put(ClauseType.CUSTOMER_CURRENCY, merchandise);
        summary.put(ClauseType.EXCHANGE_RATE, exchangeRate);
        summary.put(ClauseType.BROKER_CURRENCY, paymentCurrency);
        summary.put(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod);

        this.status = status;
        date = calendar.getTimeInMillis();

        clauses = new HashMap<>();
        clauses.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty, ClauseStatus.DRAFT));
        clauses.put(ClauseType.CUSTOMER_CURRENCY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY, merchandise, ClauseStatus.DRAFT));
        clauses.put(ClauseType.BROKER_BANK_ACCOUNT, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_BANK_ACCOUNT, "Banesco\n2165645454654", ClauseStatus.DRAFT));
        clauses.put(ClauseType.BROKER_CURRENCY, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_CURRENCY, paymentCurrency, ClauseStatus.DRAFT));
        clauses.put(ClauseType.BROKER_PAYMENT_METHOD, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod, ClauseStatus.DRAFT));
        clauses.put(ClauseType.EXCHANGE_RATE, new CryptoCustomerWalletModuleClauseInformation(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        clauses.put(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, "18-11-2015", ClauseStatus.DRAFT));
        clauses.put(ClauseType.BROKER_DATE_TIME_TO_DELIVER, new CryptoCustomerWalletModuleClauseInformation(ClauseType.BROKER_DATE_TIME_TO_DELIVER, "20-11-2015", ClauseStatus.DRAFT));
        */
    }

    @Override
    public ActorIdentity getCustomer() {
        return customerIdentity;
    }

    @Override
    public ActorIdentity getBroker() {
        return brokerIdentity;
    }

    @Override
    public Map<ClauseType, String> getNegotiationSummary() {
        return summary;
    }

    @Override
    public Map<ClauseType, ClauseInformation> getClauses() {
        return clauses;
    }

    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    @Override
    public String getMemo() {
        return null;
    }

    @Override
    public void setMemo(String memo) {

    }

    @Override
    public long getLastNegotiationUpdateDate() {
        return 0;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {

    }

    @Override
    public long getNegotiationExpirationDate() {
        return 0;
    }

    @Override
    public UUID getNegotiationId() {
        //TODO
        return null;
    }

    @Override
    public void setCancelReason(String cancelReason) {

    }

    @Override
    public String getCancelReason() {
        //TODO
        return null;
    }

    private Clause getClause(String type){

        for(Clause item: negotiationClause)
            if(item.getType().equals(type)) return item;

        return null;
    }
}
