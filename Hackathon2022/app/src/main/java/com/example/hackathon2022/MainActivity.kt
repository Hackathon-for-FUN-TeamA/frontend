package com.example.hackathon2022

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.hackathon2022.databinding.ActivityMainBinding
import com.example.hackathon2022.model.Date
import com.example.hackathon2022.model.DateRoomDatabase
import com.example.hackathon2022.ui.dashboard.DashboardViewModel
import com.example.hackathon2022.ui.home.HomeViewModel
import com.example.hackathon2022.ui.map.MapViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), SensorEventListener, LocationListener {

    private var mManager: SensorManager by Delegates.notNull()
    private var mSensor: Sensor by Delegates.notNull()
    private lateinit var locationManager: LocationManager
    private var speed = 0f

    private val homeViewModel: HomeViewModel by viewModels()
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    //mapのURL(変更可能にする必要あり)
    private val URL = "https://maps.googleapis.com/maps/api/staticmap?center=35.692134,139.759854&size=640x320&scale=1&zoom=18&key=AIzaSyA-cfLegBoleKaT2TbU5R4K1uRkzBR6vUQ"
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //センサーマネージャーを取得する
        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //加速度計のセンサーを取得する
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        //ロケーションマネージャーに端末のロケーションサービスを関連づける
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        loadDate()


        //非同期処理
        coroutineScope.launch {
            val originalDeferred = coroutineScope.async(Dispatchers.IO) {
                getOriginalBitmap()
            }

            val originalBitmap = originalDeferred.await()
            dashboardViewModel.putMap(originalBitmap)
            mapViewModel.putMap(originalBitmap)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_login, R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.navigation_login || nd.id == R.id.navigation_signup || nd.id == R.id.navigation_map) {
                navView.visibility = View.GONE
            } else{
                navView.visibility = View.VISIBLE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //センサーに何かしらのイベントが発生したときに呼ばれる
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        //3つの値が配列で入ってくる
        Log.v("sensorTest", "______")
        //X軸方法
        Log.v("sensorTest", event?.values!![0].toString())
        //Y軸方法
        Log.v("sensorTest", event.values!![1].toString())
        //Z軸方法
        Log.v("sensorTest", event.values!![2].toString())

        homeViewModel.putAcceleration(event.values!!)

        val magnitudeOfAcceleration = sqrt(event.values[0].pow(2)+event.values[1].pow(2)+event.values[2].pow(2))
        if (magnitudeOfAcceleration >= 7.9) {
            val dateNow = LocalDate.now().toString()+ "-" + LocalTime.now().toString()
            saveData(dateNow)
            loadDate()
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onPause() {
        super.onPause()
        mManager.unregisterListener(this)
        //ポーズ時にはGPSの取得を解除する
        locationManager.removeUpdates(this)
    }

    override fun onResume() {
        super.onResume()
        //GPSの取得許可があるのかをチェック
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //GPSの使用が許可されていなければパーミッションを要求し、その後再度チェックが行われる
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onLocationChanged(location: Location) {
        speed = if (location.hasSpeed()) {
            location.speed*3.6f
        } else {
            0f
        }
        homeViewModel.putSpeed(speed)
        Log.d("speedTest", speed.toString())
    }

    private fun getOriginalBitmap(): Bitmap =
        URL(URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    private fun saveData(dateNow: String) {
        val dateDB = DateRoomDatabase.getInstance(this)
        val dateDao = dateDB.dateDao()
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val number = dashboardViewModel.dataListSize
                val date = Date(number, dateNow)
                dateDao.insert(date)
            }
        }
    }

    private fun loadDate() {
        val dateDB = DateRoomDatabase.getInstance(this)
        val dateDao = dateDB.dateDao()
        val stringList = mutableListOf<String>()
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val dateList = dateDao.getAll()
                for (date in dateList) {
                    date.date?.let { stringList.add(it) }
                }

                Log.v("RoomTest", dateList.toString())
            }
        }
        dashboardViewModel.putDateList(stringList)
        dashboardViewModel.dataListSize = stringList.size
    }
}


