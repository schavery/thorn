LOCAL_PATH := $(call my-dir)

#declare the prebuilt library

include $(CLEAR_VARS)
#ffmpeg-prebuilt
LOCAL_MODULE := ffmpeg

LOCAL_SRC_FILES := android_ffmpeg/libffmpeg.so

LOCAL_EXPORT_C_INCLUDES := android_ffmpeg/include

LOCAL_EXPORT_LDLIBS := android_ffmpeg/libffmpeg.so

LOCAL_PRELINK_MODULE := true

include $(PREBUILT_SHARED_LIBRARY)

#the ffmpeg-jni library

include $(CLEAR_VARS)

LOCAL_ALLOW_UNDEFINED_SYMBOLS=false

LOCAL_MODULE := ffmpeg-jni

LOCAL_SRC_FILES := ffmpeg-jni.c

LOCAL_C_INCLUDES := $(LOCAL_PATH)/android_ffmpeg/include

#LOCAL_SHARED_LIBRARY := ffmpeg-prebuilt
LOCAL_SHARED_LIBRARY := ffmpeg

LOCAL_LDLIBS := -llog -ljnigraphics -lz -lm $(LOCAL_PATH)/android_ffmpeg/libffmpeg.so

include $(BUILD_SHARED_LIBRARY)
