package com.bitdubai.fermat_api.layer.all_definition.util;

/**
 * Created by eze on 03/05/15.
 */

/*
 * The purpose of this class is to gather the currency conversion methods used
 * along the platform.
 * Many modules need at some point to transform a certain amount of crypto
 * currency to an equivalent amount of fiat currency and vice versa. In different
 * scenarios we have different data to perform the required calculation. For example,
 * we may have the conversion index returned by the CryptoIndexManager or in a
 * different situation we may not have this index but we have a pair of values fiatAmount
 * and cryptoAmount from which we can infer conversion indexes using the quotients
 * fiatAmount/cryptoAmount and cryptoAmount/fiatAmount.
 *
 *
*/
public final class Converter {

    // We declare a private constructor to avoid
    // clients creating instances of this class
    private Converter(){}

    /*
     * Round down a double
     * This means, long roundDown(double x) return the smallest integer
     * number that is smaller than or equal to x.
     * NOTE: according to oracle documentation, longValue(x) is the same as
     * (long) x, both round down the value
     * https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#longValue()
     */
    private static long roundDown(double x){
        return (long) x;
    }

    /*
     * Round up a double
     * This means, long roundUp(double x) return the smallest integer
     * number that is greater than or equal to x.
     * Source: http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html#ceil(double)
     */
    private static long roundUp(double x){
        return (long) Math.ceil(x);
    }

/* *************************************************************************
 *
 *      ROUNDED DOWN IMPLEMENTATIONS
 *
 ***************************************************************************/


    /*
     * We assume that the unit of the the parameter cryptoIndexManagerPrice
     * is fiatCurrency/cryptoCurrency adapted to the long representation
     * used in the platform. For example: dollars/shitoshis.
     */
    public static long getCurrentCryptoAmountRoundedDown(double cryptoIndexManagerPrice, long fiatAmount) {
        double cryptoAmount = ((double) fiatAmount) / cryptoIndexManagerPrice;
        return roundDown(cryptoAmount);
    }

    /*
     * We assume that the unit of the the parameter cryptoIndexManagerPrice
     * is fiatCurrency/cryptoCurrency adapted to the long representation
     * used in the platform. For example: dollars/shitoshis.
     */
    public static long getCurrentFiatAmountRoundedDown(double cryptoIndexManagerPrice, long cryptoAmount) {
        double fiatAmount = cryptoIndexManagerPrice * ((double) cryptoAmount);
        return roundDown(fiatAmount);
    }



    public static long getProportionalCryptoAmountRoundedDown(long cryptoAmountExchangeRateReference,
                                                              long fiatAmountExchangeRateReference,
                                                              long fiatAmountToConvert) {
        double cryptoAmount = fiatAmountToConvert * (((double)cryptoAmountExchangeRateReference) / fiatAmountExchangeRateReference);
        return roundDown(cryptoAmount);
    }



    public static long getProportionalFiatAmountRoundedDown(long cryptoAmountExchangeRateReference,
                                                            long fiatAmountExchangeRateReference,
                                                            long cryptoAmountToConvert) {
        double fiatAmount = cryptoAmountToConvert * (((double)fiatAmountExchangeRateReference) / cryptoAmountExchangeRateReference);
        return roundDown(fiatAmount);
    }

/* *************************************************************************
 *
 *    ROUNDED UP IMPLEMENTATIONS
 *
 ***************************************************************************/

    /*
     * We assume that the unit of the the parameter cryptoIndexManagerPrice
     * is fiatCurrency/cryptoCurrency adapted to the long representation
     * used in the platform. For example: dollars/shitoshis.
     */
    public static long getCurrentCryptoAmountRoundedUp(double cryptoIndexManagerPrice, long fiatAmount) {
        double cryptoAmount = ((double) fiatAmount) / cryptoIndexManagerPrice;
        return roundUp(cryptoAmount);
    }

    /*
    * We assume that the unit of the the parameter cryptoIndexManagerPrice
    * is fiatCurrency/cryptoCurrency adapted to the long representation
    * used in the platform. For example: dollars/shitoshis.
    */
    public static long getCurrentFiatAmountRoundedUp(double cryptoIndexManagerPrice, long cryptoAmount) {
        double fiatAmount = cryptoIndexManagerPrice * ((double) cryptoAmount);
        return roundUp(fiatAmount);
    }


    public static long getProportionalCryptoAmountRoundedUp(long cryptoAmountExchangeRateReference,
                                                            long fiatAmountExchangeRateReference,
                                                            long fiatAmountToConvert) {
        double cryptoAmount = fiatAmountToConvert * (((double)cryptoAmountExchangeRateReference) / fiatAmountExchangeRateReference);
        return roundUp(cryptoAmount);
    }



    public static long getProportionalFiatAmountRoundedUp(long cryptoAmountExchangeRateReference,
                                                          long fiatAmountExchangeRateReference,
                                                          long cryptoAmountToConvert) {
        double fiatAmount = cryptoAmountToConvert * (((double)fiatAmountExchangeRateReference) / cryptoAmountExchangeRateReference);
        return roundUp(fiatAmount);
    }
}