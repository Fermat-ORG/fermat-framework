package com.bitdubai.fermat_api.layer._2_os.file_system;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._2_os.file_system.PlatformTextFile</code> is a interface
 *     that define the methods to get, set and save text file content.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   01/02/15.
 * */

 public interface PlatformTextFile {

    public String getContent ();

    public void setContent (String content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadFromMedia() throws CantLoadFileException;
    
}
