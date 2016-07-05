package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InsufficientFundsException;

/**
 * Created by eze on 2015.06.17..
 *
 */
public interface TransactionManager {

    void send(String walletPublicKey,
              CryptoAddress destinationAddress,
              long cryptoAmount,
              String notes,
              String deliveredByActorPublicKey,
              Actors deliveredByActorType,
              String deliveredToActorPublicKey,
              Actors deliveredToActorType,
              ReferenceWallet referenceWallet,
              BlockchainNetworkType blockchainNetworkType,
              CryptoCurrency cryptoCurrency,
              long fee,
              FeeOrigin feeOrigin) throws InsufficientFundsException, com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantSendFundsException;
}
