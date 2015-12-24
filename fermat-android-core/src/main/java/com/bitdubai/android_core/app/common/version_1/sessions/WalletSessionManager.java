package com.bitdubai.android_core.app.common.version_1.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class WalletSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSessionManager {

    private Map<String, FermatSession<InstalledWallet>> lstWalletSession;

    public WalletSessionManager() {
        lstWalletSession = new HashMap<>();
    }

    @Override
    public Map<String, FermatSession<InstalledWallet> > listOpenWallets() {
        return lstWalletSession;
    }


    @Override
    public FermatSession<InstalledWallet> openWalletSession(InstalledWallet installedWallet,ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections) {
        FermatSession<InstalledWallet> walletSession  = appConnections.buildSession(installedWallet,moduleManager,errorManager);
        lstWalletSession.put(installedWallet.getAppPublicKey(), walletSession);

//        if (installedWallet != null) {
//            switch (installedWallet.getWalletCategory()) {
//                case REFERENCE_WALLET:
//                    switch (installedWallet.getWalletPublicKey()) {
//                        case "reference_wallet":
//                            walletSession = new ReferenceWalletSession(installedWallet, cryptoWalletManager, walletSettings, walletResourcesProviderManager, errorManager,(IntraUserModuleManager)moduleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "test_wallet":
//                            walletSession = new com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session.ReferenceWalletSession(installedWallet, cryptoWalletManager, walletSettings, walletResourcesProviderManager, errorManager);
//                            //lstWalletSession.put(installedWallet.getWalletPublicKey(),walletSession);
//                            return walletSession;
//                        case "crypto_broker_wallet":
//                            walletSession = new CryptoBrokerWalletSession(installedWallet, errorManager, walletResourcesProviderManager, cryptoBrokerWalletModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "crypto_customer_wallet":
//                            walletSession = new CryptoCustomerWalletSession(installedWallet, errorManager, walletResourcesProviderManager, cryptoCustomerWalletModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "asset_issuer":
//                            walletSession = new AssetIssuerSession(installedWallet, errorManager, assetIssuerWalletManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "asset_user":
//                            walletSession = new AssetUserSession(walletResourcesProviderManager, installedWallet, errorManager, assetUserModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "redeem_point":
//                            walletSession = new RedeemPointSession(walletResourcesProviderManager, installedWallet, errorManager, assetRedeemPointModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "banking_wallet":
//                            walletSession = new BankMoneyWalletSession( installedWallet, errorManager, walletResourcesProviderManager, bankMoneyWalletModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                        case "cash_wallet":
//                            walletSession = new CashMoneyWalletSession( installedWallet, errorManager, walletResourcesProviderManager, cashMoneyWalletModuleManager);
//                            lstWalletSession.put(installedWallet.getWalletPublicKey(), walletSession);
//                            return walletSession;
//                    }
//
//                case NICHE_WALLET:
//                    break;
//                case BRANDED_NICHE_WALLET:
//                    break;
//                case BRANDED_REFERENCE_WALLET:
//                    break;
//                default:
//                    walletSession = new com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session.ReferenceWalletSession(installedWallet, cryptoWalletManager, walletSettings, walletResourcesProviderManager, errorManager);
//                    break;
//            }
//        } else {
//            walletSession = new ReferenceWalletSession(installedWallet, cryptoWalletManager, walletSettings, walletResourcesProviderManager, errorManager,(IntraUserModuleManager)moduleManager);
//        }

        return walletSession;
    }


    @Override
    public boolean closeWalletSession(String publicKey) {
        try {
            lstWalletSession.remove(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isWalletOpen(String publicKey) {
        return lstWalletSession.containsKey(publicKey);
    }

    @Override
    public FermatSession<InstalledWallet> getWalletSession(String publicKey) {
        return lstWalletSession.get(publicKey);
    }

}
