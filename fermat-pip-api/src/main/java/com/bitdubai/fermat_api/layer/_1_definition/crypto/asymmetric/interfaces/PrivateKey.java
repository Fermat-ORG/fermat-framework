package com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces;

import java.security.interfaces.ECPrivateKey;

public interface PrivateKey extends ECPrivateKey {
	public String toHexString();
}
