package com.bitdubai.smartwallet.core.platform.layer.lowlevel.service.user.version_1;

/**
 * Created by ciencias on 23.12.14.
 */
public interface LocalUser {

    public enum LoginType  {NONE, PIN_CODE,PASSWORD} ;


    public boolean Login ();

    public boolean Login (int pPIN);

    public boolean Login (String pPassword);

}
