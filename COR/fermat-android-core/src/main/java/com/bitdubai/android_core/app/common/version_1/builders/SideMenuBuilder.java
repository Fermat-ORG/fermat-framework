package com.bitdubai.android_core.app.common.version_1.builders;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.01.06..
 */
public class SideMenuBuilder {

    /**
     * Set navigation header
     */
    public static FrameLayout setHeader(Activity activity, NavigationViewPainter viewPainter) {
        if (viewPainter != null) {
            final View view = viewPainter.addNavigationViewHeader();
            FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.navigation_view_header);
            frameLayout.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            view.setLayoutParams(layoutParams);
            frameLayout.addView(view);
            return frameLayout;
        }
        return null;
    }

    /**
     * Set adapter
     */
    public static void setAdapter(RecyclerView navigation_recycler_view, FermatAdapter mAdapter, RecyclerView.ItemDecoration itemDecoration, List<MenuItem> lstItems, FermatListItemListeners fermatListItemListeners, String activityType) {
        boolean flag = false;
        int counter = 0;
        while (!flag && counter < lstItems.size()) {
            com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem = lstItems.get(counter);
            Activities navActivity = menuItem.getLinkToActivity();
            if (navActivity != null) {
                if (navActivity.getCode().equals(activityType)) {
                    menuItem.setSelected(true);
                    flag = true;
                }
            }
            counter++;
        }
        mAdapter.changeDataSet(lstItems);
        mAdapter.setFermatListEventListener(fermatListItemListeners);
        if (navigation_recycler_view != null) {
            navigation_recycler_view.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            if (itemDecoration != null) {
                navigation_recycler_view.addItemDecoration(itemDecoration);
            }
            navigation_recycler_view.invalidate();
        }
    }

    /**
     * Body
     */
    public static void setBody(RelativeLayout navigation_view_footer, boolean hasFooter, NavigationViewPainter viewPainter, LayoutInflater layoutInflater) {
        if (navigation_view_footer != null && viewPainter != null) {
            if (hasFooter) {
                navigation_view_footer.setVisibility(View.VISIBLE);
                ViewGroup viewGroup = viewPainter.addNavigationViewBodyContainer(layoutInflater, navigation_view_footer);
            }
        }
    }

    /**
     * Background color
     */
    public static void setBackground(final RelativeLayout navigation_view_body_container, final NavigationViewPainter viewPainter, final Resources resources) {
        if (navigation_view_body_container != null && viewPainter != null) {
            if (viewPainter.hasBodyBackground()) {
                AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

                    WeakReference<ViewGroup> view;

                    @Override
                    protected void onPreExecute() {
                        view = new WeakReference(navigation_view_body_container);
                    }

                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        return viewPainter.addBodyBackground();
                    }

                    @Override
                    protected void onPostExecute(Bitmap drawable) {
                        if (drawable != null) {
                            view.get().setBackground(new BitmapDrawable(resources, drawable));
                        }
                    }
                };
                asyncTask.execute();
            }

            if (viewPainter.addBodyBackgroundColor() > 0) {
                navigation_view_body_container.setBackgroundColor(viewPainter.addBodyBackgroundColor());
            }
        }
    }

}
