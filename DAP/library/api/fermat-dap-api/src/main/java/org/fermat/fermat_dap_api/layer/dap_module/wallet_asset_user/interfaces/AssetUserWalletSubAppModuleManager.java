package org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions.CantProcessBuyingTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
public interface AssetUserWalletSubAppModuleManager extends ModuleManager<org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings, ActiveActorIdentityInformation> {
    /**
     * (non-Javadoc)
     *
     * @see List< org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList > getAssetIssuerWalletBalancesBook(String publicKey)
     */
    List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList> getAssetUserWalletBalances(String publicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    Map<ActorAssetIssuer, org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList> getAssetUserWalletBalancesByIssuer(String publicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint> getAllActorAssetRedeemPointConnected() throws org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint> getRedeemPointsConnectedForAsset(String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;

    org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    void createAssetUserWallet(String walletPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;

    IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException;

    void redeemAssetToRedeemPoint(String digitalAssetPublicKey, String walletPublicKey, List<org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint> actorAssetRedeemPoints, int assetAmount) throws org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;

    void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException;

    AssetFactory getAssetFactory(final String publicKey) throws CantGetAssetFactoryException, CantCreateFileException;

    PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException;

    void changeNetworkType(BlockchainNetworkType networkType);

    BlockchainNetworkType getSelectedNetwork();

    // ASSET TRANSFER METHODS.

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getAllActorUserRegistered() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup> getAssetUserGroupsList() throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUserActorsException;

    void addUserToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser user);

    void addGroupToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void removeUserToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser user);

    void removeGroupToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void clearDeliverList();

    void addAllRegisteredUsersToDeliver() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getSelectedUsersToDeliver();

    void transferAssets(String assetPublicKey, String walletPublicKey, int assetsAmount) throws CantTransferDigitalAssetsException;

    org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser getActorByPublicKey(String publicKey) throws CantGetAssetUserActorsException;

    //ASSET SELL METHODS
    void startSell(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser userToDeliver, long amountPerUnity, long totalAmount, int quantityToBuy, String assetToOffer) throws org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.exceptions.CantStartAssetSellTransactionException;


    //ASSET BUY METHODS

    /**
     * Gets the list of pending asset negotiations
     *
     * @return a list of pending asset negotiations
     * @throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetAssetNegotiationsException
     */
    List<org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation> getPendingAssetNegotiations() throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetAssetNegotiationsException;

    /**
     * This method notifies the seller that we've accepted one of its asset and the transaction for this asset can proceed.
     *
     * @param negotiationId {@link UUID} instance that is the {@link org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation} ID.
     * @throws CantProcessBuyingTransactionException
     */
    void acceptAsset(UUID negotiationId) throws CantProcessBuyingTransactionException;

    /**
     * This method notifies the seller that we've rejected one of its asset and the transaction for this asset won't proceed.
     *
     * @param negotiationId {@link UUID} instance that is the {@link org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation} ID.
     * @throws CantProcessBuyingTransactionException
     */
    void declineAsset(UUID negotiationId) throws CantProcessBuyingTransactionException;

    /**
     * This method get the available balance of bitcoin wallet
     * @param walletPublicKey
     * @return
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException
     * @throws CantCalculateBalanceException
     */
    long getBitcoinWalletBalance(String walletPublicKey) throws CantLoadWalletsException, CantCalculateBalanceException;

    List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException;

    org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser getSellerFromNegotiation(UUID negotiationID) throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetAssetNegotiationsException;

}
