package com.chichkanov.aviasally.routedetails.presentation

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chichkanov.aviasally.R
import com.chichkanov.aviasally.core.ext.createViewModel
import com.chichkanov.aviasally.core.ext.getColorCompat
import com.chichkanov.aviasally.core.ext.getDrawableCompat
import com.chichkanov.aviasally.core.presentation.BaseFragment
import com.chichkanov.aviasally.di.DI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_route_details.*


class RouteDetailsFragment : BaseFragment<RouteDetailsViewModel>() {

    private companion object {
        private const val ROUTE_PATH_STEPS_COUNT = 100
        private const val CAMERA_PADDING_BOUNDS = 200
        private const val PATH_GAP_SIZE = 10f
        private const val PLANE_ANIMATION_DURATION = 8000L
        private const val MARKER_TEXT_SIZE = 38f
    }

    private lateinit var planeMovementInterpolator: PlaneMovementInterpolator
    private val markerTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = MARKER_TEXT_SIZE
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val args: RouteDetailsFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_route_details

    override fun provideViewModel(): RouteDetailsViewModel = createViewModel {
        DI.routeDetailsComponent.get().routeDetailsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planeMovementInterpolator = PlaneMovementInterpolator(args.fromCity, args.toCity)

        edgeToEdge {
            backBtn.fit { Edge.Top + Edge.Bottom + Edge.Left + Edge.Right }
        }

        backBtn.setOnClickListener { findNavController().popBackStack() }

        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            onGoogleMapReady(it)
        }
    }

    private fun onGoogleMapReady(googleMap: GoogleMap) {
        val markerFrom = googleMap.addMarker(
            getMarkerOptions(
                R.drawable.bg_city_marker,
                LatLng(
                    args.fromCity.coordinates.latitude,
                    args.fromCity.coordinates.longitude
                ),
                args.fromCity.iata
            )
        )

        val markerTo = googleMap.addMarker(
            getMarkerOptions(
                R.drawable.bg_city_marker,
                LatLng(
                    args.toCity.coordinates.latitude,
                    args.toCity.coordinates.longitude
                ),
                args.toCity.iata
            )
        )

        val markerPlane = googleMap.addMarker(
            getMarkerOptions(
                R.drawable.ic_plane,
                LatLng(
                    args.fromCity.coordinates.latitude,
                    args.fromCity.coordinates.longitude
                )
            ).flat(true)
        )

        setupCamera(googleMap, markerFrom, markerTo)
        setupCurvedPolyline(googleMap, markerFrom, markerTo, planeMovementInterpolator)
        animatePlane(markerPlane, markerTo, planeMovementInterpolator)
    }

    private fun setupCurvedPolyline(
        googleMap: GoogleMap,
        markerFrom: Marker,
        markerTo: Marker,
        interpolator: PlaneMovementInterpolator
    ) {

        val routePoints = mutableListOf<LatLng>()
        for (i in 0..ROUTE_PATH_STEPS_COUNT) {
            routePoints.add(
                interpolator.interpolate(
                    i / ROUTE_PATH_STEPS_COUNT.toFloat(),
                    markerFrom.position,
                    markerTo.position
                )
            )
        }

        googleMap.addPolyline(
            PolylineOptions()
                .addAll(routePoints)
                .jointType(JointType.ROUND)
                .width(PATH_GAP_SIZE)
                .color(requireContext().getColorCompat(R.color.n3))
                .pattern(listOf(Dot(), Gap(PATH_GAP_SIZE)))
        )
    }

    private fun setupCamera(googleMap: GoogleMap, markerFrom: Marker, markerTo: Marker) {
        val builder = LatLngBounds.Builder()
        builder.include(markerFrom.position)
        builder.include(markerTo.position)
        val bounds = builder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, CAMERA_PADDING_BOUNDS)
        googleMap.moveCamera(cameraUpdate)
    }

    private fun animatePlane(
        markerFrom: Marker,
        markerTo: Marker,
        interpolator: PlaneMovementInterpolator
    ) {
        val typeEvaluator: TypeEvaluator<LatLng> = TypeEvaluator { fraction, startValue, endValue ->
            interpolator.interpolate(
                fraction,
                startValue,
                endValue
            )
        }

        var latestPlaneLocation = LatLng(
            args.fromCity.coordinates.latitude,
            args.fromCity.coordinates.longitude
        )

        ValueAnimator.ofObject(
            typeEvaluator,
            markerFrom.position,
            markerTo.position
        ).apply {
            duration = PLANE_ANIMATION_DURATION
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as LatLng
                markerFrom.position = animatedValue
                markerFrom.rotation = SphericalUtil.computeHeading(
                    latestPlaneLocation,
                    animatedValue
                ).toFloat()
                latestPlaneLocation = animatedValue
            }
            start()
        }
    }

    private fun getMarkerOptions(
        @DrawableRes iconRes: Int,
        position: LatLng,
        text: String? = null
    ) = MarkerOptions()
        .position(
            position
        )
        .icon(getMarkerIconFromDrawable(requireContext().getDrawableCompat(iconRes), text))

    private fun getMarkerIconFromDrawable(
        drawable: Drawable,
        text: String? = null
    ): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        text?.let {
            canvas.drawText(
                it,
                bitmap.width / 2f,
                bitmap.height / 1.5f,
                markerTextPaint
            )
        }
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}