package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces;

import java.io.Serializable;


/**
 * Created by jorge on 28-09-2015.
 */
public interface KeyPair extends Serializable {
    String getPublicKey();
    String getPrivateKey();
}
