package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces;

import java.security.interfaces.ECPrivateKey;

public interface PrivateKey extends ECPrivateKey {
    String toHexString();
}
