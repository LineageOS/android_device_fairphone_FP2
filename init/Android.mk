LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_C_INCLUDES := \
    system/core/base/include \
    system/core/init \
    external/selinux/libselinux/include

LOCAL_CFLAGS := -Wall
LOCAL_SRC_FILES := init_msm8974.cpp
ifneq ($(TARGET_LIBINIT_MSM8974_DEFINES_FILE),)
  LOCAL_SRC_FILES += ../../../../$(TARGET_LIBINIT_MSM8974_DEFINES_FILE)
endif
LOCAL_MODULE := libinit_msm8974

include $(BUILD_STATIC_LIBRARY)
