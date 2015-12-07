package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.session.IssuerIdentitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.session.UserIdentitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.sessions.AssetRedeemPointCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.session.RedeemPointIdentitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSession;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager {

    private Map<String, SubAppsSession> lstSubAppSession;


    public SubAppSessionManager() {
        lstSubAppSession = new HashMap<>();
    }


    @Override
    public Map<String, SubAppsSession> listOpenSubApps() {
        return lstSubAppSession;
    }

    @Override
    public SubAppsSession openSubAppSession(InstalledSubApp subApp,String subAppType, ErrorManager errorManager, ModuleManager moduleManager) {
        SubAppsSession subAppsSession = null;

        try {
            switch (SubApps.getByCode(subAppType)) {
                case CWP_WALLET_FACTORY:
                    subAppsSession = new WalletFactorySubAppSession(subApp, errorManager, (WalletFactoryManager) moduleManager);
                    break;
                case CWP_WALLET_STORE:
                    subAppsSession = new WalletStoreSubAppSession(subApp, errorManager, (WalletStoreModuleManager) moduleManager);
                    break;
                case CWP_DEVELOPER_APP:
                    subAppsSession = new DeveloperSubAppSession(subApp, errorManager, (ToolManager) moduleManager);
                    break;
                case CWP_WALLET_MANAGER:
                    break;
                case CWP_WALLET_PUBLISHER:
                    subAppsSession = new WalletPublisherSubAppSession(subApp, errorManager, (WalletPublisherModuleManager) moduleManager);
                    break;
                case CWP_INTRA_USER_IDENTITY:
                    subAppsSession = new IntraUserIdentitySubAppSession(subApp, errorManager, (IntraWalletUserIdentityManager) moduleManager);
                    break;
                case DAP_ASSETS_IDENTITY_ISSUER:
                    subAppsSession = new IssuerIdentitySubAppSession(subApp, errorManager, (IdentityAssetIssuerManager) moduleManager);
                    break;
                case DAP_ASSETS_IDENTITY_USER:
                    subAppsSession = new UserIdentitySubAppSession(subApp, errorManager, (IdentityAssetUserManager) moduleManager);
                    break;
                case DAP_REDEEM_POINT_IDENTITY:
                    subAppsSession = new RedeemPointIdentitySubAppSession(subApp, errorManager, (RedeemPointIdentityManager) moduleManager);
                    break;
                case CCP_INTRA_USER_COMMUNITY:
                    subAppsSession = new IntraUserSubAppSession(subApp, errorManager, (IntraUserModuleManager) moduleManager);
                    break;
                case DAP_ASSETS_FACTORY:
                    subAppsSession = new AssetFactorySession(subApp, errorManager, (AssetFactoryModuleManager) moduleManager);
                    break;
                case DAP_ASSETS_COMMUNITY_ISSUER:
                    subAppsSession = new AssetIssuerCommunitySubAppSession(subApp, errorManager, (AssetIssuerCommunitySubAppModuleManager) moduleManager);
                    break;
                case DAP_ASSETS_COMMUNITY_USER:
                    subAppsSession = new AssetUserCommunitySubAppSession(subApp, errorManager, (AssetUserCommunitySubAppModuleManager) moduleManager);
                    break;
                case DAP_ASSETS_COMMUNITY_REDEEM_POINT:
                    subAppsSession = new AssetRedeemPointCommunitySubAppSession(subApp, errorManager, (RedeemPointCommunitySubAppModuleManager) moduleManager);
                    break;
                case CBP_CRYPTO_BROKER_IDENTITY:
                    subAppsSession = new CryptoBrokerIdentitySubAppSession(subApp, errorManager, (CryptoBrokerIdentityModuleManager) moduleManager);
                    break;
                case CBP_CRYPTO_CUSTOMER_IDENTITY:
                    subAppsSession = new CryptoCustomerIdentitySubAppSession(subApp, errorManager, (CryptoCustomerIdentityModuleManager) moduleManager);
                    break;
                default:
                    return null;
                //throw new FermatException("")
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        lstSubAppSession.put(subApp.getAppPublicKey(), subAppsSession);
        return subAppsSession;
    }


    @Override
    public boolean closeSubAppSession(String subAppPublicKey) {
        try {
            lstSubAppSession.remove(subAppPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public boolean isSubAppOpen(String subAppPublicKey) {
        return lstSubAppSession.containsKey(subAppPublicKey);
    }

    @Override
    public SubAppsSession getSubAppsSession(String subAppPublicKey) {
        return lstSubAppSession.get(subAppPublicKey);
    }


}
