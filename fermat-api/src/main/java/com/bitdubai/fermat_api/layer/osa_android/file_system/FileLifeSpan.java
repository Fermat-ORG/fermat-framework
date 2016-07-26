package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>FileLifeSpan</code>
 * defines the type of life span for a file
 *
 * @author Luis
 * @version 1.0.0
 * @since 22/01/15.
 */
public enum FileLifeSpan {

    //Modified by Manuel Perez on 05/06/2015
    PERMANENT("PERM"),
    TEMPORARY("TEMP");

    private String code;

    FileLifeSpan(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static FileLifeSpan getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "PERM":
                return FileLifeSpan.PERMANENT;
            case "TEMP":
                return FileLifeSpan.TEMPORARY;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the FileLifeSpan enum");


        }

    }

}
