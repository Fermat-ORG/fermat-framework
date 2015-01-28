package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 22.01.15.
 */
public interface PlatformFile {

    public String getContent ();

    public void setContent (String content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadToMemory () throws CantLoadFileException;

}
