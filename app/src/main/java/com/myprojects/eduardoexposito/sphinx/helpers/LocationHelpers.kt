package com.myprojects.eduardoexposito.sphinx.helpers

import android.content.Context
import android.location.Location
import com.myprojects.eduardoexposito.sphinx.R
import com.myprojects.eduardoexposito.sphinx.models.GameLocation

fun Location.getDistanceToGameLocation(gameLocation: GameLocation) = distanceTo(gameLocation.getLocation())

private const val DECIMAL_FORMATTER = "%.2f"
private const val METERS_IN_KM = 1000

fun Location.getDistanceToGameLocationInKm(context: Context, gameLocation: GameLocation) = String.format(
    context.getString(R.string.location_distance_km_unit),
    DECIMAL_FORMATTER.format(
        with(getDistanceToGameLocation(gameLocation)) {
            if (this >= METERS_IN_KM) {
                this / METERS_IN_KM
            } else {
                this
            }
        }
    )
)

fun Location.checkGameLocationReached(gameLocation: GameLocation) = getDistanceToGameLocation(gameLocation) <= gameLocation.ratius
