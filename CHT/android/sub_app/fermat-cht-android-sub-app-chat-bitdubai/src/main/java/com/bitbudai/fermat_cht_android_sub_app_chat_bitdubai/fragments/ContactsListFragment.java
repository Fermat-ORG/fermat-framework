package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Contact List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/16
 * @version 1.0
 */
public class ContactsListFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager> {

    private ContactListAdapter adapter; // The main query adapter
    private ChatManager chatManager;
    private ErrorManager errorManager;
    private ChatPreferenceSettings chatSettings;
    ChatActorCommunitySelectableIdentity chatIdentity;
    FermatApplicationCaller applicationsHelper;
    ListView list;

    ArrayList<String> contactname = new ArrayList<>();
    ArrayList<Bitmap> contacticon = new ArrayList<>();
    ArrayList<String> contactid = new ArrayList<>();
    ArrayList<String> contactStatus = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView noData;
    LinearLayout emptyView;
    View layout;
    TextView noDatalabel;
    TextView nochatssubtitle;
    TextView nochatssubtitle1;
    TextView nochatssubtitle2;
    private static final int MAX = 1000;
    private int offset = 0;
    private SearchView searchView;

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            chatManager = appSession.getModuleManager();
            chatManager.setAppPublicKey(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();
            applicationsHelper = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();
            adapter = new ContactListAdapter(getActivity(), contactname, contacticon, contactid, contactStatus,
                    errorManager, appSession, this);
            chatSettings = null;

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        try {
            chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            chatSettings = null;
        }

        //set and / or get local identity
        try {
            chatIdentity = chatSettings.getIdentitySelected();
            if (chatIdentity == null) {
                List<ChatIdentity> chatIdentityList = chatManager
                        .getIdentityChatUsersFromCurrentDeviceUser();
                if (chatIdentityList != null && chatIdentityList.size() > 0) {
                    chatIdentity = chatManager
                            .newInstanceChatActorCommunitySelectableIdentity(
                                    chatIdentityList.get(0));
                    chatSettings.setIdentitySelected(chatIdentity);
                    chatSettings.setProfileSelected(chatIdentity.getPublicKey(),
                            PlatformComponentType.ACTOR_CHAT);
                }
            }
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }

    void updateValues() {
        try {
            contactname.clear();
            contactid.clear();
            contacticon.clear();
            contactStatus.clear();
            if (chatIdentity != null) {
                List<ChatActorCommunityInformation> con = chatManager.listAllConnectedChatActor(chatIdentity, MAX, offset);

                int size = con.size();
                if (size > 0) {
                    for (ChatActorCommunityInformation conta : con) {
                        contactname.add(conta.getAlias());
                        contactid.add(conta.getPublicKey());
                        ByteArrayInputStream bytes;
                        if (conta.getImage() != null) {
                            bytes = new ByteArrayInputStream(conta.getImage());
                            BitmapDrawable bmd = new BitmapDrawable(bytes);
                            contacticon.add(bmd.getBitmap());
                        } else {
                            Drawable d = getResources().getDrawable(R.drawable.cht_image_profile);
                            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (new ByteArrayOutputStream()));
                            contacticon.add(bitmap);
                        }
                        contactStatus.add(conta.getStatus());
                    }
                    emptyView.setVisibility(View.GONE);
                    noData.setVisibility(View.GONE);
                    noDatalabel.setVisibility(View.GONE);
                    nochatssubtitle.setVisibility(View.GONE);
                    nochatssubtitle1.setVisibility(View.GONE);
                    nochatssubtitle2.setVisibility(View.GONE);
                    layout.setBackgroundResource(0);
                    layout.setBackground(new ColorDrawable(Color.parseColor("#F9F9F9")));
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.VISIBLE);
                    noDatalabel.setVisibility(View.VISIBLE);
                    nochatssubtitle.setVisibility(View.VISIBLE);
                    nochatssubtitle1.setVisibility(View.VISIBLE);
                    nochatssubtitle2.setVisibility(View.VISIBLE);
                }
            } else {
                emptyView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.VISIBLE);
                noDatalabel.setVisibility(View.VISIBLE);
                nochatssubtitle.setVisibility(View.VISIBLE);
                nochatssubtitle1.setVisibility(View.VISIBLE);
                nochatssubtitle2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = inflater.inflate(R.layout.contact_list_fragment, container, false);
        emptyView = (LinearLayout) layout.findViewById(R.id.empty_view);
        noData = (ImageView) layout.findViewById(R.id.nodata);
        noDatalabel = (TextView) layout.findViewById(R.id.nodatalabel);
        nochatssubtitle = (TextView) layout.findViewById(R.id.nochatssubtitle);
        nochatssubtitle1 = (TextView) layout.findViewById(R.id.nochatssubtitle1);
        nochatssubtitle2 = (TextView) layout.findViewById(R.id.nochatssubtitle2);
        emptyView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.VISIBLE);
        noDatalabel.setVisibility(View.VISIBLE);
        nochatssubtitle.setVisibility(View.VISIBLE);
        nochatssubtitle1.setVisibility(View.VISIBLE);
        nochatssubtitle2.setVisibility(View.VISIBLE);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        updateValues();
        adapter = new ContactListAdapter(getActivity(), contactname, contacticon, contactid, contactStatus,
                errorManager, appSession, this);
        list = (ListView) layout.findViewById(R.id.list);
        list.setAdapter(adapter);
        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()/*new AdapterView.OnItemClickListener()*/ {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    displayChat(position);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            updateValues();
//                            final ContactListAdapter adaptador =
//                                    new ContactListAdapter(getActivity(), contactname, contacticon, contactid, contactStatus,
//                                            errorManager, appSession, null);
//                            adaptador.refreshEvents(contactname, contacticon, contactid);
//                            list.invalidateViews();
//                            list.requestLayout();
//                        } catch (Exception e) {
//                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                        }
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2500);
//            }
//        });
        // Inflate the list fragment layout
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void onOptionMenuPrepared(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contact_list_menu, menu);
        menu.add(0, 2, 2, getResourceString(R.string.menu_go_to_profile))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 3, 3, getResourceString(R.string.menu_go_to_community))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 4, 4, getResourceString(R.string.menu_help))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem searchItem = menu.findItem(R.id.menu_search);// menu.findItem(1);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint(getResources().getString(R.string.cht_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    if (s.equals(searchView.getQuery().toString())) {
                        updateValues();
                        adapter.refreshEvents(contactname, contacticon, contactid);
                        adapter.getFilter().filter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                } else {
                    updateValues();
                    adapter.refreshEvents(contactname, contacticon, contactid);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 4:
                    PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.cht_banner)
                            .setIconRes(R.drawable.chat_subapp)
                            .setSubTitle(R.string.cht_chat_subtitle)
                            .setBody(R.string.cht_chat_body)
                            .setTextFooter(R.string.cht_chat_footer)
                            .setTitle(R.string.cht_dialog_welcome)
                            .setTextCloseButton(R.string.cht_dialog_button_close)
                            .setCheckboxText(R.string.cht_dialog_dont_show)
                            .setVIewColor(R.color.cht_color_dialog)
                            .build();
                    presentationDialog.show();
                    break;
                case 1:
                    break;
                case 2:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_COMMUNITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void appSessionSetDataContact(int position) {
        Contact contact = new ContactImpl();
        contact.setRemoteActorPublicKey(adapter.getContactId(position));
        contact.setAlias(adapter.getItem(position));
        contact.setContactStatus(adapter.getContactStatus(position));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (adapter.getContactIcon(position) != null) {
            adapter.getContactIcon(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
        } else {
            Drawable d = getResources().getDrawable(R.drawable.cht_image_profile); // the drawable (Captain Obvious, to the rescue!!!)
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        byte[] byteArray = stream.toByteArray();
        contact.setProfileImage(byteArray);
        appSession.setData(ChatSessionReferenceApp.CONTACT_DATA, contact);
    }

    public void displayChat(int position) {
        appSession.setData("whocallme", "contact");
        appSessionSetDataContact(position);
        changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unbindDrawables(layout.findViewById(R.id.list));
//        unbindDrawables(layout.findViewById(R.id.empty_view));
//        adapter.clear();
//        chatSettings = null;
//        errorManager = null;
//        chatIdentity = null;
//        chatManager = null;
//        applicationsHelper = null;
//        layout.destroyDrawingCache();
//        layout = null;
//        contactname.clear();
//        contacticon.clear();
//        contactid.clear();
//        contactStatus.clear();
//        emptyView.destroyDrawingCache();
//        emptyView.removeAllViewsInLayout();
//        chatIdentity = null;
//        emptyView.removeAllViews();
//        noData.destroyDrawingCache();
//        noDatalabel.destroyDrawingCache();
//        nochatssubtitle.destroyDrawingCache();
//        nochatssubtitle1.destroyDrawingCache();
//        nochatssubtitle2.destroyDrawingCache();
//        list.removeAllViewsInLayout();
//        list = null;
//        mSwipeRefreshLayout.removeAllViews();
//        mSwipeRefreshLayout.removeAllViewsInLayout();
////        mSwipeRefreshLayout.destroyDrawingCache();
//        mSwipeRefreshLayout = null;
//        searchView = null;
//        applicationsHelper = null;
        destroy();
    }

    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
            ((ViewGroup) view).removeAllViewsInLayout();
        }
    }
}
