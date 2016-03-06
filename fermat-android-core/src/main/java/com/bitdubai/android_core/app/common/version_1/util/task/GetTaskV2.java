package com.bitdubai.android_core.app.common.version_1.util.task;

import android.app.Activity;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAllRegisteredPlatformsException;
import com.bitdubai.fermat_core.FermatSystem;

import java.lang.ref.WeakReference;

public class GetTaskV2 extends FermatWorker {

    WeakReference<Activity> activityWeakReference;

    public GetTaskV2(Activity service, FermatWorkerCallBack fermatWorkerCallBack){
            super(service,fermatWorkerCallBack);
            activityWeakReference = new WeakReference<Activity>(service);
        }


    /**
         * This function is used for the run method of the fermat background worker
         *
         * @throws Exception any type of exception
         */
        @Override
        protected Object doInBackground() throws Exception {

            final FermatSystem fermatSystem =((ApplicationSession) activityWeakReference.get().getApplication()).getFermatSystem();

            if(!fermatSystem.isStarted) {
                try {
                    fermatSystem.startAllRegisteredPlatforms();

                } catch (CantStartAllRegisteredPlatformsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
}

