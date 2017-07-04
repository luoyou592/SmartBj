package smartbj.royal.com.smartbj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smartbj.royal.com.smartbj.R;

/**
 * Created by 25505 on 2017/7/3.
 */

public class LeftSlidingMenu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View menuView = inflater.inflate(R.layout.left_menu, null);
        return menuView;
    }
}
