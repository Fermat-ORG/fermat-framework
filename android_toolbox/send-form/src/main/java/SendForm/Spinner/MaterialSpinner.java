package SendForm.Spinner;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.customviews.clelia.sendform.R;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import SendForm.Custom.CaviarTextView;
import SendForm.Util.TypefaceEnumType;

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
public class MaterialSpinner
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
    private float itemTextSize;
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


    /**
     * Constructor - first form, context
     */
    public MaterialSpinner(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * Constructor - second form, context, attribute set
     */
    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * Constructor - third form, context, attribute set, style
     */
    public MaterialSpinner(Context context, AttributeSet attrs, int defStyle) {
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
        typeface = null;
        textColor = R.color.grey_dark;
        backgroundColor = R.color.white ;
        backgroundDrawable = -1;
        gravity = Gravity.START;
        visibleItemNo = 4;
        itemList = -1;
        verticalOffset = 1;
        width = 150;
        height = 80;

        // Item
        itemTextSize = 15;
        itemTypeface = null;
        itemTextColor = -1;
        itemBackgroundColor = R.color.green_light;
        itemBackgroundDrawable = -1;
        itemGravity = -1;
        leftPadding = 12;
        rightPadding = 12;
        topPadding = 6;
        bottomPadding = 6;
        itemHeight = -1;

        // Marquee activation
        setSingleLine(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);

        // Spinner general config
        setItems(itemList);
        setItemTextSize(itemTextSize);
        setItemBackgroundColor(R.color.green_light);

        // Spinner listener
        super.setOnClickListener(this);
    }

    /**
     * Set the item text size
     *
     * @param size - size of text
     */
    public void setItemTextSize(float size) {
        itemTextSize = size;
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
            arrayList = getResources().getStringArray(R.array.test_array);

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
     * Set the height of DropDown spinner equal to number of visible rows
     *
     * @param no - number (Integer) of visible item row
     */
    public void setVisibleItemNumber(int no) {
        this.visibleItemNo = no;
    }

    /**
     * Return the position of currently selected item within the adapter's data set.
     *
     * @return int position - (starting at 0).
     */
    public int getSelectedPosition() {
        return selectedPosition;
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
                viewHolder.imageView.setVisibility(View.VISIBLE);
                viewHolder.imageView.setImageResource(resId);
            } else {
                viewHolder.imageView.setVisibility(View.GONE);
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

            if(itemTextColor == -1)
                textView.setTextColor((int)getSpinnerTextColor());
            else
                textView.setTextColor(ContextCompat.getColor(context, itemTextColor));

            if(itemGravity == -1)
                textView.setGravity(getSpinnerGravity());
            else
                textView.setGravity(itemGravity);

            if(itemTypeface == null)
                textView.setTypeface(getSpinnerTypeFace());
            else
                textView.setTypeFace(itemTypeface);

            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setMarqueeRepeatLimit(-1);
            textView.setId(android.R.id.text1);
            textView.setSelected(true);

            textView.setHeight(getSpinnerHeight() - topPadding - bottomPadding);

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
        drawable.getPaint().setColor(color);
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
    public void setPopUpBackgroundColor(int color) {
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
     * Text setter for spinner action_button
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
     * Customize pop up view when spinner action_button is clicked
     */
    @Override
    public void onClick(View view) {
        if (popUpWindow == null || !popUpWindow.isShowing()) {
            popUpWindow = new PopupWindow(view);
            popUpWindow.setContentView(listView);
            if(popupWidth != -1)
                popUpWindow.setWidth(popupWidth);
            else
                popUpWindow.setWidth(super.getWidth());
            popUpWindow.setHeight(super.getHeight() * visibleItemNo);
            if(popupBackgroundColor != -1)
                popUpWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, popupBackgroundColor)));
            if(popupBackgroundDrawable != -1)
                popUpWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, popupBackgroundDrawable));
            popUpWindow.setOutsideTouchable(false);
            popUpWindow.setFocusable(true);
            popUpWindow.setClippingEnabled(false);

            // Setting up elevation / shadow
            listView.setClipToPadding(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                popUpWindow.setElevation(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

            //Setting mode and vertical offset
            popUpWindow.showAsDropDown(view, 0, -((getHeight()) * verticalOffset));
            popUpWindow.setOnDismissListener(this);
        }

        // Setting item selector / background
        if(itemBackgroundColor != -1)
            listView.setSelector(generateRoundDrawable((ContextCompat.getColor(context, itemBackgroundColor))));
        if(itemBackgroundDrawable != -1)
            listView.setSelector(ContextCompat.getDrawable(context, itemBackgroundDrawable));

        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);

        getSpinnerPadding();

        if (onClickListener != null)
            onClickListener.onClick(view);
    }

    /**
     * com.customviews.clelia.materialspinner.Spinner element, on click
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
     * @return - spinner action_button height in pixels
     */
    public int getSpinnerHeight() {
        return super.getHeight();
    }

    /**
     * @return - spinner action_button text size in pixels
     */
    public float getSpinnerTextSize() {
        return super.getTextSize();
    }

    /**
     * @return - spinner action_button current text color
     */
    public float getSpinnerTextColor () {
        return super.getCurrentTextColor();
    }

    /**
     * @return - spinner action_button gravity
     */
    public int getSpinnerGravity() {
        return  super.getGravity();
    }

    public Typeface getSpinnerTypeFace() {
        return super.getTypeface();
    }

    /**
     * Get padding left, top, right nad bottom from spinner action_button
     */
    public void getSpinnerPadding () {
        leftPadding = super.getPaddingLeft();
        topPadding = super.getPaddingTop();
        bottomPadding = super.getPaddingBottom();
        rightPadding = super.getPaddingRight();
    }

    /**
     * Set spinner action_button text size
     */
    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        textSize = size;
    }

    /**
     * Set spinner action_button type face
     *
     * @param type - TypefaceEnumType (E.g TypefaceEnumType.CAVIAR_DREAMS_BOLD)
     */
    @Override
    public void setTypeFace(TypefaceEnumType type) {
        super.setTypeFace(type);
    }

    /**
     * Set spinner action_button text color
     */
    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        textColor = color;
    }

    /**
     * Set spinner action_button background color
     */
    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    /**
     * Set spinner action_button background drawable
     */
    @Override
    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
    }

    /**
     * Set spinner action_button padding
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        leftPadding = left;
        topPadding = top;
        rightPadding = right;
        bottomPadding = bottom;
    }

    /**
     * Set the item gravity
     *
     * @param gravity - desired gravity constant (E.g: Gravity.CENTER)
     */
    @Override
    public void setGravity(int gravity) {
        super.setGravity(gravity);
        this.gravity = gravity;
    }

    @Override
    public void setWidth(int dpWidth) {
        super.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics()));
    }

    @Override
    public void setHeight(int dpHeight) {
        super.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, getResources().getDisplayMetrics()));
    }

    /**
     * Set spinner vertical offset
     *
     * @param offsetNumber - number of items over spinner action_button
     */
    public void setVerticalItemsOffset(int offsetNumber) {
        verticalOffset = offsetNumber;
    }



    @Override
    public void onDismiss() {
        popUpWindow = null;
    }

    /**
     * Set the dimensions of the spinner action_button
     *
     * @param width - desired width
     * @param height - desired height
     */
     /*@Override
     protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(this.width, this.height);
     }*/

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

        private MaterialSpinner spinner;

        // Spinner Button
        protected int spinnerTextSize = -1;
        protected TypefaceEnumType spinnerTypeface = null;
        protected int spinnerTextColor = -1;
        protected int spinnerBackgroundColor = -1;
        protected Drawable spinnerBackgroundDrawable = null;
        protected int spinnerWidth = -1;
        protected int spinnerHeight = -1;
        protected int spinnerNumberOfItems = -1;
        protected int spinnerItemsList = -1;
        protected int spinnerGravity = -1;
        protected int numberOfItemsOverSpinner = -1;

        // Item
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


        /**
         * Constructor
         *
         * @param context - fragment context
         */
        public Builder(Context context) {
            spinner = new MaterialSpinner(context);
        }

        public View build() {

            // Spinner Button
            if(spinnerTextSize != -1)
                spinner.setTextSize(spinnerTextSize);

            if(spinnerTypeface != null)
                spinner.setTypeFace(spinnerTypeface);

            if(spinnerTextColor != -1)
                spinner.setTextColor(spinnerTextColor);

            if(spinnerBackgroundColor != -1)
                spinner.setBackgroundColor(spinnerBackgroundColor);

            if(spinnerBackgroundDrawable != null)
                spinner.setBackground(spinnerBackgroundDrawable);

            if(spinnerGravity != -1)
                spinner.setGravity(spinnerGravity);

            if(spinnerWidth != -1)
                spinner.setWidth(spinnerWidth);

            if(spinnerHeight != -1)
                spinner.setHeight(spinnerHeight);

            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);

            if(numberOfItemsOverSpinner != -1)
                spinner.setVerticalItemsOffset(numberOfItemsOverSpinner);


            // Item
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


            // Spinner List
            if(spinnerListWidth != -1)
                spinner.setPopUpWidth(spinnerListWidth);

            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }

        public MaterialSpinner getMaterialSpinner(MaterialSpinner materialSpinner) {
            spinner = materialSpinner;

            // Spinner Button
            if(spinnerTextSize != -1)
                spinner.setTextSize(spinnerTextSize);

            if(spinnerTypeface != null)
                spinner.setTypeFace(spinnerTypeface);

            if(spinnerTextColor != -1)
                spinner.setTextColor(spinnerTextColor);

            if(spinnerBackgroundColor != -1)
                spinner.setBackgroundColor(spinnerBackgroundColor);

            if(spinnerBackgroundDrawable != null)
                spinner.setBackground(spinnerBackgroundDrawable);

            if(spinnerGravity != -1)
                spinner.setGravity(spinnerGravity);

            if(spinnerWidth != -1)
                spinner.setWidth(spinnerWidth);

            if(spinnerHeight != -1)
                spinner.setHeight(spinnerHeight);

            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);

            if(numberOfItemsOverSpinner != -1)
                spinner.setVerticalItemsOffset(numberOfItemsOverSpinner);


            // Item
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


            // Spinner List
            if(spinnerListWidth != -1)
                spinner.setPopUpWidth(spinnerListWidth);

            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }


        // Spinner Button
        public Builder setSpinnerTextSize(int size){
            spinnerTextSize = size;
            return this;
        }

        public Builder setSpinnerTextColor(int color){
            spinnerTextColor = color;
            return this;
        }

        public Builder setSpinnerBackgroundColor(int color) {
            spinnerBackgroundColor = color;
            return this;
        }

        public Builder setSpinnerBackgroundDrawable(Drawable drawable) {
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


        // Item
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


        // Spinner List
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