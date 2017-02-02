LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
    QCamera2Factory.cpp \
    QCamera2Hal.cpp \
    QCamera2HWI.cpp \
    QCameraMem.cpp \
    ../util/QCameraQueue.cpp \
    ../util/QCameraCmdThread.cpp \
    QCameraStateMachine.cpp \
    QCameraChannel.cpp \
    QCameraStream.cpp \
    QCameraPostProc.cpp \
    QCamera2HWICallbacks.cpp \
    QCameraParameters.cpp \
    QCameraThermalAdapter.cpp \
    wrapper/QualcommCamera.cpp

LOCAL_CFLAGS += -Wall -Wextra -Werror

LOCAL_CFLAGS += -DHAS_MULTIMEDIA_HINTS

LOCAL_CFLAGS += -DDEFAULT_DENOISE_MODE_ON

#use media extension
ifeq ($(TARGET_USES_MEDIA_EXTENSIONS), true)
LOCAL_CFLAGS += -DUSE_MEDIA_EXTENSIONS
endif

# Debug logs are disabled
LOCAL_CFLAGS += -DDISABLE_DEBUG_LOG

#ifeq ($(TARGET_USES_AOSP),true)
#LOCAL_CFLAGS += -DVANILLA_HAL
#endif

LOCAL_C_INCLUDES := \
    $(LOCAL_PATH)/../stack/common \
    frameworks/native/include/media/openmax \
    $(call project-path-for,qcom-display)/libgralloc \
    $(call project-path-for,qcom-media)/libstagefrighthw \
    $(LOCAL_PATH)/../../mm-image-codec/qexif \
    $(LOCAL_PATH)/../../mm-image-codec/qomx_core \
    $(LOCAL_PATH)/../util \
    $(LOCAL_PATH)/wrapper \
    system/media/camera/include

LOCAL_C_INCLUDES += $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr/include
LOCAL_C_INCLUDES += $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr/include/media
LOCAL_ADDITIONAL_DEPENDENCIES := $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ/usr

LOCAL_SHARED_LIBRARIES := libcamera_client liblog libhardware libutils libcutils libdl libgui libsensor
LOCAL_SHARED_LIBRARIES += libmmcamera_interface libmmjpeg_interface
LOCAL_SHARED_LIBRARIES += android.hidl.token@1.0-utils android.hardware.graphics.bufferqueue@1.0
LOCAL_STATIC_LIBRARIES := libarect
LOCAL_HEADER_LIBRARIES := libnativebase_headers

LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/hw
LOCAL_MODULE := camera.$(TARGET_BOARD_PLATFORM)
LOCAL_MODULE_TAGS := optional
LOCAL_VENDOR_MODULE := true

include $(BUILD_SHARED_LIBRARY)
