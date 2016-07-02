package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frecuency;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nerio on 22/09/15.
 */
public class RedeemPointActorRecord implements ActorAssetRedeemPoint {

    private String actorPublicKey;
    private String name;
    private long registrationDate;
    private long lastConnectionDate;
    private DAPConnectionState dapConnectionState;
    private CryptoAddress cryptoAddress;
    private Location location;
    private Double locationLatitude;
    private Double locationLongitude;
    private String contactInformation;
    private String hoursOfOperation;
    private Address address;
    private Actors actorsType = Actors.DAP_ASSET_REDEEM_POINT;
    private byte[] profileImage;
    private BlockchainNetworkType blockchainNetworkType;
    private List<String> registeredIssuers;
    private int accuracy;
    private Frecuency frecuency;

    {
        registeredIssuers = new ArrayList<>();
    }

    /**
     * Constructor
     */

    public RedeemPointActorRecord() {

    }

    /**
     * Method for Set Actor in Actor Network Service Redeem Point
     */
    public RedeemPointActorRecord(String actorPublicKey,
                                  String name,
                                  byte[] profileImage,
                                  Location location,
                                  List<String> registeredIssuers) {

        this.name = name;
        this.actorPublicKey = actorPublicKey;
        this.profileImage = profileImage.clone();

        if (location != null) {
            this.locationLatitude = location.getLatitude();
            this.locationLongitude = location.getLongitude();
        } else {
            this.locationLatitude = (double) 0;
            this.locationLongitude = (double) 0;
        }
        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;
        this.registeredIssuers = registeredIssuers;
    }

    public RedeemPointActorRecord(String name,
                                  String actorPublicKey) {

        this.actorPublicKey = actorPublicKey;
        this.name = name;
    }

    public RedeemPointActorRecord(String name,
                                  String actorPublicKey,
                                  byte[] profileImage,
                                  long registrationDate) {

        this.name = name;
        this.actorPublicKey = actorPublicKey;

        this.setProfileImage(profileImage);
        this.registrationDate = registrationDate;

        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;

    }

    public RedeemPointActorRecord(final String actorPublicKey,
                                  final String name,
                                  final DAPConnectionState dapConnectionState,
                                  final Double locationLatitude,
                                  final Double locationLongitude,
                                  final CryptoAddress cryptoAddress,
                                  final Long registrationDate,
                                  final Long lastConnectionDate,
                                  final Actors actorsType,
                                  final BlockchainNetworkType blockchainNetworkType,
                                  final byte[] profileImage) {
        this(actorPublicKey,
                name,
                dapConnectionState,
                locationLatitude,
                locationLongitude,
                cryptoAddress,
                registrationDate,
                lastConnectionDate,
                actorsType,
                blockchainNetworkType,
                profileImage,
                new ArrayList<String>());
    }

    public RedeemPointActorRecord(final String actorPublicKey,
                                  final String name,
                                  final DAPConnectionState dapConnectionState,
                                  final Double locationLatitude,
                                  final Double locationLongitude,
                                  final CryptoAddress cryptoAddress,
                                  final Long registrationDate,
                                  final Long lastConnectionDate,
                                  final Actors actorsType,
                                  final BlockchainNetworkType blockchainNetworkType,
                                  final byte[] profileImage,
                                  final List<String> registeredIssuers) {

        this.actorPublicKey = actorPublicKey;
        this.name = name;
        if (dapConnectionState != null)
            this.dapConnectionState = dapConnectionState;

        if (locationLatitude != null)
            this.locationLatitude = locationLatitude;
        if (locationLongitude != null)
            this.locationLongitude = locationLongitude;

        if (cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
        if (blockchainNetworkType != null)
            this.blockchainNetworkType = blockchainNetworkType;

        this.actorsType = actorsType;

        this.registrationDate = registrationDate;
        this.lastConnectionDate = lastConnectionDate;

        this.setProfileImage(profileImage);

        this.registeredIssuers = registeredIssuers;
    }

    private RedeemPointActorRecord(JsonObject jsonObject, Gson gson) {
        this.actorPublicKey = jsonObject.get("actorPublicKey").getAsString();
        this.name = jsonObject.get("name").getAsString();
        this.registrationDate = Long.parseLong(jsonObject.get("registrationDate").getAsString());
        this.lastConnectionDate = Long.parseLong(jsonObject.get("lastConnectionDate").getAsString());
        this.dapConnectionState = gson.fromJson(jsonObject.get("dapConnectionState").getAsString(), DAPConnectionState.class);
        this.location = gson.fromJson(jsonObject.get("location").getAsString(), Location.class);
        this.locationLatitude = Double.valueOf(jsonObject.get("locationLatitude").getAsString());
        this.locationLongitude = Double.valueOf(jsonObject.get("locationLongitude").getAsString());
        this.cryptoAddress = gson.fromJson(jsonObject.get("cryptoAddress").getAsString(), CryptoAddress.class);
        this.contactInformation = jsonObject.get("contactInformation").getAsString();
        this.hoursOfOperation = jsonObject.get("hoursOfOperation").getAsString();
        this.address = gson.fromJson(jsonObject.get("address").getAsString(), Address.class);
        this.actorsType = gson.fromJson(jsonObject.get("actorsType").getAsString(), Actors.class);
        this.profileImage = Base64.decode(jsonObject.get("profileImage").getAsString(), Base64.DEFAULT);
        this.blockchainNetworkType = gson.fromJson(jsonObject.get("blockchainNetworkType").getAsString(), BlockchainNetworkType.class);
        this.registeredIssuers = gson.fromJson(jsonObject.get("registeredIssuers").getAsString(), List.class);
    }

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented Redeem Point
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
     * The method <code>getName</code> gives us the name of the represented Redeem Point
     *
     * @return the name of the Actor
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
     * The method <code>getContactRegistrationDate</code> gives us the date when both Redeem Points
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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the
     * represented Asset Redeem Point
     *
     * @return the Connection Date
     */
    public long getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Redeem Point
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

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        if (blockchainNetworkType != null)
            this.blockchainNetworkType = blockchainNetworkType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    @Override
    public List<String> getRegisteredIssuers() {
        return registeredIssuers;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DAPConnectionState getDapConnectionState() {
        return dapConnectionState;
    }

    public void setDapConnectionState(DAPConnectionState dapConnectionState) {
        this.dapConnectionState = dapConnectionState;
    }

    //    /**
//     *
//     * {@inheritDoc}
//     */
    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    public static RedeemPointActorRecord fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new RedeemPointActorRecord(jsonObject, gson);
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("actorPublicKey", actorPublicKey);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("registrationDate", registrationDate);
        jsonObject.addProperty("lastConnectionDate", lastConnectionDate);
        jsonObject.addProperty("dapConnectionState", dapConnectionState.toString());
        jsonObject.addProperty("location", location.toString());
        jsonObject.addProperty("locationLatitude", locationLatitude.toString());
        jsonObject.addProperty("locationLongitude", locationLongitude.toString());
        jsonObject.addProperty("cryptoAddress", cryptoAddress.toString());
        jsonObject.addProperty("contactInformation", contactInformation);
        jsonObject.addProperty("hoursOfOperation", hoursOfOperation);
        jsonObject.addProperty("address", address.toString());
        jsonObject.addProperty("actorsType", actorsType.toString());
        jsonObject.addProperty("blockchainNetworkType", blockchainNetworkType.toString());
        jsonObject.addProperty("profileImage", Base64.encodeToString(profileImage, Base64.DEFAULT));
        jsonObject.addProperty("registeredIssuers", String.valueOf(registeredIssuers));
        return gson.toJson(jsonObject);
    }

    @Override
    public String toString() {
        String profileImageRedeem = null;
        if (profileImage != null)
            profileImageRedeem = Base64.encodeToString(profileImage, Base64.DEFAULT);

        return "RedeemPointActorRecord{" +
                "actorPublicKey='" + actorPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastConnectionDate=" + lastConnectionDate +
                ", dapConnectionState=" + dapConnectionState +
                ", cryptoAddress=" + cryptoAddress +
                ", location=" + location +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", contactInformation='" + contactInformation + '\'' +
                ", hoursOfOperation='" + hoursOfOperation + '\'' +
                ", address=" + address +
                ", actorsType=" + actorsType +
                ", blockchainNetworkType=" + blockchainNetworkType +
                ", profileImage=" + profileImageRedeem +
                ", registeredIssuers=" + registeredIssuers +
                '}';
    }
}
