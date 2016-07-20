package evento.com.evento.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

import evento.com.evento.R;
import evento.com.evento.view.adpters.InterstedAdapter;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;


public class ProfileFragment extends Fragment {

    ImageView IMG_profile ;
    TextView TVname;
    TextView TVbirthdate;
    TextView TVlocation;
    TextView TVgender;

    FragmentActivity mContext;

    InterstedAdapter listAdapter;
    ExpandableListView mexpListView;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    Context context;

    private SectionsPagerAdapterTwo mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        IMG_profile= (ImageView) rootView.findViewById(R.id.IMGprofile);
        mexpListView = (ExpandableListView) rootView.findViewById(R.id.LVintersted);
        TVname= (TextView) rootView.findViewById(R.id.TVnameProfile);
        TVbirthdate= (TextView) rootView.findViewById(R.id.TVprofileBirthdata);
        TVlocation= (TextView) rootView.findViewById(R.id.TVprofileLocation);
        TVgender= (TextView) rootView.findViewById(R.id.TVprofileGender);
        // get the listview
        mexpListView = (ExpandableListView) rootView.findViewById(R.id.LVintersted);


        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+Commons.currentActiveUser.getId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        IMG_profile.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        getBasicInf();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // preparing list data
        prepareListData();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapterTwo(mContext.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.Followtabs);
        tabLayout.setupWithViewPager(mViewPager);


        context = getActivity();
        listAdapter = new InterstedAdapter(context, listDataHeader, listDataChild);

        // setting list adapter
        mexpListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<String>>();

        // Adding child data
        listDataHeader.add("Interstes");

        // Adding child data
        ArrayList<String> top250 = new ArrayList<String>();
        /*top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");*/
        for (int i=0; i<Commons.currentActiveUser.getInterests().size(); i++)
            top250.add(Commons.currentActiveUser.getInterests().get(i).getName());



        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
    }

    private void getBasicInf(){
//        Picasso.with(getContext()).load(Commons.currentActiveUser.getImageUrl()).into(IMG_profile);
        TVname.setText(Commons.currentActiveUser.getName());
        TVbirthdate.setText(Commons.currentActiveUser.getBirthdate());
        TVlocation.setText(Commons.currentActiveUser.getLocation());
        TVgender.setText(Commons.currentActiveUser.getGender());
    }

    public class SectionsPagerAdapterTwo extends FragmentPagerAdapter {

        public SectionsPagerAdapterTwo(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FollowingFragment.newInstance();
                case 1:
                    return FollowersFragment.newInstance();

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
                    return getResources().getString(R.string.FollowingTab);
                case 1:
                    return getResources().getString(R.string.FollowersTab);

            }
            return null;
        }

    }
}
