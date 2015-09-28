package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.exceptions.CantCreateReceiveCashDeliveryException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.exceptions.CantGetReceiveCashDeliveryException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.exceptions.CantUpdateStatusReceiveCashDeliveryException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 25.09.15.
 */
public interface ReceiveCashDeliveryManager {
    List<ReceiveCashDelivery> getAllReceiveCashDeliveryFromCurrentDeviceUser() throws CantGetReceiveCashDeliveryException;

    ReceiveCashDelivery createReceiveCashDelivery(
         final String publicKeyCustomer
        ,final String publicKeyBroker
        ,final Float amount
        ,final String cashCurrencyType
        ,final String cashReference
        ,final String infoDelivery
    ) throws CantCreateReceiveCashDeliveryException;

    void updateStatusReceiveCashDelivery(final UUID cashtransactionId) throws CantUpdateStatusReceiveCashDeliveryException;
}
