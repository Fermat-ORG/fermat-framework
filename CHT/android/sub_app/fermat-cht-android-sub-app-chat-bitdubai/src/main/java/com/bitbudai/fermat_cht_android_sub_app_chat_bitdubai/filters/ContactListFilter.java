package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.graphics.Bitmap;
import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactListAdapter;

import java.util.ArrayList;

/**
 * ContactListFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/04/16.
 * @version 1.0
 */
public class ContactListFilter extends Filter {

    ArrayList<String> contactInfo=new ArrayList<>();
    ArrayList<Bitmap> contactIcon=new ArrayList<>();
    ArrayList<String> contactId=new ArrayList<>();
    private ContactListAdapter adapter;
    ArrayList<String> contactInfoDatan=new ArrayList<>();
    ArrayList<Bitmap> contactIconDatan=new ArrayList<>();
    ArrayList<String> contactIdDatan=new ArrayList<>();


    public ContactListFilter(ArrayList contactInfo, ArrayList contactIcon, ArrayList contactId, ContactListAdapter adapter) {
        this.contactInfo = contactInfo;
        this.contactIcon = contactIcon;
        this.contactId = contactId;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        int count = contactInfo.size();

        String filterableString;
        String resource;
        contactInfoDatan.clear();
        contactIdDatan.clear();
        contactIconDatan.clear();
        for (int i = 0; i < count; i++) {
            resource = contactInfo.get(i);
            filterableString = resource;
            if (filterableString.toLowerCase().contains(filterString)) {
                contactInfoDatan.add(contactInfo.get(i));
                contactIdDatan.add(contactId.get(i));
                contactIconDatan.add(contactIcon.get(i));
            }
        }

        results.values = contactInfoDatan;
        results.count = contactInfoDatan.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setData((ArrayList<String>) filterResults.values, contactIconDatan, contactIdDatan);
        adapter.notifyDataSetChanged();
    }
}
