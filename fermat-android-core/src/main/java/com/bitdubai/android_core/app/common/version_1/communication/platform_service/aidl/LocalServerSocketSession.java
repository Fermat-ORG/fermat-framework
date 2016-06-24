package com.bitdubai.android_core.app.common.version_1.communication.platform_service.aidl;

import android.net.LocalSocket;

import com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.sockets.LocalSocketSession;

/**
 * Created by mati on 2016.05.03..
 */
public class LocalServerSocketSession extends LocalSocketSession{


    public LocalServerSocketSession(String pkIdentity, LocalSocket localSocket) {
        super(pkIdentity, localSocket);
    }

    @Override
    public void onReceiveMessage(FermatModuleObjectWrapper object) {

    }


    public void onExceptionOccur(Exception e) {

    }
}
