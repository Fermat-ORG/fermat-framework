package com.bitdubai.fermat_api.layer._2_os.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantLoadFileException extends Exception {

    String mFileName;

    public CantLoadFileException (String fileName) {
        mFileName = fileName;
    }

    @Override
    public String getMessage() {
        return "Cant load to memory the file " + mFileName;
    }

}