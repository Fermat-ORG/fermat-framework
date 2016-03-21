package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.exceptions.OutgoingIntraActorCantCancelTransactionException;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public interface OutgoingDeviceUser extends Serializable {

    void sendToWallet(UUID trxId,
              String txHash,
              long cryptoAmount,
              String notes,
              Actors actortype,
              ReferenceWallet reference_wallet_sending,
              ReferenceWallet reference_wallet_receiving,
              String wallet_public_key_sending,
              String wallet_public_key_receiving,
              BlockchainNetworkType blockchainNetworkType) throws CantLoadWalletException, CantCalculateBalanceException,CantRegisterDebitException,CantRegisterCreditException,CantFindTransactionException,InvalidParameterException,OutgoingIntraActorCantCancelTransactionException;


}
