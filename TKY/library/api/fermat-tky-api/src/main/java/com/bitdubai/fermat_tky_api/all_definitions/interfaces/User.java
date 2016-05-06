package com.bitdubai.fermat_tky_api.all_definitions.interfaces;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public interface User extends Serializable {

    /**
     * This method returns the tokenly id.
     * @return
     */
    String getTokenlyId();

    /**
     * This method returns the tokenly username.
     * @return
     */
    String getUsername();

    /**
     * This method returns the email registered in tokenly account.
     * @return
     */
    String getEmail();

    /**
     * This method returns the user Api Token from Tokenly public API.
     * @return
     */
    String getApiToken();

    /**
     * This method returns the user Api Token from Tokenly public API.
     * @return
     */
    String getApiSecretKey();


}
