package com.bitdubai.fermat_api.layer._3_os.file_system;

import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.WrongOwnerIdException;

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
