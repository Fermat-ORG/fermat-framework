package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.sub_app.wallet_factory.R;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "page";
    private static final String ARG_PARAM2 = "title";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    public static ContactsFragment newInstance(int page, String title) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        GridViewAdapter gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getMockImageData());
        gridView.setAdapter(gridAdapter);

        return view;
    }

    private ArrayList<ImageItem> getMockImageData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.deniz_profile_picture);
        imageItems.add(new ImageItem(bm, "Deniz"));
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.caroline_profile_picture);
        imageItems.add(new ImageItem(bm1, "Caroline"));
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.celine_profile_picture);
        imageItems.add(new ImageItem(bm2, "Celine"));
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.guillermo_profile_picture);
        imageItems.add(new ImageItem(bm3, "Guillermo"));
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.helen_profile_picture);
        imageItems.add(new ImageItem(bm4, "Helen"));
        return imageItems;
    }

    public class GridViewAdapter extends ArrayAdapter {
        private Context context;
        private int layoutResourceId;
        private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageTitle = (TextView) row.findViewById(R.id.text);
                holder.image = (ImageView) row.findViewById(R.id.image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            ImageItem item = this.data.get(position);
            holder.imageTitle.setText(item.getTitle());
            holder.image.setImageBitmap(item.getImage());
            return row;
        }

    }

    class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    class ImageItem {
        private Bitmap image;
        private String title;

        public ImageItem(Bitmap image, String title) {
            super();
            this.image = image;
            this.title = title;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    /*public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.florence_profile_picture, R.drawable.caroline_profile_picture,
                R.drawable.deniz_profile_picture
        };

        // Constructor
        public ImageAdapter(Context c){
            mContext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            return imageView;
        }

    }*/

}
