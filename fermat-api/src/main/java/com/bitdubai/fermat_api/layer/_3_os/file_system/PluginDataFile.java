package com.bitdubai.fermat_api.layer._3_os.file_system;

/**
 * Created by ciencias on 22.01.15.
 */
public interface PluginDataFile {

    public String getContent () throws WrongOwnerIdException;

    public void setContent (String content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadToMemory () throws CantLoadFileException;



    public void loadFromMedia() throws CantPersistFileException;



}
