package com.ocse.baseandroid.view.tree

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.multilevel.treelist.Node
import com.multilevel.treelist.TreeRecyclerAdapter
import com.ocse.baseandroid.R

/**
 * @author hujiayi
 */
class TreeAdapter<T> : TreeRecyclerAdapter {
    var onClickListener: ClickListener? = null
    private var mRecycler: RecyclerView? = null
//    open lateinit var mContext: Context

    constructor(
        mTree: RecyclerView?,
        context: Context?,
        datas: List<Node<*, *>?>?,
        defaultExpandLevel: Int
    ) : super(mTree, context, datas, defaultExpandLevel) {
    }

    //直接调用
    constructor(
        mTree: RecyclerView?,
        context: Context,
        datas: List<Node<*, *>?>?,
        onClickListener: ClickListener?
    ) : super(
        mTree, context, datas, 0, R.mipmap.tree_ex,
        R.mipmap.tree_ec
    ) {
        this.onClickListener = onClickListener
        mRecycler = mTree
        mContext = context
    }

    override fun onBindViewHolder(
        node: Node<*, *>,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as TreeAdapter<*>.MyHoder
        if (node.icon == -1) {
            viewHolder.icon.visibility = View.INVISIBLE
        } else {
            viewHolder.icon.visibility = View.VISIBLE
            viewHolder.icon.setImageResource(node.icon)
        }
        viewHolder.label.setOnClickListener { view: View? ->
            onClickListener!!.onClick(
                node
            )
        }
        viewHolder.label.text = node.name
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false)
        return MyHoder(view)
    }

    internal inner class MyHoder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var label: TextView
        var icon: ImageView

        init {
            label = itemView
                .findViewById(R.id.id_treenode_label)
            icon = itemView.findViewById(R.id.icon)
        }
    }

    interface ClickListener {
        /**
         * @param node
         */
        fun onClick(node: Node<*, *>?)
    }
}