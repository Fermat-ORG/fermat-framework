package com.customviews.fermat.currency_selector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.customviews.fermat.currency_selector.R;
import com.customviews.fermat.currency_selector.custom.CaviarEditText;
import com.customviews.fermat.currency_selector.custom.CaviarRadioButton;
import com.customviews.fermat.currency_selector.custom.CaviarTextView;
import com.customviews.fermat.currency_selector.custom.util.TypefaceEnumType;

import java.util.ArrayList;

/**
 * Created by Clelia López on 2/4/16
 */
public class CurrencySelector
        extends View {

    /** Attributes */
    private LinearLayout titleLinearLayout;
    private CaviarTextView titleTextView;
    private HorizontalScrollView currencySelectorHorizontalScrollView;
    private RadioGroup currencySelectorRadioGroup;
    private CaviarEditText amountEditorEditText;
    private CaviarTextView nameLabelTextView;
    private RelativeLayout converterRelativeLayout;
    private HorizontalScrollView converterHorizontalScrollView;
    private CaviarTextView converterTextView;
    private CaviarTextView converterTagTextView;

    private static ArrayList<Currency> currencies;
    private int Id = 1;
    private int CurrencyTextSelectedColor = R.color.white;
    private int CurrencyTextRegularColor = R.color.white;

    private Context context;


    /**
     * Class Constructor
     */
    public CurrencySelector(Context context) {
        super(context);
        this.context = context;

        initializeViews();
    }

    /**
     * View Constructor
     */
    public View buildView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.view_currency_selector, null);
        replaceView(view.getChildAt(0), titleLinearLayout);
        replaceView(view.getChildAt(1), currencySelectorHorizontalScrollView);
        replaceView(view.getChildAt(2), amountEditorEditText);
        replaceView(view.getChildAt(3), nameLabelTextView);
        replaceView(view.getChildAt(4), converterRelativeLayout);
        return view;
    }

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.view_currency_selector, null);

        titleLinearLayout = (LinearLayout) view.getChildAt(0);
        titleTextView = (CaviarTextView) titleLinearLayout.getChildAt(0);

        currencySelectorHorizontalScrollView = (HorizontalScrollView) view.getChildAt(1);
        currencySelectorRadioGroup = (RadioGroup) currencySelectorHorizontalScrollView.getChildAt(0);

        amountEditorEditText = (CaviarEditText) view.getChildAt(2);

        nameLabelTextView = (CaviarTextView) view.getChildAt(3);

        converterRelativeLayout = (RelativeLayout) view.getChildAt(4);
        converterHorizontalScrollView = (HorizontalScrollView) converterRelativeLayout.getChildAt(0);
        converterTextView = (CaviarTextView) converterHorizontalScrollView.getChildAt(0);
        converterTagTextView = (CaviarTextView) converterRelativeLayout.getChildAt(1);

        /**
         * Updates BTC converted amount value while the user types in the value
         */
        amountEditorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * Executes auto scroll to end on this view
             */
            @Override
            public void afterTextChanged(Editable editable) {
                converterTextView.setText(amountEditorEditText.getText());
                if (amountEditorEditText.getText().toString().length() > 8) {
                    converterHorizontalScrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            converterHorizontalScrollView.fullScroll(View.FOCUS_RIGHT);
                        }
                    }, 100L);
                }
            }
        });
    }

    /**
     * Called when the user clicks a button to perform an action
     *
     * @param view  indicates the view component pressed by the user
     */
    public void handleClick(View view) {
        setRadioButtonGroupSize(view);
        for(Currency currency : currencies)
            if (currency.id == view.getId())
                nameLabelTextView.setText(currency.name);
    }

    /**
     * Increases and decreases sizes for all radio buttons once one is selected
     * @param view  indicates the view component pressed by the user
     */
    public void setRadioButtonGroupSize(View view) {
        CaviarRadioButton radioButton;
        for(Currency currency : currencies) {
            if (currency.id == view.getId()) {
                radioButton = currency.radioButton;
                radioButton.changeDrawableSize(false, currency.width, currency.height, CurrencyTextSelectedColor, CurrencyTextRegularColor, currency.drawable);
            } else {
                radioButton = currency.radioButton;
                radioButton.changeDrawableSize(true, currency.width, currency.height, CurrencyTextSelectedColor, CurrencyTextRegularColor, currency.drawable);
            }
        }
    }

    /**
     *  Create and add currency to group
     *  @param code     ISO code from currency (E.G: USD - Check: https://en.wikipedia.org/wiki/ISO_4217 for more information)
     *  @param name     official currency name (E.G: American Dollar)
     */
    public Currency createCurrency(int code, int name) {
        if(currencies == null)
            currencies = new ArrayList<>();
        Currency currency = new Currency(code, name);
        currencies.add(currency);
        currencySelectorRadioGroup.addView(currency.radioButton);
        return  currency;
    }

    /**
     * Align all currency for properly distribution on RadioGroup
     */
    public  void alignCurrencies() {
        if(currencies.size() <= 8) {
            for(Currency currency : currencies) {
                currency.setLayoutWeight(1);
                currency.setPadding(0, 0, 0, 0);
                currency.setLayoutHeight(60);
                currency.setGravity(Gravity.CENTER);
            }
        } else {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics(); //TODO
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int width =  (int)(dpWidth / currencies.size());
            for(Currency currency : currencies) {
                currency.setPadding(0, 0, 0, 0);
                currency.setLayoutWidth(width + 5);
                currency.setLayoutHeight(60);
                currency.setGravity(Gravity.CENTER);
            }
        }
    }


    // Title
    public void setTitleText(int text) {
        titleTextView.setText(text);
    }

    public void setTitleTextSize(int size) {
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTitleTextColor(int color) {
        titleTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setTitleBackgroundColor(int color) {
        titleLinearLayout.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setTitleBackgroundDrawable(int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        titleLinearLayout.setBackground(background);
    }


    // Currency - Selector
    public void setCurrencyDefault(Currency currency) {
        if(currency != null)
            currency.setDefault();
    }

    public void setCurrencyDrawable(Currency currency, int drawable) {
        if(currency != null) {
            currency.drawable = drawable;
            currency.setDrawable(currency.drawable);
        }
    }

    public void setSelectorCurrencyTextSize(int size) {
        for(Currency currency : currencies)
            currency.setTextSize(size);
    }

    public void setSelectorTextSelectedColor(int color) {
        CurrencyTextSelectedColor = color;
    }

    public void setSelectorTextRegularColor(int color) {
        CurrencyTextRegularColor = color;
        for(Currency currency : currencies)
            currency.setTextColor(color);
    }

    public void buildCurrencies() {
        createCurrency(R.string.aud_label, R.string.australian_dollar_label);
        createCurrency(R.string.cad_label, R.string.canadian_dollar_label);
        createCurrency(R.string.eur_label, R.string.euro_label);
        createCurrency(R.string.usd_label, R.string.american_dollar_label);
        createCurrency(R.string.ars_label, R.string.argentine_peso_label);
        createCurrency(R.string.vef_label, R.string.venezuelan_bolivar_label);
        createCurrency(R.string.cop_label, R.string.colombian_peso_label);
        alignCurrencies();
    }

    public void setCurrencySelectorBackgroundColor(int color) {
        currencySelectorHorizontalScrollView.setBackgroundColor(ContextCompat.getColor(context, color));
    }

    public void setCurrencySelectorBackgroundDrawable(int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        currencySelectorHorizontalScrollView.setBackground(background);
    }


    // Amount Editor
    public void setAmountEditorHint(int text) {
        amountEditorEditText.setHint(text);
    }

    public void setAmountEditorHintColor(int color) {
        amountEditorEditText.setHintTextColor(ContextCompat.getColor(context, color));
    }

    public void setAmountEditorTextSize(int size) {
        amountEditorEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setAmountEditorTextColor(int color) {
        amountEditorEditText.setTextColor(ContextCompat.getColor(context, color));
    }


    // Name Label
    public void setNameLabelDefault(int text) {
        nameLabelTextView.setText(text);
    }

    public void setNameLabelTextSize(int size) {
        nameLabelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setNameLabelTextColor(int color) {
        nameLabelTextView.setTextColor(ContextCompat.getColor(context, color));
    }


    // Converter
    public void setConverterText(int text) {
        converterTextView.setText(text);
    }

    public void setConverterTagText(int text) {
        converterTagTextView.setText(text);
    }

    public void setConverterTextColor(int color) {
        converterTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setConverterTagTextColor(int color) {
        converterTagTextView.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setConverterBackgroundDrawable(int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        converterRelativeLayout.setBackground(background);
    }

    public void setConverterTagBackgroundDrawable(int drawable) {
        Drawable background = ContextCompat.getDrawable(context, drawable);
        converterTagTextView.setBackground(background);
    }


    class Currency {
        protected int id;
        protected int code;
        protected int name;
        protected int width;
        protected int height;
        protected int textColor;
        protected int drawable;
        protected CaviarRadioButton radioButton;

        /**
         * Constructor
         *
         * @param code      ISO code from currency (E.G: USD - Check: https://en.wikipedia.org/wiki/ISO_4217 for more information)
         * @param name      official currency name (E.G: American Dollar)
         */
        public Currency(int code, int name) {
            this.code = code;
            this.name = name;
            this.id = generateViewId();
            this.drawable = R.drawable.currency_type_button;
            HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.selector_currency_horizontal_scroll_view);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.radioButton = (CaviarRadioButton) inflater.inflate(R.layout.currency_button, scrollView, false);
            this.width = radioButton.getDrawableSize().x;
            this.height = radioButton.getDrawableSize().y;
            radioButton.setId(id);
            setText(code);
            setPadding(10, 0, 0, 0);
            setTypeFace(TypefaceEnumType.CAVIAR_DREAMS_BOLD);
            setOnClick(new OnClickListener() { @Override public void onClick(View view) { handleClick(view); }});
        }

        /**
         *  Set text size for this currency
         *  @param text     entry string resource (A string in Strings.xml should be created)
         */
        public Currency setText(int text) {
            radioButton.setText(text);
            return this;
        }

        /**
         *  Set text size for this currency
         *  @param size     desired size (Default unit is SP)
         */
        public Currency setTextSize(int size) {
            radioButton.setTextSize(size);
            return this;
        }

        /**
         *  Set text color for this currency
         *  @param color    entry resource color (E.g: R.color.name)
         */
        public Currency setTextColor(int color) {
            radioButton.setTextColor(ContextCompat.getColor(context, color));
            return this;
        }

        /**
         *  Set typeFace for this currency
         *  @param type     value from class TypefaceEnumType (E.g: type.CAVIAR_DREAMS)
         */
        public Currency setTypeFace(TypefaceEnumType type) {
            radioButton.setTypeFace(type);
            return this;
        }

        /**
         *  Set drawable for this currency
         *  @param drawable    id of selected drawable (E.g: R.drawable.name),
         */
        public Currency setDrawable(int drawable) {
            radioButton.setDrawable(drawable, width, height);
            return this;
        }

        /**
         *  Set default currency / already selected currency
         */
        public Currency setDefault() {
            radioButton.setChecked(true);
            radioButton.changeDrawableSize(false, width, height, CurrencyTextSelectedColor, CurrencyTextRegularColor, drawable);
            return this;
        }

        /**
         *  Set onClick method for view component
         *  @param listener     View.OnClickListener object
         */
        private void setOnClick(OnClickListener listener) {
            radioButton.setOnClickListener(listener);
        }

        /**
         *  Generate valid id to initialize a view
         *  @return  int id
         */
        private int generateViewId() {
            View view = findViewById(R.id.title_linear_layout);
            while (view != null)
                view = findViewById(++Id);
            return Id++;
        }

        /**
         *  Set weight for view component
         *  @param weight   desired weight
         */
        protected void setLayoutWeight(float weight) {
            radioButton.setLayoutParams(new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, weight));
        }

        /**
         *  Set width for view component
         *  @param width    desired width (Default unit is DP)
         */
        protected void setLayoutWidth(int width) {
            radioButton.getLayoutParams().width =
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        }

        /**
         *  Set height for view component
         *  @param height   desired height (Default unit is DP)
         */
        protected void setLayoutHeight(int height) {
            radioButton.getLayoutParams().height =
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        }

        /**
         *  Set gravity for view component
         *  @param gravity  desired gravity (E.g: Gravity.CENTER)
         */
        protected void setGravity(int gravity) {
            radioButton.setGravity(gravity);
        }

        /**
         *  Set padding for view component
         *  @param paddingTop       desired padding top (Default unit is DP), or 0
         *  @param paddingBottom    desired padding bottom (Default unit is DP), or 0
         *  @param paddingRight     desired padding left (Default unit is DP), or 0
         *  @param paddingLeft      desired padding left (Default unit is DP), or 0
         */
        protected void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            radioButton.setPadding(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingLeft, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingTop, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingRight, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingBottom, getResources().getDisplayMetrics())
            );
        }
    }


    public class Builder {

        private CurrencySelector selector;
        private Context context;
        private Currency currency;

        // Header
        protected int title = -1;
        protected int titleTextSize = -1;
        protected int titleTextColor = -1;
        protected int titleBackgroundColor = -1;
        protected int titleBackgroundDrawable = -1;

        // Currency Selector
        protected int currencyTextSelectedColor = -1;
        protected int currencyTextRegularColor = -1;
        protected int selectorBackgroundColor = -1;
        protected int selectorBackgroundDrawable = -1;
        protected int selectorTextSize = -1;

        // Editor
        protected int editorHint = -1;
        protected int editorHintColor = -1;
        protected int editorTextSize = -1;
        protected int editorTextColor = -1;

        // Name Label
        protected int nameDefault = -1;
        protected int nameTextSize = -1;
        protected int nameTextColor = -1;

        // Converter
        protected int converterText = -1;
        protected int converterTagText = -1;
        protected int converterTextColor = -1;
        protected int converterTagTextColor = -1;
        protected int converterBackgroundDrawable = -1;
        protected int converterTagBackgroundDrawable = -1;


        public View build() {
            // Header
            if(title != -1)
                selector.setTitleText(title);

            if(titleTextSize != -1)
                selector.setTitleTextSize(titleTextSize);

            if(titleTextColor != -1)
                selector.setTitleTextColor(titleTextColor);

            if(titleBackgroundColor != -1)
                selector.setTitleBackgroundColor(titleBackgroundColor);

            if(titleBackgroundDrawable != -1)
                selector.setTitleBackgroundDrawable(titleBackgroundDrawable);

            // Currency Selector
            if(currencies != null)
                selector.alignCurrencies();
            else
                selector.buildCurrencies();

            if(selectorTextSize != -1)
                selector.setSelectorCurrencyTextSize(selectorTextSize);

            if(currencyTextSelectedColor != -1)
                selector.setSelectorTextSelectedColor(currencyTextSelectedColor);

            if(currencyTextRegularColor != -1)
                selector.setSelectorTextRegularColor(currencyTextRegularColor);

            if(selectorBackgroundColor != -1)
                selector.setCurrencySelectorBackgroundColor(selectorBackgroundColor);

            if(selectorBackgroundDrawable != -1)
                selector.setCurrencySelectorBackgroundDrawable(selectorBackgroundDrawable);


            // Editor
            if(editorHint != -1)
                selector.setAmountEditorHint(editorHint);

            if(editorHintColor != -1)
                selector.setAmountEditorHintColor(editorHintColor);

            if(editorTextSize != -1)
                selector.setAmountEditorTextSize(editorTextSize);

            if(editorTextColor!= -1)
                selector.setAmountEditorTextColor(editorTextColor);


            // Name Label
            if(nameDefault != -1)
                selector.setNameLabelDefault(nameDefault);

            if(nameTextSize != -1)
                selector.setNameLabelTextSize(nameTextSize);

            if(nameTextColor!= -1)
                selector.setNameLabelTextColor(nameTextColor);


            // Converter
            if(converterText != -1)
                selector.setConverterText(converterText);

            if(converterTagText != -1)
                selector.setConverterTagText(converterTagText);

            if(converterTextColor != -1)
                selector.setConverterTextColor(converterTextColor);

            if(converterTagTextColor != -1)
                selector.setConverterTagTextColor(converterTagTextColor);

            if(converterBackgroundDrawable != -1)
                selector.setConverterBackgroundDrawable(converterBackgroundDrawable);

            if(converterTagBackgroundDrawable != -1)
                selector.setConverterTagBackgroundDrawable(converterTagBackgroundDrawable);

            return selector.buildView(context);
        }

        public Builder(Context context) {
            selector = new CurrencySelector(context);
            this.context = context;
        }

        // Title
        public Builder setTitle(int title) {
            this.title = title;
            return this;
        }

        public Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setTitleBackgroundColor(int titleBackgroundColor) {
            this.titleBackgroundColor = titleBackgroundColor;
            return this;
        }

        public Builder setTitleBackgroundDrawable(int titleBackgroundDrawable) {
            this.titleBackgroundDrawable = titleBackgroundDrawable;
            return this;
        }


        // Currency - Selector
        public Builder createCurrency(int code, int name) {
            currency = selector.createCurrency(code, name);
            return this;
        }

        public Builder buildCurrency() {
            currency = null;
            return this;
        }

        public Builder setSelectorCurrencyTextSize(int size) {
            selectorTextSize = size;
            return this;
        }

        public Builder setCurrencyDrawable(int drawable) {
            selector.setCurrencyDrawable(currency, drawable);
            return this;
        }

        public Builder setCurrencyDefault() {
            selector.setCurrencyDefault(currency);
            return this;
        }

        public Builder setSelectorCurrencySelectedTextColor(int color) {
            currencyTextSelectedColor = color;
            return this;
        }

        public Builder setSelectorCurrencyRegularTextColor(int color) {
            currencyTextRegularColor = color;
            return this;
        }

        public Builder setSelectorBackgroundColor(int color) {
            selectorBackgroundColor = color;
            return this;
        }

        public Builder setSelectorBackgroundDrawable(int drawable) {
            selectorBackgroundDrawable = drawable;
            return this;
        }

        // Editor
        public Builder setEditorHint(int editorHint) {
            this.editorHint = editorHint;
            return this;
        }

        public Builder setEditorHintColor(int editorHintColor) {
            this.editorHintColor = editorHintColor;
            return this;
        }

        public Builder setEditorTextSize(int editorTextSize) {
            this.editorTextSize = editorTextSize;
            return this;
        }

        public Builder setEditorTextColor(int editorTextColor) {
            this.editorTextColor = editorTextColor;
            return this;
        }


        // Name Label
        public Builder setNameDefault(int nameDefault) {
            this.nameDefault = nameDefault;
            return this;
        }

        public Builder setNameTextSize(int nameTextSize) {
            this.nameTextSize = nameTextSize;
            return this;
        }

        public Builder setNameTextColor(int nameTextColor) {
            this.nameTextColor = nameTextColor;
            return this;
        }


        // Converter
        public Builder setConverterText(int converterText) {
            this.converterText = converterText;
            return this;
        }

        public Builder setConverterTagText(int converterTagText) {
            this.converterTagText = converterTagText;
            return this;
        }

        public Builder setConverterTextColor(int converterTextColor) {
            this.converterTextColor = converterTextColor;
            return this;
        }

        public Builder setConverterTagTextColor(int converterTagTextColor) {
            this.converterTagTextColor = converterTagTextColor;
            return this;
        }

        public Builder setConverterBackgroundDrawable(int converterBackgroundDrawable) {
            this.converterBackgroundDrawable = converterBackgroundDrawable;
            return this;
        }

        public Builder setConverterTagBackgroundDrawable(int converterTagBackgroundDrawable) {
            this.converterTagBackgroundDrawable = converterTagBackgroundDrawable;
            return this;
        }
    }
}
