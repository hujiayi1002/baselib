package com.ocse.baseandroid.view.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;
import com.ocse.baseandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hujiayi
 */
public class TreeAdapter<T> extends TreeRecyclerAdapter {
    public ClickListener onClickListener;
   private RecyclerView mRecycler;
    public Context mContext;



    public TreeAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    //直接调用
    public TreeAdapter(RecyclerView mTree, Context context, List<Node> datas, ClickListener onClickListener) {
        super(mTree, context, datas, 0, R.mipmap.tree_ex,
                R.mipmap.tree_ec);
        this.onClickListener = onClickListener;
        this.mRecycler = mTree;
        this.mContext = context;

    }

    @Override
    public void onBindViewHolder(Node node, RecyclerView.ViewHolder holder, int position) {
        final MyHoder viewHolder = (MyHoder) holder;
        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        viewHolder.label.setOnClickListener(view -> onClickListener.onClick(node));
        viewHolder.label.setText(node.getName());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
        return new MyHoder(view);
    }

    class MyHoder extends RecyclerView.ViewHolder {
        public TextView label;
        public ImageView icon;

        public MyHoder(View itemView) {
            super(itemView);
            label = itemView
                    .findViewById(R.id.id_treenode_label);
            icon = itemView.findViewById(R.id.icon);

        }

    }

    public interface ClickListener {
        /**
         * @param node
         */
        void onClick(Node node);
    }

//    public void setNewData(List<NodeBean> datas) {
//        List<Node> mDatas = new ArrayList();
//
//        for (int i = 0; i < datas.size(); i++) {
//            if (datas.get(i).getPID().equals("0")) {
//                mDatas.add(
//                        new Node(
//                                datas.get(i).getID().toString(),
//                                "-1",
//                                datas.get(i).getNAME()
//                        )
//                );
//
//            } else {
//                mDatas.add(
//                        new Node(
//                                datas.get(i).getID(),
//                                datas.get(i).getPID(),
//                                datas.get(i).getNAME()
//                        )
//                );
//            }
//        }
//        notifyDataSetChanged();
//    }
}
