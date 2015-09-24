package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;

import java.util.UUID;

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
              Actors deliveredToActorType) throws InsufficientFundsException, CantSendFundsException ;
}
