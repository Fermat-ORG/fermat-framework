package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

/**
 * <p>The abstract class <code>PlatformBinaryFile</code> is a interface
 * that define the methods to get, set and save binary file content.
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 *
 * @Version 1.0
 */
public interface PlatformBinaryFile {

    byte[] getContent();

    void setContent(byte[] content);

    void persistToMedia() throws CantPersistFileException;

    void loadFromMedia() throws CantLoadFileException;

}
