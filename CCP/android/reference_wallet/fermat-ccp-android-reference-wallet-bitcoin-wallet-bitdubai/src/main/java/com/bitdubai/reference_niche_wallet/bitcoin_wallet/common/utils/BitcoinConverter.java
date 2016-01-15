package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

import java.math.BigDecimal;

/**
 * Created by Joaquin C. on 13/01/16.
 */
public class BitcoinConverter {


    //-------------------------------------------------------------------------------------
    private static final BigDecimal BITS_IN_BITCOIN = BigDecimal.valueOf(1000000);
    private static final BigDecimal uBTC = BigDecimal.valueOf(0.00000100);
    private static final BigDecimal mBTC = BigDecimal.valueOf(0.00100000);
    private static final BigDecimal BTC  = BigDecimal.valueOf(1.00000000);
    //-------------------------------------------------------------------------------------
    private static final BigDecimal BIT_ARE_SATOSHIS = BigDecimal.valueOf(100);
    private static final BigDecimal MBITS_ARE_SATOSHIS = BigDecimal.valueOf(100000);
    private static final BigDecimal BTC_ARE_SATOSHIS = BigDecimal.valueOf(100000000);

    //-------------------------------------------------------------------------------------
    private static final BigDecimal ONE_SATOSHI_IS_BITCOIN = BigDecimal.valueOf(0.00000001);
    private static final BigDecimal SATOSHI = BigDecimal.valueOf(1);

    public BitcoinConverter() {

    }

    /**
     * @param satoshis
     * @return the corresponding amount of uBTC.
     */
    public String getBits(String satoshis){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(satoshis);
             operacion = ""+(operator.multiply(uBTC)).divide(BIT_ARE_SATOSHIS);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion = "0";
        }

    }

    /**
     * @param satoshis
     * @return the corresponding amount of mBTC.
     */
    public String getMBTC(String satoshis){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(satoshis);
             operacion =""+ (operator.multiply(mBTC)).divide(MBITS_ARE_SATOSHIS);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion = "0";
        }

    }

    /**
     * @param satoshis
     * @return the corresponding amount of BTC.
     */
    public String getBTC(String satoshis){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(satoshis);
             operacion =""+(operator.multiply(BTC)).divide(BTC_ARE_SATOSHIS);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion = "0";
        }
    }

    /**
     * @param bits
     * @return the corresponding amount of satoshis given the bits passed.
     */
    public String getSathoshisFromBits(String bits){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(bits);
            operacion = ""+(operator.multiply(BIT_ARE_SATOSHIS)).divide(uBTC);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion = "0";
        }
    }

    /**
     * @param mbits
     * @return the corresponding amount of satoshis given the mBTC passed.
     */
    public String getSathoshisFromMBTC(String mbits){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(mbits);
            operacion ="" +(operator.multiply(MBITS_ARE_SATOSHIS)).divide(mBTC);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion="0";
        }
    }

    /**
     * @param bitcoins
     * @return the corresponding amount of satoshis given the BTC passed.
     */
    public String getSathoshisFromBTC(String bitcoins){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(bitcoins);
            operacion =""+ (operator.multiply(BTC_ARE_SATOSHIS)).divide(BTC);
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion="0";
        }
    }

    /**
     * @param bitcoins
     * @return the corresponding amount of bits given the BTC passed.
     */
    public String getBitsFromBTC(String bitcoins){
        String operacion = "";
        try {
            BigDecimal operator =new BigDecimal(bitcoins);
            operacion =""+ (operator.multiply(BITS_IN_BITCOIN));
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion="0";
        }
    }

    /**
     * @param bits
     * @return the corresponding amount of BTC given the bits passed.
     */
    public String getBitcoinsFromBits(String bits){
        String operacion = "";
        try {
            BigDecimal operator = new BigDecimal(bits);
            operacion =""+ (operator.divide(BITS_IN_BITCOIN));
            return operacion;
        }catch (Exception e){
            e.printStackTrace();
            return operacion= "0";
        }
    }


}
