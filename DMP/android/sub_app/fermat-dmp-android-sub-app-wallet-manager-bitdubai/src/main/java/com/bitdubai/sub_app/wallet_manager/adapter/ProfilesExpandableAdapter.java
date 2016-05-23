package com.bitdubai.sub_app.wallet_manager.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTaskWithRes;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.commons.model.GrouperItem;
import com.bitdubai.sub_app.wallet_manager.holder.GrouperViewHolder;
import com.bitdubai.sub_app.wallet_manager.holder.ProfilesHolder;

import java.util.ArrayList;
import java.util.List;


public class ProfilesExpandableAdapter
        extends ExpandableRecyclerAdapter<GrouperViewHolder, ProfilesHolder, GrouperItem, InstalledApp> {

    private LayoutInflater mInflater;

    private Resources res;

    private List<BitmapWorkerTaskWithRes> taskList;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public ProfilesExpandableAdapter(Context context, List<GrouperItem> parentItemList, Resources res) {
        super(parentItemList);
        taskList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.res = res;
        for (int i=0;i<parentItemList.size();i++)
            onParentListItemExpanded(i);
    }



    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public GrouperViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.com_grouper_list_item, parent, false);
        return new GrouperViewHolder(view,res);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public ProfilesHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.profiles_list_item, parent, false);
        return new ProfilesHolder(view);
    }



    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(GrouperViewHolder parentViewHolder, int position, GrouperItem parentListItem) {
        parentViewHolder.bind(parentListItem.getChildCount(), (Platforms) parentListItem.getItem());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(ProfilesHolder childViewHolder, int position, InstalledApp childListItem) {
        childViewHolder.bind(childListItem);
    }

    @Override
    protected void onChangeDataSet(){
        for (BitmapWorkerTaskWithRes bitmapWorkerTaskWithRes : taskList) {
            try{
                bitmapWorkerTaskWithRes.cancel(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




}