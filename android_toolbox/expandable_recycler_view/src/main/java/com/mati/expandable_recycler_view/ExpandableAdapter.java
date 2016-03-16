package com.mati.expandable_recycler_view;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;
import com.mati.expandable_recycler_view.holder.ChildViewHolder;
import com.mati.expandable_recycler_view.holder.GrouperParentViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Expandable adapter
 *
 * Matias Furszyfer
 *
 */

public class ExpandableAdapter
                <GROUPER_HOLDER extends GrouperParentViewHolder,
                CHILD_HOLDER extends ChildViewHolder,
                GROUPER_ITEM extends ParentListItem,
                        ITEM> extends ExpandableRecyclerAdapter<GROUPER_HOLDER, CHILD_HOLDER, GROUPER_ITEM, ITEM> {

    private final GROUPER_HOLDER grouperHolder;
    private final CHILD_HOLDER childHolder;
    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public ExpandableAdapter(
            Context context,
            List<GROUPER_ITEM> parentItemList,
            Resources res,
            GROUPER_HOLDER grouperHolder,
            CHILD_HOLDER childHolder) {
        super(parentItemList);

        this.grouperHolder = grouperHolder;
        this.childHolder = childHolder;

    }

    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public GROUPER_HOLDER onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(grouperHolder.getHolderLayoutRes(), parent, false);
        GROUPER_HOLDER fermatViewHolder = null;
        try {
            Constructor constructor = grouperHolder.getClass().getDeclaredConstructor(View.class, Integer.class,Integer.class,Integer.class);
            constructor.setAccessible(true);
            fermatViewHolder = (GROUPER_HOLDER)constructor.newInstance(view,0,0,grouperHolder.getHolderLayoutRes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fermatViewHolder;
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public CHILD_HOLDER onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(childHolder.getHolderLayoutRes(), parent, false);
        CHILD_HOLDER fermatViewHolder = null;
        try {
            Constructor constructor = childHolder.getClass().getDeclaredConstructor(View.class, Integer.class,Integer.class,Integer.class);
            constructor.setAccessible(true);
            fermatViewHolder = (CHILD_HOLDER)constructor.newInstance(view,0,0,childHolder.getHolderLayoutRes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fermatViewHolder;
    }



    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(GROUPER_HOLDER parentViewHolder, int position, GROUPER_ITEM parentListItem) {
        parentViewHolder.bind(parentListItem.getChildCount(), (GROUPER_ITEM) parentListItem.getItem());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(CHILD_HOLDER childViewHolder, int position, ITEM childListItem) {
        childViewHolder.bind(childListItem);
    }
}