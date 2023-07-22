package com.example.jobdeskapp.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jobdeskapp.R
import com.example.jobdeskapp.application.JobdeskApp
import com.example.jobdeskapp.databinding.FragmentSecondBinding
import com.example.jobdeskapp.model.Jobdesk
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val jobdeskViewModel: JobdeskViewModel by viewModels {
        JobdeskViewModelFactory((applicationContext as JobdeskApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var jobdesk: Jobdesk? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var  fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jobdesk = args.jobdesk
        if (jobdesk != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(jobdesk?.name)
            binding.addressEditText.setText(jobdesk?.address)
            binding.phonenumberEditText.setText(jobdesk?.phonenumber)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()


        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val phonenumber = binding.phonenumberEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Name Cannot Be Empty", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Address Cannot Be Empty", Toast.LENGTH_SHORT).show()
            } else if (phonenumber.isEmpty()) {
                Toast.makeText(context, "Phone Number Cannot Be Empty", Toast.LENGTH_SHORT).show()
            } else{

                if (jobdesk == null) {
                    val jobdesk = Jobdesk(0, name.toString(), address.toString(), phonenumber.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    jobdeskViewModel.insert(jobdesk)
                } else {
                    val jobdesk = Jobdesk(jobdesk?.id!!, name.toString(), address.toString(), phonenumber.toString(),currentLatLang?.latitude, currentLatLang?.longitude)
                    jobdeskViewModel.update(jobdesk)
                }
                findNavController().popBackStack()
            }
        }
        binding.deleteButton.setOnClickListener {
            jobdesk?.let { jobdeskViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "Location Access Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null){
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"

                    if (jobdesk != null){
                        title = jobdesk?.name.toString()
                        val newCurrentLocation = LatLng(jobdesk?.latitude!!, jobdesk?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val markerOptions = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.loc))
                    mMap.addMarker (markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
                }
            }
    }
}