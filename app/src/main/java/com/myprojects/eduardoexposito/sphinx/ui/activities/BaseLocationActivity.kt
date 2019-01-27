package com.myprojects.eduardoexposito.sphinx.ui.activities

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.myprojects.eduardoexposito.sphinx.R
import com.myprojects.eduardoexposito.sphinx.helpers.createInfoDialog
import com.myprojects.eduardoexposito.sphinx.helpers.observeWithUnsubscription
import com.myprojects.eduardoexposito.sphinx.ui.viewmodels.ConnectivityViewModel

abstract class BaseLocationActivity :
    AppCompatActivity(),
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    companion object {
        private const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
        private const val UPDATE_INTERVAL = 5000L
        private const val FASTEST_INTERVAL = 5000L // = 5 seconds
        // integer for permissions results request
        private const val ALL_LOCATION_PERMISSIONS_RESULT = 1011
    }

    private lateinit var googleApiClient: GoogleApiClient
    // lists for permissions
    private val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var connectivityViewModel: ConnectivityViewModel
    private val locationCallback = CustomLocationCallback()
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityViewModel = ViewModelProviders.of(this).get(ConnectivityViewModel::class.java)

        connectivityViewModel.connectivityLiveData.observeWithUnsubscription(this) { onConnectivityChanged(it) }

        requestPermissions(
            permissionsToRequest,
            ALL_LOCATION_PERMISSIONS_RESULT
        )

        // we build google api client
        googleApiClient = initGoogleApiClient()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getPermissionsToRequest(wantedPermissions: Array<String>) = wantedPermissions
        .filter { !hasPermission(it) }

    private fun hasPermission(permission: String) = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun onStart() {
        super.onStart()

        connectGoogleApiClient()
    }

    override fun onResume() {
        super.onResume()

        if (!checkPlayServices()) {
            showDialogToInstallGooglePlayServices()
        }
    }

    override fun onPause() {
        super.onPause()

        disconnectGoogleApiClient()
    }

    private fun checkPlayServices() = GoogleApiAvailability.getInstance().let { apiAvailability ->
        apiAvailability.isGooglePlayServicesAvailable(this).let { resultCode ->
            if (resultCode == ConnectionResult.SUCCESS) {
                true
            } else {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog(
                        this, resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST
                    )
                } else {
                    finish()
                }

                false
            }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        if (hasLocationPermissions()) {
            try {
                // Permissions ok, we get last location
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    onLocationChanged(it)
                }
                startLocationUpdates()
            } catch (e: SecurityException) {
                showDialogUnexpectedPermissionsException()
            }
        }
    }

    private fun startLocationUpdates() {
        if (!hasLocationPermissions()) {
            showDialogToAskForLocationPermissions()
        } else {
            try {
                fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, null)
            } catch (e: SecurityException) {
                showDialogUnexpectedPermissionsException()
            }
        }
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onLocationChanged(location: Location?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ALL_LOCATION_PERMISSIONS_RESULT -> {
                with(getPermissionsToRequest(permissionsToRequest)) {
                    if (isNotEmpty()) {
                        if (shouldShowRequestPermissionRationale(get(0))) {
                            showDialogToRationallyAskForLocationPermissions {
                                requestPermissions(
                                    toTypedArray(),
                                    ALL_LOCATION_PERMISSIONS_RESULT
                                )
                            }
                        }
                    } else {
                        connectGoogleApiClient()
                    }
                }
            }
        }
    }

    //==========================================================================================
    // Helper methods
    //==========================================================================================
    private fun createLocationRequest() = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = UPDATE_INTERVAL
        fastestInterval = FASTEST_INTERVAL
    }

    private fun initGoogleApiClient() = GoogleApiClient.Builder(this)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build()

    private fun connectGoogleApiClient() {
        googleApiClient = initGoogleApiClient().apply { connect() }
    }

    private fun disconnectGoogleApiClient() {
        if (::googleApiClient.isInitialized && googleApiClient.isConnected) {
            // stop location updates
            fusedLocationClient.removeLocationUpdates(locationCallback)
            googleApiClient.disconnect()
        }
    }

    private fun hasLocationPermissions() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun onConnectivityChanged(isConnected: Boolean) {
        if (this.isConnected != isConnected) {
            if (isConnected) {
                connectGoogleApiClient()
            } else {
                disconnectGoogleApiClient()
                showDialogNoConnectivity()
            }
            this.isConnected = isConnected
        }
    }

    //================================================================================
    // Dialogs
    //================================================================================
    private fun showDialogToInstallGooglePlayServices() {
        createInfoDialog(
            this,
            R.string.dialog_install_google_play_services_title,
            R.string.dialog_install_google_play_services_message
        ).show()
    }

    private fun showDialogToAskForLocationPermissions() {
        createInfoDialog(
            this,
            R.string.dialog_enable_location_permissions_title,
            R.string.dialog_enable_location_permissions_message
        ).show()
    }

    private fun showDialogToRationallyAskForLocationPermissions(positiveAction: () -> Unit) {
        createInfoDialog(
            context = this,
            titleId = R.string.dialog_permissions_request_allowance_title,
            messageId = R.string.dialog_location_permissions_request_allowance_message,
            positiveAction = positiveAction
        ).show()
    }

    private fun showDialogUnexpectedPermissionsException() {
        createInfoDialog(
            context = this,
            titleId = R.string.dialog_unexpected_error_title,
            messageId = R.string.dialog_unexpected_error_message
        ).show()
    }

    private fun showDialogNoConnectivity() {
        createInfoDialog(
            context = this,
            titleId = R.string.dialog_no_connectivity_title,
            messageId = R.string.dialog_no_connectivity_message,
            positiveAction = { onLocationChanged(null) }
        ).show()
    }

    //================================================================================
    // Classes
    //================================================================================
    inner class CustomLocationCallback : LocationCallback() {
        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            locationAvailability!!.isLocationAvailable
        }

        override fun onLocationResult(locationResult: LocationResult?) {
            // locationResult?.locations
            onLocationChanged(locationResult?.lastLocation)
        }
    }
}
