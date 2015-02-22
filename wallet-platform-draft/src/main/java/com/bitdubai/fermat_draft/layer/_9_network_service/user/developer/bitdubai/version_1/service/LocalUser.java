package com.bitdubai.fermat_draft.layer._9_network_service.user.developer.bitdubai.version_1.service;

/**
 * Created by ciencias on 23.12.14.
 */
public interface LocalUser {

    public enum LoginType  {NONE, PIN_CODE,PASSWORD} ;


    public boolean Login ();

    public boolean Login (int pPIN);

    public boolean Login (String pPassword);

}
