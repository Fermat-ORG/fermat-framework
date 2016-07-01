package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson Ramirez
 *
 * @since 18/12/2015
 */
public class CryptoBrokerNavigationViewAdapter extends FermatAdapter<MenuItem, FermatViewHolder> {
    private static final int TYPE_MENU_ITEMS = 0;
    private static final int TYPE_FOOTER_TITLE = 1;
    private static final int TYPE_FOOTER_ITEM = 2;

    private List<NavViewFooterItem> earningsItems;
    private List<NavViewFooterItem> stockItems;
    private String stockTitle = "Current Stock";
    private String earningsTitle = "Daily Earnings";

    public CryptoBrokerNavigationViewAdapter(Context context) {
        super(context);

        stockItems = new ArrayList<>();
        stockItems.add(new NavViewFooterItem("Bitcoin", "145.32"));
        stockItems.add(new NavViewFooterItem("US Dollar", "14.04"));
        stockItems.add(new NavViewFooterItem("Bolivar", "350,400.25"));

        earningsItems = new ArrayList<>();
        earningsItems.add(new NavViewFooterItem("US Dollar", "1,400.01"));
        earningsItems.add(new NavViewFooterItem("Bolivar", "350,251.87"));
    }

    public CryptoBrokerNavigationViewAdapter(Context context, List<NavViewFooterItem> stock, List<NavViewFooterItem> earnings) {
        super(context);

        stockItems = stock;
        earningsItems = earnings;
    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(type), viewGroup, false), type);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        switch (type) {
            case TYPE_MENU_ITEMS:
                return new NavigationItemMenuViewHolder(itemView);
            case TYPE_FOOTER_TITLE:
                return new NavTitleFooterViewHolder(itemView);
            case TYPE_FOOTER_ITEM:
                return new NavItemFooterViewHolder(itemView);
            default:
                throw new IllegalArgumentException("Cant recognise thw given value");
        }
    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    private int getCardViewResource(int type) {
        switch (type) {
            case TYPE_MENU_ITEMS:
                return R.layout.cbw_navigation_row;
            case TYPE_FOOTER_TITLE:
                return R.layout.cbw_navigation_view_footer_title_item;
            case TYPE_FOOTER_ITEM:
                return R.layout.cbw_navigation_view_footer_item;
            default:
                throw new IllegalArgumentException("Cant recognise thw given value");
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1 + stockItems.size() + 1 + earningsItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < dataSet.size())
            return TYPE_MENU_ITEMS;
        if (isStockTitleItemPosition(position))
            return TYPE_FOOTER_TITLE;
        if (isEarningsTitleItemPosition(position))
            return TYPE_FOOTER_TITLE;
        else
            return TYPE_FOOTER_ITEM;
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {

        if (holder instanceof NavigationItemMenuViewHolder) {
            super.onBindViewHolder(holder, position);

        } else if (holder instanceof NavTitleFooterViewHolder) {
            NavTitleFooterViewHolder titleFooterViewHolder = (NavTitleFooterViewHolder) holder;
            String title = isStockTitleItemPosition(position) ? stockTitle : earningsTitle;
            titleFooterViewHolder.getNavFooterTitle().setText(title);

        } else {
            NavItemFooterViewHolder itemFooterViewHolder = (NavItemFooterViewHolder) holder;
            NavViewFooterItem item = isEarningsItemPosition(position) ?
                    earningsItems.get(getEarningsItemPosition(position)) :
                    stockItems.get(getStockItemPosition(position));

            itemFooterViewHolder.getNavFooterItemCurrency().setText(item.getCurrency());
            itemFooterViewHolder.getNavFooterItemCurrencyAmount().setText(item.getValue());
        }
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, MenuItem data, int position) {
        NavigationItemMenuViewHolder itemMenuViewHolder = (NavigationItemMenuViewHolder) holder;

        MenuItem menuItem = getItem(position);
        itemMenuViewHolder.getLabel().setText(menuItem.getLabel());
        if (menuItem.isSelected()) {
            bindSelectedMenuItem(itemMenuViewHolder, position);
        } else {
            bindMenuItem(itemMenuViewHolder, position);
        }
        data.setSelected(false);
        menuItem.setSelected(false);

    }

    private void bindMenuItem(NavigationItemMenuViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                break;
            case 1:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                break;
        }
    }

    private void bindSelectedMenuItem(NavigationItemMenuViewHolder holder, int position) {
        holder.getRowContainer().setBackgroundResource(R.color.cbw_navigation_view_menu_item_overlay);
        try {
            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getEarningsItemPosition(int position) {
        return Math.abs(dataSet.size() + 1 + stockItems.size() + 1 - position);
    }

    private int getStockItemPosition(int position) {
        return Math.abs(dataSet.size() + 1 - position);
    }

    private boolean isEarningsTitleItemPosition(int position) {
        return position == dataSet.size() + 1 + stockItems.size();
    }

    private boolean isStockTitleItemPosition(int position) {
        return position == dataSet.size();
    }

    private boolean isEarningsItemPosition(int position) {
        return position >= getItemCount() - earningsItems.size() && position < getItemCount();
    }

    public void setEarningsTitle(String earningsTitle) {
        this.earningsTitle = earningsTitle;
    }

    public void setStockTitle(String stockTitle) {
        this.stockTitle = stockTitle;
    }
}
