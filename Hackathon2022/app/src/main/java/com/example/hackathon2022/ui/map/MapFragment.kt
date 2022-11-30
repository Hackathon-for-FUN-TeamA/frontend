package com.example.hackathon2022.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.hackathon2022.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val mapViewModel: MapViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val ivMap: ImageView = binding.ivMap
        mapViewModel.map.observe(viewLifecycleOwner) {
            ivMap.setImageBitmap(it)
        }

        return root
    }

}