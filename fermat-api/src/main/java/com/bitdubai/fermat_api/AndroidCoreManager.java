package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;


/**
 * Created by natalia on 19/01/16.
 */
public interface AndroidCoreManager {


    NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException;

    NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException;

    NetworkStatus getPrivateNetworkStatus();

}
