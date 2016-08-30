package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public interface NetworkChannel {

    void connect();

    void disconnect();

    boolean isConnected();

//    UUID registerProfile(Profile profile) throws CantRegisterProfileException;

//    void updateProfile(Profile profile, UpdateTypes types) throws CantUpdateRegisteredProfileException;

    /**
     *
     * @param packageContent
     * @param packageType
     * @param networkServiceType
     * @param destinationPublicKey
     * @return
     * @throws CantSendMessageException
     */
    UUID sendMessage(PackageContent packageContent, PackageType packageType, NetworkServiceType networkServiceType, String destinationPublicKey) throws CantSendMessageException;

    /**
     * Message with home node as destination
     * @param packageContent
     * @param packageType
     * @return
     * @throws CantSendMessageException
     */
    UUID sendMessage(PackageContent packageContent, PackageType packageType,NetworkServiceType networkServiceType) throws CantSendMessageException;

    UUID sendMessage(PackageContent packageContent, PackageType packageType) throws CantSendMessageException;


}
