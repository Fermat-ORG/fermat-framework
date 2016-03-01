package com.bitdubai.sub_app.crypto_customer_community.navigationDrawer;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.popups.ListIdentitiesDialog;
import com.bitdubai.sub_app.crypto_customer_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2015.11.24..
 */
public class CustomerCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ActiveActorIdentityInformation actorIdentity;
    private CryptoCustomerCommunitySubAppSession subAppSession;
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;


    public CustomerCommunityNavigationViewPainter(Context activity, ActiveActorIdentityInformation actorIdentity, CryptoCustomerCommunitySubAppSession subAppSession) {
        this.activity = new WeakReference<Context>(activity);
        this.actorIdentity = actorIdentity;
        this.subAppSession = subAppSession;
        this.moduleManager = subAppSession.getModuleManager();
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation actorIdentityInformation) {
        View headerView = null;

        try {
            headerView = FragmentsCommons.setUpHeaderScreen((LayoutInflater) activity.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity.get(), actorIdentityInformation);
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
                                //Chamo olvidate que te deje recrear la actividad desde acá, tenes un metodo que se llama invalidate que hace esto
                                //activity.get().recreate();
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
            return new CustomerCommunityWalletNavigationViewAdapter(activity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.cbc_navigation_view_bottom, base, true);
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
