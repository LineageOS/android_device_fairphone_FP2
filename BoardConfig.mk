#
# Copyright (C) 2017 The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

DEVICE_PATH := device/fairphone/FP2

# Binder API version
TARGET_USES_64_BIT_BINDER := true

# Bootloader
TARGET_BOOTLOADER_BOARD_NAME := FP2
TARGET_NO_BOOTLOADER := false
TARGET_NO_RADIOIMAGE := true

# Platform
TARGET_BOARD_PLATFORM := msm8974
TARGET_BOARD_PLATFORM_GPU := qcom-adreno330

# Kernel
BOARD_KERNEL_BASE        := 0x00000000
BOARD_KERNEL_PAGESIZE    := 2048
BOARD_KERNEL_TAGS_OFFSET := 0x01E00000
BOARD_RAMDISK_OFFSET     := 0x02000000
TARGET_KERNEL_ARCH := arm
TARGET_KERNEL_CONFIG := lineageos_FP2_defconfig
TARGET_KERNEL_SOURCE := kernel/fairphone/msm8974
TARGET_KERNEL_CROSS_COMPILE_PREFIX := arm-linux-androideabi-
BOARD_KERNEL_CMDLINE := androidboot.hardware=qcom user_debug=31 msm_rtb.filter=0x3b7 ehci-hcd.park=3 androidboot.bootdevice=msm_sdcc.1
BOARD_KERNEL_CMDLINE += androidboot.selinux=permissive
BOARD_KERNEL_SEPARATED_DT := true
BOARD_KERNEL_IMAGE_NAME := zImage

# Architecture
TARGET_ARCH := arm
TARGET_ARCH_VARIANT := armv7-a-neon
TARGET_CPU_ABI  := armeabi-v7a
TARGET_CPU_ABI2 := armeabi
TARGET_CPU_VARIANT := krait

# Filesystem
BOARD_BOOTIMAGE_PARTITION_SIZE := 0x01000000
BOARD_CACHEIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_CACHEIMAGE_FILE_SYSTEM_TYPE := f2fs
BOARD_CACHEIMAGE_PARTITION_SIZE := 671088640
BOARD_FLASH_BLOCK_SIZE := 131072 # (BOARD_KERNEL_PAGESIZE * 64)
BOARD_PERSISTIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_PERSISTIMAGE_PARTITION_SIZE := 5242880
BOARD_RECOVERYIMAGE_PARTITION_SIZE := 0x01000000
BOARD_SYSTEMIMAGE_PARTITION_SIZE := 2147483648
BOARD_TOMBSTONESIMAGE_PARTITION_SIZE := 73400320
BOARD_USERDATAIMAGE_PARTITION_SIZE := 27358941675
TARGET_USERIMAGES_USE_EXT4 := true
TARGET_USERIMAGES_USE_F2FS := true

# Recovery
TARGET_RECOVERY_FSTAB := $(DEVICE_PATH)/root/fstab.qcom

# Added to indicate that protobuf-c is supported in this build
PROTOBUF_SUPPORTED := true

# Lights
TARGET_PROVIDES_LIBLIGHT := true

# QCOM hardware
BOARD_USES_QCOM_HARDWARE := true

# Audio
AUDIO_FEATURE_ENABLED_COMPRESS_VOIP := false
AUDIO_FEATURE_ENABLED_HWDEP_CAL := true
AUDIO_FEATURE_ENABLED_LOW_LATENCY_CAPTURE := true
AUDIO_FEATURE_ENABLED_MULTI_VOICE_SESSIONS := true
AUDIO_FEATURE_ENABLED_NEW_SAMPLE_RATE := true
AUDIO_FEATURE_LOW_LATENCY_PRIMARY := true
BOARD_USES_ALSA_AUDIO := true
USE_CUSTOM_AUDIO_POLICY := 1

# Camera
BOARD_GLOBAL_CFLAGS += -DCAMERA_VENDOR_L_COMPAT
TARGET_HAS_LEGACY_CAMERA_HAL1 := true
TARGET_USE_COMPAT_GRALLOC_ALIGN := true
USE_DEVICE_SPECIFIC_CAMERA := true

# Graphics
MAX_EGL_CACHE_KEY_SIZE := 12*1024
MAX_EGL_CACHE_SIZE := 2048*1024
NUM_FRAMEBUFFER_SURFACE_BUFFERS := 3
OVERRIDE_RS_DRIVER := libRSDriver_adreno.so
TARGET_USES_ION := true
TARGET_USES_NEW_ION_API := true
USE_OPENGL_RENDERER := true

# Encryption
TARGET_HW_DISK_ENCRYPTION := false

# Bluetooth
BOARD_HAVE_BLUETOOTH := true
BOARD_HAVE_BLUETOOTH_QCOM := true
BLUETOOTH_HCI_USE_MCT := true
BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := $(DEVICE_PATH)/bluetooth
QCOM_BT_USE_SMD_TTY := true
FEATURE_QCRIL_UIM_SAP_SERVER_MODE := true

# ANT+
BOARD_ANT_WIRELESS_DEVICE := "vfs-prerelease"

# Wifi
BOARD_HAS_QCOM_WLAN              := true
BOARD_HAS_QCOM_WLAN_SDK          := true
BOARD_WLAN_DEVICE                := qcwcn
BOARD_WPA_SUPPLICANT_DRIVER      := NL80211
BOARD_WPA_SUPPLICANT_PRIVATE_LIB := lib_driver_cmd_qcwcn
BOARD_HOSTAPD_DRIVER             := NL80211
BOARD_HOSTAPD_PRIVATE_LIB        := lib_driver_cmd_qcwcn
PRODUCT_VENDOR_MOVE_ENABLED      := true
WIFI_DRIVER_FW_PATH_STA          := "sta"
WIFI_DRIVER_FW_PATH_AP           := "ap"
WPA_SUPPLICANT_VERSION           := VER_0_8_X

TARGET_USES_QCOM_WCNSS_QMI := true

# Radio
TARGET_RIL_VARIANT := caf
BOARD_RIL_CLASS := ../../../device/fairphone/FP2/ril/

# GPS
BOARD_VENDOR_QCOM_LOC_PDK_FEATURE_SET := true
USE_DEVICE_SPECIFIC_GPS := true
USE_DEVICE_SPECIFIC_LOC_API := true
TARGET_NO_RPC := true

# SELinux
include device/qcom/sepolicy/sepolicy.mk
BOARD_SEPOLICY_DIRS += \
    $(DEVICE_PATH)/sepolicy

# Lineage Hardware
BOARD_HARDWARE_CLASS += \
    hardware/lineage/lineagehw
BACKLIGHT_PATH=/sys/class/leds/lcd-backlight/brightness

# QCOM Power
TARGET_HAS_LEGACY_POWER_STATS := true
TARGET_HAS_NO_WIFI_STATS := true
TARGET_USES_INTERACTION_BOOST := true

# Filesystem
TARGET_FS_CONFIG_GEN := $(DEVICE_PATH)/config.fs

# FM
AUDIO_FEATURE_ENABLED_FM_POWER_OPT := true
BOARD_HAVE_QCOM_FM := true
TARGET_FM_LEGACY_PATCHLOADER := true

# HIDL
DEVICE_MANIFEST_FILE := $(DEVICE_PATH)/manifest.xml

# Init
TARGET_INIT_VENDOR_LIB := libinit_msm8974
TARGET_RECOVERY_DEVICE_MODULES := libinit_msm8974

# Malloc
MALLOC_SVELTE := true

# TWRP
ifeq ($(WITH_TWRP),true)
TARGET_RECOVERY_DEVICE_DIRS += $(DEVICE_PATH)/twrp
TARGET_RECOVERY_QCOM_RTC_FIX := true
TARGET_USE_CUSTOM_LUN_FILE_PATH := "/sys/devices/msm_dwc3/f9200000.dwc3/gadget/lun%d/file"
TW_INCLUDE_CRYPTO := true
TW_MTP_DEVICE := "/dev/mtp_usb"
TW_NO_LEGACY_PROPS := true
TW_THEME := portrait_hdpi
endif

-include vendor/fairphone/FP2/BoardConfigVendor.mk
