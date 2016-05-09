package picker_unit_selector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.customviews.fermat.pickerunitselector.R;
import picker_unit_selector.util.TypefaceEnumType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is useful to make custom DropDown spinner.
 * You can Set Strings and Images in this spinner.
 *
 * @author Ankit Thakkar
 *
 * Modify by:
 * @author Clelia López
 *
 */
public class CaviarSpinner
        extends CaviarButton
        implements OnClickListener, OnItemClickListener, OnDismissListener, OnItemSelectedListener {

    /**
     * Attributes
     */
    private PopupWindow popUpWindow;
    private ListView listView;
    private List<PopupListItem> popUpListItem;
    private PopupListItemAdapter popUpListItemAdapter;
    private OnClickListener onClickListener = null;
    private OnItemClickListener onItemClickListener;
    private OnItemSelectedListener onItemSelectedListener;
    private int selectedPosition;

    // Spinner list
    private int popupHeight;
    private int popupWidth;
    private int popupBackgroundColor;
    private int popupBackgroundDrawable;

    // Spinner
    private float textSize;
    private TypefaceEnumType typeface;
    private int textColor;
    private int backgroundColor;
    private int backgroundDrawable;
    private int gravity;
    private int visibleItemNo;
    private int itemList;
    private int verticalOffset;
    private int width;
    private int height;

    // Item
    private int itemTextSize;
    private TypefaceEnumType itemTypeface;
    private int itemTextColor;
    private int itemBackgroundColor;
    private int itemBackgroundDrawable;
    private int itemGravity;
    int itemHeight;
    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;

    private Context context;

    private String TAG = getClass().getName();


    /**
     * Constructor - first form, context
     */
    public CaviarSpinner(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * Constructor - second form, context, attribute set
     */
    public CaviarSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * Constructor - third form, context, attribute set, style
     */
    public CaviarSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        // Spinner list
        popupHeight = 100;
        popupWidth = -1;
        popupBackgroundColor = R.color.white;
        popupBackgroundDrawable = -1;

        // Spinner
        textSize = 15;
        typeface = TypefaceEnumType.CAVIAR_DREAMS_BOLD;
        textColor = R.color.grey_dark;
        backgroundColor = R.color.white ;
        backgroundDrawable = -1;
        gravity = Gravity.START;
        visibleItemNo = 4;
        itemList = -1;
        verticalOffset = 1;
        width = 100;
        height = 50;

        // Item
        itemTextSize = 15;
        itemTypeface = TypefaceEnumType.CAVIAR_DREAMS_BOLD;
        itemTextColor = R.color.grey_dark;
        itemBackgroundColor = R.color.green_light;
        itemBackgroundDrawable = -1;
        itemGravity = Gravity.START;
        leftPadding = 6;
        rightPadding = 6;
        topPadding = 4;
        bottomPadding = 4;
        itemHeight = -1;

        // Marquee activation
        setSingleLine(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setGravity(gravity);

        // Spinner general config
        setItems(itemList);
        setItemTextSize(itemTextSize);
        setSpinnerTextSize(textSize);
        setSpinnerTypeFace(typeface);
        setSpinnerTextColor(textColor);
        setSpinnerBackgroundColor(backgroundColor);
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        // Spinner listener
        super.setOnClickListener(this);
    }

    /**
     * Set the item text size
     *
     * @param size - size of text
     */
    public void setItemTextSize(int size) {
        itemTextSize = size;
        setListViewHeightBasedOnChildren(listView);
    }

    /**
     * Set the item type face
     *
     * @param type - TypefaceEnumType (E.g TypefaceEnumType.CAVIAR_DREAMS_BOLD)
     */
    public void setItemTypeFace(TypefaceEnumType type) {
        itemTypeface = type;
    }

    /**
     * Set the item text font color
     *
     * @param color - color resource entry (E.g: R.color.white)
     */
    public void setItemTextColor(int color) {
        itemTextColor = color;
    }

    /**
     * Set the item text background color when is selected
     *
     * @param color - color resource entry (E.g: R.color.white)
     */
    public void setItemBackgroundColor(int color) {
        itemBackgroundColor = color;
    }

    /**
     * Set the item background drawable when is selected
     *
     * @param drawable - color resource entry (E.g: R.drawable.gradient)
     */
    public void setItemBackgroundDrawable(int drawable) {
        this.itemBackgroundDrawable = drawable;
    }

    /**
     * Set the item gravity
     *
     * @param gravity - desired gravicy constan (E.g: Gravity.CENTER)
     */
    public void setItemGravity(int gravity) {
        itemGravity = gravity;
    }

    /**
     * Set the item padding in drop down.
     *
     * @param left - pixel value
     * @param top - pixel value
     * @param right - pixel value
     * @param bottom - pixel value
     */
    public void setItemPadding(int left, int top, int right, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        setPadding(left, top, right, bottom);
        setListViewHeightBasedOnChildren(listView);
    }

    /**
     * Add the single string item in spinner
     *
     * @param item - resource entry on Strings.xml
     */
    public void addItem(int item) {
        if (popUpListItem == null) {
            popUpListItem = new ArrayList<>();
            popUpListItem.clear();
            popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
            setAdapter(popUpListItemAdapter);
        }
        popUpListItem.add(new PopupListItem(context.getResources().getString(item)));
        popUpListItemAdapter.notifyDataSetChanged();

    }

    /**
     * Add the single string item and image in spinner
     *
     * @param item - resource entry on Strings.xml
     * @param drawable - resource entry on Drawable folder
     */
    public void addItem(int item, int drawable) {
        if (popUpListItem == null) {
            popUpListItem = new ArrayList<>();
            popUpListItem.clear();
            popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
            setAdapter(popUpListItemAdapter);
        }

        if(item != -1){
            if(drawable != -1)
                popUpListItem.add(new PopupListItem(context.getResources().getString(item), drawable));
            else
                popUpListItem.add(new PopupListItem(context.getResources().getString(item), -1));
        } else {
            if(drawable != -1)
                popUpListItem.add(new PopupListItem("", drawable));
            else
                throw new InvalidParameterException("Invalid Parameter");
        }

        popUpListItemAdapter.notifyDataSetChanged();
    }

    /**
     * Set the string array in spinner
     *
     * @param array - array of strings on Strings.xml
     */
    public void setItems(int array) {
        itemList = array;
        String arrayList[];
        if (array != -1)
            arrayList = context.getResources().getStringArray(array);
        else
            arrayList = getResources().getStringArray(R.array.coins_name_array);

        if (popUpListItem == null)
            popUpListItem = new ArrayList<>();
        popUpListItem.clear();
        for (String text : arrayList)
            popUpListItem.add(new PopupListItem(text));

        popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
        setAdapter(popUpListItemAdapter);
        refreshView();
    }

    /**
     *
     * Set the string array & image array in list. size of string array and image array
     * required equal.
     *
     * @param array - array of strings on Strings.xml
     * @param icons - array of image drawables
     * @throws SizeNotMatchException - throw exception if string array & image array size not equal
     */
    public void setItems(int array, int[] icons) throws SizeNotMatchException {
        itemList = array;
        if (icons == null) {
            setItems(array);
        } else {
            String arrayList[] = context.getResources().getStringArray(array);
            if (arrayList.length == icons.length) {

                if (popUpListItem == null)
                    popUpListItem = new ArrayList<>();
                popUpListItem.clear();

                for (int i = 0; i < arrayList.length; i++) {
                    if (i < icons.length)
                        popUpListItem.add(new PopupListItem(arrayList[i], icons[i]));
                    else
                        popUpListItem.add(new PopupListItem(arrayList[i]));
                }
                popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
                setAdapter(popUpListItemAdapter);
            } else
                throw new SizeNotMatchException("Array sizes do not match");
        }

        refreshView();
    }

    /**
     * Get height of a single element
     *
     * @return int - height of element
     */
    public int getItemHeight(){
        return itemHeight;
    }

    /**
     * Set the height of DropDown spinner equal to number of visible rows
     *
     * @param no - number (Integer) of visible item row
     */
    public void setVisibleItemNumber(int no) {
        this.visibleItemNo = no;
        setListViewHeightBasedOnChildren(listView);
    }

    /**
     * Return the position of currently selected item within the adapter's data set.
     *
     * @return int position - (starting at 0).
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public String getSelectedItem() {
        return popUpListItem.get(getSelectedPosition()).getText();
    }

    /**
     * Set the currently selected item.
     *
     * @param selectedPosition - index (starting at 0) of the data item to be selected.
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        refreshView();
    }



    /**
     * Adapter setter.
     */
    private void setAdapter(PopupListItemAdapter adapter) {
        if (adapter != null) {
            this.popUpListItemAdapter = adapter;
            if (listView == null)
                listView = new ListView(getContext());
            listView.setDivider(null);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setOnItemSelectedListener(this);
            listView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            selectedPosition = 0;
        } else {
            selectedPosition = -1;
            this.popUpListItemAdapter = null;
        }
        refreshView();
    }

    /**
     * Popup list item to load from resources.
     */
    class  PopupListItem {
        private final String text;
        private final int resId;

        public PopupListItem(String text) {
            this.text = text;
            this.resId = -1;
        }

        public PopupListItem(String text, int resId) {
            this.text = text;
            this.resId = resId;
        }

        public String getText() {
            return text;
        }

        public int getResId() {
            return resId;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * spinner Adapter
     */
    public class PopupListItemAdapter
            extends ArrayAdapter<PopupListItem> {

        public PopupListItemAdapter(Context context, List<PopupListItem> objects) {
            super(context, android.R.layout.activity_list_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = newView();
                viewHolder = new ViewHolder();
                viewHolder.textView = (CaviarTextView) convertView.findViewById(android.R.id.text1);
                viewHolder.imageView = (ImageView) convertView.findViewById(android.R.id.icon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(getItem(position).getText());
            int resId = getItem(position).getResId();
            if (resId != -1) {
                viewHolder.imageView.setVisibility(VISIBLE);
                viewHolder.imageView.setImageResource(resId);
            } else {
                viewHolder.imageView.setVisibility(GONE);
            }
            convertView.setTag(viewHolder);
            return convertView;
        }

        private class ViewHolder {
            CaviarTextView textView;
            ImageView imageView;
        }

        /**
         * Create a new text view to add to the pop up list
         *
         * @return - Custom text view
         */
        private View newView() {
            LinearLayout parentLinearLayout = new LinearLayout(getContext());
            parentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            parentLinearLayout.setGravity(itemGravity);

            ImageView imageView = new ImageView(getContext());
            imageView.setId(android.R.id.icon);
            parentLinearLayout.addView(imageView);

            CaviarTextView textView = new CaviarTextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTextSize);
            textView.setTextColor(ContextCompat.getColor(context, itemTextColor));
            textView.setTypeFace(itemTypeface);
            textView.setGravity(itemGravity);

            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setMarqueeRepeatLimit(-1);
            textView.setId(android.R.id.text1);
            textView.setSelected(true);
            textView.setHeight(height - topPadding - bottomPadding);

            parentLinearLayout.addView(textView);
            parentLinearLayout.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

            return parentLinearLayout;
        }
    }

    /**
     * Generate background color drawable.
     *
     * @param color - color resource entry (E.g: R.color.white)
     */
    private Drawable generateRoundDrawable(int color) {
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.getPaint().setColor(ContextCompat.getColor(context, color));
        return drawable;
    }

    /**
     * Set appropriate width to pop up list
     * @param width - desired with in DP
     */
    public void setPopUpWidth(int width) {
        popupWidth = width;
    }

    /**
     * Set background color to pop up list
     * @param color - color resource entry (E.g: R.color.white)
     */
    public void setPopUpBackgroundColor(int color){
        popupBackgroundColor = color;
    }

    /**
     * Set background drawable to pop up list
     * @param drawable - color resource entry (E.g: R.drawable.gradient)
     */
    public void setPopupBackgroundDrawable(int drawable){
        popupBackgroundDrawable = drawable;
    }

    /**
     * Text setter for spinner button
     */
    private void refreshView() {
        if (popUpListItemAdapter != null) {
            PopupListItem popupListItem = popUpListItemAdapter.getItem(selectedPosition);
            setText(popupListItem.toString());
            setSelected(true);
            if(popupListItem.getResId() != -1)
                setCompoundDrawablesWithIntrinsicBounds(popupListItem.getResId(), 0, 0, 0);
        }
    }

    /**
     * Customize pop up view when spinner button is clicked
     */
    @Override
    public void onClick(View view) {
        if (popUpWindow == null || !popUpWindow.isShowing()) {
            popUpWindow = new PopupWindow(view);
            popUpWindow.setContentView(listView);
            if(popupWidth != -1)
                popUpWindow.setWidth(popupWidth);
            else
                popUpWindow.setWidth(this.width);
            popUpWindow.setHeight(popupHeight);
            if(popupBackgroundColor != -1)
                popUpWindow.setBackgroundDrawable(generateRoundDrawable(popupBackgroundColor));
            if(popupBackgroundDrawable != -1)
                popUpWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, popupBackgroundDrawable));
            popUpWindow.setOutsideTouchable(false);
            popUpWindow.setFocusable(true);
            popUpWindow.setClippingEnabled(true);

            //Setting mode and vertical offset
            popUpWindow.showAsDropDown(view, view.getLeft(), -(height * verticalOffset));
            popUpWindow.setOnDismissListener(this);
        }

        // Setting item selector / background
        if(itemBackgroundColor != -1)
            listView.setSelector(generateRoundDrawable(itemBackgroundColor));
        if(itemBackgroundDrawable != -1)
            listView.setSelector(ContextCompat.getDrawable(context, itemBackgroundDrawable));

        if (onClickListener != null)
            onClickListener.onClick(view);
    }

    /**
     * Spinner element, on click
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (popUpWindow != null)
            popUpWindow.dismiss();
        selectedPosition = position;
        refreshView();
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(adapter, view, position, id);
    }

    /**
     * Spinner element, on item selected
     */
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        if (onItemSelectedListener != null)
            onItemSelectedListener.onItemSelected(adapter, view, position, id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapter) {
        if (onItemSelectedListener != null)
            onItemSelectedListener.onNothingSelected(adapter);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener (OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }



    /**
     * Set spinner button text size
     */
    public void setSpinnerTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        textSize = size;
    }

    /**
     * Set spinner button type face
     *
     * @param type - TypefaceEnumType (E.g TypefaceEnumType.CAVIAR_DREAMS_BOLD)
     */

    public void setSpinnerTypeFace(TypefaceEnumType type) {
        super.setTypeFace(type);
    }

    /**
     * Set spinner button width
     *
     * @param width - desired width
     */

    public void setSpinnerWidth(int width) {
        this.width = width;
    }

    /**
     * Set spinner button height
     *
     * @param height - desired height
     */

    public void setSpinnerHeight(int height) {
        this.height = height;
    }


    /**
     * Set spinner button text color
     */
    public void setSpinnerTextColor(int color) {
        super.setTextColor(ContextCompat.getColor(context, color));
        textColor = color;
    }

    /**
     * Set spinner button background color
     */
    public void setSpinnerBackgroundColor(int color) {
        super.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    /**
     * Set spinner button background drawable
     */
    public void setSpinnerBackgroundDrawable(int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        super.setBackground(background);
    }

    /**
     * Set the item gravity
     *
     * @param gravity - desired gravicy constan (E.g: Gravity.CENTER)
     */
    public void setSpinnerGravity(int gravity) {
        this.gravity = gravity;
        setGravity(gravity);
    }

    /**
     * Set spinner vertical offset
     *
     * @param offsetNumber - number of items over spinner button
     */
    public void setVerticalItemsOffset(int offsetNumber) {
        verticalOffset = offsetNumber;
    }



    @Override
    public void onDismiss() {
        popUpWindow = null;
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int totalHeight = (height+topPadding+bottomPadding)*visibleItemNo;;
        int totalWidth = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            if (listItem.getMeasuredWidth() > totalWidth)
                totalWidth = listItem.getMeasuredWidth();
        }

        // Setting item height
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        itemHeight = listItem.getMeasuredHeight();

        // Setting list height
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (visibleItemNo - 1));
        popupHeight = params.height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * Set the dimensions of the spinner button
     *
     * @param width - desired width
     * @param height - desired height
     */
     @Override
     protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(this.width, this.height);
     }

    /**
     * Custom exception
     */
    public class SizeNotMatchException extends Exception {

        /** Attribute */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor
         *
         * @param message - User custom message
         */
        public SizeNotMatchException(String message) {
            super(message);
        }
    }


    public static class Builder {

        private CaviarSpinner spinner;

        // Spinner Button
        protected int spinnerTextSize = -1;
        protected TypefaceEnumType spinnerTypeface = null;
        protected int spinnerTextColor = -1;
        protected int spinnerBackgroundColor = -1;
        protected int spinnerBackgroundDrawable = -1;
        protected int spinnerWidth = -1;
        protected int spinnerHeight = -1;
        protected int spinnerNumberOfItems = -1;
        protected int spinnerItemsList = -1;
        protected int spinnerGravity = -1;
        protected int numberOfItemsOverSpinner = -1;

        // Dropdown Item
        protected int itemTextSize = -1;
        protected TypefaceEnumType itemTypeface = null;
        protected int itemTextColor = -1;
        protected int itemBackgroundColor = -1;
        protected int itemBackgroundDrawable = -1;
        private int itemLeftPadding = -1;
        private int itemRightPadding = -1;
        private int itemTopPadding = -1;
        private int itemBottomPadding = -1;
        private int itemGravity = -1;

        // Spinner list
        protected int spinnerListWidth = -1;
        protected int spinnerListBackgroundColor = -1;
        protected int spinnerListBackgroundDrawable = -1;


        public View build() {
            // Spinner Button
            if(spinnerTextSize != -1)
                spinner.setSpinnerTextSize(spinnerTextSize);

            if(spinnerTypeface != null)
                spinner.setSpinnerTypeFace(spinnerTypeface);

            if(spinnerTextColor != -1)
                spinner.setSpinnerTextColor(spinnerTextColor);

            if(spinnerBackgroundColor != -1)
                spinner.setSpinnerBackgroundColor(spinnerBackgroundColor);

            if(spinnerBackgroundDrawable != -1)
                spinner.setSpinnerBackgroundDrawable(spinnerBackgroundDrawable);

            if(spinnerGravity != -1)
                spinner.setSpinnerGravity(spinnerGravity);

            if(spinnerWidth != -1)
                spinner.setSpinnerWidth(spinnerWidth);

            if(spinnerHeight != -1)
                spinner.setSpinnerHeight(spinnerHeight);

            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);

            if(numberOfItemsOverSpinner != -1)
                spinner.setVerticalItemsOffset(numberOfItemsOverSpinner);


            // Dropdown Item
            if(itemTextSize != -1)
                spinner.setItemTextSize(itemTextSize);

            if(itemTypeface != null)
                spinner.setItemTypeFace(itemTypeface);

            if(itemTextColor != -1)
                spinner.setItemTextColor(itemTextColor);

            if(itemBackgroundColor != -1)
                spinner.setItemBackgroundColor(itemBackgroundColor);

            if(itemBackgroundDrawable != -1)
                spinner.setItemBackgroundDrawable(itemBackgroundDrawable);

            if(itemGravity != -1)
                spinner.setItemGravity(itemGravity);

            if(itemLeftPadding != -1 && itemRightPadding != -1 && itemTopPadding !=-1 && itemBottomPadding != -1)
                spinner.setItemPadding(itemLeftPadding, itemTopPadding, itemRightPadding, itemBottomPadding);


            // Dropdown List
            if(spinnerListWidth != -1)
                spinner.setPopUpWidth(spinnerListWidth);

            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }

        public CaviarSpinner getSpinner() {
            // Spinner Button
            if(spinnerTextSize != -1)
                spinner.setSpinnerTextSize(spinnerTextSize);

            if(spinnerTypeface != null)
                spinner.setSpinnerTypeFace(spinnerTypeface);

            if(spinnerTextColor != -1)
                spinner.setSpinnerTextColor(spinnerTextColor);

            if(spinnerBackgroundColor != -1)
                spinner.setSpinnerBackgroundColor(spinnerBackgroundColor);

            if(spinnerBackgroundDrawable != -1)
                spinner.setSpinnerBackgroundDrawable(spinnerBackgroundDrawable);

            if(spinnerGravity != -1)
                spinner.setSpinnerGravity(spinnerGravity);

            if(spinnerWidth != -1)
                spinner.setSpinnerWidth(spinnerWidth);

            if(spinnerHeight != -1)
                spinner.setSpinnerHeight(spinnerHeight);

            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);

            if(numberOfItemsOverSpinner != -1)
                spinner.setVerticalItemsOffset(numberOfItemsOverSpinner);


            // Dropdown Item
            if(itemTextSize != -1)
                spinner.setItemTextSize(itemTextSize);

            if(itemTypeface != null)
                spinner.setItemTypeFace(itemTypeface);

            if(itemTextColor != -1)
                spinner.setItemTextColor(itemTextColor);

            if(itemBackgroundColor != -1)
                spinner.setItemBackgroundColor(itemBackgroundColor);

            if(itemBackgroundDrawable != -1)
                spinner.setItemBackgroundDrawable(itemBackgroundDrawable);

            if(itemGravity != -1)
                spinner.setItemGravity(itemGravity);

            if(itemLeftPadding != -1 && itemRightPadding != -1 && itemTopPadding !=-1 && itemBottomPadding != -1)
                spinner.setItemPadding(itemLeftPadding, itemTopPadding, itemRightPadding, itemBottomPadding);


            // Dropdown List
            if(spinnerListWidth != -1)
                spinner.setPopUpWidth(spinnerListWidth);

            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }

        /**
         * Constructor
         *
         * @param context - fragment context
         */
        public Builder(Context context) {
            spinner = new CaviarSpinner(context);
        }


        // Spinner Button
        public Builder setSpinnerTextSize(int size) {
            spinnerTextSize = size;
            return this;
        }

        public Builder setSpinnerTextColor(int color) {
            spinnerTextColor = color;
            return this;
        }

        public Builder setSpinnerBackgroundColor(int color) {
            spinnerBackgroundColor = color;
            return this;
        }

        public Builder setSpinnerBackgroundDrawable(int drawable) {
            spinnerBackgroundDrawable = drawable;
            return this;
        }

        public Builder setSpinnerGravity(int gravity) {
            spinnerGravity = gravity;
            return this;
        }

        public Builder setSpinnerNumberOfVisibleItems(int number) {
            spinnerNumberOfItems = number;
            return this;
        }

        public Builder setSpinnerContent(int array) {
            spinnerItemsList = array;
            return this;
        }

        public Builder setNumberOfItemsOverSpinner(int number) {
            numberOfItemsOverSpinner = number;
            return this;
        }

        public Builder setSpinnerWidth(int width) {
            spinnerWidth = width;
            return this;
        }

        public Builder setSpinnerHeight(int height) {
            spinnerHeight = height;
            return this;
        }


        // Dropdown Item
        public Builder setItemTextSize(int size) {
            itemTextSize = size;
            return this;
        }

        public Builder setItemTextColor(int color) {
            itemTextColor = color;
            return this;
        }

        public Builder setItemBackgroundColor(int color) {
            itemBackgroundColor = color;
            return this;
        }

        public Builder setItemBackgroundDrawable(int drawable) {
            itemBackgroundDrawable = drawable;
            return this;
        }

        public Builder setItemGravity(int gravity) {
            itemGravity = gravity;
            return this;
        }

        public Builder setItemPadding(int left, int top, int right, int bottom) {
            itemLeftPadding = left;
            itemTopPadding = top;
            itemRightPadding = right;
            itemBottomPadding = bottom;
            return this;
        }


        // Dropdown List
        private Builder setSpinnerListWidth(int width) {
            spinnerListWidth = width;
            return this;
        }

        public Builder setSpinnerListBackgroundColor(int color) {
            spinnerListBackgroundColor = color;
            return this;
        }

        public Builder setSpinnerListBackgroundDrawable(int drawable) {
            spinnerListBackgroundDrawable = drawable;
            return this;
        }
    }
}