package com.bitdubai.sub_app.shop_manager.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.sub_app.shop_manager.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 12/01/2015.
 */
public class ShopDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private static int tabId;

    private int position;

    public static ShopDesktopFragment newInstance(int position) {
        ShopDesktopFragment f = new ShopDesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        String[] installed =
                {"false",
                        "false",
                        "true",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false",
                        "false"
                };
        String[] shop_names =
                {"Girls' wallet",
                        "Boys' wallet",
                        "My Donuts Shop",
                        "Youngs' wallet",
                        "Boca Juniors' wallet",
                        "Carrefour's wallet",
                        "Gucci's wallet",
                        "Bank Itau's wallet",
                        "Mc donals' wallet",
                        "Vans' wallet",
                        "Samsung's wallet",
                        "Popular Bank's wallet",
                        "Sony's wallet",
                        "BMW's wallet",
                        "HP's wallet",
                        "Billabong's wallet",
                        "Starbucks' wallet"

                };


        String[] shop_picture =
                {"wallet_store_cover_photo_girl",
                        "wallet_store_cover_photo_boy",
                        "wallet_store_cover_photo_shop",
                        "wallet_store_cover_photo_young",
                        "wallet_store_cover_photo_boca_juniors",
                        "wallet_store_cover_photo_carrefour",
                        "wallet_store_cover_photo_gucci",
                        "wallet_store_cover_photo_bank_itau",
                        "wallet_store_cover_photo_mcdonals",
                        "wallet_store_cover_photo_vans",
                        "wallet_store_cover_photo_samsung",
                        "wallet_store_cover_photo_bank_popular",
                        "wallet_store_cover_photo_sony",
                        "wallet_store_cover_photo_bmw",
                        "wallet_store_cover_photo_hp",
                        "wallet_store_cover_photo_billabong",
                        "wallet_store_cover_photo_starbucks"

                };

        mlist = new ArrayList<App>();


        for (int i = 0; i < installed.length; i++) {
            if (installed[i] == "true") {
                App item = new App();

                item.picture = shop_picture[i];
                item.company = shop_names[i];
                item.rate = (float) Math.random() * 5;
                item.value = (int) Math.floor((Math.random() * (500 - 80 + 1))) + 80;
                item.favorite = (float) Math.random() * 5;
                item.timetoarraive = (float) Math.random() * 5;
                item.sale = (float) Math.random() * 5;
                item.installed = true;
                mlist.add(item);
            }
        }

        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        //@SuppressWarnings("unchecked")
        //   ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_shop_desktop_fragment_grid_item, mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);


        return gridView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shell_shop_desktop_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public class App implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;

        public String title;

        public String description;

        public String picture;

        public String company;

        public String Open_hours;

        public String Address;

        public String Phone;

        public float rate;

        public int value;

        public float favorite;

        public float sale;

        public float timetoarraive;

        public boolean installed;

    }


    public class AppListAdapter extends ArrayAdapter<App> {


        public AppListAdapter(Context context, int textViewResourceId, List<App> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            App item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_shop_desktop_fragment_grid_item, parent, false);
                holder = new ViewHolder();

                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.company);
            //  holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());


            switch (item.picture) {
                case "wallet_store_cover_photo_girl":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_pink);
                    holder.imageView.setTag("ShopsActivity|1");
                    break;
                case "wallet_store_cover_photo_boy":
                    holder.imageView.setImageResource(R.drawable.icono_piggy_yellow);
                    holder.imageView.setTag("ShopsActivity|2");
                    break;
                case "wallet_store_cover_photo_shop":
                    holder.imageView.setImageResource(R.drawable.store_icon);
                    holder.imageView.setTag("ShopsActivity");
                    break;
                case "wallet_store_cover_photo_young":
                    holder.imageView.setImageResource(R.drawable.wallet_2);
                    holder.imageView.setTag("4");
                    break;
                case "wallet_store_cover_photo_boca_juniors":
                    holder.imageView.setImageResource(R.drawable.icono_club_1);
                    holder.imageView.setTag("ShopActivity|10");
                    break;
                case "wallet_store_cover_photo_carrefour":
                    holder.imageView.setImageResource(R.drawable.icono_retailer_1);
                    holder.imageView.setTag("ShopsActivity|7");
                    break;
                case "wallet_store_cover_photo_gucci":
                    holder.imageView.setImageResource(R.drawable.wallet_4);
                    holder.imageView.setTag("ShopsActivity|6");
                    break;
                case "wallet_store_cover_photo_bank_itau":
                    holder.imageView.setImageResource(R.drawable.icono_banco_1);
                    holder.imageView.setTag("ShopsActivity|8");
                    break;
                case "wallet_store_cover_photo_mcdonals":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_mcdonals);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
                case "wallet_store_cover_photo_vans":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_vans);
                    break;
                case "wallet_store_cover_photo_samsung":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_samsung);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
                case "wallet_store_cover_photo_bank_popular":
                    holder.imageView.setImageResource(R.drawable.icono_banco_2);
                    holder.imageView.setTag("ShopsActivity|9");
                    break;
                case "wallet_store_cover_photo_sony":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_sony);
                    holder.imageView.setTag("11");
                    break;
                case "wallet_store_cover_photo_hp":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_hp);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
                case "wallet_store_cover_photo_bmw":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_bmw);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
                case "wallet_store_cover_photo_billabong":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_billabong);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
                case "wallet_store_cover_photo_starbucks":
                    holder.imageView.setImageResource(R.drawable.wallet_store_cover_photo_starbucks);
                    holder.imageView.setTag("ShopsActivity|11");
                    break;
            }
            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {

            public ImageView imageView;
            public TextView companyTextView;

        }

    }

}

