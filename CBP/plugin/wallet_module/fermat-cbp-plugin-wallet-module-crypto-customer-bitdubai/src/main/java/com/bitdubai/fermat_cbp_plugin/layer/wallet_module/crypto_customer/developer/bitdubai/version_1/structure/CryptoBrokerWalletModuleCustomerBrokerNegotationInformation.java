package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

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
 * Customer and Broker Negotiation Information
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15.
 */
public class CryptoBrokerWalletModuleCustomerBrokerNegotationInformation implements CustomerBrokerNegotiationInformation {

    // -- for test purposes
    private static final Random random = new Random(321515131);
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private static final Calendar calendar = Calendar.getInstance();
    // for test purposes --

    private ActorIdentity customerIdentity;
    private ActorIdentity brokerIdentity;
    private Map<ClauseType, String> summary;
    private Collection<ClauseInformation> clauses;
    private NegotiationStatus status;
    private long date;


    public CryptoBrokerWalletModuleCustomerBrokerNegotationInformation(String brokerAlias, String merchandise, String paymentMethod, String paymentCurrency, NegotiationStatus status) {

        this.customerIdentity = new ActorIdentityImpl("CustomerAlias", new byte[0]);
        this.brokerIdentity = new ActorIdentityImpl(brokerAlias, new byte[0]);

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
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY_QUANTITY, currencyQty, ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.CUSTOMER_CURRENCY, merchandise, ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.BROKER_BANK, "Banesco", ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.BROKER_BANK_ACCOUNT, "2165645454654", ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.BROKER_CURRENCY, paymentCurrency, ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.BROKER_PAYMENT_METHOD, paymentMethod, ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.EXCHANGE_RATE, exchangeRate, ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER, "18-11-2015", ClauseStatus.DRAFT));
        clauses.add(new CryptoBrokerWalletModuleClauseInformation(ClauseType.BROKER_DATE_TIME_TO_DELIVER, "20-11-2015", ClauseStatus.DRAFT));
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
    public Collection<ClauseInformation> getClauses() {
        return clauses;
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
            return "54as65d4a8sd4ds8fv2vr3as2df6a85";
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
}
