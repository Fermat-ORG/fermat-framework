package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

/**
 * Implementación básica de la intefaz Address,
 * utilizada para el RedeemPointActor.
 * Created by victor on 16/10/15.
 */
public class RedeemPointActorAddress implements Address {

    private String countryName;
    private String provinceName;
    private String streetName;
    private String postalCode;
    private String houseNumber;
    private String cityName;


    public RedeemPointActorAddress() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCountryName() {
        return verifyString(countryName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProvinceName() {
        return verifyString(provinceName);
    }

    @Override
    public String getCityName() {
        return verifyString(cityName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStreetName() {
        return verifyString(streetName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPostalCode() {
        return verifyString(postalCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHouseNumber() {
        return verifyString(houseNumber);
    }

    private String verifyString(String string) {
        if (string == null) {
            return "unknown";
        } else {
            return string;
        }
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "RedeemPointActorAddress {" +
                "countryName='" + countryName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                '}';
    }
}
