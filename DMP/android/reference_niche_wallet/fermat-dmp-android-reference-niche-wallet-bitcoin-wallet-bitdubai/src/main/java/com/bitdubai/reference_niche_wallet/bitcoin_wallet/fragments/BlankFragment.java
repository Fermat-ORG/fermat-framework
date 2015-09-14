package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends FermatFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ViewGroup viewGroup = null;

        boolean firstElement = true;

        try {

            InputStream inputStream = getActivity().getApplication().getAssets().open("xml/fragment_blank.xml");




            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, null);

            //xpp.setInput(new FileReader("/path/to/layout.xml"));

            //LayoutInflater inflater1 =
            //      (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // contentView = inflater.inflate(xpp, null);



            int eventType = xpp.getEventType();

            View auxView = null;

            ViewGroup auxViewGroup =null;

            ViewGroup rootView = null;



            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //products = new ArrayList();
                        rootView = null;
                        break;
                    case XmlPullParser.START_TAG:
                        name = xpp.getName();
                        System.out.println("Comienza tag: " + name);
                        switch (name) {
                            case "android.support.v4.widget.SwipeRefreshLayout":
                                auxViewGroup = new android.support.v4.widget.SwipeRefreshLayout(getActivity());
                                break;
                            case "ScrollView":
                                auxViewGroup = new ScrollView(getActivity());
                                break;
                            case "LinearLayout":
                                auxViewGroup = new LinearLayout(getActivity());
                                break;
                            case "GridLayout":
                                auxViewGroup = new GridLayout(getActivity());
                                break;
                            case "Space":
                                auxView = new Space(getActivity());
                                break;
                            case "TextView":
                                auxView = new TextView(getActivity());
                                //if(!firstElement && auxView!=null){
                                 //   viewGroup.addView(loadAtributes(auxView, xpp));
                                //}
                                break;
                            case "com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati":
                                auxView = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati(getActivity());
                                break;

                        }


                        //auxView = loadAtributes(auxView, xpp);

//                        for(int i=0;i<xpp.getAttributeCount();i++){
//                            System.out.println(xpp.getAttributeName(i));
//
//                        }


//                        if (name == "product"){
//                            currentProduct = new Product();
//                        } else if (currentProduct != null){
//                            if (name == "productname"){
//                                currentProduct.name = parser.nextText();
//                            } else if (name == "productcolor"){
//                                currentProduct.color = parser.nextText();
//                            } else if (name == "productquantity"){
//                                currentProduct.quantity= parser.nextText();
//                            }
//                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = xpp.getName();
                        //swipeRefreshLayout.setLayoutParams();
                        System.out.println("termina tag: " + name);

                        if(!firstElement){
                            switch (name) {
                                case "android.support.v4.widget.SwipeRefreshLayout":
                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
                                    break;
                                case "ScrollView":
                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
                                    break;
                                case "LinearLayout":
                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
                                    break;
                                case "GridLayout":
                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
                                    break;
                                case "Space":
                                    auxViewGroup.addView(loadAtributes(auxView, xpp));
                                    break;
                                case "TextView":
                                    //auxView = new TextView(getActivity());
                                    auxViewGroup.addView(loadAtributes(auxView, xpp));
                                    break;
                                case "com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati":
                                    auxViewGroup.addView(loadAtributes(auxViewGroup, xpp));
                                    break;

                            }
                       }else {
                            if(auxViewGroup!=null){
                                viewGroup = loadAtributesViewGroup(auxViewGroup, xpp);
                                firstElement = false;
                            }
                        }



//                        if (name.equalsIgnoreCase("product") && currentProduct != null){
//                            products.add(currentProduct);
//                        }

                        break;
                    case XmlPullParser.END_DOCUMENT:
                        rootView.addView(auxViewGroup);
                }




                //if(viewGroup!=null && !firstElement ){

                //}
                eventType = xpp.next();
            }



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_blank, container, false);

        return viewGroup;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private View loadAtributes(View view,XmlPullParser xpp){

        int widht=0;
        int height=0;

        int paddingLeft=0;
        int paddingTop=0;
        int paddingBottom=0;
        int paddingRight=0;

        for(int i=0;i<xpp.getAttributeCount();i++){
            System.out.println(xpp.getAttributeName(i));
            switch (xpp.getAttributeName(i)){
                case "id":
                    view.setId(Integer.valueOf(xpp.getAttributeValue(i)));
                    break;
                case "layout_width":
                    if(xpp.getAttributeValue(i).equals("match_parent")){
                        widht = ViewGroup.LayoutParams.MATCH_PARENT;
                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
                        widht = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                    break;
                case "layout_height":
                    if(xpp.getAttributeValue(i).equals("match_parent")){
                        height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
                        height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                    break;
                case "background":
                    view.setBackgroundColor(Color.parseColor(xpp.getAttributeValue(i)));
                    break;
                case "orientation":
                    //((android.support.v4.widget.SwipeRefreshLayout)view).setO
                    break;
                case "paddingLeft":
                    paddingLeft = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingRight":
                    paddingRight = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingBottom":
                    paddingBottom = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingTop":
                    paddingTop = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "gravity":
                    //((android.support.v4.widget.SwipeRefreshLayout)view).set
                    break;
                case "clipToPadding":
                    //((ScrollView)view).se
                    //view.setClip
                    break;
                case "clickable":
                    view.setClickable(Boolean.parseBoolean(xpp.getAttributeValue(i)));
                    break;
                case "textAlignment":
                    if(xpp.getAttributeValue(i).equals("center")){
                        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    break;
                case "textColor":
                    ((TextView)view).setTextColor(Color.parseColor(xpp.getAttributeValue(i)));
                    break;
                case "textSize":
                    //((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_DIP,Integer.valueOf(xpp.getAttributeValue(i)));
                    break;
                case "text":
                    ((TextView)view).setText(xpp.getAttributeValue(i));
                    break;
                case "textStyle":
                    if(xpp.getAttributeName(i).equals("bold")){
                        ((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    break;
                case "layout_row":

                    break;
                case "layout_column":
                    break;
                case "layout_columnSpan":
                    break;
                case "layout_gravity":
                    break;

            }
        }

        if(view!=null){
            view.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widht,height);
            view.setLayoutParams(layoutParams);
        }


        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private ViewGroup loadAtributesViewGroup(ViewGroup view,XmlPullParser xpp){

        int widht=0;
        int height=0;

        int paddingLeft=0;
        int paddingTop=0;
        int paddingBottom=0;
        int paddingRight=0;

        for(int i=0;i<xpp.getAttributeCount();i++){
            System.out.println(xpp.getAttributeName(i));
            switch (xpp.getAttributeName(i)){
                case "id":
                    view.setId(Integer.valueOf(xpp.getAttributeValue(i)));
                    break;
                case "layout_width":
                    if(xpp.getAttributeValue(i).equals("match_parent")){
                        widht = ViewGroup.LayoutParams.MATCH_PARENT;
                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
                        widht = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                    break;
                case "layout_height":
                    if(xpp.getAttributeValue(i).equals("match_parent")){
                        height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
                        height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                    break;
                case "background":
                    view.setBackgroundColor(Color.parseColor(xpp.getAttributeValue(i)));
                    break;
                case "orientation":
                    //((android.support.v4.widget.SwipeRefreshLayout)view).setO
                    break;
                case "paddingLeft":
                    paddingLeft = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingRight":
                    paddingRight = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingBottom":
                    paddingBottom = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "paddingTop":
                    paddingTop = Integer.valueOf(xpp.getAttributeValue(i));
                    break;
                case "gravity":
                    //((android.support.v4.widget.SwipeRefreshLayout)view).set
                    break;
                case "clipToPadding":
                    //((ScrollView)view).se
                    //view.setClip
                    break;
                case "clickable":
                    view.setClickable(Boolean.parseBoolean(xpp.getAttributeValue(i)));
                    break;
                case "textAlignment":
                    if(xpp.getAttributeValue(i).equals("center")){
                        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    break;
                case "textColor":
                    //((TextView)view).setTextColor(Color.parseColor(xpp.getAttributeValue(i)));
                    break;
                case "textSize":
                    //((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_DIP,Integer.valueOf(xpp.getAttributeValue(i)));
                    break;
                case "text":
                    //((TextView)view).setText(xpp.getAttributeValue(i));
                    break;
                case "textStyle":
                    if(xpp.getAttributeName(i).equals("bold")){
                        //((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    break;
                case "layout_row":

                    break;
                case "layout_column":
                    break;
                case "layout_columnSpan":
                    break;
                case "layout_gravity":
                    break;

            }
        }

        if(view!=null){
            view.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widht,height);
            view.setLayoutParams(layoutParams);
        }




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
