package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces;


import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

/**
 * Created by MAtias Furszyfer on 2015.10.15..
 */
public interface IntraUserFactory {

    /**
     * Construct intraUSer
     *
     * @param publicKey
     * @param intraUserName
     * @param profileImage
     * @return
     */
    public IntraUserInformation constructIntraUser(String publicKey,String intraUserName, byte[] profileImage);
}
