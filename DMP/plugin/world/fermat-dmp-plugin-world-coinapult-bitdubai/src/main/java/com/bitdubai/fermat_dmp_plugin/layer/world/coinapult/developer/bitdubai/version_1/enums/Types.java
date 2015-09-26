/*
 * @#Types.java - 2015
 * Copyright 2014 bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.enums;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.definition.enums.</code> holds the different types
 * of the components of the system
 * <p/>
 * Created by Roberto Requena - (rrequena) on 04/05/15.
 *
 * @version 1.0
 */
public enum Types {

    /*
    * Transaction's type
    */
    COINAPULT_TRANSACTION_TYPE_PAYMENT ("payment"),
    COINAPULT_TRANSACTION_TYPE_CONVERSION ("conversion"),
    COINAPULT_TRANSACTION_TYPE_INVOICE ("invoice"),
    COINAPULT_TRANSACTION_TYPE_LOCK ("lock"),
    COINAPULT_TRANSACTION_TYPE_UNLOCK ("unlock")
    ;

    /**
     * Represent the value
     */
    private final String value;

    /**
     * Enum construct
     * @param value
     */
    Types(String value) {
        this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return String
     */
    public String getValue()   { return this.value ; }

    /**
     * Return this Type as String
     * @return String
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Convert the String to Type Enum or null if not is a Type
     *
     * @param  type String
     * @return Types
     */
    public static Types getType(String type){

        if (type.equalsIgnoreCase("payment")){
            return COINAPULT_TRANSACTION_TYPE_PAYMENT;
        }else if (type.equalsIgnoreCase("conversion")){
            return COINAPULT_TRANSACTION_TYPE_CONVERSION;
        } else if (type.equalsIgnoreCase("invoice")){
            return COINAPULT_TRANSACTION_TYPE_INVOICE;
        } else if (type.equalsIgnoreCase("lock")){
            return COINAPULT_TRANSACTION_TYPE_LOCK;
        } else if (type.equalsIgnoreCase("unlock")){
            return COINAPULT_TRANSACTION_TYPE_UNLOCK;
        }

        return null;
    }
}
