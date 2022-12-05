
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat

private const val TAG = "LocationService"
private const val REQUEST_CODE = 7

class PermissionUtil constructor(private val context:Activity) {
    private fun checkAndTurnOnGPS() {
        if (!checkGPS()) {
            turnOnGPS();
        }
    }

    fun checkAndRequestPermission() {
        checkAndTurnOnGPS()
        if (!checkLocationPermission()) {
            requestLocationPermission()
        }
    }

    fun checkGPS(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isTurnOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.d(TAG, "is GPS turn on : $isTurnOn")
        return isTurnOn
    }

    fun turnOnGPS() {
        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(settingsIntent)
    }

    fun requestLocationPermission(): Boolean {
        Log.d(TAG, "request location permission")
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                 context,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
        }
        return true
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                 context ,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "location permission is denied")
            return false
        }
        Log.d(TAG, "location permission is allowed")
        return true
    }
}