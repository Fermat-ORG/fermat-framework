package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database.AssetRedeemPointWalletDao;

import java.util.List;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletBalanceImpl implements org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance {

    private AssetRedeemPointWalletDao assetRedeemPointWalletDao;
    private Broadcaster broadcaster;

    /**
     * Constructor.
     */
    public AssetRedeemPointWalletBalanceImpl(AssetRedeemPointWalletDao assetRedeemPointWalletDao, Broadcaster broadcaster) {
        this.assetRedeemPointWalletDao = assetRedeemPointWalletDao;
        this.broadcaster = broadcaster;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetRedeemPointWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetIssuerWalletBalances() throws CantCalculateBalanceException {
        return assetRedeemPointWalletDao.getBalanceByAssets();
    }

    @Override
    public void debit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetRedeemPointWalletDao.addDebit(assetRedeemPointWalletTransactionRecord, balanceType);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_REDEEM_WALLET.getCode(), "ASSET-REDEEM-DEBIT_" + "Name: " + assetRedeemPointWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
    }

    @Override
    public void credit(AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetRedeemPointWalletDao.addCredit(assetRedeemPointWalletTransactionRecord, balanceType);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_REDEEM_WALLET.getCode(), "ASSET-REDEEM-CREDIT_" + "Name: " + assetRedeemPointWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
    }
}
