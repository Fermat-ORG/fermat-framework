package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.util;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.EllipticCurvePoint;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Curve;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.Point;

import java.math.BigInteger;

public class ECMath {

    /*
     *	fake ECC math adapted from the python code found in https://github.com/wobine/blackboard101/blob/master/EllipticCurvesPart4-PrivateKeyToPublicKey.py
     */
    public static Point multiplyPointScalar(final Point point, final BigInteger scalar, final Curve curve) {
        String scalarBinary = scalar.toString(2);
        Point Q = point;

        for (int i = 1; i < scalarBinary.length(); ++i) {
            Q = doublePoint(Q, curve);
            if (scalarBinary.charAt(i) == '1')
                Q = addPoint(Q, point, curve);
        }

        return Q;
    }

    public static Point doublePoint(final Point point, final Curve curve) {
        BigInteger lambda = point.getX().pow(2).multiply(new BigInteger("3")).add(curve.getA());
        lambda = lambda.multiply(modinv(point.getY().multiply(new BigInteger("2")), curve.getP()));
        lambda = lambda.mod(curve.getP());

        BigInteger x = lambda.pow(2).subtract(point.getX().multiply(new BigInteger("2"))).mod(curve.getP());
        BigInteger y = lambda.multiply(point.getX().subtract(x)).subtract(point.getY()).mod(curve.getP());

        return new EllipticCurvePoint(x, y);
    }


    public static Point addPoint(final Point p1, final Point p2, final Curve curve) {
        BigInteger lambda = p2.getY().subtract(p1.getY());
        lambda = lambda.multiply(modinv(p2.getX().subtract(p1.getX()), curve.getP()));
        lambda = lambda.mod(curve.getP());

        BigInteger x = lambda.pow(2).subtract(p1.getX()).subtract(p2.getX()).mod(curve.getP());
        BigInteger y = lambda.multiply(p1.getX().subtract(x)).subtract(p1.getY()).mod(curve.getP());

        return new EllipticCurvePoint(x, y);
    }

    public static BigInteger modinv(final BigInteger a, final BigInteger b) {
        BigInteger lm = BigInteger.ONE;
        BigInteger hm = BigInteger.ZERO;
        BigInteger low = a.mod(b);
        BigInteger high = b;
        while (low.compareTo(BigInteger.ONE) > 0) {
            BigInteger ratio = high.divide(low);
            BigInteger nm = hm.subtract(lm.multiply(ratio));
            BigInteger nl = high.subtract(low.multiply(ratio));
            hm = lm;
            high = low;
            lm = nm;
            low = nl;
        }
        return lm.mod(b);
    }

}
