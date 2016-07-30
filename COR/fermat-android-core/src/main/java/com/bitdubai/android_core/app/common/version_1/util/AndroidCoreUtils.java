package com.bitdubai.android_core.app.common.version_1.util;

import com.bitdubai.android_core.app.common.version_1.util.interfaces.BroadcasterInterface;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.02..
 */
public class AndroidCoreUtils implements com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils {

    private Map<UUID, BroadcasterInterface> context;
    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private boolean isStarted = false;


    private static final AndroidCoreUtils instance = new AndroidCoreUtils();

    public static AndroidCoreUtils getInstance() {
        return instance;
    }

    public AndroidCoreUtils() {
        this.context = new HashMap<>();
    }

    @Override
    public void publish(final BroadcasterType broadcasterType, final String code) {
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (isStarted) {
                            for (BroadcasterInterface broadcasterInterface : context.values()) {
                                broadcasterInterface.publish(broadcasterType, code);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(final BroadcasterType broadcasterType, final String appCode, final String code) {
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (isStarted) {
                            for (BroadcasterInterface broadcasterInterface : context.values()) {
                                broadcasterInterface.publish(broadcasterType, appCode, code);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void publish(final BroadcasterType broadcasterType, final String appCode, final FermatBundle bundle) {
        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (isStarted) {
                            for (BroadcasterInterface broadcasterInterface : context.values()) {
                                broadcasterInterface.publish(broadcasterType, appCode, bundle);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int publish(final BroadcasterType broadcasterType, final FermatBundle bundle) {
        int id = 0;
        try {
            if (isStarted) {
                for (BroadcasterInterface broadcasterInterface : context.values()) {
                    id = broadcasterInterface.publish(broadcasterType, bundle);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return id;
    }

    public Map<UUID, BroadcasterInterface> getListeners() {
        return context;
    }

    /**
     * @param context
     * @return the id
     */
    public void setContextAndResume(BroadcasterInterface context) {
        this.context.put(context.getId(), context);
    }

    public void clear(BroadcasterInterface context) {
        this.context.remove(context.getId());
    }


    public void setStarted(boolean started) {
        this.isStarted = started;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
