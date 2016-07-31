package com.bitdubai.sub_app.chat_community.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.sub_app.chat_community.adapters.NavigationAdapter;
import com.bitdubai.sub_app.chat_community.common.utils.FragmentsCommons;

import java.lang.ref.WeakReference;

/**
 * ChatCommunityNavigationViewPainter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> subAppSession;
    private WeakReference<FermatApplicationCaller> applicationsHelper;

    public ChatCommunityNavigationViewPainter(Context activity,
                                              ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> subAppSession,
                                              FermatApplicationCaller applicationsHelper) {
        this.activity = new WeakReference(activity);
        this.subAppSession = subAppSession;
        this.applicationsHelper = new WeakReference<>(applicationsHelper);
    }

    @Override
    public View addNavigationViewHeader() {
        View headerView = null;
        try {
            headerView = FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), subAppSession,
                    applicationsHelper.get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return headerView;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        return new NavigationAdapter(activity.get(), null);
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        return null;
    }

    @Override
    public Bitmap addBodyBackground() {
        return null;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return false;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }
}
