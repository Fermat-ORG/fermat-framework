package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.RedeemPointNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetRedeemPointManager extends FermatManager {

    /**
     * The method <code>getActorRegisteredByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor Redeem Point
     * @return                                  The information associated with the actorPublicKey.
     * @throws CantGetAssetRedeemPointActorsException
     * @throws CantAssetRedeemPointActorNotFoundException
     */
    ActorAssetRedeemPoint getActorByPublicKey(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException;

    /**
     * The method <code>createActorAssetRedeemPointFactory</code> create Actor by a Identity
     *
     * @param assetRedeemPointActorPublicKey                Referred to the Identity publicKey
     * @param assetRedeemPointActorName                     Referred to the Identity Alias
     * @param assetRedeemPointActorprofileImage             Referred to the Identity profileImage
     * @throws CantCreateActorRedeemPointException
     */
    void createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>registerActorInActorNetworkService</code> Register Actor in Actor Network Service
     */
    void registerActorInActorNetworkService() throws CantRegisterActorAssetRedeemPointException;

    /**
     * This method saves an already existing redeem point in the registered redeem point database,
     * usually uses when the redeem point request the issuer an extended public key, we save in
     * the issuer side this redeem point so we can retrieve its information on future uses.
     * @param redeemPoint The already existing redeem point with all its information
     * @throws CantCreateActorRedeemPointException
     */
    void saveRegisteredActorRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>createActorAssetRedeemPointRegisterInNetworkService</code> create Actor Registered
     *
     * @param actorAssetRedeemPoints                       Referred to the Identity publicKey
     * @throws CantCreateActorRedeemPointException
     */
    void createActorAssetRedeemPointRegisterInNetworkService(List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>getActorAssetRedeemPoint</code> get All Information about Actor
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetAssetRedeemPointActorsException;

    DAPConnectionState getActorRedeemPointRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException;
    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorInTableRegistered() throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected() throws CantGetAssetRedeemPointActorsException;

    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnectedForIssuer(String issuerPublicKey) throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>sendMessage</code> Stablish Connection
     * with Requester and Lists Issuers Delivered
     *
     * @throws CantConnectToActorAssetUserException
     */
    void sendMessage(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToActorAssetUserException;

    void disconnectToActorAssetRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws RedeemPointNotFoundException, CantUpdateRedeemPointException;

    void updateRedeemPointDAPConnectionStateActorNetworService(String actorPublicKey, DAPConnectionState state) throws CantUpdateRedeemPointException, RedeemPointNotFoundException;

}
