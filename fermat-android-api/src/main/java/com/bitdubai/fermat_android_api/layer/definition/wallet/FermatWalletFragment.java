package com.bitdubai.fermat_android_api.layer.definition.wallet;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;

/**
 * Created by Matias Furszyfer on 2015.26.21..
 */
public class FermatWalletFragment extends AbstractFermatFragment {


    /**
     * Change activity
     */
    protected final void changeActivity(Activities activity, String appPublicKey) {
        destroy();
        getFermatScreenSwapper().changeActivity(activity.getCode(), appPublicKey);
    }

    /**
     * Change activity
     */
    protected final void changeActivity(Activities activity) {
        destroy();
        getFermatScreenSwapper().changeActivity(activity.getCode(), appSession.getAppPublicKey());
    }

    /**
     * Change activity
     */
    protected final void changeFragment(String fragment, int idContainer) {
        getFermatScreenSwapper().changeScreen(fragment, idContainer, null);
    }

    /**
     *
     */


    protected final RelativeLayout getToolbarHeader() {
        return getPaintActivtyFeactures().getToolbarHeader();
    }

    protected PaintActivityFeatures getPaintActivtyFeactures() {
        return ((PaintActivityFeatures) getActivity());
    }

    protected void setNavigationDrawer(FermatAdapter adapter) {
        getPaintActivtyFeactures().changeNavigationDrawerAdapter(adapter);
    }

    protected void addNavigationHeader(View view) {
        getPaintActivtyFeactures().addNavigationViewHeader(view);
    }

    protected void addNavigationView(NavigationViewPainter navigationViewPainter){
        getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
    }

    protected Toolbar getToolbar() {
        return getPaintActivtyFeactures().getToolbar();
    }

    protected void changeApp(Engine emgine,String fermatAppToConnectPublicKey, Object[] objects) {
        getFermatScreenSwapper().connectWithOtherApp(emgine,fermatAppToConnectPublicKey, objects);
    }

    protected FermatScreenSwapper getFermatScreenSwapper() {
        return (FermatScreenSwapper) getActivity();
    }
    protected void invalidate(){
        getPaintActivtyFeactures().invalidate();
    }


    private void destroy(){
        onDestroy();
        System.gc();
    }

}


