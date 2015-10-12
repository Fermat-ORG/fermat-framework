package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.util.UUID;

/**
 * Created by jorge on 09-10-2015.
 */
public interface Negotiation {

    UUID getNegotiationId();

    CurrencyType getMerchandiseCurrency();
    CryptoDetail getMerchandiseCryptoDetail();
    CashDetail getMerchandiseCashDetail();
    BankDetail getMerchandiseBankDetail();

    CurrencyType getPaymentCurrency();
    CryptoDetail getPaymentCryptoDetail();
    CashDetail getPaymentCashDetail();
    BankDetail getPaymentBankDetail();

    ReferenceCurrency getReferenceCurrency();
    float getReferencePrice();
    float getSuggestedPrice();
    float getMarketPrice();

    float getMerchandiseAmount();
    float getPaymentAmount();

    long getCreationDate();
    long getLastUpdateDate();
    long getExpirationDate();

    NegotiationStatus getStatus();
}
