package com.example.hackathon2022

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2022.ui.map.MapFragment

class RecyclerAdapter(private val list: List<String>) : RecyclerView.Adapter<ViewHolderList>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderList {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_list, parent, false)

        return ViewHolderList(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        holder.characterList.text = list[position]

        //セルのクリックイベントにリスナをセット
        holder.itemView.setOnClickListener {
            //セルがクリックされた時にインターフェースの処理が実行される
            listener.onItemClick(holder.characterList.text as String)
        }
    }

    override fun getItemCount(): Int = list.size
}