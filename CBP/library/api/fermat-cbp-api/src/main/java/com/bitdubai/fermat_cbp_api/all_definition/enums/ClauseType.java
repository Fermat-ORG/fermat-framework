package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by jorge on 12-10-2015.
 */
public enum ClauseType {
    CUSTOMERCURRENCY("CUSCUR"),
    BROKERCURRENCY("BROCUR"),
    EXCHANGERATE("EXCRAT"),
    CUSTOMERCURRENCYQUANTITY("CUSCURQUA"),
    BROKERCURRENCYQUANTITY("CUSBROQUA"),
    CUSTOMERPAYMENTMETHOD("CUSPAYMET"),
    BROKERPAYMENTMETHOD("BROPAYMET"),
    CUSTOMERCRYPTOADDRESS("CUSCRYADD"),
    BROKERCRYPTOADDRESS("BROCRYADD"),
    CUSTOMERBANK("CUSBAN"),
    BROKERBANK("BROBAN"),
    CUSTOMERBANKACCOUNT("CUSBANACC"),
    BROKERBANKACCOUNT("BROBANACC"),
    PLACETOMEET("PLAMEE"),
    DATETIMETOMEET("DATTIMMEE"),
    BROKERPLACETODELIVER("BROPLADEL"),
    BROKERDATETIMETODELIVER("BRODATDEL"),
    CUSTOMERPLACETODELIVER("CUSPLADEL"),
    CUSTOMERDATETIMETODELIVER("CUSDATDEL");

    private final String code;

    ClauseType(final String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static ClauseType getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "CUSCUR": return CUSTOMERCURRENCY;
            case "EXCRAT": return EXCHANGERATE;
            case "BROCUR": return BROKERCURRENCY;
            case "CUSCURQUA": return CUSTOMERCURRENCYQUANTITY;
            case "CUSBROQUA": return BROKERCURRENCYQUANTITY;
            case "CUSPAYMET": return CUSTOMERPAYMENTMETHOD;
            case "BROPAYMET": return BROKERPAYMENTMETHOD;
            case "CUSCRYADD": return CUSTOMERCRYPTOADDRESS;
            case "BROCRYADD": return BROKERCRYPTOADDRESS;
            case "CUSBAN": return CUSTOMERBANK;
            case "BROBAN": return BROKERBANK;
            case "CUSBANACC": return CUSTOMERBANKACCOUNT;
            case "BROBANACC": return BROKERBANKACCOUNT;
            case "PLAMEE": return PLACETOMEET;
            case "DATTIMMEE": return DATETIMETOMEET;
            case "BROPLADEL": return BROKERPLACETODELIVER;
            case "BRODATDEL": return BROKERDATETIMETODELIVER;
            case "CUSPLADEL": return CUSTOMERPLACETODELIVER;
            case "CUSDATDEL": return CUSTOMERDATETIMETODELIVER;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ClauseType enum");
        }
    }
}
