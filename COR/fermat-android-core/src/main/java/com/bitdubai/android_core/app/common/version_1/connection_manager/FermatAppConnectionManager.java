package com.bitdubai.android_core.app.common.version_1.connection_manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection.ChatFermatAppConnection;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.app_connection.ChatIdentityFermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ComboAppType2FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection.BitcoinWalletFermatAppConnection;
import com.bitdubai.reference_niche_wallet.fermat_wallet.app_connection.FermatWalletAppConnection;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection.LossProtectedWalletFermatAppConnection;
import com.bitdubai.reference_wallet.crypto_broker_wallet.app_connection.CryptoBrokerWalletFermatAppConnection;
import com.bitdubai.reference_wallet.crypto_customer_wallet.app_connection.CryptoCustomerWalletFermatAppConnection;
import com.bitdubai.sub_app.chat_community.app_connection.ChatCommunityFermatAppConnection;
import com.bitdubai.sub_app.crypto_broker_community.app_connection.CryptoBrokerCommunityFermatAppConnection;
import com.bitdubai.sub_app.crypto_broker_identity.app_connection.CryptoBrokerIdentityFermatAppConnection;
import com.bitdubai.sub_app.crypto_customer_community.app_connection.CryptoCustomerCommunityFermatAppConnection;
import com.bitdubai.sub_app.crypto_customer_identity.app_connection.CryptoCustomerIdentityFermatAppConnection;
import com.bitdubai.sub_app.developer.app_connection.DeveloperFermatAppConnection;
import com.bitdubai.sub_app.intra_user_community.app_connection.CryptoWalletUserCommunityFermatAppConnection;
import com.bitdubai.sub_app.intra_user_identity.app_connection.CryptoWalletUserFermatAppConnection;
import com.bitdubai.sub_app.wallet_manager.app_connection.DesktopFermatAppConnection;

import org.fermat.fermat_dap_android_sub_app_asset_factory.app_connection.AssetFactoryFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.app_connection.CommunityAssetIssuerFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.app_connection.AssetIssuerFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.app_connection.CommunityAssetUserFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_asset_user_identity.app_connection.AssetUserFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.app_connection.CommunityRedeemPointFermatAppConnection;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.app_connection.RedeemPointFermatAppConnection;
import org.fermat.fermat_dap_android_wallet_asset_issuer.app_connection.WalletAssetIssuerFermatAppConnection;
import org.fermat.fermat_dap_android_wallet_asset_user.app_connection.WalletAssetUserFermatAppConnection;
import org.fermat.fermat_dap_android_wallet_redeem_point.app_connection.WalletRedeemPointFermatAppConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class FermatAppConnectionManager {

    private static final String TAG = "FermatAppConnection";

    private static Map<String, AppConnections> openConnections = new HashMap<>();

    private static AppConnections switchStatement(Context activity, String publicKey) {
        AppConnections fermatAppConnection = null;
        if (activity == null) Log.e(TAG, "Activity null");
        if (openConnections.containsKey(publicKey)) {
            fermatAppConnection = openConnections.get(publicKey);
            if(fermatAppConnection!=null) {
                if (fermatAppConnection.getContext() != null) {
                    if (!fermatAppConnection.getContext().equals(activity)) {
                        fermatAppConnection.clear();
                        fermatAppConnection.setContext(activity);
                    }
                } else {
                    fermatAppConnection.setContext(activity);
                }
            }else{
                Log.e(TAG,"AppConnection null, please check this in the FermatAppConnectionManager class");
            }
            return fermatAppConnection;
        }
        switch (publicKey) {
            //CCP WALLET
            case "reference_wallet":
                fermatAppConnection = new BitcoinWalletFermatAppConnection(activity);
                break;
            case "loss_protected_wallet":
                fermatAppConnection = new LossProtectedWalletFermatAppConnection(activity);
                break;
            case "fermat_wallet":
                fermatAppConnection = new FermatWalletAppConnection(activity);
                break;
            //CCP Sub Apps
            case "public_key_ccp_intra_user_identity":
                fermatAppConnection = new CryptoWalletUserFermatAppConnection(activity);
                break;
            case "public_key_intra_user_commmunity":
                fermatAppConnection = new CryptoWalletUserCommunityFermatAppConnection(activity);
                break;
            //DESKTOP
            case "main_desktop":
                fermatAppConnection = new DesktopFermatAppConnection(activity);
                break;
            //DAP WALLETS
            case "asset_issuer" :
                fermatAppConnection = new WalletAssetIssuerFermatAppConnection(activity);
                break;
            case "asset_user"   :
                fermatAppConnection = new WalletAssetUserFermatAppConnection(activity);
                break;
            case "redeem_point" :
                fermatAppConnection = new WalletRedeemPointFermatAppConnection(activity);
                break;
            //DAP Sub Apps
            case "public_key_dap_asset_issuer_identity":
                fermatAppConnection = new AssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_asset_user_identity":
                fermatAppConnection = new AssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_redeem_point_identity":
                fermatAppConnection = new RedeemPointFermatAppConnection(activity);
                break;
            case "public_key_dap_factory":
                fermatAppConnection = new AssetFactoryFermatAppConnection(activity);
                break;
            case "public_key_dap_issuer_community":
                fermatAppConnection = new CommunityAssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_user_community":
                fermatAppConnection = new CommunityAssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_redeem_point_community":
                fermatAppConnection = new CommunityRedeemPointFermatAppConnection(activity);
                break;

            //PIP Sub Apps
            case "public_key_pip_developer_sub_app":
                fermatAppConnection = new DeveloperFermatAppConnection(activity);
                break;
            //CBP WALLETS
            case "crypto_broker_wallet":
                fermatAppConnection = new CryptoBrokerWalletFermatAppConnection(activity);
                break;
            case "crypto_customer_wallet":
                fermatAppConnection = new CryptoCustomerWalletFermatAppConnection(activity);
                break;
            //CBP Sub Apps
            case "public_key_crypto_broker_community":
                fermatAppConnection = new CryptoBrokerCommunityFermatAppConnection(activity);
                break;
            case "public_key_crypto_customer_community":
                fermatAppConnection = new CryptoCustomerCommunityFermatAppConnection(activity);
                break;
            case "sub_app_crypto_broker_identity":
                fermatAppConnection = new CryptoBrokerIdentityFermatAppConnection(activity);
                break;
            case "sub_app_crypto_customer_identity":
                fermatAppConnection = new CryptoCustomerIdentityFermatAppConnection(activity);
                break;
//            //CASH WALLET
//            case "cash_wallet":
//                fermatAppConnection = new CashMoneyWalletFermatAppConnection(activity, null);
//                break;
//            //BANKING WALLET
//            case "banking_wallet":
//                fermatAppConnection = new BankMoneyWalletFermatAppConnection(activity);
//                break;
//            // WPD Sub Apps
//            case "public_key_store":
//                fermatAppConnection = new WalletStoreFermatAppConnection(activity);
//                break;



//            // CHT Sub Apps
            case "public_key_cht_chat":
                fermatAppConnection = new ChatFermatAppConnection(activity);
                break;
            case "public_key_cht_community":
                fermatAppConnection = new ChatCommunityFermatAppConnection(activity);
                break;
            case "public_key_cht_identity_chat":
                fermatAppConnection = new ChatIdentityFermatAppConnection(activity);
                break;
//            //TKY Fan wallet
//            case "fan_wallet":
//                fermatAppConnection = new FanWalletFermatAppConnection(activity);
//                break;
//            //TKY Sub apps
//            case "public_key_tky_artist_identity":
//                fermatAppConnection = new TkyArtistIdentityAppConnection(activity);
//                break;
//            case "sub_app_tky_fan_create_identity":
//                fermatAppConnection = new TokenlyFanUserFermatAppConnection(activity);
//                break;
//            // Art Sub apps
//            case "sub_app_art_artist_community":
//                fermatAppConnection = new ArtistCommunityFermatAppConnection(activity);
//                break;
//            case "public_key_art_fan_community":
//                fermatAppConnection = new FanCommunityFermatAppConnection(activity);
//                break;
//            case "public_key_art_fan_identity":
//                fermatAppConnection = new ArtFanUserFermatAppConnection(activity);
//                break;
//
//            case "public_key_art_artist_identity":
//                fermatAppConnection = new ArtArtistIdentityAppConnection(activity);
//                break;
//
//            case "public_key_art_music_player":
//                fermatAppConnection = new MusicPlayerFermatAppConnection(activity);
//                break;
            default:
                fermatAppConnection = new EmptyFermatAppConnection(activity);
                break;
        }

        if (!openConnections.containsKey(publicKey)) {
            openConnections.put(publicKey, fermatAppConnection);
        }

        return fermatAppConnection;
    }

    public static AppConnections getFermatAppConnection(String publicKey, Context context, FermatSession session) {
        AppConnections fermatAppConnection = switchStatement(context, publicKey);
        if (!publicKey.equals(session.getAppPublicKey()) && session instanceof ComboAppType2FermatSession) {
            try {
                session = ((ComboAppType2FermatSession) session).getFermatSession(publicKey, FermatSession.class);
            } catch (InvalidParameterException e) {
                Log.e(TAG, "Probando una cosa, no se asusten");
                e.printStackTrace();
            }
        }
        if(fermatAppConnection!=null) fermatAppConnection.setFullyLoadedSession(session);
        return fermatAppConnection;
    }

    public static AppConnections getFermatAppConnection(String appPublicKey, Activity activity) {
        return switchStatement(activity, appPublicKey);
    }

    public static AppConnections getFermatAppConnection(String appPublicKey, Context context) {
        return switchStatement(context, appPublicKey);
    }
}