package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import android.util.Log;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by franklin 23/04/2016.
 */

public class EditIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 2;
    public static final int MISSING_IMAGE = 4;

    private byte[] imageInBytes;
    private String identityName;

    //private ChatIdentitySubAppModulePluginRoot moduleManager;
    private ChatIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatIdentity identity;
    private String Publickey;
    private String identityConnectionState;
    public EditIdentityExecutor(byte[] imageInBytes,String Publickey , String identityName, String identityConnectionState) {
        this.imageInBytes = imageInBytes;
        this.Publickey = Publickey;
        this.identityName = identityName;
        this.identityConnectionState = identityConnectionState;
    }

    public EditIdentityExecutor(FermatSession session,String Publickey , String identityName, byte[] imageInBytes, String identityConnectionState) {
        this(imageInBytes, Publickey ,identityName, identityConnectionState);
        identity = null;
        if (session != null) {
            ChatIdentitySession subAppSession = (ChatIdentitySession) session;
            Log.i("*****CHT IDENTITY******", "LA SESION tiene valorrrrrr!!!!!!!");
            this.moduleManager = subAppSession.getModuleManager();
            this.errorManager = subAppSession.getErrorManager();
        }else{
            Log.i("*****CHT IDENTITY******", "LA SESION ES NULA!!!!!!!");
        }
    }

    public int execute() {

        if (imageIsInvalid())
            return MISSING_IMAGE;

        if (entryDataIsInvalid())
            return INVALID_ENTRY_DATA;

        try {
            //TODO: Buscar la manera de que esta informacion venga desde android puede ser por la geolocalizacion
            moduleManager.updateIdentityChat(Publickey, identityName, imageInBytes, "country", "state", "city", identityConnectionState);

        } catch (CantUpdateChatIdentityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public ChatIdentity getIdentity() {
        return identity;
    }

    private boolean imageIsInvalid() {
        if (imageInBytes == null) return true;
        return imageInBytes.length == 0;
    }

    private boolean entryDataIsInvalid() {
        if (moduleManager == null) return true;
        if (imageInBytes == null) return true;
        if (imageInBytes.length == 0) return true;
        if (identityName == null) return true;
        return identityName.isEmpty();
    }
}