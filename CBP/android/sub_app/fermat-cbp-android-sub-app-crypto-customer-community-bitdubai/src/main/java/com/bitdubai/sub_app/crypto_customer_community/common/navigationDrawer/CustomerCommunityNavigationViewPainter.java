package com.bitdubai.sub_app.crypto_customer_community.common.navigationDrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.dialogs.ListIdentitiesDialog;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;

import java.lang.ref.WeakReference;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;


/**
 * Created by mati on 2015.11.24..
 */
@Deprecated
public class CustomerCommunityNavigationViewPainter implements NavigationViewPainter {

    private WeakReference<Context> activity;
    private ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> subAppSession;
    private ActiveActorIdentityInformation selectedActorIdentity;


    public CustomerCommunityNavigationViewPainter(Context activity, ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> subAppSession) {
        this.activity = new WeakReference<>(activity);
        this.subAppSession = subAppSession;
    }

    @Override
    public View addNavigationViewHeader() {
        final CryptoCustomerCommunitySubAppModuleManager moduleManager = subAppSession.getModuleManager();

        final Context context = activity.get();
        final LayoutInflater layoutInflaterService = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View headerView = layoutInflaterService.inflate(R.layout.ccc_row_navigation_drawer_community_header, null, false);

        FermatWorker fermatWorker = new FermatWorker(context) {
            @Override
            protected Object doInBackground() throws Exception {
                if (selectedActorIdentity == null)
                    return moduleManager.getSelectedActorIdentity();
                return selectedActorIdentity;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                selectedActorIdentity = (ActiveActorIdentityInformation) result[0];

                try {
                    FragmentsCommons.setUpHeaderScreen(headerView, context, selectedActorIdentity);
                    headerView.findViewById(R.id.ccc_progress_bar).setVisibility(View.GONE);
                    headerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(context, subAppSession, null);
                                listIdentitiesDialog.setTitle("Connection Request");
                                listIdentitiesDialog.show();
                            } catch (Exception ignore) {
                            }
                        }
                    });
                } catch (FermatException e) {
                    subAppSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                final ErrorManager errorManager = subAppSession.getErrorManager();
                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        });

        fermatWorker.execute();

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
        return Color.parseColor("#33ffffff");
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
