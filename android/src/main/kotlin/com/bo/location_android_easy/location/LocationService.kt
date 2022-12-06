
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import PermissionUtil

/**
 * 调用定位方法时，是否检测和请求gps和定位权限？要不做为参数传入？
 * 这样的话，体验更好一些，但是定制化空间变小了
 */

private const val TAG = "LocationService"
class LocationService(private val context: Activity)  {
    private var  locationManager:LocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    private lateinit var basicMessageChannel:BasicMessageChannel<Any>
    private var permissionUtil : PermissionUtil = PermissionUtil(context);
    private val locationListener: LocationListener = object : LocationListener {
        override fun onStatusChanged(provider: String, status: Int, arg2: Bundle) {
            Log.d(TAG, "onStatusChanged: provider:$provider; status:$status; arg2:$arg2")
        }
        override fun onProviderEnabled(provider: String) {
            Log.d(TAG, "onProviderEnabled:  $provider")
        }

        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "onProviderDisabled: $provider")
        }
        override fun onLocationChanged(location: Location) {
            //[fused 22.596306,113.998566 hAcc=40.0 et=+1d20h9m37s549ms {Bundle[mParcelledData.dataSize=148]}]
            Log.d(TAG,location.toString());
            basicMessageChannel.send(mapOf("latitude" to location.latitude, "longitude" to location.longitude,"accuracy" to location.accuracy,"provider" to location.provider))
        }
    }
    private fun getProvider(): String? {
        // val criteria = Criteria()
        // criteria.accuracy = Criteria.ACCURACY_COARSE //低精度，如果设置为高精度，依然获取不了location
        // criteria.isAltitudeRequired = false //不要求海拔
        // criteria.isBearingRequired = false //不要求方位
        // criteria.isCostAllowed = true //允许有花费
        // criteria.powerRequirement = Criteria.POWER_LOW //低功耗
        // //从可用的位置提供器中，匹配以上标准的最佳提供器
        // //public static final String FUSED_PROVIDER = "fused";
        // //public static final String GPS_PROVIDER = "gps";
        // //var provider = GPS_PROVIDER;
        // return locationManager.getBestProvider(criteria, true)
        /**
         *  通过locationManager.getBestProvider(criteria, true)的方式的话，
         * 有时候，貌似是直接就设置未fused方式而不是gps方式了，它又不自动切换到gps
         */
        return "gps"
    }
    fun getLastKnownLocation(): Map<String, Any?> {
        permissionUtil.checkAndRequestPermission()
        val location: Location? = locationManager.getLastKnownLocation(getProvider().toString());
        return mapOf("latitude" to location?.latitude, "longitude" to location?.longitude,"accuracy" to location?.accuracy,"provider" to location?.provider)
    }

    fun requestLocationUpdatesListener(basicMessageChannel: BasicMessageChannel<Any>,minTime:Long,minDistance:Float): Boolean {
        permissionUtil.checkAndRequestPermission()
        //getProvider() gps关闭时返回null
        getProvider() ?: return false
        this.basicMessageChannel = basicMessageChannel
        locationManager.removeUpdates(locationListener)
        // locationManager.requestLocationUpdates(getProvider().toString(), minTime, minDistance, locationListener)
        locationManager.requestLocationUpdates("gps",100,0f, locationListener)
        return true
    }
    fun cancelLocationListener():Boolean{
        locationManager.removeUpdates(locationListener)
        return true
    }

}
