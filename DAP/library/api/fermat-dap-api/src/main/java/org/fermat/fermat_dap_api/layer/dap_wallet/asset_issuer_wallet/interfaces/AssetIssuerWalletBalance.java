package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletBalance extends Serializable {

    //TODO: Documentar
    long getBalance() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;

    List<AssetIssuerWalletList> getAssetIssuerWalletBalances() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;

    void debit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException; //TODO: Debemos de definir la estructura de la transaccion

    void credit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws CantRegisterCreditException; //TODO: Debemos de definir la estructura de la transaccion
}
