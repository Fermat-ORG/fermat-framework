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
    * @param actorPublicKey the public key of the Asset Actor User
    * @return THe information associated with the actorPublicKey.
    * @throws CantGetAssetUserActorsException
    * @throws CantAssetUserActorNotFoundException
    */
  List<ActorAssetUser> getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

  /**
   * The method <code>getActorPublicKey</code> get All Information about Actor
   *
   * @throws CantGetAssetUserActorsException
   */
  ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

  /**
   * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
   * and used in Sub App Community

   * @throws CantGetAssetUserActorsException
   * @throws CantAssetUserActorNotFoundException
   */
  List<ActorAssetUser> getAllAssetUserActorInTableRegistered() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

   /**
     * The method <code>getAllAssetUserActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetUserActorsException
     */
  List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

  /**
   * The method <code>createActorAssetUserFactory</code> create Actor in Actor Network Service
   *
   * @throws CantGetAssetUserActorsException
   */
  ActorAssetUser createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Double locationLatitude, Double locationLongitude) throws CantCreateAssetUserActorException;


//  void createAndRegisterActorAssetUserTest() throws CantCreateAssetUserActorException;

  void registerActorInActorNetowrkSerice() throws CantCreateAssetUserActorException;


  void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToAssetUserException;

  void setCryptoAddress(ActorAssetUser actorAssetUser);

}
