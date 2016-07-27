package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.UpdateProfileGeolocationMsgRequest</code>
 * represent the message for updating the coordinates of a profile<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class UpdateProfileGeolocationMsgRequest extends PackageContent {

    /**
     * Represents the identity public key
     */
    private String ipk;

    /**
     * Represents the type of the profile
     */
    private ProfileTypes typ;

    /**
     * Represents the last location of the profile
     */
    private Location loc;

    public UpdateProfileGeolocationMsgRequest(final String       identityPublicKey,
                                              final ProfileTypes type             ,
                                              final Location     location         ) {

        super(MessageContentType.JSON);

        this.ipk = identityPublicKey;
        this.typ = type             ;
        this.loc = location         ;
    }

    public String getIdentityPublicKey() {
        return ipk;
    }

    public ProfileTypes getType() {
        return typ;
    }

    public Location getLocation() {
        return loc;
    }

    /**
     * Generate the json representation
     *
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static UpdateProfileGeolocationMsgRequest parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, UpdateProfileGeolocationMsgRequest.class);
    }

    @Override
    public String toString() {
        return "UpdateProfileGeolocationMsgRequest{" +
                "identityPublicKey='" + ipk + '\'' +
                ", type=" + typ +
                ", location=" + loc +
                '}';
    }
}
