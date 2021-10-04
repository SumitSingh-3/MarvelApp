package com.marvelapp.utils

import com.marvelapp.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


class Constants {

    companion object {
        val ts = Calendar.getInstance().time.time.toString()

        const val  API_KEY = BuildConfig.MARVEL_PUBLIC_KEY
        const val  PRIVATE_KEY = BuildConfig.MARVEL_PRIVATE_KEY

        fun hash() : String{
            val input = "$ts$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

    }

}