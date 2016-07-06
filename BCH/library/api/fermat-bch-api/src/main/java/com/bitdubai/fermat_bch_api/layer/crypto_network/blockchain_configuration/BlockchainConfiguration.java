package com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by rodrigo on 6/29/16.
 * We are defining the properties for any valid provider. Every provider needs to define and publish these
 * values. Values may be common for all providers or they may choose to overwrite and specify their on.
 */
public interface BlockchainConfiguration {
    /**
     * Gets the blockchain provider. The platform currently supports two providers Bitcoin and Fermat, but it will
     * include more in the future, like Etherium, Rootstock, etc.
     * @return
     */
    BlockchainProvider getBlockchainProvider();

    /**
     * The amount of blocks a transaction must have above to be considered Irreversible.
     * It may vary for every provider. Usually it may be 3 or 6 blocks.
     * @return
     */
    int getIrreversibleBlockDepth();

    /**
     * The amounts of blocks a transaction must have or to be considered confirmed.
     * It may vary for every provider, usually 1 block is enough to be confirmed.
     * @return
     */
    int getConfirmedBlockDepth();


    /**
     * The Agent name the platform is using to connect to the network of this provider
     * @return
     */
    String getAgentName();

    /**
     * The Agent version used to connect to the network of this provider.
     * @return
     */
    Version getAgentVersion();

    /**
     * The amount of connections needed to broadcast a transaction. Usually more than 1.
     * We will wait confirmation from this amount of nodes to consider a transaction broadcasted.
     * @return
     */
    int getMinimumBroadcastConnections();

    /**
     * The amount of minutes we will wait to cancel the broadcasting and thrown an error.
     * @return
     */
    int getConnectionBroadcastingTimeOutInMinutes();

    /**
     * The minimun value we are allowing transactions to send on this provider. Since miners may not accept very
     * low values, we are forcing this minimun for every transaction output.
     * @return
     */
    long getMinNonDustySendValue();

    /**
     * If no fee is specified from the wallet, we will use this value as a default for every transaction.
     * @return
     */
    long getFixedFeeValue();
}
