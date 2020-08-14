package com.chichkanov.aviasally.routedetails.presentation

import android.animation.TypeEvaluator
import com.google.android.gms.maps.model.LatLng

class RoutePointEvaluator : TypeEvaluator<LatLng> {

    override fun evaluate(t: Float, from: LatLng, to: LatLng): LatLng {
        val lat: Double = from.latitude + t * (to.latitude - from.latitude)
        val lng: Double = from.longitude + t * (to.longitude - from.longitude)
        return LatLng(lat, lng)
    }
}