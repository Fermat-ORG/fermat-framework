package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;

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
              BlockchainNetworkType blockchainNetworkType);


}
