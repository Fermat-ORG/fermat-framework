package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ConnectionListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;*/
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.AbsListView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
//import com.bitdubai.fermat_cht_api.layer.chat_module.interfaces.ChatModuleManager;
//import com.bitdubai.fermat_cht_api.layer.platform_service.error_manager.interfaces.ErrorManager;


/**
 * Connections List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 20/01/16
 * @version 1.0
 *
 */
public class ConnectionsListFragment extends AbstractFermatFragment {

    // Defines a tag for identifying log entries
    public List<Contact> contacts;

    private Contact contactl;

    // Whether or not this fragment is showing in a two-pane layout
    private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    ListView list;
    String TAG="CHT_ConnectionsListFragment";
    ArrayList<String> contactname=new ArrayList<String>();
    ArrayList<Integer> contacticon=new ArrayList<Integer>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    //public ContactsListFragment() {}
   // static void initchatinfo(){
   // }

    public static ConnectionsListFragment newInstance() {
    //    initchatinfo();
        return new ConnectionsListFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            chatSession=((ChatSession) appSession);
            moduleManager= chatSession.getModuleManager();
            chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
        }catch (Exception e)
        {

            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.connection_list_fragment, container, false);
        TextView text=(TextView) layout.findViewById(R.id.text);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        try {
//            //Comentar, solo para pruebas
//            ContactImpl cadded=new ContactImpl();
//            cadded.setContactId(UUID.randomUUID());
//            cadded.setAlias("josejcb");
//            cadded.setRemoteActorPublicKey("jose");
//            cadded.setRemoteActorType(PlatformComponentType.ACTOR_ASSET_USER);
//            String dateString = "30/09/2014";
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = sdf.parse(dateString);
//            long startDate = date.getTime();
//            cadded.setCreationDate(startDate);
//            cadded.setRemoteName("No hay nadie conectado");
//            chatManager.saveContact(cadded);
//            //Fin Comentar
            List <Contact> con=  chatManager.getContacts();
            int size = con.size();
            if (size > 0) {
                for (int i=0;i<size;i++){
                    contactname.add(con.get(i).getAlias());
                    contactid.add(con.get(i).getContactId());
                    contacticon.add(R.drawable.ic_contact_picture_holo_light);
                }
                text.setVisibility(View.GONE);
            }else{
                text.setVisibility(View.VISIBLE);
                text.setText("No Connections");
            }
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        ConnectionListAdapter adapter=new ConnectionListAdapter(getActivity(), contactname, contacticon, contactid);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    appSession.setData(ChatSession.CONNECTION_DATA, chatManager.getContactByContactId(contactid.get(position)));
                    Contact conn = chatSession.getSelectedConnection();
                    //chatManager.saveContact(conn);
                    Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
                    //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());

                } catch (CantGetContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                //} catch (CantSaveContactException e) {
                   // errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e){
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();

                        try {
                            List <Contact> con=  chatManager.getContacts();
                            if (con.size() > 0) {
                                for (int i=0;i<con.size();i++){
                                    contactname.add(con.get(i).getAlias());
                                    contactid.add(con.get(i).getContactId());
                                    contacticon.add(R.drawable.ic_contact_picture_holo_light);
                                }
                                final ConnectionListAdapter adaptador =
                                        new ConnectionListAdapter(getActivity(), contactname, contacticon, contactid);
                                adaptador.refreshEvents(contactname, contacticon, contactid);
                                adaptador.notifyDataSetChanged();
                                list.invalidateViews();
                                list.requestLayout();
                            }else{
                                Toast.makeText(getActivity(), "No Contacts", Toast.LENGTH_SHORT).show();
                            }
                        } catch (CantGetContactException e) {

                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        } catch (Exception e) {
                            //TODO: fix this
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        return layout;
        // Inflate the list fragment layout
        //return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

}
