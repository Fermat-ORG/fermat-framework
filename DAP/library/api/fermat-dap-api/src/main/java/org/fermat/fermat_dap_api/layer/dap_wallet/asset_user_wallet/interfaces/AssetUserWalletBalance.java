package org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletBalance extends Serializable {

    //TODO: Documentar
    long getBalance() throws CantCalculateBalanceException;

    List<AssetUserWalletList> getAssetUserWalletBalances() throws CantCalculateBalanceException;

    Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer() throws CantCalculateBalanceException;

    void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException; //TODO: Debemos de definir la estructura de la transaccion

    void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException; //TODO: Debemos de definir la estructura de la transaccion

}
