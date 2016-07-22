package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.Serializable;

/**
 * <p>The abstract class <code>PluginBinaryFile</code> is a interface
 * that define the methods to get, set and save binary file content.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 11/02/15.
 */
public interface PluginBinaryFile extends Serializable {

    byte[] getContent();

    void setContent(byte[] content);

    void persistToMedia() throws CantPersistFileException;

    void loadFromMedia() throws CantLoadFileException;

    void delete() throws FileNotFoundException;
}
