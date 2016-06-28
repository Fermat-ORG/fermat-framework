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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data implements Serializable {

    public static List<DigitalAsset> getAllDigitalAssets(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        try {
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
                    Date expirationDate = (Date) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
                    digitalAsset.setExpDate(expirationDate);

                    List<Resource> resources = balance.getDigitalAsset().getResources();
                    if (resources != null && resources.size() > 0) {
                        digitalAsset.setImage(balance.getDigitalAsset().getResources().get(0).getResourceBinayData());
                    }

                    digitalAssets.add(digitalAsset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digitalAssets;
    }

    public static List<DigitalAsset> getAllDigitalAssetsDateSorted(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        try {

            List<AssetIssuerWalletList> balances = moduleManager.getAssetIssuerWalletBalances(WalletUtilities.WALLET_PUBLIC_KEY);
            DigitalAsset digitalAsset;
            for (AssetIssuerWalletList balance : balances) {
                digitalAsset = new DigitalAsset();
                digitalAsset.setAssetPublicKey(balance.getDigitalAsset().getPublicKey());
                digitalAsset.setName(balance.getDigitalAsset().getName());
                digitalAsset.setAvailableBalanceQuantity(balance.getQuantityAvailableBalance());
                digitalAsset.setBookBalanceQuantity(balance.getQuantityBookBalance());
                digitalAsset.setAvailableBalance(balance.getAvailableBalance());
                Date expirationDate = (Date) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
                digitalAsset.setExpDate(expirationDate);

                List<Resource> resources = balance.getDigitalAsset().getResources();
                if (resources != null && resources.size() > 0) {
                    digitalAsset.setImage(balance.getDigitalAsset().getResources().get(0).getResourceBinayData());
                }

                digitalAsset.setLastTransactionDate(moduleManager.assetLastTransaction(WalletUtilities.WALLET_PUBLIC_KEY,digitalAsset.getAssetPublicKey()));

                digitalAssets.add(digitalAsset);
            }

            Collections.sort(digitalAssets, new Comparator<DigitalAsset>() {
                @Override
                public int compare(DigitalAsset lhs, DigitalAsset rhs) {
                    if (lhs.getLastTransactionDate().getTime() > rhs.getLastTransactionDate().getTime())
                        return -1;
                    else if (lhs.getLastTransactionDate().getTime() < rhs.getLastTransactionDate().getTime())
                        return 1;
                    return 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
                Date expirationDate = (Date) balance.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
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

    public static List<UserDelivery> getStats(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<UserDelivery> users = new ArrayList<>();
        UserDelivery userDelivery;
        List<AssetStatistic> stats = moduleManager.getWalletStatisticsByAsset(walletPublicKey, digitalAsset.getName());
        for (AssetStatistic stat : stats) {
            if (!stat.getStatus().equals(AssetCurrentStatus.ASSET_CREATED)) {
                userDelivery = new UserDelivery(stat.getOwner().getProfileImage(), stat.getOwner().getName(), new Timestamp(stat.getDistributionDate().getTime()), stat.getStatus().getCode(), stat.getStatus().getDescription());
                users.add(userDelivery);
            }
        }
        return users;
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
                UserAppropiate = new UserAppropiate(stat.getOwner().getName(), stat.getAssetUsedDate(), stat.getStatus().getDescription());
                users.add(UserAppropiate);
            }
        }
        return users;
    }

    public static List<User> getConnectedUsers(AssetIssuerWalletSupAppModuleManager moduleManager, List<User> usersSelected) throws CantGetAssetUserActorsException {
        List<User> users = new ArrayList<>();
        List<ActorAssetUser> actorAssetUsers = moduleManager.getAllAssetUserActorConnected();
        for (ActorAssetUser actorAssetUser : actorAssetUsers) {
            User newUser = new User(actorAssetUser.getName(), actorAssetUser);
            users.add(newUser);
        }

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return rhs.getName().compareTo(lhs.getName());
            }
        });

        String lastLetter = "";
        for (User user : users) {
            String letter = user.getName().substring(0, 1);
            if (!letter.equals(lastLetter)) {
                user.setFirst(true);
                lastLetter = letter;
            }
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
            groups.add(newGroup);
        }

        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group lhs, Group rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        String lastLetter = "";
        for (Group group : groups) {
            String letter = group.getName().substring(0, 1);
            if (!letter.equals(lastLetter)) {
                group.setFirst(true);
                lastLetter = letter;
            }
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

        List<AssetIssuerWalletTransaction> assetUserWalletTransactions = moduleManager.getTransactionsForDisplay(WalletUtilities.WALLET_PUBLIC_KEY, digitalAsset.getAssetPublicKey());
        DAPActor dapActor;

        for (AssetIssuerWalletTransaction assetUserWalletTransaction : assetUserWalletTransactions) {
            if (assetUserWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                dapActor = assetUserWalletTransaction.getActorFrom();
            } else {
                dapActor = assetUserWalletTransaction.getActorTo();
            }
            Transaction transaction = new Transaction(assetUserWalletTransaction, dapActor);
            transactions.add(transaction);
        }

               Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return (int) (o2.getDate().getTime() - o1.getDate().getTime());
            }
        });
        return transactions;
    }

    public static List<String> getStatsOptions() {
        List<String> arr = new ArrayList<>();
        arr.add("All");
        AssetCurrentStatus[] statuses = AssetCurrentStatus.values();
        for (AssetCurrentStatus status : statuses) {
            if (!status.equals(AssetCurrentStatus.ASSET_CREATED.ASSET_CREATED)) {
                arr.add(status.getDescription());
            }
        }
        return arr;
    }
}
