package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The Class <code>ActorProfile</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorProfile extends Profile {

    /**
     * Represent the actorType
     */
    private String actorType;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the photo
     */
    private byte[] photo;

    /**
     * Represent the nsIdentityPublicKey
     */
    private String nsIdentityPublicKey;

    /**
     * Represent the clientIdentityPublicKey
     */
    private String clientIdentityPublicKey;

    /**
     * Constructor
     */
    public ActorProfile(){
        super(ProfileTypes.ACTOR);
    }

    /**
     * Gets the value of actorType and returns
     *
     * @return actorType
     */
    public String getActorType() {
        return actorType;
    }

    /**
     * Sets the actorType
     *
     * @param actorType to set
     */
    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    /**
     * Gets the value of alias and returns
     *
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias
     *
     * @param alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Gets the value of extraData and returns
     *
     * @return extraData
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Sets the extraData
     *
     * @param extraData to set
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    /**
     * Gets the value of name and returns
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of photo and returns
     *
     * @return photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * Sets the photo
     *
     * @param photo to set
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * Gets the value of nsIdentityPublicKey and returns
     *
     * @return nsIdentityPublicKey
     */
    public String getNsIdentityPublicKey() {
        return nsIdentityPublicKey;
    }

    /**
     * Sets the nsIdentityPublicKey
     *
     * @param nsIdentityPublicKey to set
     */
    public void setNsIdentityPublicKey(String nsIdentityPublicKey) {
        this.nsIdentityPublicKey = nsIdentityPublicKey;
    }

    /**
     * Get the ClientIdentityPublicKey
     *
     * @return String
     */
    public String getClientIdentityPublicKey() {
        return clientIdentityPublicKey;
    }

    /**
     * Sets the clientIdentityPublicKey
     *
     * @param clientIdentityPublicKey to set
     */
    public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
        this.clientIdentityPublicKey = clientIdentityPublicKey;
    }

    public static Profile readJson(final JsonReader in) throws IOException {

        ActorProfile actorProfile = new ActorProfile();

        Double latitude = 0.0;
        Double longitude = 0.0;

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "ipk":
                    actorProfile.setIdentityPublicKey(in.nextString());
                    break;
                case "lat":
                    latitude = in.nextDouble();
                    break;
                case "lng":
                    longitude = in.nextDouble();
                    break;
                case "act":
                    actorProfile.setActorType(in.nextString());
                    break;
                case "ali":
                    actorProfile.setAlias(in.nextString());
                    break;
                case "nam":
                    actorProfile.setName(in.nextString());
                    break;
                case "exd":
                    actorProfile.setExtraData(in.nextString());
                    break;
                case "nspk":
                    actorProfile.setNsIdentityPublicKey(in.nextString());
                    break;
                case "clpk":
                    actorProfile.setClientIdentityPublicKey(in.nextString());
                    break;
                case "photo":
                    actorProfile.setPhoto(Base64.decode(in.nextString(), Base64.DEFAULT));
                    break;
            }
        }

        actorProfile.setLocation(
                new NetworkNodeCommunicationDeviceLocation(
                        latitude,
                        longitude,
                        null,
                        0,
                        null,
                        0,
                        LocationSource.UNKNOWN
                )
        );

        return actorProfile;
    }

    @Override
    public JsonWriter writeJson(final JsonWriter out) throws IOException {

        super.writeJson(out);

        out.name("act").value(actorType);
        out.name("ali").value(alias);
        out.name("nam").value(name);
        out.name("exd").value(extraData);
        out.name("nspk").value(nsIdentityPublicKey);
        out.name("clpk").value(clientIdentityPublicKey);
        out.name("photo").value(Base64.encodeToString(photo, Base64.DEFAULT));

        return out;
    }

    /**
     * Return this object in json string
     *
     * @return json string
     */
    public String toJson(){
        return GsonProvider.getGson().toJson(this, ActorProfile.class);
    }

    /**
     * Get the object
     *
     * @param jsonString
     * @return ClientProfile
     */
    public static ActorProfile fromJson(String jsonString) {
        return GsonProvider.getGson().fromJson(jsonString, ActorProfile.class);
    }

    @Override
    public String toString() {
        return "ActorProfile{" +
                "actorType='" + actorType + '\'' +
                ", identityPublicKey='" + getIdentityPublicKey() + '\'' +
                ", alias='" + alias + '\'' +
                ", extraData='" + extraData + '\'' +
                ", name='" + name + '\'' +
                ", photo=" + (photo != null ? "true" : "false") +
                ", nsIdentityPublicKey ='" + nsIdentityPublicKey + '\'' +
                ", clientIdentityPublicKey ='" + clientIdentityPublicKey + '\'' +
                '}';
    }
}
