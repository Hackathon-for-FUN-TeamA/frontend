package com.example.hackathon2022.ui.notifications

import android.app.UiModeManager.MODE_NIGHT_NO
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hackathon2022.databinding.FragmentNotificationsBinding
import com.jthemedetecor.OsThemeDetector

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Dark/Lightの切り替えスイッチ
        val switchTheme: SwitchCompat = binding.switchTheme

        //ダークモードかどうかを取得する
        val sharedPref = activity?.getSharedPreferences("Dark", Context.MODE_PRIVATE)

        val isDark = sharedPref?.getString("isDark", "False")

        // Darkならtrue, Lightならfalse
        switchTheme.isChecked = isDark == "True"

        // switchがクリックされたら状態保存とテーマ切り替え
        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            // テーマの切り替えと保存
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref?.edit()?.putString("isDark", "True")?.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref?.edit()?.putString("isDark", "False")?.apply()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
