package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface BusinessTransaction {

    String getStatus();

    Float getAmount();

    Float getPrice();

    Float getReferenceCurrency();

    void setStatus(String status);

    void setAmount(Float amount);

    void setPrice(Float price);

    void setReferenceCurrency(Float ReferenceCurrency);

}
