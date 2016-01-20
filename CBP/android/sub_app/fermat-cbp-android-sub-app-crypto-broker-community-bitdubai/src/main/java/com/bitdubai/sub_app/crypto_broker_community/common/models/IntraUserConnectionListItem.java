package com.bitdubai.sub_app.crypto_broker_community.common.models;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class IntraUserConnectionListItem implements Item {

    private String name;
    private String profilePhrase;
    private byte[] profileImage;

    //esto puede ser un enum con los distintos estados de la conexion
    private String connectionStatus;


    public IntraUserConnectionListItem(String name, String profilePhrase, byte[] profileImage, String connectionStatus) {
        this.name = name;
        this.profilePhrase = profilePhrase;
        this.profileImage = profileImage;
        this.connectionStatus = connectionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhrase() {
        return profilePhrase;
    }

    public void setProfilePhrase(String profilePhrase) {
        this.profilePhrase = profilePhrase;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return true;
    }

}
