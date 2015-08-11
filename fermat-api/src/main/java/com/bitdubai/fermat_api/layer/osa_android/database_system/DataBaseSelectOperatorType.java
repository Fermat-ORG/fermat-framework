package com.bitdubai.fermat_api.layer.osa_android.database_system;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *  <p>The enum <code>DatabaseSelectOperator</code>
 *     defined operators that can be applied in a select statement
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/07/2015
 * */

public enum DataBaseSelectOperatorType {

    SUM("SUM"),
    COUNT("COUNT");

    private String code;

    DataBaseSelectOperatorType(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static DataBaseSelectOperatorType getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "SUM":
                return DataBaseSelectOperatorType.SUM;
            case "COUNT":
                return DataBaseSelectOperatorType.COUNT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DataBaseSelectOperatorType enum");


        }

    }

}
