package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

/**
 * Created by Joaquin C. on 13/01/16.
 */
public class BitcoinConverter {

    public double uBTC =  0.00000100;
    private static final double mBTC =  0.00100000;
    private static final double BTC  =  1.00000000;
    private static final double SATOSHIS_BTC_BITS = 100;
    private static final double SATOSHIS_BTC_mBITS = 100000;
    private static final double SATOSHIS_BTC = 100000000;


    public BitcoinConverter() {

    }

    /**
     * @param satoshis
     * @return the corresponding amount of uBTC.
     */
    public double getBits(double satoshis){
        double operacion = 0;
        try {
             operacion = (satoshis * uBTC)/SATOSHIS_BTC_BITS;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }

    }

    /**
     * @param satoshis
     * @return the corresponding amount of mBTC.
     */
    public double getMBTC(double satoshis){
        double operacion = 0;
        try {
             operacion = (satoshis * mBTC)/SATOSHIS_BTC_mBITS;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }

    }

    /**
     * @param satoshis
     * @return the corresponding amount of BTC.
     */
    public double getBTC(double satoshis){
        double operacion = 0;
        try {
             operacion = (satoshis * BTC)/SATOSHIS_BTC;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }
    }

    /**
     * @param bits
     * @return the corresponding amount of satoshis given the bits passed.
     */
    public double getSathoshisFromBits(double bits){
        double operacion = 0;
        try {
            operacion = (bits * SATOSHIS_BTC_BITS)/uBTC;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }
    }

    /**
     * @param mbits
     * @return the corresponding amount of satoshis given the mBTC passed.
     */
    public double getSathoshisFromMBTC(double mbits){
        double operacion = 0;
        try {
            operacion = (mbits * SATOSHIS_BTC_mBITS)/mBTC;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }
    }

    /**
     * @param bitcoins
     * @return the corresponding amount of satoshis given the BTC passed.
     */
    public double getSathoshisFromBTC(double bitcoins){
        double operacion = 0;
        try {
            operacion = (bitcoins * SATOSHIS_BTC)/BTC;
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion;
        }
    }


}
