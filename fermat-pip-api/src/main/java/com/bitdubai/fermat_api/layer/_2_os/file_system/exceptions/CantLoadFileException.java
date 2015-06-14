package com.bitdubai.fermat_api.layer._2_os.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantLoadFileException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3969485077547243542L;
	private String mFileName;

    public CantLoadFileException (String fileName) {
        this.mFileName = fileName;
    }

    @Override
    public String getMessage() {
        return "Cant load to memory the file " + mFileName;
    }

}