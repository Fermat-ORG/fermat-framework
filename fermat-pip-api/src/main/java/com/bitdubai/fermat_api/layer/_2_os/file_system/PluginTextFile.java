package com.bitdubai.fermat_api.layer._2_os.file_system;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile</code> is a interface
 *     that define the methods to get, set and save text file content.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   22/01/15.
 * */
public interface PluginTextFile {

    public String getContent ();

    public void setContent (String content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadFromMedia() throws CantLoadFileException;



}
