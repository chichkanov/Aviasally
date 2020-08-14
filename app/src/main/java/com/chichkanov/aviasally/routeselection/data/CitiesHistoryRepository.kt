package com.chichkanov.aviasally.routeselection.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.chichkanov.aviasally.core.domain.City
import com.squareup.moshi.Moshi
import javax.inject.Inject

class CitiesHistoryRepository @Inject constructor(
    private val prefs: SharedPreferences,
    private val moshi: Moshi
) {
    private companion object {
        private const val KEY_FROM_CITY = "key_from_city"
        private const val KEY_TO_CITY = "key_to_city"
    }

    var lastFromCity: City?
        get() = prefs.getString(KEY_FROM_CITY, null)?.let {
            moshi.adapter(City::class.java).fromJson(it)
        }
        set(value) {
            prefs.edit {
                putString(
                    KEY_FROM_CITY,
                    moshi.adapter(City::class.java).toJson(value)
                )
            }
        }

    var lastToCity: City?
        get() = prefs.getString(KEY_TO_CITY, null)?.let {
            moshi.adapter(City::class.java).fromJson(it)
        }
        set(value) {
            prefs.edit {
                putString(
                    KEY_TO_CITY,
                    moshi.adapter(City::class.java).toJson(value)
                )
            }
        }
}