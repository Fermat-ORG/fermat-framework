package org.fermat.fermat_dap_api.layer.agent.asset_miner.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;

/**
 * Created by rodrigo on 4/10/16.
 * Agent that monitors incoming Assets and Breaks them to get the Bitcoins.
 */
public interface AssetMinerAgentManager extends FermatManager {

    /**
     * Indicates if the Agent is enabled to execute automatic mining of expired Assets
     *
     * @return
     */
    boolean isEnabled();

    /**
     * Enable or disable the agent to perform automatic mining.
     *
     * @param enable true if enabled, false is disabled.
     */
    void setEnabled(boolean enable);

    /**
     * Sets the Wallet that this agent will use to send the bitcoins of the mined assets.
     *
     * @param btcWallet the bitcoin wallet that will be used to store the mined bitcoins.
     */
    void setBitcoinWallet(CryptoWalletWallet btcWallet);
}
