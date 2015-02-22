package com.bitdubai.android.app.subapp.wallet_factory.version_2.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.smartwallet.R;

import com.bitdubai.android.app.common.version_1.classes.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.res.Configuration;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;




public class RefillFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;

    private int position;

    public static RefillFragment newInstance(int position) {
        RefillFragment f = new RefillFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] company_names = {
                "D'Agostino",
                "Kalustyan's",
                "Jubilee Market Place",
                "Kings Super Markets",
                "Fairway Market",
                "T-Mobile Store Broadway",
                "AT&T Store Clifton" };
        String[] company_addresses = {
                "528 3rd Ave",
                "123 Lexington Ave",
                "99 John St",
                "1212 Bernard McFeeley Shipyard Ln",
                "240 E 86th St",
                "732 Broadway",
                "776 Clifton NJ 07012"
                 };
        String[] company_horario = {
                "Today 8:00 am – 10:00 pm",
                "Today 10:00 am – 8:00 pm",
                "Today Open 24 hours",
                "Today 7:00 am – 10:00 pm",
                "Today 7:00 am – 12:00 am",
                "Today 10:00 am – 8:00 pm",
                "Today 9:00 am – 9:00 pm" };
        String[] company_telefono = {
                "+1 212-684-3133",
                "+1 212-685-3451",
                "+1 212-233-0808",
                "+1 201-239-4060",
                "+1 212-327-2008",
                "+1 212-674-5377",
                "+1 973-779-2120" };
        String[] company_descriptions = {
                "X",
                "Specialty market known for Indian & Mideastern spices, teas & other global food items, plus a cafe.",
                "Specialty food market open around the clock for meats, produce & cheeses plus organic fare.",
                "X",
                "Grab-and-go selection of upscale prepared foods attached to a sprawling gourmet grocery store.",
                "Wireless provider offering cell phones, data plans, Internet devices & accessories.",
                "Telecommunications company providing cell phones, data plans, tablets & more." };
        String[] company_sites = {
                "dagnyc.com",
                "kalustyans.com",
                "jubileemarketplace.net",
                "kingsfoodmarkets.com",
                "fairwaymarket.com",
                "t-mobile.com",
                "att.com", };
        String[] available_amount = {"+ $5,000.00",
                "+ $5,000.00",
                "+ $1,000.00",
                "+ $100.00",
                "+ $5,000.00",
                "+ $10,000.00",
                "+ $10,000.00",
                "",
                "",
                "",
                "",
                ""};
        String[] percentage_amount = {
                "-12.56%",
                "+2.24%",
                "+4.42%",
                "-7.42%",
                "+8.23%",
                "-15.34%",
                "-5.53%",
                "3%",
                "",};


        if (mlist == null)
        {

            mlist = new ArrayList<App>();

            for (int i = 0; i < 7; i++) {
                App item = new App();
                item.title = company_sites[i];
                item.description = company_descriptions[i];
                item.company = company_names[i];
                item.Open_hours = company_horario[i];
                item.Phone = company_telefono[i];
                item.Address = company_addresses[i];
                item.rate = (float) Math.random() * 5;
                item.value = (int) Math.floor((Math.random() * (500 - 80 + 1))) + 80;
                item.favorite = (float) Math.random() * 5;
                item.Available = available_amount[i];
                item.percentage = percentage_amount[i];
                mlist.add(item);

            }
        }


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }

        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_factory_fragment_refills_item, mlist));

        return gridView;
    }





    public class App implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;

        public String title;

        public String percentage;

        public String Available;

        public String description;

        public String company;

        public String Open_hours;

        public String Address;

        public String Phone;

        public float rate;

        public int value;

        public float favorite;

        public String sale;

        public String triangle;


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
                convertView = inflater.inflate(R.layout.wallets_factory_fragment_refills_item, parent, false);
                holder = new ViewHolder();
                holder.sale = (ImageView) convertView.findViewById(R.id.sale);
                holder.triangle = (ImageView) convertView.findViewById(R.id.triangle);
                holder.favorite = (ImageView) convertView.findViewById(R.id.favorite);

                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
                holder.companyDescription = (TextView) convertView.findViewById(R.id.company_description);
                holder.availableAmount = (TextView) convertView.findViewById(R.id.availability);
                holder.percentageAmount = (TextView) convertView.findViewById(R.id.percentage);
                //holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
                holder.openHours = (TextView) convertView.findViewById(R.id.open_hours);
                holder.timeToArrive = (TextView) convertView.findViewById(R.id.time_to_arrive);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }





            holder.titleTextView.setText(item.title);
            holder.companyTextView.setText(item.company);
            holder.companyDescription.setText(item.Address);
            holder.availableAmount.setText(item.Available);
            holder.percentageAmount.setText(item.percentage);
            //holder.ratingBar.setRating(item.rate);

            holder.openHours.setText(  item.Open_hours);


            holder.timeToArrive.setText( position + " min");


            holder.openHours.setTypeface(MyApplication.getDefaultTypeface());
            holder.timeToArrive.setTypeface(MyApplication.getDefaultTypeface());
            holder.titleTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());
            holder.companyDescription.setTypeface(MyApplication.getDefaultTypeface());
            holder.percentageAmount.setTypeface(MyApplication.getDefaultTypeface());


            if (item.favorite > 2)
            {
                holder.favorite.setImageResource(R.drawable.grid_background_favorite);
            }
            else
            {
                holder.favorite.setImageResource(R.drawable.grid_background_not_favorite);
            }


            switch (position)
            {
                case 0:
                    holder.sale.setImageResource(R.drawable.tag_5_refills);
                    break;
                case 1:
                    holder.sale.setImageResource(R.drawable.tag_3_refills);
                    break;
                case 2:
                    holder.sale.setImageResource(R.drawable.tag_6_refills);
                    break;
                case 3:
                    holder.sale.setImageResource(R.drawable.tag_3_refills);
                    break;
                case 4:
                    holder.sale.setImageResource(R.drawable.tag_2_refills);
                    break;
                case 5:
                    holder.sale.setImageResource(R.drawable.tag_5_refills);
                    break;
                case 6:
                    holder.sale.setImageResource(R.drawable.transparent);
                    break;


            }

            switch (position)
            {
                case 0:
                    holder.triangle.setImageResource(R.drawable.green_triangle);
                    break;
                case 1:
                    holder.triangle.setImageResource(R.drawable.red_triangle);
                    break;
                case 2:
                    holder.triangle.setImageResource(R.drawable.red_triangle);
                    break;
                case 3:
                    holder.triangle.setImageResource(R.drawable.green_triangle);
                    break;
                case 4:
                    holder.triangle.setImageResource(R.drawable.red_triangle);
                    break;
                case 5:
                    holder.triangle.setImageResource(R.drawable.green_triangle);
                    break;
                case 6:
                    holder.triangle.setImageResource(R.drawable.green_triangle);
                    break;


            }


            switch (position)
            {
                case 0:
                    holder.imageView.setImageResource(R.drawable.refill_1) ;
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.refill_2);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.refill_3);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.refill_4);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.refill_5);
                    break;
                case 5:
                    holder.imageView.setImageResource(R.drawable.refill_6);
                    break;
                case 6:
                    holder.imageView.setImageResource(R.drawable.refill_7);
                    break;



            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {

            public ImageView sale;
            public ImageView favorite;
            public ImageView triangle;
            public TextView openHours;
            public TextView timeToArrive;
            public TextView Phone;
            public TextView Address;
            public TextView Available;


            public ImageView imageView;

            public TextView titleTextView;

            public TextView companyTextView;
            public TextView availableAmount;
            public TextView percentageAmount;
            public TextView companyDescription;

            public RatingBar ratingBar;

            public TextView valueTextView;

        }

    }

}

