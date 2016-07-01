package com.bitdubai.sub_app_artist_community.navigation_drawer;

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
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.commons.popups.ListIdentitiesDialog;
import com.bitdubai.sub_app_artist_community.commons.utils.FragmentsCommons;

import java.lang.ref.WeakReference;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ArtistCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation actorIdentity;
    ReferenceAppFermatSession subAppSession;
    private ArtistCommunitySubAppModuleManager moduleManager;

    public ArtistCommunityNavigationViewPainter(Context activity, ActiveActorIdentityInformation actorIdentity, ReferenceAppFermatSession subAppSession) {
        this.activity = new WeakReference<Context>(activity);
        this.actorIdentity = actorIdentity;
        this.subAppSession = subAppSession;
        this.moduleManager = (ArtistCommunitySubAppModuleManager)subAppSession.getModuleManager();

    }

    @Override
    public View addNavigationViewHeader() {
        View headerView = null;

        //todo: use the module manager to get the profile in background and paint it when this arrived.
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
                                //chamo olvidate que te haga hacer esto, hay un metodo que se llama invalidate() que lo hace
                                //activity.recreate();
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
            return new ArtistCommunitySupAppNavigationViewAdapter(activity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.aac_navigation_view_bottom, base, true);
        //FermatTextView bitcoinBalance = (FermatTextView) layout.findViewById(R.id.ccw_navigation_view_bitcoin_balance);
        //bitcoinBalance.setText("0.3521 BTC");

        return layout;
    }

    @Override
    public Bitmap addBodyBackground() {
       /* Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(), R.drawable.actionbar_background, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return drawable;*/
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
