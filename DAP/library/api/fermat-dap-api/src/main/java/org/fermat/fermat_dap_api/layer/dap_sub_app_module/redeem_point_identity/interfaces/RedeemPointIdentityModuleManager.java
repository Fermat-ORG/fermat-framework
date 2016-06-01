package org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantUpdateIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.RedeemPointIdentitySettings;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface RedeemPointIdentityModuleManager extends ModuleManager, ModuleSettingsImpl<RedeemPointIdentitySettings>, Serializable {

    /**
     * The method <code>getAllIntraWalletUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of Asset Issuer users associated to the current logged in Device User.
     * @throws CantListAssetRedeemPointException if something goes wrong.
     */
    List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException;

    /**
     * The method <code>getIdentityAssetIssuer</code> will give Identity Asset Issuer associated
     *
     * @return Identity Asset Redeem Point associated.
     * @throws CantGetRedeemPointIdentitiesException if something goes wrong.
     */
    RedeemPointIdentity getIdentityAssetRedeemPoint() throws CantGetRedeemPointIdentitiesException;

    /**
     * The method <code>createNewIntraWalletUser</code> creates a new intra wallet user Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     * @return the intra user created
     * @throws CantCreateNewRedeemPointException if something goes wrong.
     */
    RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException;


    RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage,
                                             String contactInformation, String countryName, String provinceName, String cityName,
                                             String postalCode, String streetName, String houseNumber) throws CantCreateNewRedeemPointException;

    /**
     * The method <code>updateIdentityAssetIssuer</code> change a identity information data
     *
     * @param identityPublicKey
     * @param identityAlias
     * @param profileImage
     * @throws CantUpdateIdentityRedeemPointException
     */
    void updateIdentityRedeemPoint(String identityPublicKey, String identityAlias, byte[] profileImage,
                                   String contactInformation, String countryName, String provinceName, String cityName,
                                   String postalCode, String streetName, String houseNumber) throws CantUpdateIdentityRedeemPointException;

    /**
     * The method <code>hasAssetUserIdentity</code> returns if has a intra user identity created
     *
     * @return
     * @throws CantListAssetRedeemPointException
     */
    boolean hasRedeemPointIdentity() throws CantListAssetRedeemPointException;


    void createIdentity(String name, byte[] profile_img,
                        String contactInformation, String countryName, String provinceName, String cityName,
                        String postalCode, String streetName, String houseNumber) throws Exception;
}
