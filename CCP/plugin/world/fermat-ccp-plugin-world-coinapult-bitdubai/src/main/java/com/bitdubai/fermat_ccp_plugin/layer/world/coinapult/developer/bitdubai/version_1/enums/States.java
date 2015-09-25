/*
 * @#States.java - 2015
 * Copyright 2014 bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.world.coinapult.developer.bitdubai.version_1.enums;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.definition.enums.States</code> holds the different states
 * of the components of the system
 * <p/>
 * Created by Roberto Requena - (rrequena) on 03/05/15.
 *
 * @version 1.0
 */
public enum States {

    /*
     * Address's state
     */
    COINAPULT_ADDRESS_STATE_VALID ("valid"),
    COINAPULT_ADDRESS_STATE_UNKNOWN ("unknown"),

    /*
     * Transaction's state
     */
    COINAPULT_TRANSACTION_STATE_PENDING ("pending"),
    COINAPULT_TRANSACTION_STATE_CANCELED ("canceled"),
    COINAPULT_TRANSACTION_STATE_COMPLETE ("complete")
    ;

    /**
     * Represent the value
     */
    private final String value;

    /**
     * Enum construct
     * @param value
     */
    States(String value) {
        this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return String
     */
    public String getValue()   { return this.value ; }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return value;
    }


    /**
     * Convert the String to States Enum or null if not is a States
     *
     * @param  states String
     * @return States
     */
    public static States getType(String states){

        if (states.equalsIgnoreCase("valid")){
            return COINAPULT_ADDRESS_STATE_VALID;
        }else if (states.equalsIgnoreCase("unknown")){
            return COINAPULT_ADDRESS_STATE_UNKNOWN;
        } else if (states.equalsIgnoreCase("pending")){
            return COINAPULT_TRANSACTION_STATE_PENDING;
        } else if (states.equalsIgnoreCase("canceled")){
            return COINAPULT_TRANSACTION_STATE_CANCELED;
        } else if (states.equalsIgnoreCase("complete")){
            return COINAPULT_TRANSACTION_STATE_COMPLETE;
        }

        return null;
    }
}
