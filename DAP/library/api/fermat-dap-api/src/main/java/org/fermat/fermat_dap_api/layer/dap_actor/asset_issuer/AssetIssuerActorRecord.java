package org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetIssuerActorRecord implements ActorAssetIssuer {

    private String                  publicLinkedIdentity;
    private String                  actorPublicKey;
    private String                  name;
    private String                  description;
    private long                    registrationDate;
    private long                    lastConnectionDate;
    private DAPConnectionState      dapConnectionState;
    private Location                location;
    private Double                  locationLatitude;
    private Double                  locationLongitude;
    private byte[]                  profileImage;
    private String                  extendedPublicKey;
    private Actors                  actorsType              = Actors.DAP_ASSET_ISSUER;


    /**
     * Constructor
     */

    public AssetIssuerActorRecord() {

    }

    public AssetIssuerActorRecord(String name,
                                  String actorPublicKey) {
        this.name = name;
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * Method for Set Actor in Actor Network Service Issuer
     */
    public AssetIssuerActorRecord(String actorPublicKey,
                                  String name,
                                  byte[] profileImage,
                                  Location location) {

        this.actorPublicKey = actorPublicKey;
        this.name = name;

        this.setProfileImage(profileImage);

        if (location != null) {
            this.locationLatitude = location.getLatitude();
            this.locationLongitude = location.getLongitude();
        } else {
            this.locationLatitude = (double) 0;
            this.locationLongitude = (double) 0;
        }
        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;

    }

    public AssetIssuerActorRecord(final String actorPublicKey,
                                  final String name,
                                  final DAPConnectionState dapConnectionState,
                                  final Double locationLatitude,
                                  final Double locationLongitude,
                                  final Long registrationDate,
                                  final Long lastConnectionDate,
                                  final Actors actorsType,
                                  final String description,
                                  final String extendedPublicKey,
                                  final byte[] profileImage) {

        this.actorPublicKey     = actorPublicKey;
        this.name               = name;
        if (dapConnectionState != null)
            this.dapConnectionState = dapConnectionState;
        this.locationLatitude   = locationLatitude;
        this.locationLongitude  = locationLongitude;
        this.registrationDate   = registrationDate;
        this.lastConnectionDate = lastConnectionDate;
        if (description != null)
            this.description        = description;
        if (extendedPublicKey != null)
            this.extendedPublicKey  = extendedPublicKey;
        this.actorsType         = actorsType;

        this.setProfileImage(profileImage);
    }

    private AssetIssuerActorRecord(JsonObject jsonObject, Gson gson) {

        this.publicLinkedIdentity = jsonObject.get("publicLinkedIdentity").getAsString();
        this.actorPublicKey = jsonObject.get("actorPublicKey").getAsString();
        this.name = jsonObject.get("name").getAsString();
        this.description = jsonObject.get("description").getAsString();
        this.registrationDate = Long.parseLong(jsonObject.get("registrationDate").getAsString());
        this.lastConnectionDate = Long.parseLong(jsonObject.get("lastConnectionDate").getAsString());
        this.dapConnectionState = gson.fromJson(jsonObject.get("dapConnectionState").getAsString(), DAPConnectionState.class);
        this.location = gson.fromJson(jsonObject.get("location").getAsString(), Location.class);
        this.locationLatitude = Double.valueOf(jsonObject.get("locationLatitude").getAsString());
        this.locationLongitude = Double.valueOf(jsonObject.get("locationLongitude").getAsString());
        this.profileImage = Base64.decode(jsonObject.get("profileImage").getAsString(), Base64.DEFAULT);
        this.actorsType = gson.fromJson(jsonObject.get("actorsType").getAsString(), Actors.class);
        this.extendedPublicKey = jsonObject.get("extendedPublicKey").getAsString();

    }

    /**
     * The metho <code>getActorPublicKey</code> gives us the public key of the represented Asset Issuer
     *
     * @return the public key
     */
    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }

    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * The method <code>getName</code> gives us the name of the represented Asset Issuer
     *
     * @return the name of the intra user
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * The method <code>getType</code> gives us the Enum of the represented a Actor
     *
     * @return Enum Actors
     */
    @Override
    public Actors getType() {
        return actorsType;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both  Asset Issuers
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented Asset Issuer
     *
     * @return the Connection Date
     */
    @Override
    public long getLastConnectionDate() {
        return this.lastConnectionDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset Issuer
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return this.profileImage.clone();
    }

    public void setProfileImage(byte[] profileImage) {
        if(profileImage != null)
            this.profileImage = profileImage.clone();
        else
            this.profileImage = new byte[0];
    }

    /**
     * The method <code>getConnectionState</code> gives us the Coonection state of the represented Asset Issuer
     *
     * @return the Coonection state
     */
    @Override
    public DAPConnectionState getDapConnectionState() {
        return this.dapConnectionState;
    }

    public void setDapConnectionState(DAPConnectionState dapConnectionState) {
        this.dapConnectionState = dapConnectionState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null)
            this.location = location;
    }

    @Override
    public Double getLocationLatitude() {
        return locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return locationLongitude;
    }

    @Override
    public String getExtendedPublicKey() {
        return extendedPublicKey;
    }

    public static AssetIssuerActorRecord fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new AssetIssuerActorRecord(jsonObject, gson);
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("publicLinkedIdentity",  publicLinkedIdentity);
        jsonObject.addProperty("actorPublicKey",        actorPublicKey);
        jsonObject.addProperty("name",                  name);
        jsonObject.addProperty("description",           description);
        jsonObject.addProperty("registrationDate",      registrationDate);
        jsonObject.addProperty("lastConnectionDate",    lastConnectionDate);
        jsonObject.addProperty("dapConnectionState",    dapConnectionState.toString());
        jsonObject.addProperty("location",              location.toString());
        jsonObject.addProperty("locationLatitude",      locationLatitude.toString());
        jsonObject.addProperty("locationLongitude",     locationLongitude.toString());
        jsonObject.addProperty("actorsType",            actorsType.toString());
        jsonObject.addProperty("extendedPublicKey",     extendedPublicKey);
        jsonObject.addProperty("profileImage",          Base64.encodeToString(profileImage, Base64.DEFAULT));
        return gson.toJson(jsonObject);
    }

    @Override
    public String toString() {
        String profileImageIssuer = null;
        if(profileImage != null)
            profileImageIssuer = Base64.encodeToString(profileImage, Base64.DEFAULT);

        return "AssetIssuerActorRecord{"    +
                "publicLinkedIdentity='"    + publicLinkedIdentity + '\'' +
                ", actorPublicKey='"        + actorPublicKey + '\'' +
                ", name='"                  + name + '\'' +
                ", description='"           + description + '\'' +
                ", registrationDate="       + registrationDate +
                ", lastConnectionDate="     + lastConnectionDate +
                ", dapConnectionState="     + dapConnectionState +
                ", location="               + location +
                ", locationLatitude="       + locationLatitude +
                ", locationLongitude="      + locationLongitude +
                ", profileImage="           + profileImageIssuer +
                ", actorsType="             + actorsType +
                ", extendedPublicKey='"     + extendedPublicKey + '\'' +
                '}';
    }
}
