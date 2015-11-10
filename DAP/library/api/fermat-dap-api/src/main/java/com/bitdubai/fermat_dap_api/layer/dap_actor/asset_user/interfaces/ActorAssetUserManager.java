package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager {

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor User
     * @return                                  THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor by a Identity
     *
     * @param assetUserActorPublicKey                       Referred to the Identity publicKey
     * @param assetUserActorName                            Referred to the Identity Alias
     * @param assetUserActorprofileImage                    Eeferred to the Identity profileImage
     * @throws CantCreateAssetUserActorException
     */
    void createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage) throws CantCreateAssetUserActorException;

    /**
     * The method <code>getActorPublicKey</code> get All Information about Actor
     *
     * @throws CantGetAssetUserActorsException
     */
    ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorInTableRegistered() throws CantGetAssetUserActorsException;

    /**
     * The method <code>getAllAssetUserActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

//  void createAndRegisterActorAssetUserTest() throws CantCreateAssetUserActorException;

    /**
     * The method <code>registerActorInActorNetowrkSerice</code> Register or Add Actor a Lst in
     * Actor Network Service
     *
     * @throws CantCreateAssetUserActorException
     */
    //TODO Metodo sera removido luego que existan las Sub App Identity
    void registerActorInActorNetowrkSerice() throws CantCreateAssetUserActorException;

    /**
     * The method <code>connectToActorAssetUser</code> Stablish Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToAssetUserException
     */
    void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToAssetUserException;
}
