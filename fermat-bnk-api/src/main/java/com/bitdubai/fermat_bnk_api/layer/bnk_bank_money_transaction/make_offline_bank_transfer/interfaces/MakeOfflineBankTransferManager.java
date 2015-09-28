package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantCreateMakeOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantGetMakeOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantUpdateStatusMakeOfflineBankTransferException;

import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 25/09/15.
 */
public interface MakeOfflineBankTransferManager {

    List<MakeOfflineBankTransfer> getAllMakeOfflineBankTransferFromCurrentDeviceUser() throws CantGetMakeOfflineBankTransferException;

    MakeOfflineBankTransfer createMakeOfflineBankTransfer(
         final String publicKeyCustomer
        ,final String publicKeyBroker
        ,final Float amount
        ,final String bankCurrencyType
        ,final String bankName
        ,final String bankAccountNumber
        ,final String bankAccountType
        ,final String bankDocumentReference
    ) throws CantCreateMakeOfflineBankTransferException;

    void updateStatusMakeOfflineBankTransfer(final UUID bankTransactionId) throws CantUpdateStatusMakeOfflineBankTransferException;
}
