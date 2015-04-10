package com.bitdubai.fermat_api.layer._2_os.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantPersistFileException extends Exception {

    String mFileName;

    public CantPersistFileException (String fileName) {
        mFileName = fileName;
    }

    @Override
    public String getMessage() {
        return "Cant persist to media the file " + mFileName;
    }

    public String getFileName() {
        return  mFileName;
    }

}
