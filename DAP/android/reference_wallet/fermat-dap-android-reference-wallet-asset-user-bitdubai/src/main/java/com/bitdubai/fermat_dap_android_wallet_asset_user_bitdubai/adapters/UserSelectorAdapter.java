package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madscientist on 17/03/16.
 */
public class UserSelectorAdapter  extends ArrayAdapter<User>{

    Context context;
    int resource;
    int textViewResourceId;
    int imageViewResourceId;
    FermatTextView nameText;
    ImageView imageViewUser;
    List<User> items, tempItems, suggestions;


    public UserSelectorAdapter(Context context, int resource, int textViewResourceId, List<User>items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<User>(items);
        suggestions = new ArrayList<User>();
    }
    public UserSelectorAdapter(Context context, int resource, int textViewResourceId,int imageViewResourceId, List<User>items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
        this.items = items;
        tempItems = new ArrayList<User>(items);
        suggestions = new ArrayList<User>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dap_wallet_asset_user_asset_sell_select_users_item, parent, false);
        }
        User user = items.get(position);
        if (user != null) {
            nameText = (FermatTextView) view.findViewById(R.id.userName);
            imageViewUser = (ImageView) view.findViewById(R.id.imageView_user_sell_avatar);
            if (nameText != null)
                nameText.setText(user.getName());
            if (user.getActorAssetUser().getProfileImage() != null && user.getActorAssetUser().getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getActorAssetUser().getProfileImage(), 0, user.getActorAssetUser().getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                imageViewUser.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((User) resultValue).getName();
            return str;
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (User user : tempItems) {
                    if (user.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(user);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<User> filterList = (ArrayList<User>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (User user : filterList) {
                    add(user);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
