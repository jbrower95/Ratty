package com.jbrower.ratty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jbrower.ratty.display.MenuItem;
import com.jbrower.ratty.model.RattyMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Shows the menu for the ratty.
 * Created by Justin on 9/25/15.
 */
public class RattyFragment extends Fragment {

    private static final String KEY_MENU = "menu";

    private RattyMenu mMenu;

    @Bind(R.id.menu)
    ListView mMenuList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            mMenu = arguments.getParcelable(KEY_MENU);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_ratty, root, false);
        ButterKnife.bind(this, view);

        if (mMenu != null && mMenuList != null) {
            mMenuList.setAdapter(new ArrayAdapter<MenuItem>(getContext(), R.layout.food_item, mMenu.getAllMenuItems()) {
                @Override
                public View getView(int i, View reuseView, ViewGroup view) {
                    MenuItem item = getItem(i);
                    final View foodView = LayoutInflater.from(getContext()).inflate(item.getLayout(), view, false);
                    item.bindToView(foodView);
                    return foodView;
                }
            });
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public static RattyFragment create(RattyMenu menu) {
        final RattyFragment fragment = new RattyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_MENU, menu);
        fragment.setArguments(bundle);
        return fragment;
    }
}
