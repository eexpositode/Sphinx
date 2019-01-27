package com.myprojects.eduardoexposito.sphinx.ui.activities

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import com.myprojects.eduardoexposito.sphinx.R
import com.myprojects.eduardoexposito.sphinx.helpers.checkGameLocationReached
import com.myprojects.eduardoexposito.sphinx.helpers.getDistanceToGameLocationInKm
import com.myprojects.eduardoexposito.sphinx.models.GameLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_test_state_renderer.*

class PlaygroundActivity : BaseLocationActivity() {
    //TODO Save last machine state (in the StateMachineManager)
    //TODO Restore last machine state (in the StateMachineManager)

    //=====================================================================
    //=====================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPlaygroundGameContainer.setUp()
    }

    //=====================================================================
    //=====================================================================
    override fun onLocationChanged(location: Location?) {
        updateLocationDisplay(location)
        updateDistances(location)
        updateReachability(location)
    }

    private fun updateLocationDisplay(location: Location?) {
        //TODO ACTION ON LOCATION CHANGED
        if (location == null) {
            Log.w("LOGGING_APP", "LOCATION NULL")
            locationLongitudeValue.text = ""
            locationLatitudeValue.text = ""
        } else {
            Log.w("LOGGING_APP", "LOCATION: ${location.longitude}, ${location.latitude}")
            locationLongitudeValue.text = "${location.longitude}"
            locationLatitudeValue.text = "${location.latitude}"
        }
    }

    private fun updateDistances(location: Location?) {
        with(location) {
            locationDistanceToTenerife.text = getFormattedDistanceToGameLocation(
                this,
                GameLocation.TENERIFE
            )
            locationDistanceToBerlin.text = getFormattedDistanceToGameLocation(
                this,
                GameLocation.BERLIN
            )
            locationDistanceToBerlinGendarmenmarkt.text = getFormattedDistanceToGameLocation(
                this,
                GameLocation.BERLIN_GENDARMENMARKT
            )
            locationDistanceToBerlinCharlottenburg.text = getFormattedDistanceToGameLocation(
                this,
                GameLocation.BERLIN_CHARLOTTENBURG
            )
            locationDistanceToBerlinAlexanderplatz.text = getFormattedDistanceToGameLocation(
                this,
                GameLocation.BERLIN_ALEXANDERPLATZ
            )
        }
    }

    private fun getFormattedDistanceToGameLocation(location: Location?, gameLocation: GameLocation) =
        if (location == null) {
            getString(R.string.location_distance_by_null_location)
        } else {
            String.format(
                getString(R.string.location_distance_to_template),
                location.getDistanceToGameLocationInKm(this, gameLocation)
            )
        }

    private fun updateReachability(location: Location?) {
        location?.let {
            locationTenerifeReached.setVisibility(it.checkGameLocationReached(GameLocation.TENERIFE))
            locationBerlinReached.setVisibility(it.checkGameLocationReached(GameLocation.BERLIN))
            locationBerlinGendarmenmarktReached.setVisibility(it.checkGameLocationReached(GameLocation.BERLIN_GENDARMENMARKT))
            locationBerlinCharlottenburgReached.setVisibility(it.checkGameLocationReached(GameLocation.BERLIN_CHARLOTTENBURG))
            locationBerlinAlexanderplatzReached.setVisibility(it.checkGameLocationReached(GameLocation.BERLIN_ALEXANDERPLATZ))
        }
    }

    private fun View.setVisibility(isVisible: Boolean) = apply {
        visibility = (if (isVisible) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        })
    }
}
