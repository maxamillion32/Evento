package evento.com.evento.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.adpters.EventAdapter;
import evento.com.evento.view.items.EventItem;


public class EventsFragment extends Fragment {

    private static final String LOG_TAG = EventsFragment.class.getSimpleName();
    private static final int EVENT_LOADER = 1;

    private ArrayList<EventItem> mlist;
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private SectionsPagerAdapterThree mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private FragmentActivity mContext;

    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) getActivity();
        super.onAttach(activity);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapterThree(mContext.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container3);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.Eventstabs);
        tabLayout.setupWithViewPager(mViewPager);

    }




    public class SectionsPagerAdapterThree extends FragmentPagerAdapter {

        public SectionsPagerAdapterThree(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return JoinedFragment.newInstance();
                case 1:
                    return CreatedEventsFragment.newInstance();

            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.JointTab);
                case 1:
                    return getResources().getString(R.string.CreatedTab);

            }
            return null;
        }

    }



}
