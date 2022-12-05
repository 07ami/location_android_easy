
import 'location_android_easy_method_channel.dart';
import 'location_android_easy_platform_interface.dart';

class LocationAndroidEasy {
  static final LocationAndroidEasy instance = LocationAndroidEasy._internal();
  LocationAndroidEasy._internal();
  final LocationAndroidEasyPlatform _platform = LocationAndroidEasyPlatform.instance;


  Future<LocationInfo?> getLocation({int timeout = 5, bool useLastKnownLocation = false}) async {
    return await _platform.getLocation(timeout: timeout, useLastKnownLocation: useLastKnownLocation);
  }

  Future<bool> checkGPS() async {
    return await _platform.checkGPS();
  }

  Future<void> toSettingsTurnOnGPS() async {
    await _platform.toSettingsTurnOnGPS();
  }

  Future<bool> checkLocationPermission() async {
    return await _platform.checkLocationPermission();
  }

  Future<void> requestLocationPermission() async {
    await _platform.requestLocationPermission();
  }

  Future<LocationInfo?> getLastKnownLocation() async {
    return await _platform.getLastKnownLocation();
  }

  Future<void> getLocationListener(Function(LocationInfo) callback,
      {int? locationUpdateTime, int? locationUpdateDistance}) async {
    await _platform.getLocationListener(callback,
        locationUpdateTime: locationUpdateTime, locationUpdateDistance: locationUpdateDistance);
  }

  Future<void> cancelLocationListener() async {
    await _platform.cancelLocationListener();
  }
}
