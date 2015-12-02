package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetHistoryException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetIssuerWalletSupAppModuleManager extends ModuleManager {

    //TODO DOCUMENT ALL THESE METHODS WHEN THEY'RE IMPLEMENTED.

    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesAvailable(String publicKey) throws CantLoadWalletException;

    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook(String publicKey) throws CantLoadWalletException;

    // ********************** USER LIST METHODS ************************************

    List<ActorAssetUser> getAllActorUserRegistered() throws CantGetAssetUserActorsException;

    List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

    List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupExcepcion;

    List<ActorAssetUser> getListActorAssetUserByGroups(ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void toggleShowUsersOutsideTheirGroup();

    // ********************** USER GROUP METHODS ************************************
    void createAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantCreateAssetUserGroupException;

    void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException;

    void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException;

    void addAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException;

    void removeAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantCreateAssetUserGroupException;

    // ********************** ASSET DISTRIBUTION METHODS ************************************

    void addUserToDeliver(ActorAssetUser user);

    void addGroupToDeliver(ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void removeUserToDeliver(ActorAssetUser user);

    void removeGroupToDeliver(ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void clearDeliverList();

    void addAllRegisteredUsersToDeliver() throws CantGetAssetUserActorsException;

    List<ActorAssetUser> getSelectedUsersToDeliver();

    void distributionAssets(String assetPublicKey, String walletPublicKey) throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException;

    // ********************** ASSET STATISTIC METHODS ************************************

    List<AssetIssuerWalletTransaction> getTransactionsAssetAll(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException;

    List<DigitalAsset> getMyAssets();

    int getUnusedAmountForAsset(String assetPublicKey) throws CantGetAssetHistoryException;

    int getAppropriatedAmountForAsset(String assetPublicKey) throws CantGetAssetHistoryException;

    int getRedeemedAmountForAsset(String assetPublicKey) throws CantGetAssetHistoryException;

    List<AssetStatistic> getAllStatisticForGivenAsset(String assetPublicKey) throws CantGetAssetStatisticException;

    List<AssetStatistic> getStatisticForGivenAssetByStatus(String assetPublicKey, AssetCurrentStatus status) throws CantGetAssetStatisticException;

    List<AssetStatistic> getAllStatisticForAllAssets() throws CantGetAssetStatisticException;

    List<AssetStatistic> getStatisticForAllAssetsByStatus(AssetCurrentStatus status) throws CantGetAssetStatisticException;

    // ********************** ISSUER WALLET METHODS ************************************

    AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws CantLoadWalletException;

    void createWalletAssetIssuer(String walletPublicKey) throws CantCreateWalletException;
}
