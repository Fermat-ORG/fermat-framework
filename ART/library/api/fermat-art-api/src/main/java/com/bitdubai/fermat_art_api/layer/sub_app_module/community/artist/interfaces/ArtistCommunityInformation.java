package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public interface ArtistCommunityInformation extends Serializable {

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented Artist
     * @return the public key of the Artist
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the name of the represented Artist
     *
     * @return the name of the Artist
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented Artist
     *
     * @return the profile image
     */
    byte[] getImage();

    /**
     * The method <code>listArtistWallets</code> returns the list of the public Artist wallets
     * @return
     */
    List listArtistWallets();

    /**
     * The method <code>getConnectionState</code> returns the Connection State Status
     * @return ConnectionState object
     */
    ConnectionState getConnectionState();

    /**
     * The method <code>getConnectionId</code> returns the Connection UUID this actor has with the selected actor
     * @return UUID object
     */
    UUID getConnectionId();
    /**
     * The method <code>getArtistExternalPlatformInformation</code> returns the ArtistExternalPlatformInformation this actor has with the selected actor
     * @return ArtistExternalPlatformInformation object
     */
    ArtistExternalPlatformInformation getArtistExternalPlatformInformation();
}
