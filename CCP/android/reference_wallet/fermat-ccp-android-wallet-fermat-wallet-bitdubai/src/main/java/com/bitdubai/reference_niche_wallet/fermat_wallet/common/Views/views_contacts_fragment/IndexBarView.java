// @author Bhavya Mehta
package com.bitdubai.reference_niche_wallet.fermat_wallet.common.Views.views_contacts_fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.ContactIndexStrucs;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// Represents right side index bar view with unique first latter of list view row text 
public class IndexBarView extends View {

    // table to indicate where is every section of the index bar
    private static LinkedHashMap<String, Integer> contactIndexTable;

    // index bar margin
    float mIndexbarMargin;
    static float mMaxMeasuredHeight = Float.MIN_VALUE;

    // flag used in touch events manipulations
    boolean mIsIndexing = false;


    // array list to store section positions
    public ArrayList<Integer> mListSections;

    // array list to store listView data
    ArrayList<Object> mListItems;

    // paint object
    Paint mIndexPaint;

    // context object
    Context mContext;

    // interface object used as bridge between list view and index bar view for
    // filtering list view content on touch event
    IIndexBarFilter mIndexBarFilter;


    public IndexBarView(Context context) {
        super(context);
        this.mContext = context;
    }


    public IndexBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    public IndexBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }


    private static void updateIndexTable(ArrayList<Object> listItems, ArrayList<Integer> listSections) {
        if (contactIndexTable == null) {
            contactIndexTable = new LinkedHashMap<String, Integer>(ContactIndexStrucs.getContactIndexTable());
        } else {
            contactIndexTable.putAll(ContactIndexStrucs.getContactIndexTable());
        }

        for (int secIndex : listSections) {
            Object object = listItems.get(secIndex);
            String section = null;
            if(object instanceof String){
                section = (String) listItems.get(secIndex);
            }else if( object instanceof FermatWalletWalletContact){
                section = ((FermatWalletWalletContact) listItems.get(secIndex)).getActorName();
            }else{
                section = "algo malo pasó";
            }
            contactIndexTable.put(section, secIndex);
        }
    }


    public void setData(PinnedHeaderListView listView, ArrayList<Object> listItems, ArrayList<Integer> listSections) {
        this.mListItems = listItems;
        this.mListSections = listSections;
        updateIndexTable(listItems, listSections);

        // list view implements mIndexBarFilter interface
        mIndexBarFilter = listView;

        // set index bar margin from resources
        mIndexbarMargin = mContext.getResources().getDimension(R.dimen.index_bar_view_margin);

        // index bar item color and text size
        mIndexPaint = new Paint();
        mIndexPaint.setColor(Color.parseColor("#99ffffff"));
        mIndexPaint.setAntiAlias(true);
        mIndexPaint.setTextSize(mContext.getResources().getDimension(R.dimen.index_bar_view_text_size));
    }


    // draw view content on canvas using paint
    @Override
    protected void onDraw(Canvas canvas) {

        if (mListSections != null && mListSections.size() > 1) {
            final String[] sections = ContactIndexStrucs.ALPHABET_INDEX;
            final int length = sections.length;

            if(mMaxMeasuredHeight < getMeasuredHeight()) {
                mMaxMeasuredHeight = getMeasuredHeight();
            }

            float sectionHeight = (mMaxMeasuredHeight - 2 * mIndexbarMargin) / sections.length;
            float paddingTop = (sectionHeight - (mIndexPaint.descent() - mIndexPaint.ascent())) / 2;

            for (int index = 0; index < length; index++) {
                String section = sections[index];

                final float xPos = (getMeasuredWidth() - mIndexPaint.measureText(section)) / 2;
                final float yPos = mIndexbarMargin + (sectionHeight * index) + paddingTop + mIndexPaint.descent();

                canvas.drawText(section, xPos, yPos, mIndexPaint);
            }
        }
        super.onDraw(canvas);
    }


    boolean contains(float x, float y) {
        // Determine if the point is in index bar region, which includes the
        // right margin of the bar
        return (x >= getLeft() && y >= getTop() && y <= getTop() + getMeasuredHeight());
    }


    private void filterListItem(float sideIndexY) {
        final String[] sections = ContactIndexStrucs.ALPHABET_INDEX;

        //filter list items and get touched section position with in index bar
        int mCurrentSectionPosition = (int) (((sideIndexY) - getTop() - mIndexbarMargin) /
                ((mMaxMeasuredHeight - (2 * mIndexbarMargin)) / sections.length));

        if (mCurrentSectionPosition >= 0 && mCurrentSectionPosition < sections.length) {
            String section = sections[mCurrentSectionPosition];
            int position = contactIndexTable.get(section);
            if (position >= 0)
                mIndexBarFilter.filterList(position, section);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // If down event occurs inside index bar region, start indexing
                if (contains(ev.getX(), ev.getY())) {
                    // It demonstrates that the motion event started from index bar
                    mIsIndexing = true;
                    // Determine which section the point is in, and move the list to that section
                    filterListItem(ev.getY());
                    return true;
                }
                return false;

            case MotionEvent.ACTION_MOVE:
                if (mIsIndexing) {
                    // If this event moves inside index bar
                    if (contains(ev.getX(), ev.getY())) {
                        // Determine which section the point is in, and move the list to that section
                        filterListItem(ev.getY());
                        return true;
                    }
                    return false;
                }
            case MotionEvent.ACTION_UP:
                if (mIsIndexing) {
                    mIsIndexing = false;
                }
                break;
        }
        return false;
    }
}
