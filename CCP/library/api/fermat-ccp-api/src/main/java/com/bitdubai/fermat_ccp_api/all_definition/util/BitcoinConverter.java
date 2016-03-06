package com.bitdubai.fermat_ccp_api.all_definition.util;

import java.math.BigDecimal;

/**
 * Created by Joaquin C. on 13/01/16.
 */
public class BitcoinConverter {


    //-------------------------------------------------------------------------------------
    private static final BigDecimal BITS_IN_BITCOIN = BigDecimal.valueOf(1000000);

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
             operacion = ""+(operator.divide(new BigDecimal(100)));
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
             operacion =""+ (operator.divide(new BigDecimal(100000)));
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
             operacion =""+(operator.divide(new BigDecimal(100000000)));
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
            operacion = ""+(operator.multiply(new BigDecimal(100)));
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
            operacion ="" +(operator.multiply(new BigDecimal(100000)));
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
            operacion =""+ (operator.multiply(new BigDecimal(100000000)));
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
