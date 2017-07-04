package smartbj.royal.com.smartbj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbj.royal.com.smartbj.R;

/**
 * Created by 25505 on 2017/7/3.
 */

public class LeftSlidingMenu extends Fragment {
    @BindView(R.id.lv_menu)
    ListView mLvMenu;
    private String[] mMenus = {"新闻","专题","组图","互动"};
    private int mLastposition;
    private OnLeftMenuChangeListener mLeftMenuChangeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View menuView = inflater.inflate(R.layout.left_menu, null);
        ButterKnife.bind(this, menuView);
        init();
        setlistener();
        return menuView;
    }

    private void setlistener() {
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==mLastposition){
                    return;
                }else {
                    //索引不同表示切换
                    mLeftMenuChangeListener.OnLeftMenuChange(position);
                }
                mLvMenu.getChildAt(position).setEnabled(true);
                mLvMenu.getChildAt(mLastposition).setEnabled(false);
                mLastposition = position;
            }
        });
    }

    private void init() {
        mLvMenu.setAdapter(mAdapter);
    }
    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mMenus.length;
        }

        @Override
        public Object getItem(int position) {
            return mMenus[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(),R.layout.item_left_menu, null);
            TextView tvMenu = (TextView) convertView.findViewById(R.id.tv_menu);
            tvMenu.setText(mMenus[position]);
            if (position!=0){
                tvMenu.setEnabled(false);
            }
            return convertView;
        }
    };
    public interface OnLeftMenuChangeListener {
        void OnLeftMenuChange(int position);
    }
    public void setOnLeftMenuChangeListener(OnLeftMenuChangeListener listener){
        mLeftMenuChangeListener = listener;
    }
}
