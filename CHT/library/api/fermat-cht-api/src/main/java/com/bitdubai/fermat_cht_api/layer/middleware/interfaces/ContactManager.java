package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import java.util.List;
import java.sql.Date;
/**
 * Created by miguel payarez (miguel_payarez@hotmail.com)  on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface ContactManager {

    //Todo: create a contact record or contact wrapper to clean this method
    //Please, check the observations on ChatManager
    List<Object> contactDetail(
            Integer contactId,
            String remoteName,
            String alias,
            String remoteActorType,
            String remoteActorPublicKey,
            Date creationDate
    );

    void contactEdit(
            Integer contactId,
            String remoteName,
            String remoteActorType,
            String remoteActorPublicKey);


    void deleteContact(
            Integer contactId,
            String remoteName,
            String remoteActorType,
            String remoteActorPublicKey);



}
