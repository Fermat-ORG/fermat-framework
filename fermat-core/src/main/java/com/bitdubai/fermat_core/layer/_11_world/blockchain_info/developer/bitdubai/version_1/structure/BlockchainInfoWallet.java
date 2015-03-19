package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoWallet implements CryptoWallet , DealsWithPluginDatabaseSystem, DealsWithBlockchainInfoApi {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public long getWalletBalance(CryptoCurrency cryptoCurrency, UUID walletId) {
        return 0;
    }

    @Override
    public long getAddressBalance(CryptoAddress cryptoAddress) {
        return 0;
    }

    @Override
    public void sendCrypto(UUID walletId, CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress) {

    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

    }

    @Override
    public void setApiKey(String apiKey) {

    }
}
