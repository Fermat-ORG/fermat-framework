package com.bitdubai.android_core.app.common.version_1.util.task;

import android.app.Service;
import android.util.Log;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatServiceWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAllRegisteredPlatformsException;
import com.bitdubai.fermat_core.FermatSystem;

import java.lang.ref.WeakReference;

public class GetTask extends FermatServiceWorker {

    WeakReference<Service> activityWeakReference;

    public GetTask(Service service, FermatWorkerCallBack fermatWorkerCallBack) {
        super(service, fermatWorkerCallBack);
        activityWeakReference = new WeakReference<Service>(service);
    }


    /**
     * This function is used for the run method of the fermat background worker
     *
     * @throws Exception any type of exception
     */
    @Override
    protected Object doInBackground() throws Exception {

        Log.i("GetTask", "fermat system");
        final FermatSystem fermatSystem = ((FermatApplication) activityWeakReference.get().getApplication()).getFermatSystem();

        if (!fermatSystem.isStarted) {
            try {
                Log.i("GetTask", "fermat system previous to start");
                    /* now let's wait until the debugger attaches */
//                   android.os.Debug.waitForDebugger();
                fermatSystem.startAllRegisteredPlatformsMati();
                Log.i("GetTask", "fermat system started");

            } catch (CantStartAllRegisteredPlatformsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

