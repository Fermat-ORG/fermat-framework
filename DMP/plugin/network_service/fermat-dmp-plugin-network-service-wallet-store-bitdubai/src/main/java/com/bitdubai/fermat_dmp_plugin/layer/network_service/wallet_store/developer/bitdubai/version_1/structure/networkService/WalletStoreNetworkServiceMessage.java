package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;

import java.io.Serializable;

/**
 * Created by rodrigo on 8/10/15.
 */
public class WalletStoreNetworkServiceMessage implements Message, Serializable {

    @Override
    public void setTextContent(String content) {

    }

    @Override
    public String getTextContent() {
        return null;
    }

    @Override
    public MessagesStatus getStatus() {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public Message fromJson(String json) {
        return null;
    }
}
