package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.CantSendTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.TransferIntraWalletUsersNotEnoughFundsException;

import java.io.Serializable;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public interface TransferIntraWalletUsers extends Serializable {

    void sendToWallet(
              String txHash,
              long cryptoAmount,
              String notes,
              Actors actortypeFrom,
              Actors actortypeTo,
              ReferenceWallet reference_wallet_sending,
              ReferenceWallet reference_wallet_receiving,
              String wallet_public_key_sending,
              String wallet_public_key_receiving,
              BlockchainNetworkType blockchainNetworkType,
              CryptoCurrency cryptoCurrency) throws CantSendTransactionException, TransferIntraWalletUsersNotEnoughFundsException;


}
