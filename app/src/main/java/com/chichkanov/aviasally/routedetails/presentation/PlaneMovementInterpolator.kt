package com.chichkanov.aviasally.routedetails.presentation

import com.chichkanov.aviasally.core.domain.City
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.*


class PlaneMovementInterpolator(from: City, to: City) {

    private companion object {
        private val WEST_ANGLES = -180..0
        private val EAST_ANGLES = 0..180

        private val NANO_DIST_RANGE = 0..100_000
        private val MICRO_DIST_RANGE = 100_000..200_000
        private val LOW_DIST_RANGE = 200_000..1_000_000
        private val MID_DIST_RANGE = 1_000_000..4_000_000
        private val BIG_DIST_RANGE = 4_000_000..10_000_000
    }

    private val bearingAngle: Int = SphericalUtil.computeHeading(
        LatLng(from.coordinates.latitude, from.coordinates.longitude),
        LatLng(to.coordinates.latitude, to.coordinates.longitude)
    ).toInt()

    private val distanceCurvePow = computeDistanceCurvePow(
        SphericalUtil.computeDistanceBetween(
            LatLng(from.coordinates.latitude, from.coordinates.longitude),
            LatLng(to.coordinates.latitude, to.coordinates.longitude)
        ).toInt()
    )

    // Modified github.com/googlemaps/android-maps-utils
    fun interpolate(fraction: Float, from: LatLng, to: LatLng): LatLng {
        // http://en.wikipedia.org/wiki/Slerp
        val fromLat = Math.toRadians(from.latitude)
        val fromLng = Math.toRadians(from.longitude)
        val toLat = Math.toRadians(to.latitude)
        val toLng = Math.toRadians(to.longitude)
        val cosFromLat = cos(fromLat)
        val cosToLat = cos(toLat)

        // Computes Spherical interpolation coefficients.
        val angle = computeAngleBetween(fromLat, fromLng, toLat, toLng)
        val sinAngle = sin(angle)

        if (sinAngle < 1E-6) {
            return from
        }
        val a = sin((1 - fraction) * angle) / sinAngle
        val b = sin(fraction * angle) / sinAngle

        val xPow: Double = if (bearingAngle in WEST_ANGLES) distanceCurvePow else 1.0
        val yPow: Double = if (bearingAngle in EAST_ANGLES) distanceCurvePow else 1.0

        // Converts from polar to vector and interpolate.
        val x = a.pow(xPow) * cosFromLat * cos(fromLng) + b.pow(1 / xPow) * cosToLat * cos(toLng)
        val y = a.pow(yPow) * cosFromLat * sin(fromLng) + b.pow(1 / yPow) * cosToLat * sin(toLng)
        val z = a * sin(fromLat) + b * sin(toLat)

        // Converts interpolated vector back to polar.
        val lat = atan2(z, sqrt(x * x + y * y))
        val lng = atan2(y, x)
        return LatLng(Math.toDegrees(lat), Math.toDegrees(lng))
    }

    private fun computeAngleBetween(
        fromLat: Double,
        fromLng: Double,
        toLat: Double,
        toLng: Double
    ): Double {
        // Haversine's formula
        val dLat = fromLat - toLat
        val dLng = fromLng - toLng
        return 2 * asin(
            sqrt(
                sin(dLat / 2).pow(2.0) + cos(fromLat) * cos(toLat) * sin(dLng / 2).pow(2.0)
            )
        )
    }

    private fun computeDistanceCurvePow(distanceBetween: Int): Double = when (distanceBetween) {
        in NANO_DIST_RANGE -> 1.0
        in MICRO_DIST_RANGE -> 1.1
        in LOW_DIST_RANGE -> 1.3
        in MID_DIST_RANGE -> 2.0
        in BIG_DIST_RANGE -> 4.0
        else -> 5.0
    }
}