package com.bo.location_android_easy

import LocationService
import PermissionUtil
import android.util.Log
import android.app.Activity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.StandardMessageCodec
private const val  TAG = "LocationService"
private const val  CHANNEL_METHOD = "methodChannel"
private const val  CHANNEL_BASIC_MESSAGE = "methodBasicMessage"
private const val  Method_GetLastKnownLocation = "methodGetLastKnownLocation"
private const val  Method_RequestLocationUpdatesListener = "methodRequestLocationUpdatesListener"
private const val  Method_CancelLocationUpdatesListener = "methodCancelLocationUpdatesListener"
private const val  METHOD_CHECK_LOCATION_PERMISSION = "methodCheckLocationPermission"
private const val  METHOD_REQUEST_LOCATION_PERMISSION = "methodRequestLocationPermission"
private const val  METHOD_CHECK_GPS = "methodCheckGPS"
private const val  METHOD_TO_SETTINGS_TURN_ON_GPS = "methodToSettingsTurnOnGPS"
/** LocationAndroidEasyPlugin */
class LocationAndroidEasyPlugin: FlutterPlugin, ActivityAware, MethodCallHandler {
  private lateinit var methodChannel : MethodChannel
  private lateinit var basicMessageChannel: BasicMessageChannel<Any>
  private lateinit var locationService: LocationService
  private lateinit var permissionUtil: PermissionUtil

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    methodChannel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_METHOD)
    methodChannel.setMethodCallHandler(this)
    basicMessageChannel = BasicMessageChannel(flutterPluginBinding.binaryMessenger, CHANNEL_BASIC_MESSAGE, StandardMessageCodec())
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when(call.method) {
      Method_GetLastKnownLocation -> result.success(locationService.getLastKnownLocation())
      Method_RequestLocationUpdatesListener -> result.success(locationService.requestLocationUpdatesListener(basicMessageChannel, call.argument<Long>("minTime")!!, call.argument<Float>("minDistance")!!))
      Method_CancelLocationUpdatesListener -> result.success(locationService.cancelLocationListener())
      METHOD_CHECK_LOCATION_PERMISSION -> result.success(permissionUtil.checkLocationPermission())
      METHOD_REQUEST_LOCATION_PERMISSION -> result.success(permissionUtil.requestLocationPermission())
      METHOD_CHECK_GPS -> result.success(permissionUtil.checkGPS())
      METHOD_TO_SETTINGS_TURN_ON_GPS -> permissionUtil.turnOnGPS()
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    methodChannel.setMethodCallHandler(null)
  }

  ///ActivityAware
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    permissionUtil = PermissionUtil( binding.activity)
    locationService = LocationService( binding.activity)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    TODO("Not yet implemented")
  }

  override fun onDetachedFromActivity() {
    TODO("Not yet implemented")
  }
  ///ActivityAware ---
}

