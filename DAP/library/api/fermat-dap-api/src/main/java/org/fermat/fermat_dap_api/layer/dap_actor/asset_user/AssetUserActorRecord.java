package org.fermat.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetUserActorRecord implements ActorAssetUser {

    private String publicLinkedIdentity;
    private String actorPublicKey;
    private String name;
    private String age;
    private Genders genders;
    private DAPConnectionState dapConnectionState;
    private Location location;
    private Double locationLatitude;
    private Double locationLongitude;
    private long registrationDate;
    private long lastConnectionDate;
    private CryptoAddress cryptoAddress;
    private BlockchainNetworkType blockchainNetworkType;
    private Actors actorsType = Actors.DAP_ASSET_USER;
    private byte[] profileImage;


    /**
     * Constructor
     */
    public AssetUserActorRecord() {

    }

    /**
     * Method for Set Actor in Actor Network Service User
     */
    public AssetUserActorRecord(String actorPublicKey,
                                String name,
                                byte[] profileImage,
                                Location location) {

        this.name = name;
        this.actorPublicKey = actorPublicKey;

        this.setProfileImage(profileImage);

        if (location != null) {
            this.locationLatitude = location.getLatitude();
            this.locationLongitude = location.getLongitude();
        } else {
            this.locationLatitude = (double) 0;
            this.locationLongitude = (double) 0;
        }

        this.genders = Genders.INDEFINITE;
//        if(!age.isEmpty())
//            this.age = age;
        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;

    }

    public AssetUserActorRecord(final String actorPublicKey,
                                final String name,
                                final String age,
                                final Genders genders,
                                final DAPConnectionState dapConnectionState,
                                final Double locationLatitude,
                                final Double locationLongitude,
                                final CryptoAddress cryptoAddress,
                                final Long registrationDate,
                                final Long lastConnectionDate,
                                final BlockchainNetworkType blockchainNetworkType,
                                final Actors actorsType,
                                final byte[] profileImage) {

        this.actorPublicKey = actorPublicKey;
        this.name = name;
        if (age != null)
            this.age = age;
        if (genders != null)
            this.genders = genders;
        if (dapConnectionState != null)
            this.dapConnectionState = dapConnectionState;

        if (locationLatitude != -1)
            this.locationLatitude = locationLatitude;
        if (locationLongitude != -1)
            this.locationLongitude = locationLongitude;

        if (cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
        if (blockchainNetworkType != null)
            this.blockchainNetworkType = blockchainNetworkType;

        this.registrationDate = registrationDate;
        this.lastConnectionDate = lastConnectionDate;

        this.actorsType = actorsType;

        this.setProfileImage(profileImage);

    }

    private AssetUserActorRecord(JsonObject jsonObject, Gson gson) {
        this.publicLinkedIdentity = jsonObject.get("publicLinkedIdentity").getAsString();
        this.actorPublicKey = jsonObject.get("actorPublicKey").getAsString();
        this.name = jsonObject.get("name").getAsString();
        this.age = jsonObject.get("age").getAsString();
        this.genders = gson.fromJson(jsonObject.get("genders").getAsString(), Genders.class);
        this.registrationDate = Long.parseLong(jsonObject.get("registrationDate").getAsString());
        this.lastConnectionDate = Long.parseLong(jsonObject.get("lastConnectionDate").getAsString());
        this.dapConnectionState = gson.fromJson(jsonObject.get("dapConnectionState").getAsString(), DAPConnectionState.class);
        this.location = gson.fromJson(jsonObject.get("location").getAsString(), Location.class);
        this.locationLatitude = Double.valueOf(jsonObject.get("locationLatitude").getAsString());
        this.locationLongitude = Double.valueOf(jsonObject.get("locationLongitude").getAsString());
        this.cryptoAddress = gson.fromJson(jsonObject.get("cryptoAddress").getAsString(), CryptoAddress.class);
        this.blockchainNetworkType = gson.fromJson(jsonObject.get("blockchainNetworkType").getAsString(), BlockchainNetworkType.class);
        this.actorsType = gson.fromJson(jsonObject.get("actorsType").getAsString(), Actors.class);
        this.profileImage = Base64.decode(jsonObject.get("profileImage").getAsString(), Base64.DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetUserActorRecord that = (AssetUserActorRecord) o;

        if (!getActorPublicKey().equals(that.getActorPublicKey())) return false;
        return getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = getActorPublicKey().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    /**
     * The method <code>getPubliclinkedIdentity</code> gives us the public Linked Identity of the represented Asset User
     *
     * @return the Public Linked Identity
     */
    public String getPublicLinkedIdentity() {
        return publicLinkedIdentity;
    }

    public void setPublicLinkedIdentity(String publicLinkedIdentity) {
        this.publicLinkedIdentity = publicLinkedIdentity;
    }

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented Asset User
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
     * The method <code>getName</code> gives us the name of the represented Asset User
     *
     * @return the name of the Asset User
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
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Age of the Asset user
     */
    @Override
    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * The method <code>getGenders</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGenders() {
        if (this.genders == null)
            return Genders.INDEFINITE;
        else
            return genders;
    }

    public void setGenders(Genders genders) {
        if (genders != null)
            this.genders = genders;
        else
            this.genders = Genders.INDEFINITE;
    }

    /**
     * The method <code>getRegistrationDate</code> gives us the date when both Asset Users
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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented Asset User
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
     * The method <code>getConnectionState</code> gives us the connection state of the represented Asset User
     *
     * @return the Connection state
     */
    @Override
    public DAPConnectionState getDapConnectionState() {
        return this.dapConnectionState;
    }

    public void setConnectionState(DAPConnectionState dapConnectionState) {
        if (dapConnectionState != null)
            this.dapConnectionState = dapConnectionState;
        else
            this.dapConnectionState = DAPConnectionState.REGISTERED_LOCALLY;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        } else {
            this.locationLatitude = (double) 0;
            this.locationLongitude = (double) 0;
        }
    }

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Latitude of the Asset user
     */
    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Longitude of the Asset user
     */
    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset User
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return this.profileImage.clone();
    }

    public void setProfileImage(byte[] profileImage) {
        if (profileImage != null)
            this.profileImage = profileImage.clone();
        else
            this.profileImage = new byte[0];
    }

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return this.cryptoAddress;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        if (cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
    }

    /**
     * The method <code>getNetworkType</code> returns the network type which it belongs
     *
     * @return BlockchainNetworkType instance with the network type.
     */
    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return this.blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        if (blockchainNetworkType != null)
            this.blockchainNetworkType = blockchainNetworkType;
    }

    public static AssetUserActorRecord fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new AssetUserActorRecord(jsonObject, gson);
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("publicLinkedIdentity", publicLinkedIdentity);
        jsonObject.addProperty("actorPublicKey", actorPublicKey);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("age", age);
        jsonObject.addProperty("genders", genders.toString());
        jsonObject.addProperty("registrationDate", registrationDate);
        jsonObject.addProperty("lastConnectionDate", lastConnectionDate);
        jsonObject.addProperty("dapConnectionState", dapConnectionState.toString());
        jsonObject.addProperty("location", location.toString());
        jsonObject.addProperty("locationLatitude", locationLatitude.toString());
        jsonObject.addProperty("locationLongitude", locationLongitude.toString());
        jsonObject.addProperty("cryptoAddress", cryptoAddress.toString());
        jsonObject.addProperty("blockchainNetworkType", blockchainNetworkType.toString());
        jsonObject.addProperty("actorsType", actorsType.toString());
        jsonObject.addProperty("profileImage", Base64.encodeToString(profileImage, Base64.DEFAULT));
        return gson.toJson(jsonObject);
    }

    @Override
    public String toString() {
        String profileImageUser = null;
        if (profileImage != null)
            profileImageUser = Base64.encodeToString(profileImage, Base64.DEFAULT);

        return "AssetUserActorRecord{" +
                "publicLinkedIdentity='" + publicLinkedIdentity + '\'' +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", genders=" + genders +
                ", dapConnectionState=" + dapConnectionState +
                ", location=" + location +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", registrationDate=" + registrationDate +
                ", lastConnectionDate=" + lastConnectionDate +
                ", cryptoAddress=" + cryptoAddress +
                ", blockchainNetworkType=" + blockchainNetworkType +
                ", actorsType=" + actorsType +
                ", profileImage=" + profileImageUser +
                '}';
    }
}
