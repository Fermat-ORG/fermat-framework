package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import android.util.Log;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.ChatIdentitySubAppModulePluginRoot;

/**
 * Created by angel on 20/1/16.
 */

public class EditIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 2;
    public static final int MISSING_IMAGE = 4;

    private byte[] imageInBytes;
    private String identityName;

    private ChatIdentitySubAppModulePluginRoot moduleManager;
    private ErrorManager errorManager;
    private ChatIdentity identity;
    private String Publickey;
    public EditIdentityExecutor(byte[] imageInBytes,String Publickey , String identityName) {
        this.imageInBytes = imageInBytes;
        this.Publickey = Publickey;
        this.identityName = identityName;
    }

    public EditIdentityExecutor(FermatSession session,String Publickey , String identityName, byte[] imageInBytes) {
        this(imageInBytes, Publickey ,identityName);
        identity = null;
        if (session != null) {
            ChatIdentitySession subAppSession = (ChatIdentitySession) session;
            Log.i("*****CHT IDENTITY******", "LA SESION tiene valorrrrrr!!!!!!!");
            this.moduleManager = (ChatIdentitySubAppModulePluginRoot) subAppSession.getModuleManager();
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
            moduleManager.getChatIdentityManager().updateIdentityChat(Publickey,identityName, imageInBytes);

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