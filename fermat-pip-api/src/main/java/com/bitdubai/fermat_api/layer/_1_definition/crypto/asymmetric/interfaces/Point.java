package com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.interfaces;

import java.math.BigInteger;

public interface Point {
	public BigInteger getX();
	public BigInteger getY();
	public String toUncompressedString();
	public String toCompressedString();
	public boolean equals(Object obj);
	public int hashCode();
}
