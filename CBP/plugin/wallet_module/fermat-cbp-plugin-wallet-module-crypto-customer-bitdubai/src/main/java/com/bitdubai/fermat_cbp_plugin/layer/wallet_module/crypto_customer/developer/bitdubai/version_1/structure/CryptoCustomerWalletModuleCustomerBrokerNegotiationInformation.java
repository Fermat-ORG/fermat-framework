package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Customer and Broker Negotiation Information
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15.
 */
public class CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation implements CustomerBrokerNegotiationInformation, Serializable {

    private ActorIdentity customerIdentity;
    private ActorIdentity brokerIdentity;
    private Map<ClauseType, String> summary;
    private Map<ClauseType, ClauseInformation> clauses;
    private UUID negotiationId;
    private NegotiationStatus status;
    private long lastUpdateDate;
    private String note;
    private String cancelReason;
    private long expirationDatetime;
    private NumberFormat numberFormat = DecimalFormat.getInstance();


    public CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
            ActorIdentity customerIdentity,
            ActorIdentity brokerIdentity,
            UUID negotiationId,
            NegotiationStatus status,
            Map<ClauseType, ClauseInformation> clauses,
            String note,
            long lastUpdateDate,
            long expirationDatetime,
            String cancelReason
    ) {
        numberFormat.setMaximumFractionDigits(8);


        this.customerIdentity = customerIdentity;
        this.brokerIdentity = brokerIdentity;

        String currencyQty = getDecimalFormat(getBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue()));
        String exchangeRate = getDecimalFormat(getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue()));
        String merchandise = "";
        String paymentMethod = "";
        String paymentCurrency = "";

        if (clauses.get(ClauseType.CUSTOMER_CURRENCY) != null)
            merchandise = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();

        if (clauses.get(ClauseType.BROKER_PAYMENT_METHOD) != null)
            paymentMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();

        if (clauses.get(ClauseType.BROKER_CURRENCY) != null)
            paymentCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        summary = new HashMap<>();
        summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty);
        summary.put(ClauseType.CUSTOMER_CURRENCY, merchandise);
        summary.put(ClauseType.EXCHANGE_RATE, exchangeRate);
        summary.put(ClauseType.BROKER_CURRENCY, paymentCurrency);
        summary.put(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod);

        this.negotiationId = negotiationId;
        this.status = status;
        this.clauses = clauses;
        this.note = note;
        this.lastUpdateDate = lastUpdateDate;
        this.cancelReason = cancelReason;
        this.expirationDatetime = expirationDatetime;

    }

    public CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(
            CustomerBrokerNegotiationInformation negotiationInformation, String cancelReason
    ) {

        Map<ClauseType, ClauseInformation> clauses;

        clauses = negotiationInformation.getClauses();
        this.customerIdentity = negotiationInformation.getCustomer();
        this.brokerIdentity = negotiationInformation.getBroker();

        String currencyQty = getDecimalFormat(getBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue()));
        String exchangeRate = getDecimalFormat(getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue()));
        String merchandise = "";
        String paymentMethod = "";
        String paymentCurrency = "";

        if (clauses.get(ClauseType.CUSTOMER_CURRENCY) != null)
            merchandise = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();

        if (clauses.get(ClauseType.BROKER_PAYMENT_METHOD) != null)
            paymentMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();

        if (clauses.get(ClauseType.BROKER_CURRENCY) != null)
            paymentCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        summary = new HashMap<>();
        summary.put(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty);
        summary.put(ClauseType.CUSTOMER_CURRENCY, merchandise);
        summary.put(ClauseType.EXCHANGE_RATE, exchangeRate);
        summary.put(ClauseType.BROKER_CURRENCY, paymentCurrency);
        summary.put(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod);

        this.negotiationId = negotiationInformation.getNegotiationId();
        this.status = negotiationInformation.getStatus();
        this.clauses = clauses;
        this.note = negotiationInformation.getMemo();
        this.lastUpdateDate = negotiationInformation.getLastNegotiationUpdateDate();
        this.cancelReason = cancelReason;
        this.expirationDatetime = negotiationInformation.getNegotiationExpirationDate();
    }

    public CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation(CustomerBrokerPurchaseNegotiation negotiation, ActorIdentity customerIdentity, ActorIdentity brokerIdentity) {
        this.customerIdentity = customerIdentity;
        this.brokerIdentity = brokerIdentity;
        status = negotiation.getStatus();
        note = negotiation.getMemo();
        lastUpdateDate = negotiation.getLastNegotiationUpdateDate();
        expirationDatetime = negotiation.getNegotiationExpirationDate();
        negotiationId = negotiation.getNegotiationId();
        cancelReason = negotiation.getCancelReason();

        summary = new HashMap<>();
        clauses = new HashMap<>();

        Collection<Clause> saleNegotiationClauses;
        try {
            saleNegotiationClauses = negotiation.getClauses();
        } catch (CantGetListClauseException e) {
            saleNegotiationClauses = new ArrayList<>();
        }

        for (Clause clause : saleNegotiationClauses) {
            clauses.put(clause.getType(), new com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.classes.CryptoCustomerWalletModuleClauseInformation(clause));
        }
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
        return note;
    }

    @Override
    public void setMemo(String note) {
        this.note = note;
    }

    @Override
    public long getLastNegotiationUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public long getNegotiationExpirationDate() {
        return expirationDatetime;
    }

    @Override
    public void setNegotiationExpirationDate(long negotiationExpirationDatetime) {
        this.expirationDatetime = negotiationExpirationDatetime;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String getCancelReason() {
        return cancelReason;
    }

    private BigDecimal getBigDecimal(String value) {

        try {
            return new BigDecimal(numberFormat.parse(value).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return new BigDecimal(0);
        }
        // change lostwood
        // return new BigDecimal(value.replace(",", ""));
    }

    private String getDecimalFormat(BigDecimal value) {
        return numberFormat.format(value.doubleValue());
    }
}
