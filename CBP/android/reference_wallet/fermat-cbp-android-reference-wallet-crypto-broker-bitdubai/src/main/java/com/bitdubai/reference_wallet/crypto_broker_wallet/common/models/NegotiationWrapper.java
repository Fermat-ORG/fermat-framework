package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleClauseInformation;
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

import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.ACCEPTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.CHANGED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus.DRAFT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_BANK_ACCOUNT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CRYPTO_ADDRESS;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_DATE_TIME_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_PLACE_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CRYPTO_ADDRESS;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_PAYMENT_METHOD;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType.BANK;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType.CASH_DELIVERY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType.CASH_ON_HAND;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType.SALE;


/**
 * Created by Nelson Ramirez
 *
 * @since 17/02/16.
 */
final public class NegotiationWrapper {

    private CustomerBrokerNegotiationInformation negotiationInfo;
    private Set<ClauseType> confirmedClauses;

    /**
     * Constructor that wrap the {@link CustomerBrokerNegotiationInformation} object offering handy methods to operate with,
     * and having the capacity to add possible missing clauses to the wrapped object
     *
     * @param negotiationInfo the {@link CustomerBrokerNegotiationInformation} object to wrap
     * @param appSession      the session with the Module Manager and Error Manager in case of need to add missing clauses
     */
    public NegotiationWrapper(CustomerBrokerNegotiationInformation negotiationInfo, CryptoBrokerWalletSession appSession) {
        this.negotiationInfo = negotiationInfo;
        confirmedClauses = new HashSet<>();

        final ErrorManager errorManager = appSession.getErrorManager();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
        final long actualTimeInMillis = Calendar.getInstance().getTimeInMillis();

        try {
            CryptoBrokerWalletModuleManager moduleManager = appSession.getModuleManager();

            if (clauses.get(CUSTOMER_DATE_TIME_TO_DELIVER) == null)
                addClause(CUSTOMER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(BROKER_DATE_TIME_TO_DELIVER) == null)
                addClause(BROKER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(CUSTOMER_PAYMENT_METHOD) == null) {
                final String currencyToReceive = clauses.get(BROKER_CURRENCY).getValue();
                final List<MoneyType> paymentMethods = moduleManager.getPaymentMethods(currencyToReceive, appSession.getAppPublicKey());
                final MoneyType paymentMethod = paymentMethods.get(0);

                addClause(CUSTOMER_PAYMENT_METHOD, paymentMethod.getCode());

                if (paymentMethod == BANK) {
                    List<String> bankAccounts = moduleManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
                    addClause(BROKER_BANK_ACCOUNT, bankAccounts.isEmpty() ? "" : bankAccounts.get(0));

                } else if (paymentMethod == CASH_ON_HAND || paymentMethod == CASH_DELIVERY) {
                    ArrayList<NegotiationLocations> locations = Lists.newArrayList(moduleManager.getAllLocations(SALE));
                    addClause(BROKER_PLACE_TO_DELIVER, locations.isEmpty() ? "" : locations.get(0).getLocation());
                } else {
                    addClause(BROKER_CRYPTO_ADDRESS, "");
                    changeClauseValue(clauses.get(BROKER_CRYPTO_ADDRESS), "");
                }
            }

        } catch (FermatException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    /**
     * @return all the negotiation's clauses
     */
    public Map<ClauseType, ClauseInformation> getClauses() {
        return negotiationInfo.getClauses();
    }

    /**
     * @return the {@link CustomerBrokerNegotiationInformation} object with all the negotiation information
     */
    public CustomerBrokerNegotiationInformation getNegotiationInfo() {
        return negotiationInfo;
    }

    /**
     * @return <code>true</code> if the negotiation have a note, <code>false</code> otherwise
     */
    public boolean haveNote() {
        return negotiationInfo.getMemo() != null && !negotiationInfo.getMemo().isEmpty();
    }

    /**
     * Verify if a clause is confirmed
     *
     * @param clause the clause to verify
     *
     * @return <code>true</code> if the clause is confirmed, <code>false</code> otherwise
     */
    public boolean isClauseConfirmed(ClauseInformation clause) {
        return confirmedClauses.contains(clause.getType());
    }

    /**
     * Verify if all the clauses except the obviations are all confirmed
     *
     * @return <code>true</code> if all the clauses are confirmed, <code>false</code> otherwise
     */
    public boolean isClausesConfirmed() {
        final Collection<ClauseInformation> clauseList = getClauses().values();
        final List<ClauseType> obviationList = Arrays.asList(CUSTOMER_CRYPTO_ADDRESS, BROKER_CRYPTO_ADDRESS, BROKER_CURRENCY, CUSTOMER_CURRENCY);

        for (ClauseInformation clause : clauseList) {
            if (!isClauseConfirmed(clause) && clause.getStatus() == DRAFT) {
                if (!obviationList.contains(clause.getType()))
                    return false;
            }
        }
        return true;
    }

    /**
     * Confirm the clause value changes if any and update its status in advance
     *
     * @param clause the clause to confirm
     */
    public void confirmClauseChanges(ClauseInformation clause) {
        changeClauseValue(clause, clause.getValue());
        confirmedClauses.add(clause.getType());
    }

    /**
     * Add a clause to the clauses map of the {@link CustomerBrokerNegotiationInformation} object with the {@link ClauseStatus#DRAFT} status
     *
     * @param clauseType the clause type to add
     * @param value      the value of the new clause
     */
    public void addClause(final ClauseType clauseType, final String value) {
        final String clauseValue = (value != null) ? value : "";
        final CryptoBrokerWalletModuleClauseInformation clauseInformation = new CryptoBrokerWalletModuleClauseInformation(clauseType, clauseValue, DRAFT);
        negotiationInfo.getClauses().put(clauseType, clauseInformation);
    }

    /**
     * Change the value and the state of the given clause
     *
     * @param clause the clause the value is gonna change
     * @param value  the new value (change the state to {@link ClauseStatus#CHANGED}) or the same (change the state to {@link ClauseStatus#ACCEPTED})
     */
    public void changeClauseValue(final ClauseInformation clause, final String value) {
        final ClauseStatus clauseStatus = clause.getValue().equals(value) && clause.getStatus() == DRAFT ? ACCEPTED : CHANGED;

        final CryptoBrokerWalletModuleClauseInformation clauseInformation = new CryptoBrokerWalletModuleClauseInformation(clause);
        clauseInformation.setStatus(clauseStatus);
        clauseInformation.setValue(value);

        negotiationInfo.getClauses().put(clause.getType(), clauseInformation);
    }
}
