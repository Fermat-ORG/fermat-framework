package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nelson Ramirez
 * @since 17/02/16.
 */
public class NegotiationWrapper {

    private CustomerBrokerNegotiationInformation negotiationInfo;
    private ClauseStatus expirationTimeStatus;
    private boolean expirationTimeConfirmButtonClicked;

    public NegotiationWrapper(CustomerBrokerNegotiationInformation negotiationInfo, CryptoBrokerWalletSession appSession) {
        this.negotiationInfo = negotiationInfo;
        expirationTimeStatus = ClauseStatus.DRAFT;
        expirationTimeConfirmButtonClicked = false;

        final ErrorManager errorManager = appSession.getErrorManager();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
        final long actualTimeInMillis = Calendar.getInstance().getTimeInMillis();

        try {
            CryptoBrokerWalletManager walletManager = appSession.getModuleManager().getCryptoBrokerWallet(appSession.getAppPublicKey());

            if (negotiationInfo.getNegotiationExpirationDate() == 0)
                negotiationInfo.setLastNegotiationUpdateDate(actualTimeInMillis);

            if (clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) == null)
                addClause(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(ClauseType.BROKER_DATE_TIME_TO_DELIVER) == null)
                addClause(ClauseType.BROKER_DATE_TIME_TO_DELIVER, Long.toString(actualTimeInMillis));

            if (clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD) == null) {
                final String currencyToSell = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
                final List<MoneyType> paymentMethods = walletManager.getPaymentMethods(currencyToSell, appSession.getAppPublicKey());
                final MoneyType paymentMethod = paymentMethods.get(0);

                addClause(ClauseType.CUSTOMER_PAYMENT_METHOD, paymentMethod.getFriendlyName());

                if (paymentMethod == MoneyType.BANK) {
                    List<String> bankAccounts = walletManager.getAccounts(currencyToSell, appSession.getAppPublicKey());
                    addClause(ClauseType.BROKER_BANK_ACCOUNT, bankAccounts.isEmpty() ? "" : bankAccounts.get(0));

                } else if (paymentMethod == MoneyType.CASH_DELIVERY) {
                    ArrayList<NegotiationLocations> locations = Lists.newArrayList(walletManager.getAllLocations(NegotiationType.SALE));
                    addClause(ClauseType.BROKER_BANK_ACCOUNT, locations.isEmpty() ? "" : locations.get(0).getLocation());
                }
            }

        } catch (FermatException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public CustomerBrokerNegotiationInformation getNegotiationInfo() {
        return negotiationInfo;
    }

    public ClauseStatus getExpirationTimeStatus() {
        return expirationTimeStatus;
    }

    public boolean isExpirationTimeConfirmButtonClicked() {
        return expirationTimeConfirmButtonClicked;
    }

    public void setExpirationTimeConfirmButtonClicked(boolean expirationTimeConfirmButtonClicked) {
        this.expirationTimeConfirmButtonClicked = expirationTimeConfirmButtonClicked;
    }

    public boolean haveNote() {
        return negotiationInfo.getMemo() != null && !negotiationInfo.getMemo().isEmpty();
    }

    public void putClause(final ClauseInformation clause, final String value) {

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
                return clause.getStatus();
            }
        };

        negotiationInfo.getClauses().put(type, clauseInformation);
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
                return ClauseStatus.DRAFT;
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
                boolean equalValues = value.equals(clause.getValue());

                if (equalValues && clause.getStatus() == ClauseStatus.DRAFT)
                    return ClauseStatus.ACCEPTED;
                if (!equalValues && clause.getStatus() == ClauseStatus.ACCEPTED)
                    return ClauseStatus.CHANGED;

                return equalValues ? clause.getStatus() : ClauseStatus.CHANGED;
            }
        };

        negotiationInfo.getClauses().put(type, clauseInformation);
    }
}
