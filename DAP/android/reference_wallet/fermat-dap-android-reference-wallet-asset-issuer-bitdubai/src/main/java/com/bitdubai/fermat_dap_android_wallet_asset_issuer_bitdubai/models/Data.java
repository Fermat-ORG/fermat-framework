package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetStatistic;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        List<AssetIssuerWalletList> assets = moduleManager.getAssetIssuerWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;
        for (AssetIssuerWalletList asset : assets) {
            AssetFactory assetFactory = moduleManager.getAssetFactory(asset.getAssetPublicKey());

            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getAssetPublicKey());
            digitalAsset.setName(asset.getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getAvailableBalance());
            digitalAsset.setExpDate(assetFactory.getExpirationDate());

            List<Resource> resources = assetFactory.getResources();
            if (resources != null && resources.size() > 0) {
                digitalAsset.setImage(moduleManager.getAssetFactoryResource(resources.get(0)).getContent());
            }

            digitalAssets.add(digitalAsset);
        }
        return digitalAssets;
    }

    public static List<UserDelivery> getUserDeliveryList(String walletPublicKey, DigitalAsset digitalAsset, AssetIssuerWalletSupAppModuleManager moduleManager) throws Exception {
        //TODO get from database
//        List<UserDelivery> users = new ArrayList<>();
//        UserDelivery userDelivery = new UserDelivery();
//        userDelivery.setUserName("Janet Williams");
//        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
//        userDelivery.setDeliveryStatus("Redeemed");
//        users.add(userDelivery);
//        userDelivery = new UserDelivery();
//        userDelivery.setUserName("Amanda Hood");
//        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
//        userDelivery.setDeliveryStatus("Appropriated");
//        users.add(userDelivery);
//        userDelivery = new UserDelivery();
//        userDelivery.setUserName("Tom Snow");
//        userDelivery.setDeliveryDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
//        userDelivery.setDeliveryStatus("Unused");
//        users.add(userDelivery);
        List<UserDelivery> users = new ArrayList<>();
        UserDelivery userDelivery;
        List<AssetStatistic> stats = moduleManager.getWalletStatisticsByAsset(walletPublicKey, digitalAsset.getName());
        for (AssetStatistic stat :
                stats) {
            userDelivery = new UserDelivery(stat.getRedeemPoint().getName(), new Timestamp(stat.getDistributionDate().getTime()), stat.getStatus().getCode());
            users.add(userDelivery);
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
