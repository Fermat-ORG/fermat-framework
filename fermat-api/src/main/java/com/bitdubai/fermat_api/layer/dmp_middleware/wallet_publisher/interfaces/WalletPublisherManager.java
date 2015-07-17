package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedWalletsInformationException;

import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletPublisherManager</code>
 * indicates the functionality of a WalletPublisherManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletPublisherManager {

    /**
     * This method returns the information stored about the published wallets of the logged in developer
     *
     * @return A map where each entry corresponds to a wallet name and the list of versions published with their information
     * @throws CantGetPublishedWalletsInformationException
     */
    public Map<String,List<WalletPublishedInformation>> showPublishedWallets() throws CantGetPublishedWalletsInformationException;

    /**
     * This method tells us if the given wallet factory project can be published in the wallet store.
     * This is done by consulting the platform if the code has been accepted by bitDubai
     *
     * @param walletFactoryProjectProposal the wallet factory project to publish
     * @return true if it can be published, false otherwise.
     * @throws CantCheckPublicationException
     */
    public boolean canBePublished(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCheckPublicationException;

    /**
     * This method publishes the wallet factory project <code>walletFactoryProjectVersion</code> in
     * the wallet store and register relevant information of this process (publication timestamp).
     *partirse
     * @param walletFactoryProjectProposal the wallet factory project to publish
     * @throws CantPublishWalletException
     */
    public void publishWallet(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException;
}