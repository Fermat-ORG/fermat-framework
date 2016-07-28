package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Curve;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PrivateKey;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;

public class AsymmetricPrivateKey implements PrivateKey {

    /**
     *
     */
    private static final long serialVersionUID = -3606455537479726373L;

    private static final String ECC_ALGORITHM = "ECIES";

    private final BigInteger s;
    private transient final Curve curve;

    public AsymmetricPrivateKey(final BigInteger s) {
        this.s = s;
        curve = EllipticCryptographyCurve.getSecP256K1();
    }

    /*
     *	implementation of ECPrivateKey interface
     */
    @Override
    public BigInteger getS() {
        return s;
    }

    /*
     *	implementation of ECKey interface
     */
    @Override
    public ECParameterSpec getParams() {
        return curve.getParams();
    }

    /*
     *	implementation of PrivateKey interface
     */
    @Override
    public String getAlgorithm() {
        return ECC_ALGORITHM;
    }

    @Override
    public byte[] getEncoded() {
        return s.toByteArray();
    }

    @Override
    public String getFormat() {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public String toHexString() {
        return s.toString(16);
    }

    @Override
    public String toString() {
        return toHexString();
    }

}
