package com.bitdubai.sub_app.crypto_customer_community.navigationDrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
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
    private CryptoCustomerCommunitySubAppSession subAppSession;


    public CustomerCommunityNavigationViewPainter(Context activity, CryptoCustomerCommunitySubAppSession subAppSession) {
        this.activity = new WeakReference<>(activity);
        this.subAppSession = subAppSession;
    }

    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation actorIdentityInformation) {
        View headerView = null;

        try {
            final Context context = activity.get();
            final Object layoutInflaterService = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            headerView = FragmentsCommons.setUpHeaderScreen((LayoutInflater) layoutInflaterService, context, actorIdentityInformation);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(context, subAppSession, null);
                        listIdentitiesDialog.setTitle("Connection Request");
                        listIdentitiesDialog.show();
                    }catch(Exception ignore){ }
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

        return (RelativeLayout) layoutInflater.inflate(R.layout.cbc_navigation_view_bottom, base, true);
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
