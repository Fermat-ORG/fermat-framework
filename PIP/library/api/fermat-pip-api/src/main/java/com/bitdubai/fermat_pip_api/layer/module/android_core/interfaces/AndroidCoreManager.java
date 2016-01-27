package com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_pip_api.all_definition.enums.NetworkStatus;
import com.bitdubai.fermat_pip_api.layer.module.android_core.exception.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_pip_api.layer.module.android_core.exception.CantGetCommunicationNetworkStatusException;

/**
 * Created by natalia on 19/01/16.
 */
public interface AndroidCoreManager {


    NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException;

    NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException;

    NetworkStatus getPrivateNetworkStatus();

}
