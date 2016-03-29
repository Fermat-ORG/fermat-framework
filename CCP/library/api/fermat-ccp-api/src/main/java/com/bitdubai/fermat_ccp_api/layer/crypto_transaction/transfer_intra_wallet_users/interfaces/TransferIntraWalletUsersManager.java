package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by natalia on 22/03/16.
 */
public interface TransferIntraWalletUsersManager extends FermatManager {

    TransferIntraWalletUsers getOutgoingDeviceUser();
}
