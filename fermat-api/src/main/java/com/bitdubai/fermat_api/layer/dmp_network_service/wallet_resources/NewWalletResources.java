package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources;

import java.util.HashMap;

/**
 * Created by eze on 2015.07.10..
 */
public interface NewWalletResources {
    public HashMap<String,byte[]> getStoredImages();
    public HashMap<String,String> getStoredLayout();
}
