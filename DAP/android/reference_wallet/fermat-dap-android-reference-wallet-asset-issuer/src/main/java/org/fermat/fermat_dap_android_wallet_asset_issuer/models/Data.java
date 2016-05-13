package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {

    public static List<DigitalAsset> getAllDigitalAssets(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<DigitalAsset> digitalAssets = new ArrayList<>();

            List<AssetIssuerWalletList> balances = moduleManager.getAssetIssuerWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset digitalAsset;
            if (balances != null) {
                for (AssetIssuerWalletList balance : balances) {
                    digitalAsset = new DigitalAsset();
                    digitalAsset.setAssetPublicKey(balance.getDigitalAsset().getPublicKey());
                    digitalAsset.setName(balance.getDigitalAsset().getName());
                    digitalAsset.setAvailableBalanceQuantity(balance.getQuantityAvailableBalance());
                    digitalAsset.setBookBalanceQuantity(balance.getQuantityBookBalance());
                    digitalAsset.setAvailableBalance(balance.getAvailableBalance());
                    Timestamp expirationDate = (Timestamp) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
                    digitalAsset.setExpDate(expirationDate);

                    List<Resource> resources = balance.getDigitalAsset().getResources();
                    if (resources != null && resources.size() > 0) {
                        digitalAsset.setImage(balance.getDigitalAsset().getResources().get(0).getResourceBinayData());
                    }

                    digitalAssets.add(digitalAsset);
                }
            }
        return digitalAssets;
    }

    public static DigitalAsset getDigitalAsset(AssetIssuerWalletSupAppModuleManager moduleManager, String digitalAssetPublicKey) throws CantLoadWalletException {
        List<AssetIssuerWalletList> balances = moduleManager.getAssetIssuerWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
        DigitalAsset digitalAsset;
        String publicKey;
        for (AssetIssuerWalletList balance : balances) {
            publicKey = balance.getDigitalAsset().getPublicKey();
            if (publicKey.equals(digitalAssetPublicKey)) {
                digitalAsset = new DigitalAsset();
                digitalAsset.setAssetPublicKey(balance.getDigitalAsset().getPublicKey());
                digitalAsset.setName(balance.getDigitalAsset().getName());
                digitalAsset.setAvailableBalanceQuantity(balance.getQuantityAvailableBalance());
                digitalAsset.setBookBalanceQuantity(balance.getQuantityBookBalance());
                digitalAsset.setAvailableBalance(balance.getAvailableBalance());
                Timestamp expirationDate = (Timestamp) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
                digitalAsset.setExpDate(expirationDate);

                List<Resource> resources = balance.getDigitalAsset().getResources();
                if (resources != null && resources.size() > 0) {
                    digitalAsset.setImage(balance.getDigitalAsset().getResources().get(0).getResourceBinayData());
                }
                return digitalAsset;
            }
        }
        return null;
    }

    public static List<UserDelivery> getUserDeliveryList(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<UserDelivery> users = new ArrayList<>();
        UserDelivery userDelivery;
        List<AssetStatistic> stats = moduleManager.getWalletStatisticsByAsset(walletPublicKey, digitalAsset.getName());
        for (AssetStatistic stat : stats) {
            if (stat.getStatus().equals(AssetCurrentStatus.ASSET_UNUSED)) {
                userDelivery = new UserDelivery(stat.getOwner().getName(), new Timestamp(stat.getDistributionDate().getTime()), stat.getStatus().getDescription());
                users.add(userDelivery);
            }
        }
        return users;
    }

    public static List<UserRedeemed> getUserRedeemedList(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<UserRedeemed> users = new ArrayList<>();
        UserRedeemed UserRedeemed;
        List<AssetStatistic> stats = moduleManager.getWalletStatisticsByAsset(walletPublicKey, digitalAsset.getName());
        for (AssetStatistic stat :
                stats) {
            if (stat.getStatus().equals(AssetCurrentStatus.ASSET_REDEEMED)) {
                UserRedeemed = new UserRedeemed(stat.getOwner().getName(), new Timestamp(stat.getDistributionDate().getTime()), stat.getStatus().getDescription(), stat.getRedeemPoint().getName());
                users.add(UserRedeemed);
            }
        }
        return users;
    }

    public static List<UserAppropiate> getUserAppropiateList(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<UserAppropiate> users = new ArrayList<>();
        UserAppropiate UserAppropiate;
        List<AssetStatistic> stats = moduleManager.getWalletStatisticsByAsset(walletPublicKey, digitalAsset.getName());
        for (AssetStatistic stat :
                stats) {
            if (stat.getStatus().equals(AssetCurrentStatus.ASSET_APPROPRIATED)) {
                UserAppropiate = new UserAppropiate(stat.getOwner().getName(), new Timestamp(stat.getAssetUsedDate().getTime()), stat.getStatus().getDescription());
                users.add(UserAppropiate);
            }
        }
        return users;
    }

    public static List<User> getConnectedUsers(AssetIssuerWalletSupAppModuleManager moduleManager, List<User> usersSelected) throws CantGetAssetUserActorsException {
//        List<User> users = new ArrayList<>();
//        users.add(new User("Frank Contreras"));
//        users.add(new User("Victor Mars"));
//        users.add(new User("Nerio Indriago"));
//        users.add(new User("Rodrigo Acosta"));
        List<User> users = new ArrayList<>();
        List<ActorAssetUser> actorAssetUsers = moduleManager.getAllAssetUserActorConnected();
        for (ActorAssetUser actorAssetUser : actorAssetUsers) {
            User newUser = new User(actorAssetUser.getName(), actorAssetUser);
//            int index = usersSelected.indexOf(newUser);
//            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
            users.add(newUser);
        }
        return users;
    }

    public static List<Group> getGroups(AssetIssuerWalletSupAppModuleManager moduleManager, List<Group> groupsSelected) throws CantGetAssetUserGroupException, CantGetAssetUserActorsException {
        List<Group> groups = new ArrayList<>();
        List<ActorAssetUserGroup> actorAssetUserGroups = moduleManager.getAssetUserGroupsList();
        for (ActorAssetUserGroup actorAssetUserGroup : actorAssetUserGroups) {
            Group newGroup = new Group(actorAssetUserGroup.getGroupName(), actorAssetUserGroup);
            List<ActorAssetUser> actorAssetUsers = moduleManager.getListActorAssetUserByGroups(actorAssetUserGroup.getGroupId());
            List<User> users = new ArrayList<>();
            for (ActorAssetUser actorAssetUser : actorAssetUsers) {
                users.add(new User(actorAssetUser.getName(), actorAssetUser));
            }
            newGroup.setUsers(users);
//            int index = usersSelected.indexOf(newUser);
//            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
            groups.add(newGroup);
        }
        return groups;
    }

    public static void setStatistics(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws CantGetAssetStatisticException, CantLoadWalletException {
        int unused = moduleManager.getWalletStatisticsByAssetAndStatus(walletPublicKey, digitalAsset.getName(), AssetCurrentStatus.ASSET_UNUSED).size();
        int appropriated = moduleManager.getWalletStatisticsByAssetAndStatus(walletPublicKey, digitalAsset.getName(), AssetCurrentStatus.ASSET_APPROPRIATED).size();
        int redeemed = moduleManager.getWalletStatisticsByAssetAndStatus(walletPublicKey, digitalAsset.getName(), AssetCurrentStatus.ASSET_REDEEMED).size();

        digitalAsset.setUnused(unused);
        digitalAsset.setAppropriated(appropriated);
        digitalAsset.setRedeemed(redeemed);
    }

    public static List<Transaction> getTransactions(AssetIssuerWalletSupAppModuleManager moduleManager, DigitalAsset digitalAsset) throws CantLoadWalletException, CantGetTransactionsException {
        List<Transaction> transactions = new ArrayList<>();
        List<AssetIssuerWalletTransaction> assetUserWalletTransactions = moduleManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY).getTransactionsForDisplay(digitalAsset.getAssetPublicKey());
        DAPActor dapActor;
        for (AssetIssuerWalletTransaction assetUserWalletTransaction :
                assetUserWalletTransactions) {
            if (assetUserWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                dapActor = assetUserWalletTransaction.getActorFrom();
            } else {
                dapActor = assetUserWalletTransaction.getActorTo();
            }
            Transaction transaction = new Transaction(assetUserWalletTransaction, dapActor);
            transactions.add(transaction);
        }
        return transactions;
    }
}
