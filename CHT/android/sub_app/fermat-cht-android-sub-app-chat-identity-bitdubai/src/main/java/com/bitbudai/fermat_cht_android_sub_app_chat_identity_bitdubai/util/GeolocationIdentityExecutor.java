package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;

/**
 * Created by Lozadaa 23/04/2016.
 */

public class GeolocationIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 2;
    public static final int MISSING_IMAGE = 4;

    private ChatIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatIdentity identity;
    private String Publickey;
    private String identityConnectionState;
    private GeoFrequency frecuency;
    private long accuracy;
    private String identityName;
    private byte[] imageInBytes;
    private String country, state, city;

    /**
     * <code>GelocationidentityExecutor</code> this is constructor
     *
     * @param imageInBytes
     * @param Publickey
     * @param identityName
     * @param identityConnectionState
     * @param accuracy
     * @param frecuency
     */
    public GeolocationIdentityExecutor(byte[] imageInBytes, String Publickey, String identityName, String identityConnectionState, String country, String state, String city, long accuracy, GeoFrequency frecuency) {
        this.imageInBytes = imageInBytes;
        this.Publickey = Publickey;
        this.identityName = identityName;
        this.identityConnectionState = identityConnectionState;
        this.country = country;
        this.state = state;
        this.city = city;
        this.accuracy = accuracy;
        this.frecuency = frecuency;
    }

    /**
     * <code>GelocationidentityExecutor</code> this is constructor
     *
     * @param session
     * @param Publickey
     * @param identityName
     * @param imageInBytes
     * @param identityConnectionState
     * @param frecuency
     * @param acuraccy
     */

    public GeolocationIdentityExecutor(ReferenceAppFermatSession<ChatIdentityModuleManager> session, String Publickey, String identityName, byte[] imageInBytes, String identityConnectionState, String country, String state, String city, GeoFrequency frecuency, long acuraccy) {
        this(imageInBytes, Publickey,
                identityName, identityConnectionState, country, state, city, acuraccy, frecuency);
        identity = null;
        if (session != null) {
            this.moduleManager = session.getModuleManager();
            this.errorManager = session.getErrorManager();
        }
    }

    public int execute() {

        try {
            moduleManager.updateIdentityChat(Publickey, identityName, imageInBytes, country, state, city, identityConnectionState, accuracy, frecuency);
        } catch (CantUpdateChatIdentityException e) {

        } catch (Exception e) {

        }

        return SUCCESS;
    }

    public ChatIdentity getIdentity() {
        return identity;
    }
}