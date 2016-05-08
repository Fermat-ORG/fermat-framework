package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ContactListFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.ContactsListFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatsList;

/**
 * Contact List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

public class ContactListAdapter extends ArrayAdapter implements Filterable {//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory

    ArrayList<String> contactInfo=new ArrayList<>();
    ArrayList<Bitmap> contactIcon=new ArrayList<>();
    ArrayList<String> contactId=new ArrayList<>();
    private ChatManager chatManager;
    private FermatSession appSession;
    private ErrorManager errorManager;
    private ChatModuleManager moduleManager;
    private ChatSession chatSession;
    private ContactsListFragment contactsListFragment;
    private Context context;
    private Context mContext;
    ImageView imagen;
    TextView contactName;
    private AdapterCallback mAdapterCallback;

    ArrayList<String> filteredData;
    private String filterString;

    public ContactListAdapter(Context context, ArrayList contactInfo, ArrayList contactIcon, ArrayList contactId,
                              ChatManager chatManager, ChatModuleManager moduleManager,
                              ErrorManager errorManager, ChatSession chatSession, FermatSession appSession, AdapterCallback mAdapterCallback) {
        super(context, R.layout.contact_list_item, contactInfo);
        this.contactInfo = contactInfo;
        this.contactIcon = contactIcon;
        this.contactId = contactId;
        this.chatManager=chatManager;
        this.moduleManager=moduleManager;
        this.errorManager=errorManager;
        this.chatSession=chatSession;
        this.appSession=appSession;
        this.mContext=context;
        try {
            this.mAdapterCallback = mAdapterCallback;
        }catch (Exception e)
        {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.contact_list_item, null, true);
        try {
            imagen = (ImageView) item.findViewById(R.id.icon);//imagen.setImageResource(contacticon.get(position));//contacticon[position]);
            imagen.setImageBitmap(Utils.getRoundedShape(contactIcon.get(position), 400));//imagen.setImageBitmap(getRoundedShape(decodeFile(getContext(), contacticon.get(position)), 300));

            contactName = (TextView) item.findViewById(R.id.text1);
            contactName.setText(contactInfo.get(position));
            //contactname.setTypeface(tf, Typeface.NORMAL);

            final int pos=position;
            imagen.setOnClickListener(new View.OnClickListener() {
               // int pos = position;
                @Override
                public void onClick(View v) {
                    try {
                        Contact contact=new ContactImpl();
                        contact.setRemoteActorPublicKey(contactId.get(pos));
                        contact.setAlias(contactInfo.get(pos));
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        contactIcon.get(pos).compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        contact.setProfileImage(byteArray);
                        appSession.setData(ChatSession.CONTACT_DATA, null);//chatManager.getContactByContactId(contactid.get(pos)));
                        appSession.setData(ChatSession.CONTACT_DATA, contact);
                        //TODO:metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                        //appSession.setData(ChatSession.CONTACT_DATA, contactid.get(pos));
                        mAdapterCallback.onMethodCallback();//solution to access to changeactivity. j
                        //} catch (CantGetContactException e) {
                        //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return item;
    }

    public static interface AdapterCallback {
        void onMethodCallback();
    }

    public void refreshEvents(ArrayList contactInfo, ArrayList contactIcon, ArrayList contactId) {
        this.contactInfo=contactInfo;
        this.contactIcon=contactIcon;
        this.contactId=contactId;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (contactInfo != null) {
            if (filteredData != null) {
                if (filteredData.size() < contactInfo.size()) {
                    return filteredData.size();
                } else {
                    return contactInfo.size();
                }
            }else{
                return contactInfo.size();
            }
        } else {
            return 0;
        }
    }

    @Override
    public String getItem(int position) {
        return contactInfo.get(position);
    }

    public Bitmap getContactIcon(int position) {
        return contactIcon.get(position);
    }

    public String getContactId(int position) {
        return contactId.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList contactInfo, ArrayList contactIcon, ArrayList contactId) {
        this.contactInfo=contactInfo;
        this.contactIcon=contactIcon;
        this.contactId=contactId;
        this.filteredData = contactInfo;
    }

    public Filter getFilter() {
        return new ContactListFilter(contactInfo, contactIcon, contactId, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }

}