package com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by rodrigo on 6/29/16.
 */
public abstract class AbstractBlockChainConfiguration implements BlockchainConfiguration {
    /**
     * Gets the blockchain provider. The platform currently supports two providers Bitcoin and Fermat, but it will
     * include more in the future, like Etherium, Rootstock, etc.
     * Each provider needs to specify a value.
     * @return
     */
    @Override
    public abstract BlockchainProvider getBlockchainProvider();

    /**
     * The amount of blocks a transaction must have above to be considered Irreversible.
     * We are setting default to 3, but each provider may overwrite this method and specify a different value
     * @return
     */
    @Override
    public int getIrreversibleBlockDepth() {
        return 3;
    }

    /**
     * The amounts of blocks a transaction must have or to be considered confirmed.
     * We are setting this value to 1, but each provider may overwrite this method.
     * @return
     */
    @Override
    public int getConfirmedBlockDepth() {
        return 1;
    }

    /**
     * The Agent version used to connect to the network of this provider.
     * @return
     */
    @Override
    public String getAgentName() {
        return "Fermat Agent";
    }

    /**
     * The Agent version used to connect to the network of this provider.
     * @return
     */
    @Override
    public Version getAgentVersion() {
        return new Version(1,0,0);
    }

    /**
     * The amount of connections needed to broadcast a transaction. Usually more than 1.
     * We will wait confirmation from this amount of nodes to consider a transaction broadcasted.
     * We are defaulting to 2 nodes, but each provider may overwrite.
     * @return
     */
    @Override
    public int getMinimumBroadcastConnections() {
        return 2;
    }

    /**
     * The amount of minutes we will wait to cancel the broadcasting and thrown an error.
     * We are defaulting to 2 minutes, but each provider may overwrite.
     * @return
     */
    @Override
    public int getConnectionBroadcastingTimeOutInMinutes() {
        return 2;
    }


    /**
     * The minimum value we are allowing transactions to send on this provider. Since miners may not accept very
     * low values, we are forcing this minimum for every transaction output.
     * We are defaulting to 35430 of whatever coin the provider supports, but this may be overwrite by the provider
     * @return
     */
    @Override
    public long getMinNonDustySendValue() {
        return 35430;
    }

    /**
     * If no fee is specified from the wallet, we will use this value as a default for every transaction.
     * We are defaulting to 30000 of whatever coin the provider supports, but this may be overwrite by the provider
     * @return
     */
    @Override
    public long getFixedFeeValue() {
        return 30000;
    }
}
