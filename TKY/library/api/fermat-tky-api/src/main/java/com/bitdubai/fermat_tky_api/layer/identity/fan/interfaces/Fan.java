package com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.Identity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */

public interface Fan extends Identity, Serializable {

    /**
     * This interface, in this version only extends the default methods in ArtIdentity interface.
     * This can be changed in the future.
     */

    /**
     * This method returns a list with the username from the artists connected.
     * @return
     */
    List<String> getConnectedArtists();

    /**
     * This method persist the username in the fan identity.
     * @param userName
     * @throws ObjectNotSetException
     */
    void addNewArtistConnected(String  userName) throws ObjectNotSetException;

    /**
     * This method returns the XML String representation from the Artist Connected List.
     * @return
     */
    String getArtistsConnectedStringList();

    /**
     * This method sets the artist connected list from a XML String.
     * @param xmlStringList
     */
    void addArtistConnectedList(String xmlStringList);

}
