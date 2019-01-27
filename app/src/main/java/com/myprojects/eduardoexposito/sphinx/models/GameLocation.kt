package com.myprojects.eduardoexposito.sphinx.models

import android.location.Location
import com.myprojects.eduardoexposito.sphinx.R

enum class GameLocation(
    val latitude: Double,
    val longitude: Double,
    val ratius: Int,
    val title: Int,
    val description: Int,
    val color: Int
) {
    TENERIFE(
        28.462064,
        -16.255332,
        50000,
        R.string.location_tenerife_title,
        R.string.location_tenerife_description,
        R.color.location_tenerife
    ),
    BERLIN(
        52.505136,
        13.488561,
        1000,
        R.string.location_berlin_title,
        R.string.location_berlin_description,
        R.color.location_berlin
    ),
    BERLIN_GENDARMENMARKT(
        52.513631,
        13.392466,
        500,
        R.string.location_berlin_gendarmenmarkt_title,
        R.string.location_berlin_gendarmenmarkt_description,
        R.color.location_berlin_gendarmenmarkt
    ),
    BERLIN_CHARLOTTENBURG(
        52.519920,
        13.295888,
        500,
        R.string.location_berlin_charlottenburg_title,
        R.string.location_berlin_charlottenburg_description,
        R.color.location_berlin_charlottenburg
    ),
    BERLIN_ALEXANDERPLATZ(
        52.522232,
        13.412861,
        500,
        R.string.location_berlin_alexanderplatz_title,
        R.string.location_berlin_alexanderplatz_description,
        R.color.location_alexanderplatz
    );

    fun getLocation(): Location {
        val location = Location(name)
        location.latitude = latitude
        location.longitude = longitude
        return location
    }
}
