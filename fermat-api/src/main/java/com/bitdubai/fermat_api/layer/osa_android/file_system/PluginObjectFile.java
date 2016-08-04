package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

/**
 * Created by Matias Furszyfer on 03/08/16.
 */
public interface PluginObjectFile<O> {

    O getContent();

    void setContent(O object);

    void persistToMedia() throws CantPersistFileException;

    void loadFromMedia() throws CantLoadFileException;

    void delete() throws FileNotFoundException;

}
