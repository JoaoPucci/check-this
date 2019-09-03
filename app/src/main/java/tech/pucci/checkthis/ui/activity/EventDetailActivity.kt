package tech.pucci.checkthis.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.pucci.checkthis.R
import tech.pucci.checkthis.model.Event
import tech.pucci.checkthis.model.Person
import tech.pucci.checkthis.network.RetrofitInitializer
import tech.pucci.checkthis.ui.adapter.PeopleAdapter
import tech.pucci.checkthis.util.SharedPrefsUtil
import java.util.*

class EventDetailActivity : AppCompatActivity() {

    private lateinit var event: Event
    private lateinit var ivImage: ImageView
    private lateinit var tvDescription: TextView
    private lateinit var mvMap: MapView
    private lateinit var rvPeople: RecyclerView
    private lateinit var tvAddress: TextView
    private lateinit var fabCheckThis: FloatingActionButton
    private lateinit var tvLabelDescription: TextView
    private lateinit var tvDatetime: TextView

    private val rcGeoLocationPermission = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        ivImage = findViewById(R.id.event_item_image)
        tvDescription = findViewById(R.id.event_item_description)
        mvMap = findViewById(R.id.mapView)
        rvPeople = findViewById(R.id.people_recycler_view)
        tvAddress = findViewById(R.id.event_detail_address)
        fabCheckThis = findViewById(R.id.event_detail_check)
        tvLabelDescription = findViewById(R.id.event_item_label_description)
        tvDatetime = findViewById(R.id.event_item_label_datetime)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val eventJsonString: String? = intent.getStringExtra(Event.EXTRA_EVENT)
        event = Gson().fromJson<Event>(eventJsonString, Event::class.java)

        configureFab()
        configureMapIfPermitted()

        mvMap.onCreate(Bundle())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configureFab() {
        fabCheckThis.setOnClickListener {
            AlertDialog.Builder(this).setTitle(getString(R.string.check_in_dialog_title))
                .setPositiveButton(getString(R.string.check_in_dialog_positive)) { _, _ ->
                    checkInRequest()
                }
                .setNegativeButton(getString(R.string.check_in_dialog_cancel)) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }.create().show()
        }
    }

    private fun checkInRequest() {
        val user = SharedPrefsUtil(this).getPerson()
        user?.eventId = event.id

        user?.eventId?.let {
            RetrofitInitializer()
                .eventsService()
                .checkIn(user)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@EventDetailActivity, getString(R.string.error_check_in), Toast.LENGTH_LONG).show()
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<ResponseBody>,response: Response<ResponseBody>) {
                        Toast.makeText(this@EventDetailActivity,getString(R.string.success_check_in) + ", " + user.name, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    private fun configureMapIfPermitted() {
        mvMap.getMapAsync {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                setupMapVisualization(it)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    rcGeoLocationPermission
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == rcGeoLocationPermission) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                    mvMap.getMapAsync {
                        setupMapVisualization(it)
                    }
                }

            } else {
                (mvMap.parent as View).visibility = View.GONE
            }
        }
    }

    private fun setupMapVisualization(map: GoogleMap) {
        val geoLocation = LatLng(event.latitude.toDouble(), event.longitude.toDouble())

        map.addMarker(MarkerOptions().position(geoLocation).title(event.title))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(geoLocation, 18f))

        tvAddress.text = getAddressLine(geoLocation)
    }

    private fun getAddressLine(geoLocation: LatLng): CharSequence? {
        return try {
            val geoCoder = Geocoder(this, Locale.getDefault()).getFromLocation(geoLocation.latitude, geoLocation.longitude, 1)
            geoCoder?.first()?.getAddressLine(0)
        } catch (e: Exception) {
            Log.e("geolocation", "Could not get adress line")
            e.printStackTrace()

            geoLocation.latitude.toString() + "," + geoLocation.longitude.toString()
        }
    }

    override fun onResume() {
        super.onResume()

        Picasso.with(this)
            .load(event.image)
            .placeholder(R.drawable.image_placeholder)
            .into(ivImage)

        tvLabelDescription.text = event.title
        tvDatetime.text = event.formattedTime
        tvDescription.text = event.description
        rvPeople.adapter = PeopleAdapter(this, event.people)

        mvMap.onResume()
    }



    override fun onStart() {
        mvMap.onStart()
        super.onStart()
    }

    override fun onPause() {
        mvMap.onPause()
        super.onPause()
    }

    override fun onStop() {
        mvMap.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mvMap.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mvMap.onLowMemory()
        super.onLowMemory()
    }
}
