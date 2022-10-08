package com.example.hackathon2022.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackathon2022.R
import com.example.hackathon2022.RecyclerAdapter
import com.example.hackathon2022.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(){

    private var _binding: FragmentDashboardBinding? = null
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val TextList = listOf(
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo",
            "○月○日　oo:oo"
        )

        val recyclerView = binding.RecyclerList
        val adapter = RecyclerAdapter(TextList)

        adapter.setOnCellClickListener(
            object : RecyclerAdapter.OnCellClickListener {
                override fun onItemClick(item: String) {
                    //itemデータを渡す処理
                    setFragmentResult("stringData", bundleOf("itemString" to item))

                    //画面遷移処理
                    findNavController().navigate(R.id.navigation_map)
                }
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}