LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
# Module name should match apk name to be installed
 
LOCAL_MODULE := TBProvider
 
LOCAL_MODULE_TAGS := optional 

LOCAL_SRC_FILES := $(LOCAL_MODULE).apk
 
LOCAL_MODULE_CLASS := APPS
 
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)

LOCAL_CERTIFICATE := platform

LOCAL_PRIVILEGED_MODULE := true

LOCAL_DEX_PREOPT := false
 
include $(BUILD_PREBUILT)

