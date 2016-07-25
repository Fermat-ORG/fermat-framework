package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.notifications;

/**
 * Created by Matias Furszyfer on 2016.06.20..
 */
public interface FermatNotification {

    /* Default notification {}. If your application does not prioritize its own
        * notifications, use this value for all notifications.
               */
    public static final int PRIORITY_DEFAULT = 0;

    /**
     * Lower {}, for items that are less important. The UI may choose to show these
     * items smaller, or at a different position in the list, compared with your app's
     * {@link #PRIORITY_DEFAULT} items.
     */
    public static final int PRIORITY_LOW = -1;

    /**
     * Lowest {}; these items might not be shown to the user except under special
     * circumstances, such as detailed notification logs.
     */
    public static final int PRIORITY_MIN = -2;

    /**
     * Higher {}, for more important notifications or alerts. The UI may choose to
     * show these items larger, or at a different position in notification lists, compared with
     * your app's {@link #PRIORITY_DEFAULT} items.
     */
    public static final int PRIORITY_HIGH = 1;

    /**
     * Highest {}, for your application's most important items that require the
     * user's prompt attention or input.
     */
    public static final int PRIORITY_MAX = 2;

    /**
     * Relative priority for this notification.
     * <p/>
     * Priority is an indication of how much of the user's valuable attention should be consumed by
     * this notification. Low-priority notifications may be hidden from the user in certain
     * situations, while the user might be interrupted for a higher-priority notification. The
     * system will make a determination about how to interpret this priority when presenting
     * the notification.
     * <p/>
     * <p>
     * A notification that is at least {@link #PRIORITY_HIGH} is more likely to be presented
     * as a heads-up notification.
     * </p>
     */

    int getId();

    int getPriority();


}
