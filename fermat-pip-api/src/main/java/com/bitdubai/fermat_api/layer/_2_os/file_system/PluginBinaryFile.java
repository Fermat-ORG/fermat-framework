package com.bitdubai.fermat_api.layer._2_os.file_system;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._2_os.file_system.PluginBinaryFile</code> is a interface
 *     that define the methods to get, set and save binary file content.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   11/02/15.
 * */
public interface PluginBinaryFile {
    
    public byte[] getContent ();

    public void setContent (byte[] content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadFromMedia() throws CantLoadFileException;
}
