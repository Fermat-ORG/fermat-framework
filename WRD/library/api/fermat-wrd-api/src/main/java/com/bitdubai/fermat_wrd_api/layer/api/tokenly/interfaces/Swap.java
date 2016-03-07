package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public interface Swap {

    /**
     * This method returns the strategy
     * @return
     */
    String getStrategy();

    /**
     * This method returns the direction
     * @return
     */
    String getDirection();

    /**
     * This method returns the in.
     * @return
     */
    String getIn();

    /**
     * This method returns the out.
     * @return
     */
    String getOut();

    /**
     * This method returns the cost
     * @return
     */
    float getCost();

    /**
     * This method returns the divisible state
     * @return
     */
    boolean getDivisible();

    /**
     * This method returns the min out.
     * @return
     */
    long getMinOut();

    /**
     * This method returns the swap type
     * @return
     */
    String getType();

    /**
     * This method returns the fiat type
     * @return
     */
    String getFiat();

    /**
     * This method returns the source
     * @return
     */
    String getSource();

    /**
     * This method returns the swap rule ids.
     * @return
     */
    String[] getSwapRuleIds();

}
