package view.lyd.com.qqhealth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fragment.CustomViewGroupFragment;
import fragment.QQhealthFragment;
import fragment.TicketViewFragment;

public class MainActivity extends AppCompatActivity {


    private LayoutInflater mLayoutInflater = null;

    private Fragment mTicketFragment = null;
    private Fragment mQQHealthFragment = null;
    private Fragment mCustomViewGroupFra = null;
//    private FragmentManager mFragmentManager = null;

    private RecyclerView mRecyclerView = null;
    private LinearLayoutManager mLinearLayoutManager = null;
    private ViewPager mViewPager = null;


    private NavigationAdapter mNavigationAdapter = null;
    private ViewAdapter mViewAdapter = null;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private int mPagerPosition = 0;

    private int mWhiteColor;
    private int mGrayColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutInflater = LayoutInflater.from(this);

        initView();
        initData();

    }

    private void initView() {

        mTicketFragment = new TicketViewFragment();
        mQQHealthFragment = new QQhealthFragment();
        mCustomViewGroupFra = new CustomViewGroupFragment();
        mFragmentList.add(mTicketFragment);
        mFragmentList.add(mQQHealthFragment);
        mFragmentList.add(mCustomViewGroupFra);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView_id);
        mNavigationAdapter = new NavigationAdapter();
        mRecyclerView.setAdapter(mNavigationAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mViewPager = (ViewPager) findViewById(R.id.main_ViewPager_id);
        mViewAdapter = new ViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerPosition = position;
                mNavigationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mWhiteColor = this.getResources().getColor(R.color.main_white);
        mGrayColor = this.getResources().getColor(R.color.main_gray);
    }

    private void initData() {
        mTitleList = new ArrayList<>();
        mTitleList.add("优惠券");
        mTitleList.add("qq健康");
        mTitleList.add("自定义viewGroup");

    }

    private class NavigationAdapter extends RecyclerView.Adapter<NavaHolder> {

        @Override
        public NavaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new NavaHolder(mLayoutInflater.inflate(R.layout.main_title_layout, null));
        }

        @Override
        public void onBindViewHolder(NavaHolder navaHolder, final int i) {
            navaHolder.titleView.setText(mTitleList.get(i));
            navaHolder.titleView.setTextColor(mPagerPosition == i ? mWhiteColor : mGrayColor);
        }

        @Override
        public int getItemCount() {
            return mTitleList.size();
        }
    }

    class NavaHolder extends RecyclerView.ViewHolder {
        public TextView titleView = null;

        public NavaHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.main_nava_title_view);
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(getAdapterPosition());
                }
            });
        }
    }

    private class ViewAdapter extends FragmentPagerAdapter {

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

    }
}
