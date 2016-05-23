package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.structure;

import android.net.LocalSocket;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.BufferChannelAIDL;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.sockets.LocalSocketSession;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.05.02..
 */
public class LocalClientSocketSession extends LocalSocketSession {

    private static final String TAG = "ClientSocketSession";
    private BufferChannelAIDL bufferChannelAIDL;

    public LocalClientSocketSession(String pkIdentity,LocalSocket localSocket,BufferChannelAIDL bufferChannelAIDL) {
        super(pkIdentity,localSocket);
        this.bufferChannelAIDL = bufferChannelAIDL;
    }


    @Override
    public void onReceiveMessage(FermatModuleObjectWrapper object) {
        try {
            Log.i(TAG,"Recieving object");
            if(object.getE()!=null){
                bufferChannelAIDL.addFullDataAndNotificateArrive(object.getObjectRequestId(),object.getE());
            }else {
                bufferChannelAIDL.addFullDataAndNotificateArrive(object.getObjectRequestId(), (Serializable) object.getObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onExceptionOccur(Exception e) {
        e.printStackTrace();
    }

}
