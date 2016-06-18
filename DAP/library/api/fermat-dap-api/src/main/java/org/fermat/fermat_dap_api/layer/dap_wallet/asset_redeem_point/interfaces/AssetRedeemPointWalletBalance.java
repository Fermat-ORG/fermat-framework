package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;

import java.util.List;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletBalance {
    //TODO: Documentar
    long getBalance() throws CantCalculateBalanceException;

    List<AssetRedeemPointWalletList> getAssetIssuerWalletBalances() throws CantCalculateBalanceException;

    void debit(org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws CantRegisterDebitException; //TODO: Debemos de definir la estructura de la transaccion

    void credit(org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws CantRegisterCreditException; //TODO: Debemos de definir la estructura de la transaccion

}
