Android CropImage
=================

The `CropImage` activity extracted from `Gallery.apk`. Compatible with Android
2.3 and later versions.




Looking for a New Maintainer
============================

Due to time constraints and the fact that I'm not personally
using this library at the moment, I am looking for people willing to
co-maintain this project.
If interested drop me a line by sending an email to <lorenzo@villani.me>.




Intent-based API
================

The `CropImage` activity is controlled by an Intent-based API. Please use the
wrapper class
[com.android.camera.CropImageIntentBuilder](https://github.com/lvillani/android-cropimage/blob/develop/src/com/android/camera/CropImageIntentBuilder.java)
for a type-safe interface.




Android 4 Notes
===============

If your application targets Android 4 (API Level 14 and later) then it will use
hardware acceleration by default. There's a bug in the CropImage activity which
may trigger a Force Close error when hardware acceleration is enabled.

To work-around this bug you have to disable hardware acceleration for the
`CropImage` activity by adding an entry like this in your `AndroidManifest.xml`:

    <activity
        android:name="com.android.camera.CropImage"
        android:hardwareAccelerated="false"/>


Details
-------

On Android 4.0.0 and later, when hardware acceleration is enabled, the
`CropImage` activity throws an unmanaged exception. The sources were imported
from Android 2.1 and it is possible for some graphics operation to raise
errors on newer platforms.

Stacktrace follows:

    java.lang.UnsupportedOperationException
		at android.view.GLES20Canvas.clipPath(GLES20Canvas.java:413)
		at com.android.camera.HighlightView.draw(HighlightView.java:101)
		at com.android.camera.CropImageView.onDraw(CropImage.java:783)
		at android.view.View.draw(View.java:10978)
		at android.view.View.getDisplayList(View.java:10417)
		at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:2597)
		at android.view.View.getDisplayList(View.java:10380)
		at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:2597)
		at android.view.View.getDisplayList(View.java:10380)
		at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:2597)
		at android.view.View.getDisplayList(View.java:10380)
		at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:2597)
		at android.view.View.getDisplayList(View.java:10380)
		at android.view.ViewGroup.dispatchGetDisplayList(ViewGroup.java:2597)
		at android.view.View.getDisplayList(View.java:10380)
		at android.view.HardwareRenderer$GlRenderer.draw(HardwareRenderer.java:842)
		at android.view.ViewRootImpl.draw(ViewRootImpl.java:1910)
		at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:1634)
		at android.view.ViewRootImpl.handleMessage(ViewRootImpl.java:2442)
		at android.os.Handler.dispatchMessage(Handler.java:99)
		at android.os.Looper.loop(Looper.java:137)
		at android.app.ActivityThread.main(ActivityThread.java:4424)
		at java.lang.reflect.Method.invokeNative(Native Method)
		at java.lang.reflect.Method.invoke(Method.java:511)
		at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:784)
		at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:551)
		at dalvik.system.NativeStart.main(Native Method)





Project Information
===================

 * Home Page: https://github.com/lvillani/android-cropimage
