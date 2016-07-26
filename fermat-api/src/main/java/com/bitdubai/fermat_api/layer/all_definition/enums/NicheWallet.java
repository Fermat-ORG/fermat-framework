package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatWalletEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet</code>
 * Lists all the NicheWallets of Fermat.
 * Created by eze on 2015.07.19..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 23/09/2015.
 * Modified by PatricioGesualdi - (pmgesualdi@hotmail.com) on 18/11/2015.
 */
public enum NicheWallet implements FermatWalletEnum {
    /**
     * The value NONE is used to store the information of no wallet
     * <p/>
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    NONE("NONE", null),
    UNKNOWN_WALLET("UNKW", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

    private final String code;

    private final ReferenceWallet referenceWallet;

    NicheWallet(final String code, final ReferenceWallet referenceWallet) {
        this.code = code;
        this.referenceWallet = referenceWallet;
    }

    public static NicheWallet getByCode(String code) throws InvalidParameterException, CallToGetByCodeOnNONEException {

        switch (code) {

            case "NONE":
                throw new CallToGetByCodeOnNONEException("method getByCode called by a NONE wallet", null, "", "");
            case "UNKW":
                return UNKNOWN_WALLET;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the NicheWallet enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public ReferenceWallet getReferenceWallet() {
        return this.referenceWallet;
    }

}
