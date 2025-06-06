package com.uken.motovault.api.vindecoderAPI

import android.util.Log
import com.uken.motovault.api.Constants
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class APIConnector {
    fun getInfo(vin: String): Vehicle? {

        val apiBaseUrl = Constants.VIN_DECODER_BASE_URL
        val apiKey = Constants.VIN_DECODER_API_KEY
        val secretKey = Constants.VIN_DECODER_SECRET_KEY
        val action = Constants.VIN_DECODER_ACTION

        var vehicle: Vehicle? = null
        try {
            val controlSum = sha1("$vin|$action|$apiKey|$secretKey").substring(0, 10)
            val requestUrl = "$apiBaseUrl/$apiKey/$controlSum/$action/$vin.json"
            val url = URL(requestUrl)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            val `in` = BufferedReader(InputStreamReader(con.inputStream))

            var inputLine: String?
            val response = StringBuffer()

            while ((`in`.readLine().also { inputLine = it }) != null) {
                response.append(inputLine)
            }
            `in`.close()

            vehicle = Vehicle(response.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        Log.d("APIConnector", "getInfo: $vehicle")
        return vehicle
    }

    companion object {
        @Throws(NoSuchAlgorithmException::class)
        fun sha1(input: String): String {
            val mDigest = MessageDigest.getInstance("SHA1")
            val result = mDigest.digest(input.toByteArray())
            val sb = StringBuffer()
            for (i in result.indices) {
                sb.append(((result[i].toInt() and 0xff) + 0x100).toString(16).substring(1))
            }

            return sb.toString()
        }
    }
}