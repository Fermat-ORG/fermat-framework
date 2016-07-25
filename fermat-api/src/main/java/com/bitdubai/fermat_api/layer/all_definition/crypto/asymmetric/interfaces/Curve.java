package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;

public interface Curve {
    BigInteger getA();

    BigInteger getB();

    Point getG();

    int getH();

    BigInteger getN();

    BigInteger getP();

    ECParameterSpec getParams();
}
