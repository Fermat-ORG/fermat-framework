package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_device_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * Created by natalia on 22/03/16.
 */
public interface OutgoingDeviceUserManager extends FermatManager {

    OutgoingDeviceUser getOutgoingDeviceUser();
}
