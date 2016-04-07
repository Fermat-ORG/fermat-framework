package org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;

import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletBalance {

    //TODO: Documentar
    long getBalance() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;

    List<AssetUserWalletList> getAssetUserWalletBalances() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;

    Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;

    void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws CantRegisterDebitException; //TODO: Debemos de definir la estructura de la transaccion

    void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws CantRegisterCreditException; //TODO: Debemos de definir la estructura de la transaccion

}
