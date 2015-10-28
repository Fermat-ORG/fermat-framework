package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 * Created by nelson on 28/10/15.
 */
public class CustomerBrokerNegotiationInformationImpl implements CustomerBrokerNegotiationInformation {
    private static final Random random = new Random(321515131);
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private static final Calendar calendar = Calendar.getInstance();

    private ActorIdentityImpl customerIdentity;
    private Map<ClauseType, String> summary;
    private Collection<ClauseInformation> clauses;
    private NegotiationStatus status;
    private long date;

    public CustomerBrokerNegotiationInformationImpl(String customerAlias, String merchandise, String paymentMethod, String paymentCurrency, NegotiationStatus status) {

        this.customerIdentity = new ActorIdentityImpl(customerAlias, new byte[0]);

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

        clauses = new HashSet<>();
        clauses.add(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty, ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.CUSTOMER_CURRENCY, merchandise, ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.BROKER_BANK, "Banesco", ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.BROKER_BANK_ACCOUNT, "2165645454654", ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.BROKER_CURRENCY, paymentCurrency, ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod, ClauseStatus.DRAFT));
        clauses.add(new ClauseInformationImpl(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
    }

    @Override
    public ActorIdentity getCustomer() {
        return customerIdentity;
    }

    @Override
    public ActorIdentity getBroker() {
        return null;
    }

    @Override
    public Map<ClauseType, String> getNegotiationSummary() {
        return summary;
    }

    @Override
    public Collection<ClauseInformation> getClauses() {
        return null;
    }

    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    @Override
    public long getLastUpdate() {
        return date;
    }

    private class ActorIdentityImpl implements ActorIdentity {

        private String alias;
        private byte[] img;

        public ActorIdentityImpl(String alias, byte[] img) {
            this.alias = alias;
            this.img = img;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        @Override
        public String getPublicKey() {
            return "54as65d4a8sd4ds8fasdf6a85";
        }

        @Override
        public byte[] getProfileImage() {
            return img;
        }

        @Override
        public void setNewProfileImage(byte[] imageBytes) {

        }

        @Override
        public boolean isPublished() {
            return true;
        }

        @Override
        public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
            return null;
        }
    }

    private class ClauseInformationImpl implements ClauseInformation {

        private ClauseType clauseType;
        private String value;
        private ClauseStatus status;

        public ClauseInformationImpl(ClauseType clauseType, String value, ClauseStatus status) {
            this.clauseType = clauseType;
            this.value = value;
            this.status = status;
        }

        @Override
        public ClauseType getType() {
            return clauseType;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ClauseStatus getStatus() {
            return status;
        }
    }
}
