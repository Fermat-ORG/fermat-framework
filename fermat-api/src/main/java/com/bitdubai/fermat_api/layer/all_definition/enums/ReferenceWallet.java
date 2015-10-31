package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatWalletEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet</code>
 * enums the platform wallet types.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 */
public enum ReferenceWallet implements FermatWalletEnum {

    BASIC_WALLET_BITCOIN_WALLET("BWBW"),
    BASIC_WALLET_DISCOUNT_WALLET("BWDW"),
    BASIC_WALLET_FIAT_WALLET("BWFW"),
    BASIC_WALLET_LOSS_PROTECTED_WALLET("BWLP"),
    COMPOSITE_WALLET_MULTI_ACCOUNT("CWMA");

    private String code;

    ReferenceWallet(String code) {
        this.code = code;
    }

    public static ReferenceWallet getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "BWBW": return BASIC_WALLET_BITCOIN_WALLET;
            case "BWDW": return BASIC_WALLET_DISCOUNT_WALLET;
            case "BWFW": return BASIC_WALLET_FIAT_WALLET;
            case "BWLP": return BASIC_WALLET_LOSS_PROTECTED_WALLET;
            case "CWMA": return COMPOSITE_WALLET_MULTI_ACCOUNT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ReferenceWallet enum");
        }
    }

    public static ReferenceWallet getByCategoryAndIdentifier(WalletCategory walletCategory, String platformIdentifier) throws InvalidParameterException, CallToGetByCodeOnNONEException {
        switch (walletCategory) {
            case REFERENCE_WALLET: return ReferenceWallet.getByCode(platformIdentifier);
            case NICHE_WALLET:     return NicheWallet.getByCode(platformIdentifier).getReferenceWallet();
            default:
                throw new InvalidParameterException();
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public ReferenceWallet getReferenceWallet() {
        return this;
    }

}
