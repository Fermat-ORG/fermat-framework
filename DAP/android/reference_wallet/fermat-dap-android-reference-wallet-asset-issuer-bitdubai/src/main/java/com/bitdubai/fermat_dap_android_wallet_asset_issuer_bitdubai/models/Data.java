package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<AssetIssuerWalletList> balances = moduleManager.getAssetIssuerWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
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
        return digitalAssets;
    }

    public static DigitalAsset getDigitalAsset(AssetIssuerWalletSupAppModuleManager moduleManager, String digitalAssetPublicKey) throws CantLoadWalletException {
        List<AssetIssuerWalletList> balances = moduleManager.getAssetIssuerWalletBalances("walletPublicKeyTest");
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
                UserRedeemed = new UserRedeemed(stat.getOwner().getName(), new Timestamp(stat.getDistributionDate().getTime()), stat.getStatus().getDescription());
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
        for (ActorAssetUser actorAssetUser:actorAssetUsers) {
            User newUser = new User(actorAssetUser.getName(), actorAssetUser);
//            int index = usersSelected.indexOf(newUser);
//            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
            users.add(newUser);
        }
        return users;
    }

    public static List<Group> getGroups(AssetIssuerWalletSupAppModuleManager moduleManager, List<Group> groupsSelected) throws CantGetAssetUserGroupException {
        List<Group> groups = new ArrayList<>();
        List<ActorAssetUserGroup> actorAssetUserGroups = moduleManager.getAssetUserGroupsList();
        for (ActorAssetUserGroup actorAssetUserGroup:actorAssetUserGroups) {
            Group newUser = new Group(actorAssetUserGroup.getGroupName(), actorAssetUserGroup);
//            int index = usersSelected.indexOf(newUser);
//            if (index > 0) newUser.setSelected(usersSelected.get(index).isSelected());
            groups.add(newUser);
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
}
