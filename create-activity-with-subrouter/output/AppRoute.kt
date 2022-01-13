package com.c51.core.navigation.router

import com.c51.feature.settings.CommunicationPreferenceActivity
import com.c51.feature.settings.MyPrefActivity


class AppSubRoute : ISubRoute {
    override fun registerSubRoute(map: HashMap<String, RouteMeta>) {
        // INSERT1
		map[ROUTE_MY_PREF] = RouteMeta(
			ROUTE_MY_PREF,
			MyPrefActivity::class.java
		)

        map[ROUTE_COMMUNICATION_PREFERENCE] = RouteMeta(
            ROUTE_COMMUNICATION_PREFERENCE,
            CommunicationPreferenceActivity::class.java
        )
    }
}

const val ROUTE_MY_PREF = "preference/mime"
const val ROUTE_COMMUNICATION_PREFERENCE = "preferences/notifications"

