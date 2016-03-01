package com.mati.toolbar_widgets.search_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mati.toolbar_widgets.R;


/**
 * Created by mati on 2016.02.16..
 */
public class SearchViewWidget extends ViewGroup {

    public final int IC_SEARCH = 999;

    Menu menu;
    Toolbar toolbar;
    private EditText searchEditText;
    private ImageView closeSearch;
    private CharSequence toolbarTitle;
    private SearchViewCallback searchViewCallback;
    private View root;

    public SearchViewWidget(Context context,Menu menu,Toolbar toolbar,SearchViewCallback searchViewCallback) {
        super(context);
        this.menu = menu;
        this.toolbar = toolbar;
        this.searchViewCallback = searchViewCallback;
        init();
    }

    public SearchViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchViewWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void setMenuElementsVisibility(boolean visible){
        for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(visible);
        }
    }

    private void init(){

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.search_edit_text, null);
        searchEditText = (EditText) root.findViewById(R.id.search);
        closeSearch = (ImageView) root.findViewById(R.id.close_search);
        toolbarTitle = toolbar.getTitle();
        try {

            menu.add(0, IC_SEARCH, 0, "search").setIcon(R.drawable.ic_action_search)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            MenuItem menuItem = menu.findItem(IC_SEARCH).setVisible(true);
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //setMenuElementsVisibility(false);
                    toolbar.setTitle("");
                    toolbar.addView(root);
                    if (closeSearch != null)
                        closeSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setMenuElementsVisibility(true);
                                toolbar.removeView(root);
                                toolbar.setTitle(toolbarTitle);
                                if(searchViewCallback!=null)searchViewCallback.onCloseView();
                            }
                        });

                    if (searchEditText != null) {
                        searchEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                                if (s.length() > 0) {
                                    if(searchViewCallback!=null)searchViewCallback.onTextChange(s,start,before,count);
                                } else {
                                    setMenuElementsVisibility(true);
                                    toolbar.removeView(getRootView());
                                    toolbar.setTitle(toolbarTitle);
                                    if(searchViewCallback!=null)searchViewCallback.onCloseView();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            searchViewCallback.onErrorOccur(e);
        }
    }


    public static class Builder{

        private Context context;
        private Menu menu;
        private Toolbar toolbar;
        private SearchViewCallback searchViewCallback;

        public Builder(Context context, Menu menu, Toolbar toolbar) {
            this.context = context;
            this.menu = menu;
            this.toolbar = toolbar;
        }

        public Builder setSearchViewCallback(SearchViewCallback searchViewCallback) {
            this.searchViewCallback = searchViewCallback;
            return this;
        }

        public SearchViewWidget build(){
            SearchViewWidget searchViewWidget = new SearchViewWidget(context,menu,toolbar,searchViewCallback);
            return searchViewWidget;

        }
    }

}
