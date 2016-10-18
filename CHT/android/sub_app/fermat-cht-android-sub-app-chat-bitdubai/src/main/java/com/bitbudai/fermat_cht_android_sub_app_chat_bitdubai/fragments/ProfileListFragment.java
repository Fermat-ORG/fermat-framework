package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ProfileListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;

import java.util.ArrayList;

/**
 * Profile List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 03/03/16
 * @version 1.0
 */
public class ProfileListFragment extends AbstractFermatFragment implements ProfileListAdapter.AdapterCallback {

    private ProfileListAdapter adapter; // The main query adapter

    private ChatManager chatManager;
    private ErrorManager errorManager;
    private ChatPreferenceSettings chatSettings;
    private ChatSessionReferenceApp chatSession;
    private Toolbar toolbar;
    ListView list;

    ArrayList<String> profilename = new ArrayList<>();
    ArrayList<Bitmap> profileicon = new ArrayList<>();
    ArrayList<String> profileid = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView text;
    View layout;
    Typeface tf;

    public static ProfileListFragment newInstance() {
        return new ProfileListFragment();
    }

    @Override
    public void onMethodCallback() {//solution to access to another activity clicking the photo icon of the list
        changeActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL, appSession.getAppPublicKey());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            chatSession = ((ChatSessionReferenceApp) appSession);
            chatManager = chatSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            try {
                chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                chatSettings = null;
            }
            if (chatSettings == null) {
                chatSettings = new ChatPreferenceSettings();
                chatSettings.setIsPresentationHelpEnabled(true);
                try {
                    chatManager.persistSettings(appSession.getAppPublicKey(), chatSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));

            adapter = new ProfileListAdapter(getActivity(), profilename, profileicon, profileid, errorManager, appSession, this);
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        layout = inflater.inflate(R.layout.profile_list_fragment, container, false);
        text = (TextView) layout.findViewById(R.id.text);
        //text.setTypeface(tf, Typeface.NORMAL);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);

        try {
            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
            text.setText("No Registered Profile. Please create one in any Fermat Community.");

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
            }
        });

        adapter = new ProfileListAdapter(getActivity(), profilename, profileicon, profileid, errorManager, appSession, this);
        list = (ListView) layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()/*new AdapterView.OnItemClickListener()*/ {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                    final ChatUserIdentity profileSelected = null;//chatManager.getChatUserIdentity(profileid.get(position));
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Do you want to select " + profileSelected.getAlias() + " as your active profile?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            getResourceString(R.string.cht_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    try {
                                        chatSettings.setProfileSelected(profileSelected.getPublicKey(), profileSelected.getPlatformComponentType());
                                        Toast.makeText(getActivity(), "Profile Selected: " + profileSelected.getAlias(), Toast.LENGTH_SHORT).show();
                                        changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
                                    } catch (Exception e) {
                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                    }
                                    changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
                                }
                            });

                    builder1.setNegativeButton(
                            getResourceString(R.string.cht_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        dialog.cancel();
                                    } catch (Exception e) {
                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                    }
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                    appSession.setData(ChatSessionReferenceApp.PROFILE_DATA, null);//chatManager.getChatUserIdentity(profileid.get(position)));
                    changeActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL, appSession.getAppPublicKey());
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                return true;
            }
        });

        // Inflate the list fragment layout
        return layout;
    }

}
