package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import android.util.Log;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */
public class CreateChatIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 2;
    public static final int MISSING_IMAGE = 4;

    private byte[] imageInBytes;
    private String identityName;
    private String identityConnectionState;

    private ChatIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatIdentity identity;

    public CreateChatIdentityExecutor(byte[] imageInBytes, String identityName, String identityConnectionState) {
        this.imageInBytes = imageInBytes;
        this.identityName = identityName;
        this.identityConnectionState = identityConnectionState;
    }

    public CreateChatIdentityExecutor(ChatIdentityModuleManager moduleManager, ErrorManager errorManager, byte[] imageInBytes, String identityName, String identityConnectionState) {
        this(imageInBytes, identityName, identityConnectionState);

        this.moduleManager = moduleManager;
        this.errorManager = errorManager;
        identity = null;
    }

    public CreateChatIdentityExecutor(FermatSession session, String identityName, byte[] imageInBytes, String identityConnectionState) throws CantGetChatIdentityException {
        this(imageInBytes, identityName, identityConnectionState);
        identity = null;

        if (session != null) {
            ChatIdentitySession subAppSession = (ChatIdentitySession) session;
            this.moduleManager = subAppSession.getModuleManager();
            this.errorManager = subAppSession.getErrorManager();
        }
    }

    public int execute() {

        if (imageIsInvalid())
            return MISSING_IMAGE;

        if (entryDataIsInvalid())
            return INVALID_ENTRY_DATA;

        try {
            Log.i("CHT CREATE IDENTITY",identityName+imageInBytes);
            //TODO: Buscar la manera de que esta informacion venga desde android puede ser por la geolocalizacion
            moduleManager.createNewIdentityChat(identityName, imageInBytes, "country", "state", "city", identityConnectionState);


        } catch (CantCreateNewChatIdentityException e) {
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
