package com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 28/9/15.
 */
public class CryptoBrokerWalletIdentityMiddleware {

    String identity;
    List<UUID> wallets;

    public CryptoBrokerWalletIdentityMiddleware(String identity, UUID wallet){
        this.identity = identity;

        this.wallets = new ArrayList<>();
        this.wallets.add(wallet);
    }

    public String getIdentity() {
        return identity;
    }

    public List<UUID> getListWallets() {
        return wallets;
    }

    public void addWallet(UUID wallet) {
        this.wallets.add(wallet);
    }
}
