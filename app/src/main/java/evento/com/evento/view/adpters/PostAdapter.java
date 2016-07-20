package evento.com.evento.view.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.items.PostItem;

/**
 * Created by amr masoud on 6/21/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postHolder> {

    ArrayList<PostItem> mlist;

    public PostAdapter(ArrayList<PostItem> mlist) {
        this.mlist = mlist;
    }

    @Override
    public postHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_post, parent, false);
        return new postHolder(itemView);
    }

    @Override
    public void onBindViewHolder(postHolder holder, int position) {
        PostItem item = mlist.get(position);
        holder.authorView.setText(item.author);
        holder.bodyView.setText(item.body);
        holder.numStarsView.setText(item.starCount);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class postHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        //public ImageView starView;
        public TextView numStarsView;
        public TextView bodyView;

        public postHolder(View itemView) {
            super(itemView);

            authorView = (TextView) itemView.findViewById(R.id.post_author);
            //starView = (ImageView) itemView.findViewById(R.id.star);
            bodyView = (TextView) itemView.findViewById(R.id.post_body);
        }


     }
}
