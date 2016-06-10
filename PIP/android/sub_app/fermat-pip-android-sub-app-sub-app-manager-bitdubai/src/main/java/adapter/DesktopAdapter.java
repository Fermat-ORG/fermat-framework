package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.ui.adapters.AdapterChangeListener;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.interface_objects.FermatFolder;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;
import com.bitdubai.sub_app.manager.R;

import java.util.Collections;
import java.util.List;

import commons.helpers.ItemTouchHelperAdapter;
import holder.FermatAppHolder;

public class DesktopAdapter extends FermatAdapter<Item, FermatAppHolder> implements ItemTouchHelperAdapter {

    public static final int DEKSTOP = 1;
    public static final int FOLDER = 2;
    public static final int DESKTOP_FOLDER = 3;
    private Typeface tf;
    private Item parentItem;

    private int fragmentWhoUseThisAdapter;
    private boolean isChild = false;
    private DesktopHolderClickCallback desktopHolderClickCallback;
    private AdapterChangeListener adapterChangeListener;

    public DesktopAdapter(Context context) {
        super(context);
    }

    public DesktopAdapter(Context context, List<Item> dataSet, DesktopHolderClickCallback desktopHolderClickCallback, int fragmentWhoUseThisAdapter) {
        super(context, dataSet);
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        this.fragmentWhoUseThisAdapter = fragmentWhoUseThisAdapter;
    }

    public DesktopAdapter(Context context, List<Item> dataSet, Item parent, DesktopHolderClickCallback desktopHolderClickCallback, int fragmentWhoUseThisAdapter, boolean isChild) {
        super(context, dataSet);
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        this.fragmentWhoUseThisAdapter = fragmentWhoUseThisAdapter;
        this.isChild = isChild;
        this.parentItem = parent;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/CaviarDreams.ttf");
    }


    @Override
    protected FermatAppHolder createHolder(View itemView, int type) {
        FermatAppHolder fermatAppHolder = new FermatAppHolder(itemView);
        return fermatAppHolder;
    }

    @Override
    protected int getCardViewResource() {
        if (DEKSTOP == fragmentWhoUseThisAdapter)
            return R.layout.shell_sub_app_desktop_fragment_grid_item;
//            else if(FOLDER==fragmentWhoUseThisAdapter) return R.layout.grid_folder;
//            else if (DESKTOP_FOLDER==fragmentWhoUseThisAdapter) return R.layout.desktop_grid_item;
//
//            return R.layout.shell_wallet_desktop_front_grid_item;
        return 1;
    }

    @Override
    protected void bindHolder(FermatAppHolder holder, Item data, final int position) {


//            byte[] profileImage = data.getIcon();
//            if (profileImage != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
//                holder.thumbnail.setImageBitmap(bitmap);
//            }
        if (data.getIconResource() != 0)
            if (data.getType() != InterfaceType.EMPTY)
                holder.thumbnail.setImageResource(data.getIconResource());
        if (data.getType() == InterfaceType.FOLDER) {
            holder.thumbnail.setVisibility(View.GONE);
            holder.folder.setVisibility(View.VISIBLE);
            holder.folder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(position).selected = !dataSet.get(position).selected;
                    Item item = dataSet.get(position);
                    notifyItemChanged(position);
                    desktopHolderClickCallback.onHolderItemClickListener(item, position);
                    if (adapterChangeListener != null)
                        adapterChangeListener.onDataSetChanged(dataSet);
                }
            });
            holder.folder.setAdapter(new DesktopAdapter(context, ((FermatFolder) data.getInterfaceObject()).getLstFolderItems(), data, desktopHolderClickCallback, DESKTOP_FOLDER, true));

        } else {

            if (!isChild) {
                holder.name.setText(data.getName());
                //holder.name.setTypeface(tf);
//                    holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dataSet.get(position).selected = !dataSet.get(position).selected;
//                            Item item = dataSet.get(position);
//                            notifyItemChanged(position);
//                            desktopHolderClickCallback.onHolderItemClickListener(item, position);
//                            if (adapterChangeListener != null)
//                                adapterChangeListener.onDataSetChanged(dataSet);
//                        }
//                    });
                holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSet.get(position).selected = !dataSet.get(position).selected;
                        Item item = dataSet.get(position);
                        notifyItemChanged(position);
                        desktopHolderClickCallback.onHolderItemClickListener(item, position);
                        if (adapterChangeListener != null)
                            adapterChangeListener.onDataSetChanged(dataSet);
                    }
                });
            } else {
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), data.getIconResource());
                Bitmap bt = Bitmap.createScaledBitmap(bm, 50, 50, false);
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                            dataSet.get(parentItem.getPosition()).selected = !dataSet.get(parentItem.getPosition()).selected;
//                            Item item = dataSet.get(position);
//                            notifyItemChanged(position);
                        desktopHolderClickCallback.onHolderItemClickListener(parentItem, parentItem.getPosition());
//                            if (adapterChangeListener != null)
//                                adapterChangeListener.onDataSetChanged(dataSet);
                    }
                };
                holder.grid_container.setOnClickListener(onClickListener);
                holder.thumbnail.setOnClickListener(onClickListener);
            }


        }

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataSet, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataSet, i, i - 1);
            }
        }
        getItem(fromPosition).setPosition(toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void setAdapterChangeListener(AdapterChangeListener adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }
}