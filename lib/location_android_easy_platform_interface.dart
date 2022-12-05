// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// import 'location_android_easy_method_channel.dart';

// abstract class LocationAndroidEasyPlatform extends PlatformInterface {
//   /// Constructs a LocationAndroidEasyPlatform.
//   LocationAndroidEasyPlatform() : super(token: _token);

//   static final Object _token = Object();

//   static LocationAndroidEasyPlatform _instance = MethodChannelLocationAndroidEasy();

//   /// The default instance of [LocationAndroidEasyPlatform] to use.
//   ///
//   /// Defaults to [MethodChannelLocationAndroidEasy].
//   static LocationAndroidEasyPlatform get instance => _instance;

//   /// Platform-specific implementations should set this with their own
//   /// platform-specific class that extends [LocationAndroidEasyPlatform] when
//   /// they register themselves.
//   static set instance(LocationAndroidEasyPlatform instance) {
//     PlatformInterface.verifyToken(instance, _token);
//     _instance = instance;
//   }

//   Future<String?> getPlatformVersion() {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }
// }


import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'location_android_easy_method_channel.dart';
import 'package:flutter/services.dart';

abstract class LocationAndroidEasyPlatform extends PlatformInterface {
  LocationAndroidEasyPlatform() : super(token: _token);
  static final Object _token = Object();
  final methodChannel = "methodChannel";
  final methodBasicMessage = "methodBasicMessage";
  final methodGetLastKnownLocation = "methodGetLastKnownLocation";
  final methodRequestLocationUpdatesListener =
      "methodRequestLocationUpdatesListener";
  final methodCancelLocationUpdatesListener =
      "methodCancelLocationUpdatesListener";
  final methodCheckLocationPermission = "methodCheckLocationPermission";
  final methodRequestLocationPermission = "methodRequestLocationPermission";
  final methodCheckGPS = "methodCheckGPS";
  final methodToSettingsTurnOnGPS = "methodToSettingsTurnOnGPS";
  late MethodChannel channelMethod;
  late BasicMessageChannel channelBasicMessage;
  static MethodChannelLocationAndroidEasy _instance = MethodChannelLocationAndroidEasy();

  static MethodChannelLocationAndroidEasy get instance => _instance;

  static set instance(MethodChannelLocationAndroidEasy instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<LocationInfo?> getLocation(
      {int timeout = 5, bool useLastKnownLocation = false}) {
    throw UnimplementedError('getLocation() has not been implemented.');
  }

  Future<bool> checkGPS() {
    throw UnimplementedError('checkGPS() has not been implemented.');
  }

  Future<void> toSettingsTurnOnGPS() {
    throw UnimplementedError('toSettingsTurnOnGPS() has not been implemented.');
  }

  Future<bool> checkLocationPermission() {
    throw UnimplementedError(
        'checkLocationPermission() has not been implemented.');
  }

  Future<void> requestLocationPermission() {
    throw UnimplementedError(
        'requestLocationPermission() has not been implemented.');
  }

  Future<LocationInfo?> getLastKnownLocation() async {
    throw UnimplementedError(
        'getLastKnownLocation() has not been implemented.');
  }

  Future<void> getLocationListener(Function(LocationInfo) callback,
      {int? locationUpdateTime, int? locationUpdateDistance}) {
    throw UnimplementedError('getLocationListener() has not been implemented.');
  }

  Future<void> cancelLocationListener() {
    throw UnimplementedError(
        'cancelLocationListener() has not been implemented.');
  }
}


