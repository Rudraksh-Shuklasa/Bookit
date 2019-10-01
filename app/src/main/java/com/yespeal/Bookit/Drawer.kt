    package com.yespeal.Bookit

    import android.Manifest
    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.content.pm.PackageManager
    import android.graphics.Bitmap
    import android.graphics.Canvas
    import android.location.Location
    import android.os.*
    import android.support.design.widget.NavigationView
    import android.support.v4.app.ActivityCompat
    import android.support.v4.content.ContextCompat
    import android.support.v4.view.GravityCompat
    import android.support.v7.app.ActionBarDrawerToggle
    import android.support.v7.app.AppCompatActivity
    import android.support.v7.widget.LinearLayoutManager
    import android.support.v7.widget.RecyclerView
    import android.util.Log
    import android.view.Menu
    import android.view.MenuItem
    import android.widget.LinearLayout
    import android.widget.Toast
    import com.google.android.gms.common.api.Status
    import com.google.android.gms.location.*
    import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment

    import com.google.android.gms.maps.CameraUpdateFactory
    import com.google.android.gms.maps.GoogleMap
    import com.google.android.gms.maps.OnMapReadyCallback
    import com.google.android.gms.maps.SupportMapFragment
    import com.google.android.gms.maps.model.*
    import com.google.android.libraries.places.api.Places
    import com.google.android.libraries.places.api.model.Place
    import com.google.android.libraries.places.api.model.TypeFilter
    import com.google.android.libraries.places.widget.AutocompleteSupportFragment
    import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
    import com.yespeal.Bookit.Adapter.CarSelecationAdapter
    import com.yespeal.Bookit.model.DataAdapterCarSelection

    import kotlinx.android.synthetic.main.activity_drawer.*
    import kotlinx.android.synthetic.main.app_bar_drawer.*
    import java.util.*

    class Drawer() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback ,
       GoogleMap.OnMarkerDragListener {


        private var mFusedLocationClient: FusedLocationProviderClient? = null
        private lateinit var mMap: GoogleMap
        lateinit var placeAutoComplete : PlaceAutocompleteFragment
        lateinit var pref : SharedPreferences
        var markerClicked: Boolean = false
        private var mPlaceName: String? = null
        var mLocationRequest: LocationRequest? = null
        var mLastLocation: Location? = null
        var mCurrLocationMarker: Marker? = null



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_drawer)

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            pref = applicationContext.getSharedPreferences("BookIt", 0)

            val recyclerView = findViewById(R.id.idRecyclerViewHorizontalList) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
            val users = ArrayList<DataAdapterCarSelection>()

            //adding some dummy data to the list
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))
            users.add(DataAdapterCarSelection(R.drawable.ic_car, "MIcro", "5 Min", false))


            val adapter = CarSelecationAdapter(users, this);
            recyclerView.adapter = adapter

            val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )

            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(),"AIzaSyArQuiXcsenB8_fIj9lygrUurvy8bk2NnU");
            }

            markerClicked = false

            var autocompleteFragment =
            getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

            autocompleteFragment.setCountry("IN")
            autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS)

            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));


             autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
        override fun onError(p0: Status) {
            Log.d("dasd","Error")
        }

        override fun onPlaceSelected(place : Place) {
            // TODO: Get info about the selected place.
           addMarker(place)
        }
    });


            toolbar.inflateMenu(R.menu.toolbar);
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()

            nav_view.setNavigationItemSelectedListener(this)
        }

          fun addMarker(p: Place){

             var markerOptions =  MarkerOptions();

            markerOptions.position(p?.getLatLng()!!);
            markerOptions.title(p?.getName().toString());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p?.getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15F));
        }

        override fun onBackPressed() {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.drawer, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            when (item.itemId) {
                R.id.action_settings -> return true
                else -> return super.onOptionsItemSelected(item)
            }
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            // Handle navigation view item clicks here.
            when (item.itemId) {
                R.id.nav_signout -> {
                    val editor = pref.edit()
                    editor.putBoolean("isLogin",false);
                    editor.commit()
                    var goToHome= Intent(this,SplashScreen::class.java)
                    startActivity(goToHome)
                    finish()
                }
                R.id.nav_Trips->{
                    var goToHistory= Intent(this,RideHistory::class.java)
                    startActivity(goToHistory)
                }
                R.id.nav_support->{
                    var intent=Intent(this,SupportActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_refer->{
                    var intent=Intent(this,ReferActivity::class.java)
                    startActivity(intent)
                }

            }

            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }


        override fun onResume() {
            super.onResume()

        }

        override fun onMapReady(googleMap: GoogleMap) {

            mMap = googleMap
            mMap.setOnMarkerDragListener(this)


            mMap.mapType=GoogleMap.MAP_TYPE_TERRAIN
            // Add a marker in Sydney and move the camera
            mLocationRequest = LocationRequest()
            mLocationRequest!!.setInterval(120000) // two minute interval
            mLocationRequest!!.setFastestInterval(120000)
            mLocationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    //Location Permission already granted
                    mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                    mMap.setMyLocationEnabled(true)
                } else {
                    //Request Location Permission
                    checkPermissionForLocation(this)
                }
            } else {
                mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                mMap.setMyLocationEnabled(true)
            }
        }

        var mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val locationList = locationResult.locations
                if (locationList.size > 0) {
                    //The last location in the list is the newest
                    val location = locationList[locationList.size - 1]
                    Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)
                    mLastLocation = location
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker!!.remove()
                    }

                    //Place current location marker
                    val latLng = LatLng(location.latitude, location.longitude)
                    val cameraPosition = CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Current Location
                        .zoom(30f)                   // Sets the zoom
                        .bearing(90f)                // Sets the orientation of the camera to east
                        .tilt(45f)                   // Sets the tilt of the camera to 30 degrees
                        .build()                   // Creates a CameraPosition from the builder
                    val markerOptions = MarkerOptions()
                    markerOptions.position(latLng)
                    markerOptions.draggable(true)
                    markerOptions.title("Current Position")
                    markerOptions.icon(bitmapDescriptorFromVector(applicationContext, R.drawable.ic_marker));
                    mCurrLocationMarker = mMap.addMarker(markerOptions)

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
        }
        var  REQUEST_PERMISSION_LOCATION = 99

        constructor(parcel: Parcel) : this() {
            markerClicked = parcel.readByte() != 0.toByte()
            mPlaceName = parcel.readString()
            mLocationRequest = parcel.readParcelable(LocationRequest::class.java.classLoader)
            mLastLocation = parcel.readParcelable(Location::class.java.classLoader)
            REQUEST_PERMISSION_LOCATION = parcel.readInt()
        }


        fun checkPermissionForLocation(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED){
                    true
                }else{
                    // Show the permission request
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_LOCATION)
                    false
                }
            } else {
                true
            }
        }


        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
        ) {
            when (requestCode) {
                REQUEST_PERMISSION_LOCATION -> {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // location-related task you need to do.
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                            mFusedLocationClient!!.requestLocationUpdates(
                                mLocationRequest,
                                mLocationCallback,
                                Looper.myLooper()
                            )
                            mMap.setMyLocationEnabled(true)
                        }

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                    }
                    return
                }
            }// other 'case' lines to check for other
            // permissions this app might request
        }

        private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
            return ContextCompat.getDrawable(context, vectorResId)?.run {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
                draw(Canvas(bitmap))
                BitmapDescriptorFactory.fromBitmap(bitmap)
            }
        }



        override fun onMarkerDragEnd(marker: Marker?) {
            Toast.makeText(this,"Marker " + marker!!.getId() + " DragEnd",Toast.LENGTH_SHORT).show()

        }

        override fun onMarkerDragStart(marker: Marker?) {
            Toast.makeText(this,"Marker " + marker!!.getId() + " DragEnd",Toast.LENGTH_SHORT).show()
        }

        override fun onMarkerDrag(marker: Marker?) {
            Toast.makeText(this,"Marker " + marker!!.getId() + " DragEnd",Toast.LENGTH_SHORT).show()
        }




    }
