package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.exceptions.CantCreateReceiveCashOnHandException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.exceptions.CantGetReceiveCashOnHandException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.exceptions.CantUpdateStatusReceiveCashOnHandException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 25.09.15.
 */
public interface ReceiveCashOnHandManager {
    List<ReceiveCashOnHand> getAllReceiveCashOnHandFromCurrentDeviceUser() throws CantGetReceiveCashOnHandException;

    ReceiveCashOnHand createReceiveCashOnHand(
             final String publicKeyCustomer
            ,final String publicKeyBroker
            ,final String balanceType
            ,final String transactionType
            ,final float amount
            ,final String cashCurrencyType
            ,final String cashReference
    ) throws CantCreateReceiveCashOnHandException;

    void updateStatusReceiveCashOnHand(final UUID cashTransactionId) throws CantUpdateStatusReceiveCashOnHandException;
}
