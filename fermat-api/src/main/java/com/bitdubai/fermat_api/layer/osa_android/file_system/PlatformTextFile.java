package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;


/**
 * <p>The abstract class <code>PlatformTextFile</code> is a interface
 * that define the methods to get, set and save text file content.
 *
 * @author Luis
 * @version 1.0.0
 * @since 01/02/15.
 */

public interface PlatformTextFile {

    String getContent();

    void setContent(String content);

    void persistToMedia() throws CantPersistFileException;

    void loadFromMedia() throws CantLoadFileException;

}
