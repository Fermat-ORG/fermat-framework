package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

 /**
 * Created by toshiba on 05/03/2015.
 */
public enum Repositories {
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("fermat-wallet-resource-package-age-kids-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI ("fermat-wallet-resource-package-age-teens-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI ("fermat-wallet-resource-package-age-adults-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI ("fermat-wallet-resource-package-age-young-all-bitdubai");

    //Modified by Manuel Perez on 05/08/2015
    private String code;

    Repositories(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code; }

    /*public String toString(){
        return code;
    }*/

    public static Repositories getByCode(String code) throws InvalidParameterException {

        switch (code){

            case "fermat-wallet-resource-package-age-kids-all-bitdubai":
                return Repositories.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI;
            case "fermat-wallet-resource-package-age-teens-all-bitdubai":
                return Repositories.CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI;
            case "fermat-wallet-resource-package-age-adults-all-bitdubai":
                return Repositories.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI;
            case "fermat-wallet-resource-package-age-young-all-bitdubai":
                return Repositories.CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TemplateStatus enum");


        }

    }

    public static String getValueFromType(Wallets type) {
        for (Repositories repo : Repositories.values()) {
            if (repo.name().equals(type.toString())) {
                return repo.code;
            }
        }
        // throw an IllegalArgumentException or return null
        throw new IllegalArgumentException("the given number doesn't match any Status.");
    }

}
