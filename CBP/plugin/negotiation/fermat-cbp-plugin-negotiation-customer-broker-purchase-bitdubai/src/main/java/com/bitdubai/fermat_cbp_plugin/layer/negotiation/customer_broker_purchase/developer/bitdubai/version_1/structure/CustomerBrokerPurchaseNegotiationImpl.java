package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions.CantUpdateClausesException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;

import java.util.Collection;
import java.util.UUID;

/**
 *  Created by angel on 19/10/15.
 */

public class CustomerBrokerPurchaseNegotiationImpl implements CustomerBrokerPurchaseNegotiation {

    private final UUID   negotiationId;
    private final String publicKeyCustomer;
    private final String publicKeyBroker;
    private final long   startDataTime;
    private NegotiationStatus statusNegotiation;

    private CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;

    public CustomerBrokerPurchaseNegotiationImpl(
            UUID   negotiationId,
            String publicKeyCustomer,
            String publicKeyBroker,
            long startDataTime,
            NegotiationStatus statusNegotiation,
            CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao
    ){
        this.negotiationId = negotiationId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.startDataTime = startDataTime;
        this.statusNegotiation = statusNegotiation;
        this.customerBrokerPurchaseNegotiationDao = customerBrokerPurchaseNegotiationDao;
    }

    @Override
    public String getCustomerPublicKey() {
        return this.publicKeyCustomer;
    }

    @Override
    public String getBrokerPublicKey() {
        return this.publicKeyBroker;
    }

    @Override
    public UUID getNegotiationId() {
        return this.negotiationId;
    }

    @Override
    public long getStartDate() {
        return this.startDataTime;
    }

    @Override
    public NegotiationStatus getStatus() {
        return this.statusNegotiation;
    }

    @Override
    public void setStatus(NegotiationStatus status) {
        this.statusNegotiation = status;
    }

    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        return this.customerBrokerPurchaseNegotiationDao.getClauses(this.negotiationId);
    }

    @Override
    public Clause addNewBrokerClause(ClauseType type, String value) throws CantAddNewClausesException {
        return this.customerBrokerPurchaseNegotiationDao.addNewClause(this.negotiationId, type, value, this.getBrokerPublicKey());
    }

    @Override
    public Clause addNewCustomerClause(ClauseType type, String value) throws CantAddNewClausesException {
        return this.customerBrokerPurchaseNegotiationDao.addNewClause(this.negotiationId, type, value, this.getCustomerPublicKey());
    }

    @Override
    public Clause modifyClause(Clause clause, String value) throws CantUpdateClausesException {
        return this.customerBrokerPurchaseNegotiationDao.modifyClause(this.negotiationId, clause, value);
    }

    @Override
    public Clause modifyClauseStatus(Clause clause, ClauseStatus status) throws CantUpdateClausesException {
        return this.customerBrokerPurchaseNegotiationDao.modifyClauseStatus(this.negotiationId, clause, status);
    }

    @Override
    public ClauseType getNextClauseType() throws CantGetNextClauseTypeException {

        try {
            ClauseType type = this.customerBrokerPurchaseNegotiationDao.getNextClauseType(this.negotiationId);

            switch (type) {
                case CUSTOMER_CURRENCY:
                    return ClauseType.EXCHANGE_RATE;

                case EXCHANGE_RATE:
                    return ClauseType.CUSTOMER_CURRENCY_QUANTITY;

                case CUSTOMER_CURRENCY_QUANTITY:
                    return ClauseType.CUSTOMER_PAYMENT_METHOD;

                case CUSTOMER_PAYMENT_METHOD:
                    CurrencyType paymentMethod = CurrencyType.getByCode(this.customerBrokerPurchaseNegotiationDao.getPaymentMethod(this.negotiationId));

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

                case CUSTOMER_BANK:
                    return ClauseType.CUSTOMER_BANK_ACCOUNT;

                case PLACE_TO_MEET:
                    return ClauseType.DATE_TIME_TO_MEET;

                case CUSTOMER_PLACE_TO_DELIVER:
                    return ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;

                default:
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
            }
        } catch (InvalidParameterException e) {
            throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE);
        }
    }
}
