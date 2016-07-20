package evento.com.evento.view.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.items.FollowItem;

/**
 * Created by amr masoud on 6/12/2016.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.followHolder> {

    ArrayList<FollowItem> list;

    public FollowAdapter(ArrayList<FollowItem> list) {
        this.list = list;
    }

    @Override
    public followHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_follow, parent, false);
        return new followHolder(itemView);
    }

    @Override
    public void onBindViewHolder(followHolder holder, int position) {
        FollowItem item = list.get(position);
        holder.image.setImageResource(item.image);
        holder.name.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class followHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        public followHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.IMGfollow);
            name = (TextView) itemView.findViewById(R.id.TVname_follow);

        }
    }

}


