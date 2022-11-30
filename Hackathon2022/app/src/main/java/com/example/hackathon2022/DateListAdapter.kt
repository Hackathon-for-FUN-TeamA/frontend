package com.example.hackathon2022

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2022.model.Date

class DateListAdapter: ListAdapter<Date, DateListAdapter.DateViewHolder>(DATES_COMPARATOR){

    // リスナを格納する変数を定義（lateinitで初期化を遅らせている）
    private lateinit var listener: OnCellClickListener

    //インターフェースを作成
    interface OnCellClickListener {
        fun onItemClick(item: String)
    }

    fun setOnCellClickListener(listener: OnCellClickListener) {
        //定義した変数listenerに実行したい処理を引数で渡す（Fragmentで渡す）
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.date)

        //セルのクリックイベントにリスナをセット
        holder.itemView.setOnClickListener {
            //セルがクリックされた時にインターフェースの処理が実行される
            listener.onItemClick(current.date as String)
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateItemView: TextView = itemView.findViewById(R.id.Text1)

        fun bind(text: String?) {
            dateItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): DateViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_list, parent, false)
                return DateViewHolder(view)
            }
        }
    }

    companion object {
        private val DATES_COMPARATOR = object :  DiffUtil.ItemCallback<Date>() {
            override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean {
                return  oldItem.date == newItem.date
            }
        }
    }
}