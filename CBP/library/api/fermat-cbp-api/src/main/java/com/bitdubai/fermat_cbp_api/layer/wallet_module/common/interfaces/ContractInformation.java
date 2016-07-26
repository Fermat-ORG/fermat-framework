package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface ContractInformation {

    /**
     * @return the contract ID
     */
    UUID getContractId();

    /**
     * @return the contract status (NEGOTIATION_BROKER, NEGOTIATION_CUSTOMER, WAITING_PAY, WAITING_MERCHANDISE, MERCHANDISE_GIVEN, COMPLETED, CANCEL)
     * TODO WHY DON'T YOU USE AN ENUM?
     */
    String getStatus();

    /**
     * set the contract status (NEGOTIATION_BROKER, NEGOTIATION_CUSTOMER, WAITING_PAY, WAITING_MERCHANDISE, MERCHANDISE_GIVEN, COMPLETED, CANCEL)
     * TODO WHY DON'T YOU USE AN ENUM?
     *
     * @param statusKey contract status key
     */
    void setStatus(String statusKey);

    /**
     * @return the payment date
     */
    Date getPaymentDate();

    /**
     * set the payment date
     *
     * @param date payment date
     */
    void setPaymentDate(Date date);

    /**
     * @return the date when the merchandise will be given
     */
    Date getGiveMerchandiseDate();

    /**
     * set the date when the merchandise will be given
     *
     * @param date date when the merchandise will be given
     */
    void setGiveMerchandiseDate(Date date);

    /**
     * @return the payment method (CRYPTO, CASH_IN_AND, CASH_DELIVERY, BANK)
     */
    String getPaymentMenthod();

    /**
     * set the payment method (CRYPTO, CASH_IN_AND, CASH_DELIVERY, BANK)
     *
     * @param paymentMethodKey the payment method key
     */
    void setPaymentMethod(String paymentMethodKey);

    /**
     * @return the value of change rate used to sell the merchandise
     */
    float getChangeRate();

    /**
     * set the change rate used to sell the merchandise
     *
     * @param changeRate the change rate value
     */
    void setChangeRate(float changeRate);

    /**
     * @return the currency used to to define the change rate
     */
    String getChangeRateCurrency();

    /**
     * @return the the merchandise's key that is going to be sold (BITCOIN, BOLIVAR, DOLLAR, etc)
     */
    String getMerchandise();

    /**
     * set the merchandise's key that is going to be sold (BITCOIN, BOLIVAR, DOLLAR, etc)
     *
     * @param merchandise merchandise's key
     */
    void setMerchandise(String merchandise);


    /**
     * @return the amount of merchandise to be sold
     */
    float getMerchandiseAmount();

    /**
     * set the amount of merchandise to be sold
     *
     * @param amount amount of merchandise
     */
    void setMerchandiseAmount(float amount);

    /**
     * @return additional information for the contract, as the address where is going to be deliver de merchandise, the bank account, etc
     */
    String getAdditionalInformation();

    /**
     * set additional information for the contract, as the address where is going to be deliver de merchandise, the bank account, etc
     *
     * @param additionalInfo string with the additional information
     */
    void setAdditionalInformation(String additionalInfo);

    /**
     * @return has given the payment the customer to the broker?
     */
    boolean theCustomerHasGivenThePayment();

    /**
     * @return the broker has given the merchandise to the customer?
     */
    boolean theBrokerHasGivenTheMerchandise();
}
