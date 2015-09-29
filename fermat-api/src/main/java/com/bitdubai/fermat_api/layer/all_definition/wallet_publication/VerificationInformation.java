package com.bitdubai.fermat_api.layer.all_definition.wallet_publication;

import java.util.Map;
import java.util.UUID;

/**
 * This interface gives us the validation information related to the wallet that implements it.
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface VerificationInformation {

    /**
     * @return a hash used to verify that the catalogue item is real.
     */
    public String getLastVersionHash();

    /**
     * This method gives us the public key of the developer of the wallet
     *
     * @return the public key represented as a String
     */
    public String getDeveloperPublicKey();

    /**
     * This method gives us the hash of the navigation structure of the wallet
     *
     * @return the hash of the navigation structure
     */
    public String getNavigationStructureHash();

    /**
     * This method gives us the a map of the hashes of the languages packages that can be installed
     *
     * @return the map where each entry consist of the id of the language package and its verified hash
     */
    public Map<UUID, String> getLanguagesHashes();

    /**
     * This method gives us the a map of the hashes of the skins that can be installed
     *
     * @return the map where each entry consist of the id of the skin and its verified hash
     */
    public Map<UUID, String> getSkinsHashes();
}

