package com.practicasii.geolocalizacionreto

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.practicasii.geolocalizacionreto.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnObtenCoordenadas.setOnClickListener{
            ObtenerCoordenadas()
        }

    }

    fun ObtenerCoordenadas(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Log.d("LocationPermissions", "Doesn't have permission")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
            return
        }else{
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location!= null){
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocationName("${binding.etCiudad.text}",1)
                    Log.d("LocationPermissions", "Success ${address[0].latitude}, ${address[0].longitude},${address[0].countryName}")
                    binding.tvResCiudad.text = "Ciudad:${address[0].featureName}"
                  //  binding.tvResEstado.text = "Estado:${address[0].adminArea}"
                    binding.tvResLatitud.text = "Latitud:${address[0].latitude}"
                    binding.tvResLongitud.text = "Longitud:${address[0].longitude}"
                }
            }
        }
    }
}