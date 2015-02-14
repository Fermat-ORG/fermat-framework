package com.bitdubai.wallet_platform_api.layer._3_os.File_System;

/**
 * Created by ciencias on 01.02.15.
 */
public interface PlatformDataFile {

    public String getContent ();

    public void setContent (String content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadToMemory () throws CantLoadFileException;



    public void loadFromMedia() throws CantPersistFileException;
    
}
