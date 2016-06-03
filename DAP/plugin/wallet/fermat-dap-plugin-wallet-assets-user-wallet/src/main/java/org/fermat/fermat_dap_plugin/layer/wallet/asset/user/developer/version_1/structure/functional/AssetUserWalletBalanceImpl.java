package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.*;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetUserWalletBalanceImpl implements org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance {

    private final org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database.AssetUserWalletDao assetUserWalletDao;
    private Broadcaster broadcaster;

    /**
     * Constructor.
     */
    public AssetUserWalletBalanceImpl(org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database.AssetUserWalletDao assetUserWalletDao, Broadcaster broadcaster) {
        this.assetUserWalletDao = assetUserWalletDao;
        this.broadcaster = broadcaster;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetUserWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalances() throws CantCalculateBalanceException {
        return assetUserWalletDao.getBalanceByAssets();
    }

    @Override
    public Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer() throws CantCalculateBalanceException {
        List<AssetUserWalletList> balances = getAssetUserWalletBalances();
        HashMap<ActorAssetIssuer, AssetUserWalletList> toReturn = new HashMap<>();
        for (AssetUserWalletList balance : balances) {
            toReturn.put(assetUserWalletDao.getActorByAsset(balance.getDigitalAsset()), balance);
        }
        return toReturn;
    }

    @Override
    public void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetUserWalletDao.addDebit(assetUserWalletTransactionRecord, balanceType);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_USER_WALLET.getCode(), "ASSET-USER-DEBIT_" + "Name: " + assetUserWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
    }

    @Override
    public void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetUserWalletDao.addCredit(assetUserWalletTransactionRecord, balanceType);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_USER_WALLET.getCode(), "ASSET-USER-CREDIT_" + "Name: " + assetUserWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
    }
}
