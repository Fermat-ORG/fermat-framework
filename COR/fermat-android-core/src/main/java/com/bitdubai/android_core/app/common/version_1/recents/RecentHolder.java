package com.bitdubai.android_core.app.common.version_1.recents;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.wirelesspienetwork.overview.model.ViewHolder;

/**
 * Created by mati on 2016.03.03..
 */
public class RecentHolder extends ViewHolder<View, RecentApp> {

    private ViewGroup root;
    private FermatTextView title;
    private ImageView icon;

    public RecentHolder(View view) {
        super(view);
        root = (ViewGroup) view.findViewById(R.id.app_container);
        title = (FermatTextView) view.findViewById(R.id.recent_title);
        icon = (ImageView) view.findViewById(R.id.img_recent_icon);
    }

    public ViewGroup getRoot() {
        return root;
    }

    public FermatTextView getTitle() {
        return title;
    }

    public ImageView getIcon() {
        return icon;
    }
}
