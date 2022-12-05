// import 'package:flutter/foundation.dart';
// import 'package:flutter/services.dart';

// import 'location_android_easy_platform_interface.dart';

// /// An implementation of [LocationAndroidEasyPlatform] that uses method channels.
// class MethodChannelLocationAndroidEasy extends LocationAndroidEasyPlatform {
//   /// The method channel used to interact with the native platform.
//   @visibleForTesting
//   final methodChannel = const MethodChannel('location_android_easy');

//   @override
//   Future<String?> getPlatformVersion() async {
//     final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
//     return version;
//   }
// }


import 'package:flutter/services.dart';
import 'dart:io';
import 'location_android_easy_platform_interface.dart';

class MethodChannelLocationAndroidEasy extends LocationAndroidEasyPlatform {
  MethodChannelLocationAndroidEasy() {
    channelMethod = MethodChannel(methodChannel);
    channelBasicMessage =
        BasicMessageChannel(methodBasicMessage, const StandardMessageCodec());
  }
  ///获取定位信息
  ///[timeout]:获取location超时时间
  ///[useLastKnownLocation]:是否优先将上次的位置信息作为结果返回。如为false，则会获取当前最新值
  @override
  Future<LocationInfo?> getLocation(
      {int timeout = 5, bool useLastKnownLocation = false}) async {
    if (!Platform.isAndroid) return null;
    LocationInfo? location;
    if (useLastKnownLocation) {
      location = await getLastKnownLocation();
      if (location?.latitude != null && location?.longitude != null) {
        return null;
      }
    }
    getLocationListener((LocationInfo locationInfo) {
      location = locationInfo;
    });
    int count = timeout * 5; //一秒检测5次
    while (count-- > 0 && location == null) {
      await Future.delayed(const Duration(milliseconds: 200));
    }
    cancelLocationListener();
    return location;
  }

  ///检测GPS是否打开
  @override
  Future<bool> checkGPS() async {
    return await channelMethod.invokeMethod(methodCheckGPS);
  }

  ///跳转手机设置中打开GPS的位置
  @override
  Future<void> toSettingsTurnOnGPS() async {
    await channelMethod.invokeMethod(methodToSettingsTurnOnGPS);
  }

  ///检测定位权限
  @override
  Future<bool> checkLocationPermission() async {
    return await channelMethod.invokeMethod(methodCheckLocationPermission);
  }

  ///请求定位权限
  @override
  Future<void> requestLocationPermission() async {
    await channelMethod.invokeMethod(methodRequestLocationPermission);
  }

  @override
  Future<LocationInfo?> getLastKnownLocation() async {
    if (!Platform.isAndroid) return null;
    return LocationInfo.fromJson(
        await channelMethod.invokeMethod(methodGetLastKnownLocation));
  }

  /// [locationUpodateInterval]:位置信息更新触发的时间间隔 ms
  /// [locationUpodateDistance]:位置信息更新触发的距离 m
  /// 上面两个条件都符合时才会更新
  @override
  Future<void> getLocationListener(Function(LocationInfo) callback,
      {int? locationUpdateTime, int? locationUpdateDistance}) async {
    if (!Platform.isAndroid) return;
    await channelMethod.invokeMethod(methodRequestLocationUpdatesListener,
        {'minTime': locationUpdateTime??200, 'minDistance': locationUpdateDistance??0});
    channelBasicMessage.setMessageHandler((message) async {
      callback(LocationInfo.fromJson(message));
    });

  }

  @override
  Future<void> cancelLocationListener() async {
    if (!Platform.isAndroid) return;
    await channelMethod.invokeMethod(methodCancelLocationUpdatesListener);
  }
}

///位置信息
class LocationInfo {
  ///经度
  double? longitude;

  ///纬度
  double? latitude;

  ///精度
  double? accuracy;

  ///定位方式：gps/fused
  String? provider;

  LocationInfo.fromJson(json)
      : latitude = json['latitude'],
        longitude = json['latitude'],
        accuracy = json['accuracy'],
        provider = json['provider'];

  @override
  String toString() {
    return 'Locaiton {latitude:$latitude,longitude:$longitude,accuracy:$accuracy,provider:$provider}';
  }
}

