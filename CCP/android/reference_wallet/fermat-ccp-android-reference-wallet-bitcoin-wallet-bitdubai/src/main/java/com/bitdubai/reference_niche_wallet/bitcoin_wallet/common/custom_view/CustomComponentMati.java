//package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.FermatDrawable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
//import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.RoundedDrawable;
//import com.bitdubai.reference_niche_wallet.bitcoin_wallet.preference_settings.ReferenceWalletPreferenceSettings;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Matias Furszyfer on 2015.08.12..
// */
//
//
//public class CustomComponentMati extends LinearLayout implements Animation.AnimationListener {
//
//    private static final String DEBUG_TAG = "MATTIIIIIIIII";
//    /**
//     *  Header
//     */
//    TextView txtLastTransactions;
//    TextView txtSeeAlltransactions;
//
//
//    /**
//     *  Body
//     */
//    TextView txtViewTitleTransaction;
//    TextView txtViewDetailTransaction;
//
//    ImageView imageView_transaction;
//
//
//    /**
//     *  Buttons
//     */
//    ImageView imageView_action_next;
//    ImageView imageView_action_prev;
//
//    /**
//     * Data
//     */
//    private List<ListComponent> lstData;
//
//    /**
//     * List position
//     */
//    private int listPosition;
//
//    /**
//     * Resources
//     */
//    private Resources resources;
//
//
//    LinearLayout linearLayout_container;
//
//
//    Animation animationNext;
//    Animation animationPrev;
//
//
//    CustomComponentsObjects transactionInScreen;
//
//    private Activity activity;
//
//    private WalletResourcesProviderManager walletResourcesProviderManager;
//
//    /**
//     * Wallet settings
//     */
//    private ReferenceWalletPreferenceSettings walletSettings;
//
//    /**
//     *
//     * @param context
//     * @param attrst
//     */
//    public CustomComponentMati(Context context, AttributeSet attrst){
//        super(context, attrst);
//        initControl(context);
//        initData();
//    }
//
//    public CustomComponentMati(Context context) {
//        super(context);
//    }
//
//    private void initData() {
//        lstData= new ArrayList<CustomComponentsObjects>();
//        listPosition++;
//
//        load(listPosition);
//    }
//
//    /**
//     * Load component XML layout
//     */
//    private void initControl(final Context context)
//    {
//        LayoutInflater inflater = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        inflater.inflate(R.layout.wallet_manager_transaction_view, this);
//
//        // layout is inflated, assign local variables to components
//        txtLastTransactions = (TextView) findViewById(R.id.txtLastTransactions);
//        txtSeeAlltransactions = (TextView) findViewById(R.id.txtSeeAlltransactions);
//        txtViewTitleTransaction = (TextView) findViewById(R.id.txtViewTitleTransaction);
//        txtViewDetailTransaction = (TextView) findViewById(R.id.txtViewDetailTransaction);
//
//        imageView_transaction = (ImageView)findViewById(R.id.imageView_transaction);
//        imageView_action_next = (ImageView)findViewById(R.id.imageView_action_next);
//        imageView_action_prev = (ImageView)findViewById(R.id.imageView_action_prev);
//
//        imageView_action_next.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listPosition++;
//
//                if( lstData.size()>listPosition){
//                    linearLayout_container.startAnimation(animationNext);
//                    load(listPosition);
//                }else {
//                    listPosition--;
//                }
//
//            }
//        });
//        imageView_action_prev.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listPosition--;
//
//
//                if (listPosition > -1) {
//                    linearLayout_container.startAnimation(animationPrev);
//                    load(listPosition);
//                } else {
//                    listPosition++;
//                }
//
//            }
//        });
//
//        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);
//        animationNext =    AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
//        animationPrev = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
//        animationNext.setAnimationListener(this);
//        animationPrev.setAnimationListener(this);
//
//
//    }
//
//    public void setDataList(List<ListComponent> lstData){
//        this.lstData=lstData;
//        listPosition=0;
//        load(listPosition);
//    }
//    public void setResources(Resources resources){
//        this.resources=resources;
//    }
//
//    private void load(int position){
//        if(!lstData.isEmpty() && lstData.size()>listPosition  && listPosition>-1) {
//            //if()
//            transactionInScreen = lstData.get(position);
////            txtViewTitleTransaction.setText(customComponentsObjects.getTitle());
////            txtViewDetailTransaction.setText(customComponentsObjects.getDetail());
//            //imageView_transaction.setImageDrawable();
////            imageView_transaction.setImageResource(
////                    resources.getIdentifier(
////                            "com.bitdubai.reference_niche_wallet.bitcoin_wallet:drawable/" + customComponentsObjects.getImageUrl()
////                            , null, null));
////            byte[] image = customComponentsObjects.getImage();
////            FermatDrawable drawableImage = null;
////            if(image!=null){
////                drawableImage = new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
////            }else{
////                if(walletSettings!=null){
////                    try {
////                        image = walletResourcesProviderManager.getImageResource("unknown",walletSettings.getDefaultSkin());
////                    } catch (CantGetResourcesException e) {
////                        e.printStackTrace();
////                    } catch (CantGetDefaultSkinException e) {
////                        e.printStackTrace();
////                    }
////                    drawableImage = new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
////                }
////            }
////
////            imageView_transaction.setImageDrawable(drawableImage);
//        }
//
//        //invalidate();
//    }
//
//
//    public void setLastTransactionsEvent(OnClickListener onClickListener){
//        this.txtLastTransactions.setOnClickListener(onClickListener);
//    }
//    public void setSeeAlltransactionsEvent(OnClickListener onClickListener){
//        this.txtSeeAlltransactions.setOnClickListener(onClickListener);
//    }
//
//    public void setWalletResources(WalletResourcesProviderManager walletResourcesProviderManager){
//        this.walletResourcesProviderManager = walletResourcesProviderManager;
//    }
//
//    public void setActivity(Activity activity){
//        this.activity=activity;
//    }
//
//    public void setWalletSettings(ReferenceWalletPreferenceSettings walletSettings) {
//        this.walletSettings = walletSettings;
//    }
//
//    @Override
//    public void onAnimationStart(Animation animation) {
//
//    }
//
//    @Override
//    public void onAnimationEnd(Animation animation) {
//        txtViewTitleTransaction.setText(transactionInScreen.getTitle());
//        txtViewDetailTransaction.setText(transactionInScreen.getDetail());
//        byte[] image = transactionInScreen.getImage();
//        Bitmap imageBitmap = null;
//            FermatDrawable drawableImage = null;
//            if(image!=null){
//                imageBitmap = BitmapFactory.decodeByteArray(image , 0, image.length);
//                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageView_transaction.getWidth(), imageView_transaction.getHeight(), true);
//            }else{
//                if(walletSettings!=null){
////                    try {
////                        //image = walletResourcesProviderManager.getImageResource("unknown",walletSettings.getDefaultSkin());
////                        imageBitmap = BitmapFactory.decodeByteArray(image , 0, image.length);
////                        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageView_transaction.getWidth(), imageView_transaction.getHeight(), true);
////                    } catch (CantGetResourcesException e) {
////                        e.printStackTrace();
////                    } catch (CantGetDefaultSkinException e) {
////                        e.printStackTrace();
////                    }
//                    //drawableImage = new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
//                }
//
//            }
//            //imageView_transaction.setImageDrawable(imageBitmap);
//        if(imageBitmap!=null){
//            imageView_transaction.setBackground(new RoundedDrawable(imageBitmap, imageView_transaction));
//            imageView_transaction.setImageDrawable(null);
//        }else{
//            imageBitmap =BitmapFactory.decodeResource(resources,R.drawable.person1);
//            imageView_transaction.setBackground(new RoundedDrawable(imageBitmap, imageView_transaction));
//            imageView_transaction.setImageDrawable(null);
//        }
//
//        invalidate();
//    }
//
//    @Override
//    public void onAnimationRepeat(Animation animation) {
//
//    }
//}
