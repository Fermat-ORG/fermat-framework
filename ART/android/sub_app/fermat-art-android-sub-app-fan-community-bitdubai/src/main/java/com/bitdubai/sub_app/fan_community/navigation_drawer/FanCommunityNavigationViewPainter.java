package com.bitdubai.sub_app.fan_community.navigation_drawer;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.commons.popups.ListIdentitiesDialog;
import com.bitdubai.sub_app.fan_community.commons.utils.FragmentsCommons;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSessionReferenceApp;

import java.lang.ref.WeakReference;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class FanCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation actorIdentity;
    private ReferenceAppFermatSession subAppSession;
    private FanCommunityModuleManager moduleManager;


    public FanCommunityNavigationViewPainter(
            Context activity,
            ActiveActorIdentityInformation actorIdentity,
            ReferenceAppFermatSession subAppSession) {
        this.activity = new WeakReference<Context>(activity);
        this.actorIdentity = actorIdentity;
        this.subAppSession = subAppSession;
        this.moduleManager = (FanCommunityModuleManager) subAppSession.getModuleManager();
    }

    @Override
    public View addNavigationViewHeader() {
        View headerView=null;
        //TODO: el actorIdentityInformation lo podes obtener del module en un hilo en background y hacer un lindo loader mientras tanto
        try {
            //If the actor is not set yet, I'll try to find it.
            if(actorIdentity==null&&moduleManager!=null){
                try{
                    //I'll set the app public cas just in case.
                    String subAppPublicKey = subAppSession.getAppPublicKey();
                    if(subAppPublicKey!=null&&!subAppPublicKey.isEmpty()){
                        moduleManager.setAppPublicKey(subAppPublicKey);
                    }
                    actorIdentity = moduleManager.getSelectedActorIdentity();
                } catch (Exception e) {
                    //Cannot find the selected identity in any form... I'll let the identity in null
                }
            }
            headerView = FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), actorIdentity);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(activity.get(), subAppSession, null);
                        listIdentitiesDialog.setTitle("Connection Request");
                        listIdentitiesDialog.show();
                        listIdentitiesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                        listIdentitiesDialog.show();
                    }catch(Exception e){ }
                }
            });
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return headerView;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new FanCommunityWalletNavigationViewAdapter(activity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
                R.layout.afc_navigation_view_bottom,
                base,
                true);
        return layout;
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
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }
}
