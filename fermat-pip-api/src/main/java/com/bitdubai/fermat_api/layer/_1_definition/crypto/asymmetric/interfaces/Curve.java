package com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;

public interface Curve {
	public BigInteger getA();
	public BigInteger getB();	
	public Point getG();
	public int getH();
	public BigInteger getN();
	public BigInteger getP();
	public ECParameterSpec getParams();	
}
