package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;

/**
 * Created by angel on 20/1/16.
 */

public class EditIdentityWorker extends FermatWorker {
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 4;

    private ChatIdentityModuleManager moduleManager;
    private ChatIdentity identityInfo;
    private ChatIdentity identity;

    public EditIdentityWorker(Activity context, FermatSession session, ChatIdentity identity, FermatWorkerCallBack callBack) {
        super(context, callBack);

        this.identity = identity;

        if (session != null) {
            ChatIdentity subAppSession = (ChatIdentity) session;
            identityInfo = (ChatIdentity) subAppSession;
         //   this.moduleManager = subAppSession.getModuleManager();
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        return null;
    }

  /*  @Override
    protected Object doInBackground() throws Exception {


        boolean valueChanged = (identity != identityInfo.isPublished());

        if ( identity == null ) {
            return INVALID_ENTRY_DATA;
        } else {
            moduleManager.updateCryptoBrokerIdentity(identity);
            if (valueChanged) {
                if (identity.isPublished()) {
                    moduleManager.publishIdentity(identity.getPublicKey());
                }else {
                    moduleManager.hideIdentity(identity.getPublicKey());
                }
            }
            return SUCCESS;
        }
    }*/
}