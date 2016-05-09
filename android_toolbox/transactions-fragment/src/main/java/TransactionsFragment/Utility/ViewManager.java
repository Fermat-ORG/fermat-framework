package TransactionsFragment.Utility;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Clelia López on 3/8/16
 */
public class ViewManager {
    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void remove_View(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null)
            return;
        final int index = parent.indexOfChild(currentView);
        remove_View(currentView);
        remove_View(newView);
        parent.addView(newView, index);
    }
}
