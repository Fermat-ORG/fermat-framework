//package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;
//
//import android.annotation.TargetApi;
//import android.app.ActionBar;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.util.AttributeSet;
//import android.util.Base64;
//import android.view.Gravity;
//import android.view.InflateException;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridLayout;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.Space;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
////import com.google.common.io.Files;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2.ViewInflater;
//import com.squareup.picasso.Picasso;
//
//import org.apache.commons.codec.Charsets;
//import org.apache.commons.io.IOUtils;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.security.acl.Group;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
////import com.google.common.io.Files;
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link BlankFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link BlankFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class BlankFragment extends FermatFragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BlankFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BlankFragment newInstance(String param1, String param2) {
//        BlankFragment fragment = new BlankFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public BlankFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//
//    public static final String PREFIX = "stream2file";
//    public static final String SUFFIX = ".tmp";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//
//        ViewGroup viewGroup = null;
//
//        boolean firstElement = true;
//
//        List<View> lstViews = new ArrayList<View>();
//
//        List<Integer> lstViewsDepth = new ArrayList<Integer>();          //StringWriter writer = new StringWriter();
//        //IOUtils.copy(inputStream, writer, encoding);
//        //String theString = writer.toString();
//
//        //File file = new File(/);
//
////            File file = new File("");
////
////
////            String fileStr = file.getAbsolutePath();
////            System.out.println("ACAAaaa:"+file.getAbsolutePath());
//
////        try {
////
//      //      InputStream inputStream = getActivity().getApplication().getAssets().open("xml/fragment_blank.xml");
//
//
//
//
//        //String data = getIntent().getStringExtra(DATA);
//
//        ViewInflater inflater1 = new ViewInflater(getActivity(),null);
//
//        XmlPullParser parse;
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            parse = factory.newPullParser();
//
//            parse.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
//                    "              android:layout_width=\"fill_parent\"\n" +
//                    "              android:layout_height=\"wrap_content\"\n" +
//                    "              android:orientation=\"horizontal\"\n" +
//                    "              android:paddingBottom=\"10dp\"\n" +
//                    "              android:paddingTop=\"10dp\"\n" +
//                    "              android:paddingLeft=\"16dp\"\n" +
//                    "              android:paddingRight=\"16dp\"\n" +
//                    "              android:weightSum=\"1\"\n" +
//                    "              android:background=\"#a2ba12\">\n" +
//                    "\n" +
//                    "    <ImageView\n" +
//                    "        android:layout_width=\"wrap_content\"\n" +
//                    "        android:layout_height=\"match_parent\"\n" +
//                    "        android:layout_weight=\"0.6\"\n" +
//                    "        android:src=\"@drawable/person1\"/>\n" +
//                    "\n" +
//                    "    <TextView\n" +
//                    "        android:id=\"@+id/row_title\"\n" +
//                    "        android:layout_width=\"fill_parent\"\n" +
//                    "        android:layout_height=\"wrap_content\"\n" +
//                    "        android:layout_weight=\"0.4\"\n" +
//                    "        android:gravity=\"center_vertical\"\n" +
//                    "        android:padding=\"10dp\"\n" +
//                    "        android:singleLine=\"true\"\n" +
//                    "        android:textSize=\"18sp\"\n" +
//                    "        android:textColor=\"@color/color_black\"\n" +
//                    "        android:text=\"Nelson Alfonso Ramirez Noguera\"\n" +
//                    "        />\n" +
//                    "\n" +
//                    "\n" +
//                    "</LinearLayout>"));
//
//            View v = inflater1.inflate(parse);
//            return v;
//        }
//        catch (XmlPullParserException ex) { ex.printStackTrace(); }
//        catch (IOException ex) { ex.printStackTrace(); }
//
//
////
////            final File tempFile = File.createTempFile(PREFIX, SUFFIX);
////            tempFile.deleteOnExit();
////            try (FileOutputStream out = new FileOutputStream(tempFile)) {
////                IOUtils.copy(inputStream, out);
////            }
////
////
////
////            String xmlText = Files.toString(tempFile, Charsets.UTF_8);
////            String xmlText1 = xmlText.replaceAll("\\\\n", "");
////            byte[] data = xmlText.getBytes("UTF-8");
////            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
////            //String layout = Base64.encodeToString(xmlText);
////
////            return inflater.inflate(getParser(base64), container, false);
////
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
//        // try {
////
////            InputStream inputStream = getActivity().getApplication().getAssets().open("xml/fragment_blank.xml");
////
////
////
////
////            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
////            factory.setNamespaceAware(true);
////            XmlPullParser xpp = factory.newPullParser();
////            xpp.setInput(inputStream, null);
////
////            //xpp.setInput(new FileReader("/path/to/layout.xml"));
////
////
////
////
////
////            int eventType = xpp.getEventType();
////
////            View auxView = null;
////
////            ViewGroup auxViewGroup =null;
////
////            ViewGroup rootView = null;
////
////            int depth = xpp.getDepth();
////
////            int count = xpp.getAttributeCount();
////
////
////            while (eventType != XmlPullParser.END_DOCUMENT) {
////                String name = null;
////
////                depth = xpp.getDepth();
////
////                count = xpp.getAttributeCount();
////
////                switch (eventType) {
////                    case XmlPullParser.START_DOCUMENT:
////                        //products = new ArrayList();
////                        break;
////
////                    case XmlPullParser.START_TAG:
////                        name = xpp.getName();
////                        //System.out.println("Comienza tag: " + name);
////                        switch (name) {
////                            case "android.support.v4.widget.SwipeRefreshLayout":
////                                //auxViewGroup = new android.support.v4.widget.SwipeRefreshLayout(getActivity());
////                                lstViews.add(loadAtributesViewGroup(new android.support.v4.widget.SwipeRefreshLayout(getActivity()),xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "ScrollView":
////                                //auxViewGroup = new ScrollView(getActivity());
////                                lstViews.add(loadAtributesViewGroup(new ScrollView(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "LinearLayout":
////                                //auxViewGroup = new LinearLayout(getActivity());
////                                lstViews.add(loadAtributesViewGroup(new LinearLayout(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "RelativeLayout":
////                                //auxViewGroup = new LinearLayout(getActivity());
////                                lstViews.add(loadAtributesViewGroup(new RelativeLayout(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "GridLayout":
////                                //auxViewGroup = new GridLayout(getActivity());
////                                lstViews.add(loadAtributesViewGroup(new GridLayout(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "Space":
////                                //auxView = new Space(getActivity());
////                                lstViews.add(loadAtributes(new Space(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "TextView":
////                                //auxView = new TextView(getActivity());
////                                lstViews.add(loadAtributes(new TextView(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "ImageView":
////                                lstViews.add(loadAtributes(new ImageView(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////                            case "com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati":
////                                //auxView = new com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati(getActivity());
////                                lstViews.add(loadAtributes(new com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati(getActivity()), xpp));
////                                lstViewsDepth.add(depth);
////                                break;
////
////                        }
////
////
////                        //auxView = loadAtributes(auxView, xpp);
////
//////                        }
////                        break;
////
//////                    case XmlPullParser.END_TAG:
//////                        name = xpp.getName();
//////                        //swipeRefreshLayout.setLayoutParams();
//////                        System.out.println("termina tag: " + name);
//////
//////                        if(depth!=0) {
//////                            switch (name) {
//////                                case "android.support.v4.widget.SwipeRefreshLayout":
//////                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
//////                                    break;
//////                                case "ScrollView":
//////                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
//////                                    break;
//////                                case "LinearLayout":
//////                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
//////                                    break;
//////                                case "GridLayout":
//////                                    auxViewGroup.addView(loadAtributesViewGroup(auxViewGroup, xpp));
//////                                    break;
//////                                case "Space":
//////                                    auxViewGroup.addView(loadAtributes(auxView, xpp));
//////                                    break;
//////                                case "TextView":
//////                                    //auxView = new TextView(getActivity());
//////                                    auxViewGroup.addView(loadAtributes(auxView, xpp));
//////                                    break;
//////                                case "com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView.CustomComponentMati":
//////                                    auxViewGroup.addView(loadAtributes(auxViewGroup, xpp));
//////                                    break;
//////
//////                            }
//////
//////
//////                            if (auxViewGroup != null) {
//////                                viewGroup = loadAtributesViewGroup(auxViewGroup, xpp);
//////
//////                            }
//////                        }
////
////
//////                        if (name.equalsIgnoreCase("product") && currentProduct != null){
//////                            products.add(currentProduct);
//////                        }
////
////                        //break;
////                    case XmlPullParser.END_DOCUMENT:
////                        //rootView.addView(auxViewGroup);
////                }
////
////
////
////
////                //if(viewGroup!=null && !firstElement ){
////
////                //}
////                eventType = xpp.next();
////
////
////
////
////                //System.out.println("DEPTH: "+depth);
////
////                //System.out.println("COUNT: "+count);
////            }
////
////
////
////        } catch (XmlPullParserException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        // Inflate the layout for this fragment
////        //return inflater.inflate(R.layout.fragment_blank, container, false);
////
//////        for (int pos = 0; pos < lstViews.size(); pos++) {
//////            System.out.println("View: "+lstViews.get(pos)+" Profundidad: "+lstViewsDepth.get(pos));
//////        }
////
////        viewGroup = buildView(lstViews, lstViewsDepth);
//
//
//
//        return viewGroup;
//
//    }
//
//    /**
//     * return XmlPullParser
//     * @param xml compiled XML encoded in base64
//     * @return XmlPullParser
//     */
//    public static XmlPullParser getParser(String xml) {
//        try {
//            byte[] data = Base64.decode(xml, Base64.DEFAULT);
//
//            // XmlBlock block = new XmlBlock(LAYOUT.getBytes("UTF-8"));
//            Class<?> clazz = Class.forName("android.content.res.XmlBlock");
//            Constructor<?> constructor = clazz.getDeclaredConstructor(byte[].class);
//            constructor.setAccessible(true);
//            Object block = constructor.newInstance(data);
//
//            // XmlPullParser parser = block.newParser();
//            Method method = clazz.getDeclaredMethod("newParser");
//            method.setAccessible(true);
//            return (XmlPullParser) method.invoke(block);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private ViewGroup buildView(List<View> lstViews,List<Integer> lstViewsDepth){
//        ViewGroup rootView=null;
//
//        int depth=0;
//        View view=null;
//
//        int depthElem;
//        int depthPrevElem;
//
//        View prevView;
//        View actualView;
//
//        rootView =(ViewGroup) lstViews.get(0);
//
//        for (int pos = 1; pos < lstViews.size(); pos++) {
//            depthElem = lstViewsDepth.get(pos);
//            depthPrevElem = lstViewsDepth.get(pos-1);
//            prevView = lstViews.get(pos-1);
//            actualView = lstViews.get(pos);
//
//            if(depthElem>depthPrevElem){
//                if(prevView instanceof ViewGroup){
//                    ((ViewGroup)prevView).addView(actualView);
//                }
//            }
//            if(depthElem==depthPrevElem){
//                boolean flag = false;
//                int elementNumber = pos-1;
//                while(!flag){
//                    elementNumber--;
//                    depthPrevElem = lstViewsDepth.get(elementNumber);
//                    prevView = lstViews.get(elementNumber);
//                    if (depthElem>depthPrevElem){
//                        System.out.print(prevView.getClass().getCanonicalName());
//                        if(prevView instanceof ViewGroup){
//                            ((ViewGroup)prevView).addView(actualView);
//                            flag=true;
//                        }
//                    }
//                }
//            }
//            if(depthElem<depthPrevElem){
//                boolean flag = false;
//                int elementNumber = pos-1;
//                while(!flag){
//                    elementNumber--;
//                    depthPrevElem = lstViewsDepth.get(elementNumber);
//                    prevView = lstViews.get(elementNumber);
//                    if (depthElem>depthPrevElem){
//                        if(prevView instanceof ViewGroup){
//                            ((ViewGroup)prevView).addView(actualView);
//                            flag=true;
//                        }
//                    }
//                }
//            }
//        }
//
//
////        for (int pos = 0; pos < lstViews.size(); pos++) {
////           // System.out.println("View: "+lstViews.get(pos)+" Profundidad: "+lstViewsDepth.get(pos));
////            depth = lstViewsDepth.get(pos);
////            view = lstViews.get(pos);
////           if(depth==0){
////               rootView = view;
////           }else{
////
////           }
////        }
//
//
//        return rootView;
//    }
//
////    void rInflate(XmlPullParser parser, View parent, final AttributeSet attrs,
////                  boolean finishInflate) throws XmlPullParserException, IOException { // Depth first inflate, all to ensure you are in the onFinish
////        // Inflate()Can be found through the findViewById has created the child view
////        final int depth = parser.getDepth();
////        int type;
////
////        while (((type = parser.next()) != XmlPullParser.END_TAG ||
////                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
////
////            if (type != XmlPullParser.START_TAG) {
////                continue;
////            }
////            // That is a START_TAG node
////            final String name = parser.getName(); // Get tagName
////
////            if (TAG_REQUEST_FOCUS.equals(name)) { // Treatment of REQUEST_FOCUS tag
////                parseRequestFocus(parser, parent);
////            } else if (TAG_INCLUDE.equals(name)) { // Treatment of include tag
////                if (parser.getDepth() == 0) { // The include node is not the root node, or throw an exception. .
////                    throw new InflateException("<include /> cannot be the root element");
////                }
////                parseInclude(parser, parent, attrs);
////            } else if (TAG_MERGE.equals(name)) { // The merge node must be the root node in the XML file, which means that there should no longer appear merge node.
////                throw new InflateException("<merge /> must be the root element");
////            } else if (TAG_1995.equals(name)) {
////                final View view = new BlinkLayout(mContext, attrs);
////                final ViewGroup viewGroup = (ViewGroup) parent;
////                final ViewGroup.LayoutParams params = viewGroup.generateLayoutParams(attrs);
////                rInflate(parser, view, attrs, true);
////                viewGroup.addView(view, params);
////            } else { // In general, Android view, widget view node or user defined
////                final View view = createViewFromTag(parent, name, attrs);
////                final ViewGroup viewGroup = (ViewGroup) parent;
////                final ViewGroup.LayoutParams params = viewGroup.generateLayoutParams(attrs);
////                rInflate(parser, view, attrs, true);
////                viewGroup.addView(view, params);
////            }
////        }
////
////        if (finishInflate) parent.onFinishInflate(); // When all the children are inflate parent node is finished, call the onFinishInflate callback
////    }
//
//    private void parseRequestFocus(XmlPullParser parser, View parent)
//            throws XmlPullParserException, IOException {
//        int type;
//        parent.requestFocus();
//        final int currentDepth = parser.getDepth();
//        while (((type = parser.next()) != XmlPullParser.END_TAG || // Ignore all the remaining START_TAG content of this node, until next new
//                parser.getDepth() > currentDepth) && type != XmlPullParser.END_DOCUMENT) {
//            // Empty
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private View loadAtributes(View view,XmlPullParser xpp){
//
//        int widht=0;
//        int height=0;
//
//        int paddingLeft=0;
//        int paddingTop=0;
//        int paddingBottom=0;
//        int paddingRight=0;
//
//        float layout_weight=0;
//
//
//        // GridLayout param
//        int layout_row=-1;
//        int layout_column = -1;
//        int layout_columnSpan = -1;
//
//        int layout_gravity;
//
//        for(int i=0;i<xpp.getAttributeCount();i++){
//            //System.out.println(xpp.getAttributeName(i));
//            switch (xpp.getAttributeName(i)){
//                case "id":
//                    //view.setId(Integer.valueOf(xpp.getAttributeValue(i)));
//                    break;
//                case "layout_width":
//                    if(xpp.getAttributeValue(i).equals("match_parent")){
//                        widht = ViewGroup.LayoutParams.MATCH_PARENT;
//                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
//                        widht = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    }else if (xpp.getAttributeValue(i).equals("fill_parent")){
//                        widht = ViewGroup.LayoutParams.FILL_PARENT;
//                    }else{
//                        widht = matchIntFromString(xpp.getAttributeValue(i));
//                    }
//                    break;
//                case "layout_height":
//                    if(xpp.getAttributeValue(i).equals("match_parent")){
//                        height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
//                        height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    }else if (xpp.getAttributeValue(i).equals("fill_parent")){
//                        widht = ViewGroup.LayoutParams.FILL_PARENT;
//                    }else{
//                        height = matchIntFromString(xpp.getAttributeValue(i));
//                    }
//                    break;
//                case "background":
//                    String color = xpp.getAttributeValue(i);
//                    if(color.charAt(0)=='#'){
//                        view.setBackgroundColor(Color.parseColor(color));
//                    }else{
//                        //TODO: aca iria a buscar un drawable de fondo
//                    }
//                    break;
//                case "orientation":
//                    //((android.support.v4.widget.SwipeRefreshLayout)view).setO
//                    if(view instanceof LinearLayout){
//                        if(xpp.getAttributeValue(i).equals("vertical")){
//                            ((LinearLayout)view).setOrientation(LinearLayout.VERTICAL);
//                        }else{
//                            ((LinearLayout)view).setOrientation(LinearLayout.HORIZONTAL);
//                        }
//                    }
//                    break;
//                case "paddingLeft":
//                    paddingLeft = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingRight":
//                    paddingRight = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingBottom":
//                    paddingBottom = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingTop":
//                    paddingTop = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "gravity":
//                    //((android.support.v4.widget.SwipeRefreshLayout)view).set
//                    break;
//                case "clipToPadding":
//                    //((ScrollView)view).se
//                    //view.setClip
//                    break;
//                case "clickable":
//                    view.setClickable(Boolean.parseBoolean(xpp.getAttributeValue(i)));
//                    break;
//                case "textAlignment":
//                    if(xpp.getAttributeValue(i).equals("center")){
//                        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    }
//                    break;
//                case "textColor":
//                    if(view instanceof TextView){
//                        //((TextView)view).setTextColor(Color.parseColor(xpp.getAttributeValue(i)));
//                    }
//
//                    break;
//                case "textSize":
//                    //((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_DIP,Integer.valueOf(xpp.getAttributeValue(i)));
//                    break;
//                case "text":
//                    if(view instanceof TextView){
//                        ((TextView)view).setText(xpp.getAttributeValue(i));
//                    }
//                    break;
//                case "textStyle":
//                    if(xpp.getAttributeName(i).equals("bold")){
//                        ((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
//                    }
//                    break;
//                case "layout_row":
//                    layout_row = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_column":
//                    layout_column = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_columnSpan":
//                    layout_columnSpan = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_gravity":
//                    if (xpp.getAttributeValue(i).equals("fill")){
//                        layout_gravity = Gravity.FILL;
//                    }
//
//                    //layout_gravity = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_weight":
//                    layout_weight =Float.parseFloat(xpp.getAttributeValue(i));
//                    break;
//                case "src":
//                    if(view instanceof ImageView){
//                        try {
//                            InputStream inputStreamImage = getActivity().getApplication().getAssets().open("drawables/person1.png");
//                            Bitmap bitmap = null;
//
//
//                                //BitmapFactory is an Android graphics utility for images
//                            bitmap = BitmapFactory.decodeStream(inputStreamImage);
//                            ((ImageView) view).setImageBitmap(bitmap);
//                            //Picasso.with(getActivity()).load(xpp.getAttributeValue(i)).into(();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        //Get the texture from the Android resource directory
//                        //InputStream is = context.getResources().openRawResource(R.drawable.radiocd5);
//
//
//                    }
//                    break;
//            }
//        }
//
//        if(view!=null){
//            if(layout_column!=-1){
//                view.setLayoutParams(new TableRow.LayoutParams(layout_column));
//                //GridView.LayoutParams params = new GridView.LayoutParams();
//            }
//            if( view instanceof LinearLayout){
//                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                        height,
//                        widht, layout_weight);
//            }
//            view.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widht,height);
//            view.setLayoutParams(layoutParams);
//        }
//
//
//        return view;
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private ViewGroup loadAtributesViewGroup(ViewGroup view,XmlPullParser xpp){
//
//        int widht=0;
//        int height=0;
//
//        int paddingLeft=0;
//        int paddingTop=0;
//        int paddingBottom=0;
//        int paddingRight=0;
//
//        int layout_row;
//        int layout_column;
//        int layout_columnSpan;
//
//        int layout_gravity;
//
//        float layout_weight;
//
//        for(int i=0;i<xpp.getAttributeCount();i++){
//            //System.out.println(xpp.getAttributeName(i));
//            switch (xpp.getAttributeName(i)){
//                case "id":
//                    //view.setId(Integer.valueOf(xpp.getAttributeValue(i)));
//                    break;
//                case "layout_width":
//                    if(xpp.getAttributeValue(i).equals("match_parent")){
//                        widht = ViewGroup.LayoutParams.MATCH_PARENT;
//                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
//                        widht = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    }else if (xpp.getAttributeValue(i).equals("fill_parent")) {
//                        widht = ViewGroup.LayoutParams.FILL_PARENT;
//                    }else{
//                        height = matchIntFromString(xpp.getAttributeValue(i));
//                    }
//                    break;
//                case "layout_height":
//                    if(xpp.getAttributeValue(i).equals("match_parent")){
//                        height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    }else if (xpp.getAttributeValue(i).equals("wrap_content")){
//                        height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    }else if (xpp.getAttributeValue(i).equals("fill_parent")) {
//                        height = ViewGroup.LayoutParams.FILL_PARENT;
//                    }else{
//                        height = matchIntFromString(xpp.getAttributeValue(i));
//                    }
//                    break;
//                case "background":
//                    String color = xpp.getAttributeValue(i);
//                    if(color.charAt(0)=='#'){
//                        view.setBackgroundColor(Color.parseColor(color));
//                    }else{
//                        //TODO: aca iria a buscar un drawable de fondo
//                    }
//
//                    break;
//                case "orientation":
//                    if(view instanceof LinearLayout){
//                        if(xpp.getAttributeValue(i).equals("vertical")){
//                            ((LinearLayout)view).setOrientation(LinearLayout.VERTICAL);
//                        }else{
//                            ((LinearLayout)view).setOrientation(LinearLayout.HORIZONTAL);
//                        }
//                    }
//                    break;
//                case "paddingLeft":
//                    paddingLeft = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingRight":
//                    paddingRight = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingBottom":
//                    paddingBottom = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "paddingTop":
//                    paddingTop = matchIntFromString(xpp.getAttributeValue(i));
//                    break;
//                case "gravity":
//                    //((android.support.v4.widget.SwipeRefreshLayout)view).set
//                    break;
//                case "clipToPadding":
//                    //((ScrollView)view).se
//                    //view.setClip
//                    break;
//                case "clickable":
//                    view.setClickable(Boolean.parseBoolean(xpp.getAttributeValue(i)));
//                    break;
//                case "textAlignment":
//                    if(xpp.getAttributeValue(i).equals("center")){
//                        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                    }
//                    break;
//                case "textColor":
//                    //((TextView)view).setTextColor(Color.parseColor(xpp.getAttributeValue(i)));
//                    break;
//                case "textSize":
//                    //((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_DIP,Integer.valueOf(xpp.getAttributeValue(i)));
//                    break;
//                case "text":
//                    //((TextView)view).setText(xpp.getAttributeValue(i));
//                    break;
//                case "textStyle":
//                    if(xpp.getAttributeName(i).equals("bold")){
//                        //((TextView)view).setTypeface(Typeface.DEFAULT_BOLD);
//                    }
//                    break;
//                case "layout_row":
//                    layout_row = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_column":
//                    layout_column = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_columnSpan":
//                    layout_columnSpan = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_gravity":
//                    if (xpp.getAttributeValue(i).equals("fill")){
//                        layout_gravity = Gravity.FILL;
//                    }
//
//                    //layout_gravity = Integer.parseInt(xpp.getAttributeValue(i));
//                    break;
//                case "layout_weight":
//                    layout_weight =Float.parseFloat(xpp.getAttributeValue(i));
//                    break;
//                case "elevation":
//                    try {
//                        Float elevation = matchFloatFromString(xpp.getAttributeValue(i));
//                        if (!(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)){
//                            view.setElevation(elevation);
//                        }
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//
//        if(view!=null){
//            view.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widht,height);
//            view.setLayoutParams(layoutParams);
//        }
//
//
//
//
//        return view;
//    }
//
//    private int matchIntFromString(String str){
//        Pattern p = Pattern.compile("-?\\d+");
//        Matcher m = p.matcher(str);
//        StringBuffer stringBuffer = new StringBuffer();
//        while (m.find()) {
//            stringBuffer.append(m.group());
//        }
//        System.out.println("STRING BUFFER: "+stringBuffer.toString());
//        return Integer.parseInt(stringBuffer.toString());
//    }
//    private float matchFloatFromString(String str){
//        Pattern p = Pattern.compile("-?\\d+");
//        Matcher m = p.matcher(str);
//        StringBuffer stringBuffer = new StringBuffer();
//        while (m.find()) {
//            stringBuffer.append(m.group());
//        }
//        System.out.println("STRING BUFFER: "+stringBuffer.toString());
//        return Float.parseFloat(stringBuffer.toString());
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            //mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }
//
//}
