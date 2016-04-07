package org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.NotEnoughAcceptsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;

import java.util.List;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetIssuerWalletSupAppModuleManager extends ModuleManager<org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings, ActiveActorIdentityInformation> {

    //TODO DOCUMENT ALL THESE METHODS WHEN THEY'RE IMPLEMENTED.

    List<AssetIssuerWalletList> getAssetIssuerWalletBalances(String Key) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    // ********************** USER LIST METHODS ************************************

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getAllActorUserRegistered() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup> getAssetUserGroupsList() throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUserActorsException;

    void toggleShowUsersOutsideTheirGroup();

    org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer getActiveAssetIssuerIdentity() throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;

    // ********************** ASSET DISTRIBUTION METHODS ************************************

    void addUserToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser user);

    void addGroupToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void removeUserToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser user);

    void removeGroupToDeliver(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup group) throws CantGetAssetUserActorsException;

    void clearDeliverList();

    void addAllRegisteredUsersToDeliver() throws CantGetAssetUserActorsException;

    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getSelectedUsersToDeliver();

    void distributionAssets(String assetPublicKey, String walletPublicKey, int assetsAmount) throws org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    void appropriateAsset(String digitalAssetPublicKey, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException, NotEnoughAcceptsException;

    // ********************** ISSUER WALLET METHODS ************************************

    org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

    void createWalletAssetIssuer(String walletPublicKey) throws CantCreateWalletException;

    AssetFactory getAssetFactory(final String Key) throws CantGetAssetFactoryException, CantCreateFileException;

    PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException;

    List<AssetStatistic> getWalletStatisticsByAssetAndStatus(String walletPublicKey, String assetName, AssetCurrentStatus assetCurrentStatus) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException, CantGetAssetStatisticException;

    List<AssetStatistic> getWalletStatisticsByAsset(String walletPublicKey, String assetName) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException, CantGetAssetStatisticException;

    void changeNetworkType(BlockchainNetworkType networkType);

    BlockchainNetworkType getSelectedNetwork();
}
