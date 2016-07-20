package evento.com.evento.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.activities.ShowProfileActivity;
import evento.com.evento.view.adpters.FollowViewHolder;
import evento.com.evento.controllers.UserController;
import evento.com.evento.model.beans.UserRepresentation;
import evento.com.evento.view.items.FollowItem;


public class FollowingFragment extends Fragment {

    private ArrayList<FollowItem> mlist;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<UserRepresentation , FollowViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;


    public FollowingFragment(){
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static FollowingFragment newInstance() {
        FollowingFragment fragment = new FollowingFragment();
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /// Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_following, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.LVfollowing);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query followRef = UserController.showFollowing();
        mAdapter = new FirebaseRecyclerAdapter<UserRepresentation, FollowViewHolder>(UserRepresentation.class, R.layout.item_follow, FollowViewHolder.class, followRef) {
            @Override
            protected void populateViewHolder(FollowViewHolder viewHolder, final UserRepresentation model, int position) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserController.getUserToShowProfile( model.getId(), getContext(), ShowProfileActivity.class);
                    }



                });

                viewHolder.bindTOFollow(model);

            }
        };

        mRecyclerView.setAdapter(mAdapter);

        // specify an adapter (see also next example)
        /*mAdapter = new FollowAdapter(getFollowing(100));
        mRecyclerView.setAdapter(mAdapter);*/
    }



    ArrayList<FollowItem> getFollowing(int size){
        ArrayList<FollowItem> list = new ArrayList<FollowItem>();

        for (int i=1; i<size; i++){
            FollowItem item = new FollowItem() ;
            item.image = R.drawable.ame;
            item.name = "name following " + i;
            list.add(item);
            //mlist.get(i).setImgPath(R.drawable.amr);
        }
        return list;

    }




}

