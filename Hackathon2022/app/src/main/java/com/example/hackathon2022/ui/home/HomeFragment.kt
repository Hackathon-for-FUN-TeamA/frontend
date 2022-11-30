package com.example.hackathon2022.ui.home


import android.graphics.Color
import android.os.Bundle
import android.os.DropBoxManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.hackathon2022.AlertDialogFragment
import com.example.hackathon2022.SensorViewModel
import com.example.hackathon2022.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null

    private val viewModel: SensorViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /*加速度に使用*/
    private val labels = arrayOf(
        "linear_accelerationX",
        "linear_accelerationY",
        "linear_accelerationZ"
    )
    private val colors = intArrayOf(
        Color.BLUE,
        Color.GRAY,
        Color.MAGENTA
    )
    private var lineardata = true
    private var addCount=0
    /*加速度に使用*/


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textX: TextView = binding.textX
        val textY: TextView = binding.textY
        val textZ: TextView = binding.textZ

        /*sensorで動作*/
        var mChart: LineChart = binding.chart
        val valueX: ArrayList<Entry> = ArrayList()
        val valueY: ArrayList<Entry> = ArrayList()
        val valueZ: ArrayList<Entry> = ArrayList()

        var valueDataSetX = LineDataSet(valueX, "X")//set
        var valueDataSetY = LineDataSet(valueY, "Y")//set
        var valueDataSetZ = LineDataSet(valueZ, "Z")//set


        /*複数のデータセットを格納するリスト*/
        val dataSets = ArrayList<ILineDataSet>()
        viewModel.acceleration.observe(viewLifecycleOwner) {
            addCount++
            textX.text = it[0].toString()
            textY.text = it[1].toString()
            textZ.text = it[2].toString()
            val magnitudeOfAcceleration = sqrt(it[0].pow(2)+it[1].pow(2)+it[2].pow(2))
            if (magnitudeOfAcceleration < 4) {
                viewModel.putBackgroundColor(Color.WHITE)
            } else if (magnitudeOfAcceleration < 7.9) {
                viewModel.putBackgroundColor(Color.YELLOW)
            } else {
                val dialog = AlertDialogFragment()
                dialog.show(childFragmentManager, "sample")
                viewModel.putBackgroundColor(Color.RED)
            }

            // alpha is calculated as t / (t + dT)
            // with t, the low-pass filter's time-constant
            // and dT, the event delivery rate
            val gravity = FloatArray(3)
            val linearAcceleration = FloatArray(3)
            val alpha = 0.6f

            gravity[0] = alpha * gravity[0] + (1 - alpha) * it[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * it[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * it[2]
            linearAcceleration[0] = it[0] - gravity[0]
            linearAcceleration[1] = it[1] - gravity[1]
            linearAcceleration[2] = it[2] - gravity[2]

            /*テキストの変換不要？*/
            val accelero: String
            accelero = if (!lineardata) {
                String.format(
                    Locale.US,
                    "X: %.3f\nY: %.3f\nZ: %.3f",
                    it[0], it[1], it[2]
                )
            } else {
                String.format(
                    Locale.US,
                    "X: %.3f\nY: %.3f\nZ: %.3f",
                    gravity[0], gravity[1], gravity[2]
                )
            }

            /*リアル加速度表示*/
            valueX.add(Entry(addCount.toFloat(),linearAcceleration[0]))
            valueY.add(Entry(addCount.toFloat(),linearAcceleration[1]))
            valueZ.add(Entry(addCount.toFloat(),linearAcceleration[2]))
            valueDataSetX = LineDataSet(valueX, "X")//set
            valueDataSetY = LineDataSet(valueY, "Y")//set
            valueDataSetZ = LineDataSet(valueZ, "Z")//set
            valueDataSetX.lineWidth = 1.0f
            valueDataSetX.color = colors[0]
            valueDataSetY.lineWidth = 1.0f
            valueDataSetY.color = colors[1]
            valueDataSetZ.lineWidth = 1.0f
            valueDataSetZ.color = colors[2]

//            Log.v("valueSize", valueX.size.toString())

            dataSets.add(valueDataSetX)
            dataSets.add(valueDataSetY)
            dataSets.add(valueDataSetZ)

            /*リストが50以上で頭から抜き出し、容量軽くする*/
            if (valueX.size > 50) {
                valueX.removeAt(0)
                valueY.removeAt(0)
                valueZ.removeAt(0)
            }

            if(dataSets.size>50){
                dataSets.removeAt(0)
                dataSets.removeAt(0)
                dataSets.removeAt(0)
            }

//          Log.v("valueSize", dataSets.size.toString())
//            Log.v("valueSize", dataSets[0].toString())

            mChart.data = LineData(dataSets)
            mChart.setVisibleXRangeMaximum(50f) // 表示の幅を決定する
            mChart.moveViewToX(addCount.toFloat()) // 最新のデータまで表示を移動させる
        }

        viewModel.backgroundColor.observe(viewLifecycleOwner) {
            binding.root.setBackgroundColor(it)
        }

        val textSpeed: TextView = binding.textSpeed

        viewModel.speed.observe(viewLifecycleOwner) {
            /*このスコープ内のitがスピードを表している*/
            textSpeed.text = it.toString()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
