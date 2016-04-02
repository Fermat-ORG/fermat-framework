package ToolbarExpandibleMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.customviews.clelia.toolbarexpandiblemenu.R;

import java.util.ArrayList;

import ToolbarExpandibleMenu.MaterialSpinner.MaterialSpinner;
import ToolbarExpandibleMenu.MaterialSpinner.MenuButton;
import ToolbarExpandibleMenu.Utility.ScreenUnitConverter;
import ToolbarExpandibleMenu.Utility.TypefaceEnumType;
import ToolbarExpandibleMenu.Utility.ViewManager;

/**
 * Created by Clelia López on 2/27/16
 */
public class OverflowMenu
        extends LinearLayout {

    /**
     * Attributes
     */
    private MaterialSpinner materialSpinner;
    private MenuButton menuButton;

    // Spinner
    private int visibleItems;
    private TypefaceEnumType typeface = null;

    // Item
    private int itemTextColor;
    private int itemTextSize;
    private int itemSelectedColor;

    // List
    private int listBackgroundColor;
    private int listBackgroundDrawable;

    // Menu button
    private int pointsColor;
    private int menuBackgroundColor;

    private Context context;
    boolean initializedByParsing = false;

    boolean isItemSelectedColorSet = false;
    boolean isListBackgroundColorSet = false;
    boolean isListBackgroundDrawableSet = false;

    private static OnItemClickListener listener = null;

    /**
     * Class Constructor
     */
    public OverflowMenu(Context context) {
        super(context);

        this.context = context;

        initializeViews();
    }

    public OverflowMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initializedByParsing = true;

        parseAttributes(attrs);

        initializeViews();
    }

    private void parseAttributes(AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.OverflowMenu);

        // Spinner
        visibleItems = typedArray.getInteger(R.styleable.OverflowMenu_menuVisibleItems, 4);

        // Item
        itemTextSize = typedArray.getInteger(R.styleable.OverflowMenu_menuItemTextSize, 15);
        itemTextColor = typedArray.getResourceId(R.styleable.OverflowMenu_menuItemTextColor, R.color.grey_dark);
        itemSelectedColor = typedArray.getResourceId(R.styleable.OverflowMenu_menuItemSelectedColor, R.color.green_light);
        if(itemSelectedColor != R.color.green_light)
            isItemSelectedColorSet = true;

        // List
        listBackgroundColor = typedArray.getResourceId(R.styleable.OverflowMenu_menuListBackgroundColor, R.color.white);
        if(listBackgroundColor != R.color.white)
            isListBackgroundColorSet = true;
        listBackgroundDrawable = typedArray.getResourceId(R.styleable.OverflowMenu_menuListBackgroundDrawable, R.drawable.test_gradient);
        if(listBackgroundDrawable != R.drawable.test_gradient)
            isListBackgroundDrawableSet = true;

        // Menu button
        pointsColor = typedArray.getResourceId(R.styleable.OverflowMenu_menuPointsColor, R.color.green_light);
        menuBackgroundColor = typedArray.getResourceId(R.styleable.OverflowMenu_menuBackgroundColor, R.color.translucent);

        String spinnerTypeface = typedArray.getString(R.styleable.OverflowMenu_typeface);
        typeface = TypefaceEnumType.ROBOTO_REGULAR;
        if(spinnerTypeface != null) {
            switch (Integer.parseInt(spinnerTypeface)) {
                case 0: typeface  = TypefaceEnumType.ROBOTO_REGULAR; break;
                case 1: typeface  = TypefaceEnumType.ROBOTO_BOLD; break;
                case 2: typeface  = TypefaceEnumType.ROBOTO_ITALIC; break;
                case 3: typeface  = TypefaceEnumType.ROBOTO_BOLD_ITALIC; break;
                case 4: typeface  = TypefaceEnumType.CAVIAR_DREAMS; break;
                case 5: typeface  = TypefaceEnumType.CAVIAR_DREAMS_BOLD; break;
                case 6: typeface  = TypefaceEnumType.CAVIAR_DREAMS_ITALIC; break;
                case 7: typeface  = TypefaceEnumType.CAVIAR_DREAMS_BOLD_ITALIC; break;
            }
        }

        typedArray.recycle();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.material_spinner_layout_expandiblemenu, this, true);
        FrameLayout containerFrameLayout = (FrameLayout) view.getChildAt(0);
        materialSpinner = (MaterialSpinner) containerFrameLayout.getChildAt(0);
        menuButton = (MenuButton) containerFrameLayout.getChildAt(1);

        if(initializedByParsing) {

            // Spinner
            materialSpinner.setVisibleItemNumber(visibleItems);
            if (typeface != null)
                materialSpinner.setTypeFace(typeface);

            // Item
            materialSpinner.setItemTextSize(itemTextSize);
            materialSpinner.setItemTextColor(itemTextColor);
            if(isItemSelectedColorSet)
                materialSpinner.setItemBackgroundColor(itemSelectedColor);

            // List
            if(isListBackgroundColorSet)
                materialSpinner.setPopUpBackgroundColor(listBackgroundColor);
            if(isListBackgroundDrawableSet && !isListBackgroundColorSet)
                materialSpinner.setPopupBackgroundDrawable(listBackgroundDrawable);

            // Menu button
            menuButton.setPointsColor(pointsColor);
            menuButton.setBackground(menuBackgroundColor);
        }

        // Setting up menu button listener
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                materialSpinner.onClick(materialSpinner);
            }
        });

        materialSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.onItemClick(materialSpinner.getSelectedItem());
            }
        });
    }

    /**
     * Observer
     */
    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public static class OverflowMenuListener {

        public void setListener(OnItemClickListener externalListener) {
            listener = externalListener;
        }
    }

    /**
     * View Constructor
     */
    private View buildView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.material_spinner_layout_expandiblemenu, null);
        ViewManager.replaceView(view.getChildAt(0), materialSpinner);
        ViewManager.replaceView(view.getChildAt(1), menuButton);

        return view;
    }

    /**
     * Return material design spinner
     * @return - inflated spinner
     */
    public MaterialSpinner getSpinner() {
        return materialSpinner;
    }

    /**
     * Replace material spinner object for another one
     * @param spinner - material spinner object
     */
    private void setMaterialSpinner(MaterialSpinner spinner) {
        materialSpinner = spinner;
    }

    /**
     * Set width to overflow menu pop up window
     * @param width - desired width in DP unit
     */
    public void setOverflowPopUpWidth(int width) {
        getSpinner().setPopUpWidth((int)ScreenUnitConverter.convertValueTo(context, width, ScreenUnitConverter.Unit.PIXELS));
    }

    /**
     * Set height to overflow menu item
     * @param height - desired height in DP unit
     */
    public void setOverflowItemHeight(int height) {
        getSpinner().setItemHeight((int) ScreenUnitConverter.convertValueTo(context, height, ScreenUnitConverter.Unit.PIXELS));
    }

    /**
     * @param color - desired color
     */
    public void setOverflowPointsColor(int color) {
       menuButton.setPointsColor(color);
    }

    /**
     * @param color - desired color
     */
    public void setOverflowBackgroundColor(int color) {
        menuButton.setBackground(color);
    }


    /**
     * Builder class
     */
    public static class Builder
            extends MaterialSpinner.Builder {

        MaterialSpinner innerSpinner;
        OverflowMenu overflowMenu;
        Context context;

        private int pointsColor = -1;
        private int menuBackgroundColor = -1;
        private int popUpWidth = -1;
        private int popUpItemHeight = -1;

        ArrayList<String> items = null;
        int array = -1;


        /**
         * Constructor
         *
         * @param context - User class context
         */
        public Builder(Context context) {
            super(context);
            this.context = context;
            overflowMenu = new OverflowMenu(context);
        }

        /**
         * Constructor
         *
         * @param context - User class context
         * @param array - array resource id on String.xml
         */
        public Builder(Context context, int array) {
            super(context);
            this.context = context;
            overflowMenu = new OverflowMenu(context);
            this.array = array;
        }

        /**
         * Constructor
         *
         * @param context - User class context
         * @param array - ArrayList collection
         */
        public Builder(Context context, ArrayList<String> array) {
            super(context);
            this.context = context;
            overflowMenu = new OverflowMenu(context);
            items = array;
        }

        public View build() {

            // Menu button
            if(pointsColor != -1)
                overflowMenu.setOverflowPointsColor(pointsColor);
            if(menuBackgroundColor != -1)
                overflowMenu.setOverflowBackgroundColor(menuBackgroundColor);

            // Overflow window width
            if(popUpWidth != -1)
                overflowMenu.setOverflowPopUpWidth(popUpWidth);

            // Overflow item height
            if(popUpItemHeight != -1)
                overflowMenu.setOverflowItemHeight(popUpItemHeight);

            innerSpinner = getMaterialSpinner(overflowMenu.getSpinner());

            // Array
            if(array != -1)
                innerSpinner.setItems(array);
            if(items != null)
                innerSpinner.setItems(items);

            overflowMenu.setMaterialSpinner(innerSpinner);

            return overflowMenu.buildView(context);
        }

        // Array
        public Builder setOverflowMenuItems(int array) {
            this.array = array;
            return this;
        }

        public Builder setOverflowMenuItems(ArrayList<String> array) {
            items = array;
            return this;
        }

        // Menu button
        public Builder setOverflowMenuPointsColor(int color) {
            pointsColor = color;
            return this;
        }

        public Builder setOverflowMenuBackgroundColor(int color) {
            menuBackgroundColor = color;
            return this;
        }

        // Overflow window width
        public Builder setOverflowMenuPopUpWidth(int width) {
            popUpWidth = width;
            return this;
        }

        // Overflow item height
        public Builder setOverflowItemHeight(int height) {
            popUpItemHeight = height;
            return this;
        }
    }
}
