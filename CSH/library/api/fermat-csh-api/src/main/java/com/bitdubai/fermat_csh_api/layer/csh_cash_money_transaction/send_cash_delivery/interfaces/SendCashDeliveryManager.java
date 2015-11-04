package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.send_cash_delivery.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.send_cash_delivery.exceptions.CantCreateSendCashDeliveryException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.send_cash_delivery.exceptions.CantGetSendCashDeliveryException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.send_cash_delivery.exceptions.CantUpdateStatusSendCashDeliveryException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 25.09.15.
 */
public interface SendCashDeliveryManager {
    List<SendCashDelivery> getAllSendCashDeliveryFromCurrentDeviceUser() throws CantGetSendCashDeliveryException;

    SendCashDelivery createSendCashDelivery(
             final String publicKeyCustomer
            ,final String publicKeyBroker
            ,final String balanceType
            ,final String transactionType
            ,final float amount
            ,final String cashCurrencyType
            ,final String cashReference
            ,final String infoDelivery
    ) throws CantCreateSendCashDeliveryException;

    void updateStatusSendCashDelivery(final UUID cashTransactionId) throws CantUpdateStatusSendCashDeliveryException;
}
