import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:location_android_easy/location_android_easy_method_channel.dart';

void main() {
  MethodChannelLocationAndroidEasy platform = MethodChannelLocationAndroidEasy();
  const MethodChannel channel = MethodChannel('location_android_easy');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  // test('getPlatformVersion', () async {
  //   expect(await platform.getPlatformVersion(), '42');
  // });
}
