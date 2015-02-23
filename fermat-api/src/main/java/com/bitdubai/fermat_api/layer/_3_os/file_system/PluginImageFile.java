package com.bitdubai.fermat_api.layer._3_os.file_system;

import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._3_os.file_system.exceptions.CantPersistFileException;

/**
 * Created by Natalia on 11/02/2015.
 */
public interface PluginImageFile {
    public String getContent ();

    public void setContent (String content);

    public Object getBitMap ();

    public void setBitMap (Object bitMap);

    public void persistToMedia() throws CantPersistFileException;

    public void peristToMemory () throws CantLoadFileException;

    public void loadFromMemory() throws CantLoadFileException;

    public void loadFromMedia() throws CantPersistFileException;
}
