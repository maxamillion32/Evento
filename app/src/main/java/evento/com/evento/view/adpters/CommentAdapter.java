package evento.com.evento.view.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.view.items.CommentItem;

/**
 * Created by amr masoud on 6/21/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.commentHolder> {

    ArrayList<CommentItem> mlist;

    public CommentAdapter(ArrayList<CommentItem> mlist) {
        this.mlist = mlist;
    }

    @Override
    public commentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_comment, parent, false);
        return new commentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(commentHolder holder, int position) {
        CommentItem item = mlist.get(position);
        holder.authorView.setText(item.author);
        holder.bodyView.setText(item.text);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class commentHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        public TextView bodyView;

        public commentHolder(View itemView) {
            super(itemView);

            authorView = (TextView) itemView.findViewById(R.id.comment_author);
            bodyView = (TextView) itemView.findViewById(R.id.comment_body);
        }
    }
}
