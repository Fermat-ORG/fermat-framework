package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions.CantCreateGiveCashOnHandException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions.CantUpdateStatusGiveCashOnHandException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions.CantGetGiveCashOnHandException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 25.09.15.
 */
public interface GiveCashOnHandManager {
    List<GiveCashOnHand> getAllGiveCashOnHandFromCurrentDeviceUser() throws CantGetGiveCashOnHandException;

    GiveCashOnHand createGiveCashOnHand(
         final String publicKeyActorTo
        ,final String publicKeyActorFrom
        ,final String balanceType
        ,final String transactionType
        ,final float amount
        ,final String cashCurrencyType
        ,final String cashReference
    ) throws CantCreateGiveCashOnHandException;

    void updateStatusGiveCashOnHand(final UUID cashTransactionId) throws CantUpdateStatusGiveCashOnHandException;
}
