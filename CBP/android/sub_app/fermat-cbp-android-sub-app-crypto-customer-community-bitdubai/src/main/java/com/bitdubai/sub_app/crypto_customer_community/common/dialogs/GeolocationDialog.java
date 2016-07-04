package com.bitdubai.sub_app.crypto_customer_community.common.dialogs;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.adapters.GeolocationAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by roy on 11/06/16.
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 29/06/16.
 */
public class GeolocationDialog extends FermatDialog<ReferenceAppFermatSession, ResourceProviderManager>
        implements FermatListItemListeners<ExtendedCity> {

    //ATTRIBUTES
    private EditText searchInput;
    private RecyclerView recyclerView;
    private TextView noDataLabel;
    private ProgressBar progressBar;

    private ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> appSession;
    private CryptoCustomerCommunitySubAppModuleManager mActorCommunityManager;
    private ErrorManager errorManager;

    private List<ExtendedCity> lstLocations = new ArrayList<>();

    private GeolocationAdapter adapter;

    private AdapterCallback mAdapterCallback;

    public GeolocationDialog(Activity activity,
                             ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> appSession,
                             ResourceProviderManager resources,
                             AdapterCallback mAdapterCallback) {

        super(activity, appSession, resources);
        this.appSession = appSession;
        this.mAdapterCallback = mAdapterCallback;

        Window window = this.getWindow();
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.TOP;
        windowAttributes.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowAttributes);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onItemClickListener(ExtendedCity data, int position) {
        if (mAdapterCallback != null)
            mAdapterCallback.onLocationItemClicked(data);
    }

    @Override
    public void onLongItemClickListener(ExtendedCity data, int position) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            mActorCommunityManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            mActorCommunityManager.setAppPublicKey(appSession.getAppPublicKey());

            recyclerView = (RecyclerView) findViewById(R.id.ccc_geolocation_recycler_view);
            noDataLabel = (TextView) findViewById(R.id.ccc_no_data_geo);
            searchInput = (EditText) findViewById(R.id.ccc_geolocation_input);
            progressBar = (ProgressBar) findViewById(R.id.ccc_geolocation_progress_bar);
            ImageView closeButton = (ImageView) findViewById(R.id.ccc_close_geolocation_dialog);
            ImageView lupaButton = (ImageView) this.findViewById(R.id.lupita_button);

            adapter = new GeolocationAdapter(getActivity(), lstLocations);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (searchInput.getText().toString().isEmpty())
                        dismiss();
                    else
                        searchInput.getText().clear();
                }
            });

            lupaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMoreDataAsync();
                }
            });

        } catch (Exception e) {
            System.out.println("Exception at Geolocation Dialog");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccc_dialog_geolocation_browser;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    /**
     * Get the list of locations asynchronously and show it in the recycler view
     */
    public void getMoreDataAsync() {
        progressBar.setVisibility(View.VISIBLE);

        final FermatWorker fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {
                return mActorCommunityManager.getExtendedCitiesByFilter(searchInput.getText().toString());
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                progressBar.setVisibility(View.GONE);

                if (result != null && result.length > 0) {
                    lstLocations = (List<ExtendedCity>) result[0];
                    adapter.changeDataSet(lstLocations);
                }

                showOrHideEmptyView();
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                progressBar.setVisibility(View.GONE);

                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        });

        fermatWorker.execute();
    }

    public void showOrHideEmptyView() {
        final boolean show = lstLocations.isEmpty();

        if (show && (noDataLabel.getVisibility() == View.GONE || noDataLabel.getVisibility() == View.INVISIBLE)) {
            noDataLabel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else if (!show && noDataLabel.getVisibility() == View.VISIBLE) {
            noDataLabel.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public interface AdapterCallback {
        void onLocationItemClicked(ExtendedCity cityFromList);
    }
}
