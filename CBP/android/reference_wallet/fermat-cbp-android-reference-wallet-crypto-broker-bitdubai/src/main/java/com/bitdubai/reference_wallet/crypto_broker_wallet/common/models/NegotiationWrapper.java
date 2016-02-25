package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.*;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.DRAFT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.*;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType.*;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType.CASH_DELIVERY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType.SALE;


/**
 * Created by Nelson Ramirez
 *
 * @since 17/02/16.
 */
final public class NegotiationWrapper {

    private CustomerBrokerNegotiationInformation negotiationInfo;
    private ClauseStatus expirationTimeStatus;
    private boolean expirationTimeConfirmButtonClicked;
    private Set<ClauseType> confirmedClauses;
    private long previousExpirationTime;

    public NegotiationWrapper(CustomerBrokerNegotiationInformation negotiationInfo, CryptoBrokerWalletSession appSession) {
        this.negotiationInfo = negotiationInfo;
        expirationTimeStatus = DRAFT;
        expirationTimeConfirmButtonClicked = false;
        confirmedClauses = new HashSet<>();
        previousExpirationTime = negotiationInfo.getNegotiationExpirationDate();

        final ErrorManager errorManager = appSession.getErrorManager();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
        final long actualTimeInMillis = Calendar.getInstance().getTimeInMillis();

        try {
            CryptoBrokerWalletManager walletManager = appSession.getModuleManager().getCryptoBrokerWallet(appSession.getAppPublicKey());

            if (negotiationInfo.getNegotiationExpirationDate() == 0)
                setExpirationTime(actualTimeInMillis);

            if (clauses.get(CUSTOMER_DATE_TIME_TO_DELIVER) == null)
                addClause(CUSTOMER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(BROKER_DATE_TIME_TO_DELIVER) == null)
                addClause(BROKER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(CUSTOMER_PAYMENT_METHOD) == null) {
                final String currencyToReceive = clauses.get(BROKER_CURRENCY).getValue();
                final List<MoneyType> paymentMethods = walletManager.getPaymentMethods(currencyToReceive, appSession.getAppPublicKey());
                final MoneyType paymentMethod = paymentMethods.get(0);

                addClause(CUSTOMER_PAYMENT_METHOD, paymentMethod.getCode());

                if (paymentMethod == BANK) {
                    List<String> bankAccounts = walletManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
                    addClause(BROKER_BANK_ACCOUNT, bankAccounts.isEmpty() ? "" : bankAccounts.get(0));

                } else if (paymentMethod == CASH_ON_HAND || paymentMethod == CASH_DELIVERY) {
                    ArrayList<NegotiationLocations> locations = Lists.newArrayList(walletManager.getAllLocations(SALE));
                    addClause(BROKER_PLACE_TO_DELIVER, locations.isEmpty() ? "" : locations.get(0).getLocation());
                }
            }

        } catch (FermatException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public Map<ClauseType, ClauseInformation> getClauses() {
        return negotiationInfo.getClauses();
    }

    public CustomerBrokerNegotiationInformation getNegotiationInfo() {
        return negotiationInfo;
    }

    public long getExpirationDate() {
        return negotiationInfo.getNegotiationExpirationDate();
    }

    public void setExpirationTime(long expirationTime) {
        negotiationInfo.setNegotiationExpirationDate(expirationTime);

        if (previousExpirationTime == expirationTime && expirationTimeStatus == DRAFT)
            expirationTimeStatus = ACCEPTED;
        else if (previousExpirationTime != expirationTime)
            expirationTimeStatus = CHANGED;
    }

    public ClauseStatus getExpirationTimeStatus() {
        return expirationTimeStatus;
    }

    public boolean isExpirationTimeConfirmButtonClicked() {
        return expirationTimeConfirmButtonClicked;
    }

    public void setExpirationDatetimeConfirmButtonClicked() {
        setExpirationTime(negotiationInfo.getNegotiationExpirationDate());
        this.expirationTimeConfirmButtonClicked = true;
    }

    public boolean haveNote() {
        return negotiationInfo.getMemo() != null && !negotiationInfo.getMemo().isEmpty();
    }

    public boolean isClauseConfirmed(ClauseInformation clause) {
        return confirmedClauses.contains(clause.getType());
    }

    public boolean isClausesConfirmed(ClauseType... obviations) {
        final Collection<ClauseInformation> clauseList = getClauses().values();
        final List<ClauseType> obviationList = Arrays.asList(obviations);

        for(ClauseInformation clause: clauseList){
            if(!isClauseConfirmed(clause) && clause.getStatus() == DRAFT){
                if(!obviationList.contains(clause.getType()))
                    return false;
            }
        }
        return true;
    }

    public void setClauseAsConfirmed(ClauseInformation clause) {
        changeClauseValue(clause, clause.getValue());
        confirmedClauses.add(clause.getType());
    }

    public void addClause(final ClauseType clauseType, final String value) {

        ClauseInformation clauseInformation = new ClauseInformation() {
            @Override
            public UUID getClauseID() {
                return UUID.randomUUID();
            }

            @Override
            public ClauseType getType() {
                return clauseType;
            }

            @Override
            public String getValue() {
                return (value != null) ? value : "";
            }

            @Override
            public ClauseStatus getStatus() {
                return DRAFT;
            }
        };

        negotiationInfo.getClauses().put(clauseType, clauseInformation);
    }

    public void changeClauseValue(final ClauseInformation clause, final String value) {

        final ClauseType type = clause.getType();
        ClauseInformation clauseInformation = new ClauseInformation() {
            @Override
            public UUID getClauseID() {
                return clause.getClauseID();
            }

            @Override
            public ClauseType getType() {
                return type;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public ClauseStatus getStatus() {
                final boolean equalValues = value.equals(clause.getValue());

                if (equalValues && clause.getStatus() == DRAFT)
                    return ACCEPTED;
                if (!equalValues)
                    return CHANGED;

                return clause.getStatus();
            }
        };

        negotiationInfo.getClauses().put(type, clauseInformation);
    }
}
