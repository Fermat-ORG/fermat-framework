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
    BASIC_WALLET_FERMAT_WALLET("BWFMW"),
    BNK_BANKING_WALLET("BNKW"),
    CASH_MONEY_WALLET("CASH"),
    COMPOSITE_WALLET_MULTI_ACCOUNT("CWMA"),
    CBP_CRYPTO_BROKER_WALLET("CBPB"),
    CBP_CRYPTO_CUSTOMER_WALLET("CBPC"),
    DAP_ASSET_ISSUER_WALLET("DAIW"),
    DAP_ASSET_USER_WALLET("DAUW"),
    DAP_REDEEM_POINT_WALLET("DARW"),;
    private String code;

    ReferenceWallet(String code) {
        this.code = code;
    }

    public static ReferenceWallet getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "BWBW":
                return BASIC_WALLET_BITCOIN_WALLET;
            case "BWDW":
                return BASIC_WALLET_DISCOUNT_WALLET;
            case "BWFW":
                return BASIC_WALLET_FIAT_WALLET;
            case "BWFERW":
                return BASIC_WALLET_FERMAT_WALLET;
            case "BWLP":
                return BASIC_WALLET_LOSS_PROTECTED_WALLET;
            case "BNKW":
                return BNK_BANKING_WALLET;
            case "CASH":
                return CASH_MONEY_WALLET;
            case "CWMA":
                return COMPOSITE_WALLET_MULTI_ACCOUNT;
            case "CBPB":
                return CBP_CRYPTO_BROKER_WALLET;
            case "CBPC":
                return CBP_CRYPTO_CUSTOMER_WALLET;
            case "DAIW":
                return DAP_ASSET_ISSUER_WALLET;
            case "DAUW":
                return DAP_ASSET_USER_WALLET;
            case "DARW":
                return DAP_REDEEM_POINT_WALLET;
            case "BWFMW":
                return BASIC_WALLET_FERMAT_WALLET;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ReferenceWallet enum");
        }
    }

    public static ReferenceWallet getByCategoryAndIdentifier(WalletCategory walletCategory, String platformIdentifier) throws InvalidParameterException, CallToGetByCodeOnNONEException {
        switch (walletCategory) {
            case REFERENCE_WALLET:
                return ReferenceWallet.getByCode(platformIdentifier);
            case NICHE_WALLET:
                return NicheWallet.getByCode(platformIdentifier).getReferenceWallet();
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
