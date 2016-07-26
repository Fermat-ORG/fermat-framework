package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * <p>The enum <code>FilePrivacy</code>
 * defines the type of privacy for a file
 *
 * @author Luis
 * @version 1.0.0
 * @since 22/01/15.
 */
public enum FilePrivacy {

    //Modified by Manuel Perez on 05/08/2015
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private String code;

    FilePrivacy(String code) {

        this.code = code;

    }

    public String getCode() {

        return this.code;

    }

    public static FilePrivacy getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "PRIVATE":
                return FilePrivacy.PRIVATE;
            case "PUBLIC":
                return FilePrivacy.PUBLIC;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the FilePrivacy enum");


        }

    }

}
