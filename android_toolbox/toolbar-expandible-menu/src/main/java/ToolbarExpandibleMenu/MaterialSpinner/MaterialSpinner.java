package ToolbarExpandibleMenu.MaterialSpinner;

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

import com.customviews.clelia.toolbarexpandiblemenu.R;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ToolbarExpandibleMenu.Custom.CaviarTextView;
import ToolbarExpandibleMenu.Utility.RippleDrawableGenerator;
import ToolbarExpandibleMenu.Utility.ScreenUnitConverter;
import ToolbarExpandibleMenu.Utility.TypefaceEnumType;


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
    private ArrayList<String> itemListString;
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

    private int numberOfItems = 0;

    private Context context;

    /**
     * Constructor - first form, context
     */

    public MaterialSpinner(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    /**
     * Constructor - second form, context, attribute set
     */
    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }

    /**
     * Constructor - third form, context, attribute set, style
     */
    public MaterialSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
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
        itemListString = null;
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
     * @param gravity - desired gravity constant (E.g: Gravity.CENTER)
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
        numberOfItems++;
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
     * Add the single string item in spinner
     *
     * @param item - string value
     */
    public void addItem(String item) {
        numberOfItems++;
        if (popUpListItem == null) {
            popUpListItem = new ArrayList<>();
            popUpListItem.clear();
            popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
            setAdapter(popUpListItemAdapter);
        }
        popUpListItem.add(new PopupListItem(item));
        popUpListItemAdapter.notifyDataSetChanged();
    }

    /**
     * Add the single string item and image in spinner
     *
     * @param item - resource entry on Strings.xml
     * @param drawable - resource entry on Drawable folder
     */
    public void addItem(int item, int drawable) {
        numberOfItems++;
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
     * Add the single string item and image in spinner
     *
     * @param item - string value
     * @param drawable - resource entry on Drawable folder
     */
    public void addItem(String item, int drawable) {
        numberOfItems++;
        if (popUpListItem == null) {
            popUpListItem = new ArrayList<>();
            popUpListItem.clear();
            popUpListItemAdapter = new PopupListItemAdapter(getContext(), popUpListItem);
            setAdapter(popUpListItemAdapter);
        }

        if(item != null){
            if(drawable != -1)
                popUpListItem.add(new PopupListItem(item, drawable));
            else
                popUpListItem.add(new PopupListItem(item,-1));
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

        numberOfItems = arrayList.length;

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
     * Set the string array in spinner
     *
     * @param array - array of strings on Strings.xml
     */
    public void setItems(ArrayList<String> array) {
        itemListString = array;
        ArrayList<String> arrayList = new ArrayList<>();
        if (array != null)
            arrayList = array;
        else {
            String arrayTemporal[] = getResources().getStringArray(R.array.test_array);
            arrayList.addAll(Arrays.asList(arrayTemporal));
        }

        numberOfItems = arrayList.size();

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
        numberOfItems = icons.length;
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
     *
     * Set the string array & image array in list. size of string array and image array
     * required equal.
     *
     * @param array - array of strings on Strings.xml
     * @param icons - array of image drawables
     * @throws SizeNotMatchException - throw exception if string array & image array size not equal
     */
    public void setItems(ArrayList<String> array, int[] icons) throws SizeNotMatchException {
        numberOfItems = icons.length;
        itemListString = array;
        ArrayList<String> arrayList = new ArrayList<>();
        if (icons == null) {
            setItems(array);
        } else {
            if (array != null)
                arrayList = array;
            else {
                String arrayTemporal[] = getResources().getStringArray(R.array.test_array);
                arrayList.addAll(Arrays.asList(arrayTemporal));
            }
            if (arrayList.size() == icons.length) {
                if (popUpListItem == null)
                    popUpListItem = new ArrayList<>();
                popUpListItem.clear();

                for (int i = 0; i < arrayList.size(); i++) {
                    if (i < icons.length)
                        popUpListItem.add(new PopupListItem(arrayList.get(i), icons[i]));
                    else
                        popUpListItem.add(new PopupListItem(arrayList.get(i)));
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

            // Setting  up item height
            if(itemHeight != -1)
                textView.setHeight(itemHeight - topPadding - bottomPadding);
            else {
                int fixedHeight = (int) ScreenUnitConverter.convertValueTo(context, 45, ScreenUnitConverter.Unit.PIXELS);
                textView.setHeight(fixedHeight - topPadding - bottomPadding);
            }

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

            // Setting up visible items number
            if(numberOfItems < 10)
                visibleItemNo = numberOfItems;
            else
                visibleItemNo = 10;

            // Calculating pop up width and height
            if(itemHeight != -1)
                popUpWindow.setHeight(itemHeight * visibleItemNo);
            else {
                int fixedHeight = (int)ScreenUnitConverter.convertValueTo(context, 45, ScreenUnitConverter.Unit.PIXELS);
                popUpWindow.setHeight(fixedHeight * visibleItemNo);
            }

            float calculatedPopUpWidth = -1;
            if(popupWidth != -1)
                popUpWindow.setWidth(popupWidth);
            else {
                float part = ScreenUnitConverter.getScreenWidth(context, ScreenUnitConverter.Unit.PIXELS) / 6;
                calculatedPopUpWidth = part * 3;
                popUpWindow.setWidth((int) calculatedPopUpWidth);
            }

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

            // Calculating final position on screen
            float screenWidth = ScreenUnitConverter.getScreenWidth(context, ScreenUnitConverter.Unit.PIXELS);
            float margin = ScreenUnitConverter.convertValueTo(context, 5, ScreenUnitConverter.Unit.PIXELS);
            int position[] = {-1, -1};
            view.getLocationOnScreen(position);
            float location;
            if(calculatedPopUpWidth != -1) {
                location = screenWidth - position[0] - margin - calculatedPopUpWidth;
                popUpWindow.showAsDropDown(view, (int) location, -((getHeight()) * verticalOffset));
            } else {
                location = screenWidth - position[0] - margin - popupWidth;
                popUpWindow.showAsDropDown(view, (int)location, -((getHeight()) * verticalOffset));
            }
            popUpWindow.setOnDismissListener(this);
        }

        // Setting item selector / background
        if(itemBackgroundColor != -1)
            listView.setSelector(RippleDrawableGenerator.generate(getContext(), itemBackgroundColor, itemBackgroundColor));

        getSpinnerPadding();

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
     * @return - spinner button height in pixels
     */
    public int getSpinnerHeight() {
        return super.getHeight();
    }

    /**
     * @return - spinner button text size in pixels
     */
    public float getSpinnerTextSize() {
        return super.getTextSize();
    }

    /**
     * @return - spinner button current text color
     */
    public float getSpinnerTextColor () {
        return super.getCurrentTextColor();
    }

    /**
     * @return - spinner button gravity
     */
    public int getSpinnerGravity() {
        return  super.getGravity();
    }

    public Typeface getSpinnerTypeFace() {
        return super.getTypeface();
    }

    /**
     * Get padding left, top, right nad bottom from spinner button
     */
    public void getSpinnerPadding () {
        leftPadding = super.getPaddingLeft();
        topPadding = super.getPaddingTop();
        bottomPadding = super.getPaddingBottom();
        rightPadding = super.getPaddingRight();
    }

    /**
     * Set spinner button text size
     */
    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        textSize = size;
    }

    /**
     * Set spinner button type face
     *
     * @param type - TypefaceEnumType (E.g TypefaceEnumType.CAVIAR_DREAMS_BOLD)
     */
    @Override
    public void setTypeFace(TypefaceEnumType type) {
        super.setTypeFace(type);
    }

    /**
     * Set spinner button text color
     */
    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        textColor = color;
    }

    /**
     * Set spinner button background color
     */
    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    /**
     * Set spinner button background drawable
     */
    @Override
    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
    }

    /**
     * Set spinner button padding
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
        super.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, getResources().getDisplayMetrics()));
    }

    public void setItemHeight(int height) {
        itemHeight = height;
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

    /**
     * Set the dimensions of the spinner button
     *
     * @param width - desired width
     * @param height - desired height
     */
     @Override
     protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(0, this.height);
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

        private MaterialSpinner spinner;

        // Spinner Button
        protected int spinnerNumberOfItems = -1;
        protected int spinnerItemsList = -1;

        // Item
        protected int itemTextSize = -1;
        protected TypefaceEnumType itemTypeface = null;
        protected int itemTextColor = -1;
        protected int itemBackgroundColor = -1;
        private int itemLeftPadding = -1;
        private int itemRightPadding = -1;
        private int itemTopPadding = -1;
        private int itemBottomPadding = -1;
        private int itemGravity = -1;

        // Spinner list
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

            // General spinner
            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);


            // Item
            if(itemTextSize != -1)
                spinner.setItemTextSize(itemTextSize);

            if(itemTypeface != null)
                spinner.setItemTypeFace(itemTypeface);

            if(itemTextColor != -1)
                spinner.setItemTextColor(itemTextColor);

            if(itemBackgroundColor != -1)
                spinner.setItemBackgroundColor(itemBackgroundColor);

            if(itemGravity != -1)
                spinner.setItemGravity(itemGravity);

            if(itemLeftPadding != -1 && itemRightPadding != -1 && itemTopPadding !=-1 && itemBottomPadding != -1)
                spinner.setItemPadding(itemLeftPadding, itemTopPadding, itemRightPadding, itemBottomPadding);


            // Spinner List
            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }

        public MaterialSpinner getMaterialSpinner(MaterialSpinner materialSpinner) {
            spinner = materialSpinner;

            // Spinner Button
            if(spinnerItemsList != -1)
                spinner.setItems(spinnerItemsList);

            if (spinnerNumberOfItems != -1)
                spinner.setVisibleItemNumber(spinnerNumberOfItems);


            // Item
            if(itemTextSize != -1)
                spinner.setItemTextSize(itemTextSize);

            if(itemTypeface != null)
                spinner.setItemTypeFace(itemTypeface);

            if(itemTextColor != -1)
                spinner.setItemTextColor(itemTextColor);

            if(itemBackgroundColor != -1)
                spinner.setItemBackgroundColor(itemBackgroundColor);

            if(itemGravity != -1)
                spinner.setItemGravity(itemGravity);

            if(itemLeftPadding != -1 && itemRightPadding != -1 && itemTopPadding !=-1 && itemBottomPadding != -1)
                spinner.setItemPadding(itemLeftPadding, itemTopPadding, itemRightPadding, itemBottomPadding);


            // Spinner List
            if(spinnerListBackgroundColor != -1)
                spinner.setPopUpBackgroundColor(spinnerListBackgroundColor);

            if(spinnerListBackgroundDrawable != -1)
                spinner.setPopupBackgroundDrawable(spinnerListBackgroundDrawable);

            return spinner;
        }


        // Spinner general
        public Builder setOverflowMenuNumberOfVisibleItems(int number) {
            spinnerNumberOfItems = number;
            return this;
        }

        public Builder setOverflowMenuContent(int array) {
            spinnerItemsList = array;
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
        public Builder setOverflowMenuListBackgroundColor(int color) {
            spinnerListBackgroundColor = color;
            return this;
        }

        public Builder setOverflowMenuListBackgroundDrawable(int drawable) {
            spinnerListBackgroundDrawable = drawable;
            return this;
        }
    }
}
