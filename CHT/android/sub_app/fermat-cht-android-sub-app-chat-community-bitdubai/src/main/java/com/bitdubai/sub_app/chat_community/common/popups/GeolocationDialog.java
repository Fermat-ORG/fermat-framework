package com.bitdubai.sub_app.chat_community.common.popups;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils.CitiesImpl;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.adapters.GeolocationAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 11/06/16.
 */
public class GeolocationDialog extends FermatDialog<ReferenceAppFermatSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    //ATTRIBUTES
    private EditText searchInput;
    private ChatActorCommunitySubAppModuleManager mChatActorCommunityManager;
    private ListView mListView;
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private CitiesImpl cityFromList;
    private ImageView lupaButton;
    private ImageView closeButton;

    //THREAD ATTRIBUTES
    private boolean isRefreshing = false;
    private List<Cities> lstChatUserInformations = new ArrayList<>();
    private GeolocationAdapter adapter;
    private ErrorManager errorManager;
    private LinearLayout emptyView;
    private final Activity activity;
    TextView noDatalabel;

    //SETTERS ATTRIBUTES
    String Country;
    String State;
    String Input;

    public GeolocationDialog (Activity activity, ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession, ResourceProviderManager resources){
        super(activity, appSession, null);
        this.appSession = appSession;
        this.activity = activity;
    }

    public void onClick(View v) {
        int id = v.getId();
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {

            mChatActorCommunityManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            mChatActorCommunityManager.setAppPublicKey(appSession.getAppPublicKey());

            mListView = (ListView) findViewById(R.id.geolocation_view);
            noDatalabel = (TextView) findViewById(R.id.nodatalabel_geo);
            searchInput = (EditText) findViewById(R.id.geolocation_input);
            emptyView = (LinearLayout) findViewById(R.id.empty_view_geo);
            closeButton = (ImageView) findViewById(R.id.close_geolocation_dialog);

            lupaButton = (ImageView) this.findViewById(R.id.lupita_button);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

//            lupaButton.setOnClickListener( new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onRefresh();
//                        }
//                    }
//            );
            adapter = new GeolocationAdapter(getActivity(), lstChatUserInformations, errorManager);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //cityFromList = (CitiesImpl) parent.getItemAtPosition(position);
                    cityFromList = (CitiesImpl) lstChatUserInformations.get(position);
                }
            });

            lupaButton = (ImageView) this.findViewById(R.id.lupita_button); ///TODO Roy: checar cómo hacer el ImageView del layout un botón sin usar ImageButton.
            lupaButton.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //onRefresh();
                            try {
                                lstChatUserInformations = mChatActorCommunityManager.getCities(searchInput.getText().toString());
                                adapter = new GeolocationAdapter(getActivity(), lstChatUserInformations, errorManager);
                                mListView.setAdapter(adapter);
                                adapter.refreshEvents(lstChatUserInformations);
                                onRefresh();
                            }catch (CantConnectWithExternalAPIException | CantCreateBackupFileException |
                            CantCreateCountriesListException  | CantGetCitiesListException e){
                                if (getActivity() != null)
                                    errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                                            UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                            }
                        }
                    }
            );
            showEmpty(true, emptyView);
        }catch (Exception e){
            System.out.println("Exception at Geolocation Dialog");
        }
    }

    protected int setLayoutId() {
        return R.layout.cht_comm_geolocation_dialog;
    }

    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public void onRefresh(){
        if (!isRefreshing) {
            isRefreshing = true;
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData(searchInput.getText().toString());
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    isRefreshing = false;
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity()!= null && adapter != null) {
                            lstChatUserInformations = (ArrayList<Cities>) result[0];
                            mListView.setAdapter(adapter);
                            adapter.refreshEvents(lstChatUserInformations);
                            if (lstChatUserInformations.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else
                        showEmpty(true, emptyView);
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (getActivity() != null)
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
                }
            });
            worker.execute();
        }
    }

    private synchronized List<Cities> getMoreData(String filter) {
        System.out.println("****************** GETMORE DATA SYNCHRONIZED ENTERING");
        List<Cities> dataSet = new ArrayList<>();

        try {
            List<Cities> result = mChatActorCommunityManager.getCities(filter);
            dataSet.addAll(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("****************** GETMORE DATA SYNCRHONIZED SALIO BIEN: ");
        return dataSet;
    }

    public void showEmpty(boolean show, View emptyView) {
        if (show &&
                (noDatalabel.getVisibility() == View.GONE || noDatalabel.getVisibility() == View.INVISIBLE)) {
            noDatalabel.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.VISIBLE);
            if (adapter != null) {
                adapter.refreshEvents(null);
                mListView.setAdapter(adapter);
            }
        } else if (!show && noDatalabel.getVisibility() == View.VISIBLE) {
            noDatalabel.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }
    }


}
