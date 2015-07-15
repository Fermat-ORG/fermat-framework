package com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectVersion;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPendingPublicationWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.WalletPublicationFailedException;

import java.util.List;
import java.util.Map;

/**
 * This interface provide the services needed by the front-end to display the information about the
 * published wallets
 *
 * @author Ezequiel Postan
 */
public interface WalletPublisherManager {

    /**
     * This method asks the wallet factory plugin in the middleware layer the list of closed wallet factory
     * projects and returns the ones that haven't been published yet
     *
     * @return The wallet factory project names and associated versions that
     * @throws CantGetPendingPublicationWalletsException
     */
    public Map<String,List<WalletFactoryProjectVersion>> getPendingPublicationWallets() throws CantGetPendingPublicationWalletsException;

    public Map<String,List<PublishedWallet>> getPublishedWallets() throws CantGetPublishedWalletsException;

    /**
     * This method tells us if the given wallet factory project can be published in the wallet store.
     * This is done by consulting the plugin wallet publisher of the middleware layer.
     *
     * @param walletFactoryProjectVersion the wallet factory project to publish
     * @return true if it can be published, false otherwise.
     */
    public boolean canBePublished(WalletFactoryProjectVersion walletFactoryProjectVersion);

    /**
     * This method calls the wallet publisher plugin in the middleware layer to publish it in the wallet store.
     *
     * @param walletFactoryProjectVersion the wallet factory project to publish
     * @throws WalletPublicationFailedException
     */
    public void publishWallet(WalletFactoryProjectVersion walletFactoryProjectVersion) throws WalletPublicationFailedException;
}
