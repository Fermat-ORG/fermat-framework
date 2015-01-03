package com.bitdubai.smartwallet.platform.layer._7_network_service.user.version_1;

/**
 * Created by ciencias on 23.12.14.
 */
public interface LocalUser {

    public enum LoginType  {NONE, PIN_CODE,PASSWORD} ;


    public boolean Login ();

    public boolean Login (int pPIN);

    public boolean Login (String pPassword);

}
