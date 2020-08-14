package com.chichkanov.aviasally.core.data

import com.chichkanov.aviasally.core.domain.BaseTransformer
import com.chichkanov.aviasally.core.domain.Coordinates
import javax.inject.Inject

class CoordinatesResponseTransformer @Inject constructor() :
    BaseTransformer<CoordinatesResponse, Coordinates> {

    override fun transform(data: CoordinatesResponse): Coordinates = Coordinates(
        data.latitude,
        data.longitude
    )
}