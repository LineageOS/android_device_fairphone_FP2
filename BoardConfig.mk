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
TARGET_KERNEL_CONFIG := fairphone-perf_defconfig
TARGET_KERNEL_SOURCE := kernel/fairphone/msm8974
TARGET_KERNEL_CROSS_COMPILE_PREFIX := arm-linux-androideabi-
ifneq (,$(filter userdebug eng,$(TARGET_BUILD_VARIANT)))
BOARD_KERNEL_CMDLINE := console=ttyHSL0,115200,n8 androidboot.hardware=qcom user_debug=31 msm_rtb.filter=0x3b7 ehci-hcd.park=3 androidboot.bootdevice=msm_sdcc.1 
else
BOARD_KERNEL_CMDLINE := androidboot.hardware=qcom user_debug=31 msm_rtb.filter=0x3b7 ehci-hcd.park=3 androidboot.bootdevice=msm_sdcc.1
endif
BOARD_KERNEL_SEPARATED_DT := true

# Architecture
ARCH_ARM_HAVE_TLS_REGISTER := true
TARGET_ARCH := arm
TARGET_ARCH_VARIANT := armv7-a-neon
TARGET_CPU_ABI  := armeabi-v7a
TARGET_CPU_ABI2 := armeabi
TARGET_CPU_SMP := true
TARGET_CPU_VARIANT := krait
TARGET_GLOBAL_CFLAGS += -mfpu=neon -mfloat-abi=softfp
TARGET_GLOBAL_CPPFLAGS += -mfpu=neon -mfloat-abi=softfp
TARGET_KRAIT_BIONIC_BBTHRESH := 64
TARGET_KRAIT_BIONIC_PLDOFFS := 10
TARGET_KRAIT_BIONIC_PLDSIZE := 64
TARGET_KRAIT_BIONIC_PLDTHRESH := 10
TARGET_USE_KRAIT_PLD_SET := true

# Filesystem
BOARD_BOOTIMAGE_PARTITION_SIZE := 0x01000000
BOARD_CACHEIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_CACHEIMAGE_PARTITION_SIZE := 671088640
BOARD_FLASH_BLOCK_SIZE := 131072 # (BOARD_KERNEL_PAGESIZE * 64)
BOARD_PERSISTIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_PERSISTIMAGE_PARTITION_SIZE := 5242880
BOARD_RECOVERYIMAGE_PARTITION_SIZE := 0x01000000
BOARD_SYSTEMIMAGE_PARTITION_SIZE := 2147483648
BOARD_TOMBSTONESIMAGE_PARTITION_SIZE := 73400320
BOARD_USERDATAIMAGE_PARTITION_SIZE := 27358941675
TARGET_USERIMAGES_USE_EXT4 := true

# Wifi
BOARD_HAS_QCOM_WLAN              := true
BOARD_HAS_QCOM_WLAN_SDK          := true
BOARD_WLAN_DEVICE                := qcwcn
BOARD_WPA_SUPPLICANT_DRIVER      := NL80211
BOARD_WPA_SUPPLICANT_PRIVATE_LIB := lib_driver_cmd_qcwcn
BOARD_HOSTAPD_DRIVER             := NL80211
BOARD_HOSTAPD_PRIVATE_LIB        := lib_driver_cmd_qcwcn
WIFI_DRIVER_FW_PATH_STA          := "sta"
WIFI_DRIVER_FW_PATH_AP           := "ap"
WPA_SUPPLICANT_VERSION           := VER_0_8_X

# Radio
TARGET_RIL_VARIANT := caf

BOARD_USES_GENERIC_AUDIO := true
USE_CAMERA_STUB := false

#TODO: Fix-me: Setting TARGET_HAVE_HDMI_OUT to false
# to get rid of compilation error.
TARGET_HAVE_HDMI_OUT := false
TARGET_USES_OVERLAY := true
NUM_FRAMEBUFFER_SURFACE_BUFFERS := 3
TARGET_NO_BOOTLOADER := false
TARGET_NO_KERNEL := false
TARGET_NO_RPC := true

TARGET_HARDWARE_3D := false
TARGET_BOARD_PLATFORM := msm8974
TARGET_BOOTLOADER_BOARD_NAME := FP2 

# Enables Adreno RS driver
OVERRIDE_RS_DRIVER := libRSDriver_adreno.so

# Shader cache config options
# Maximum size of the  GLES Shaders that can be cached for reuse.
# Increase the size if shaders of size greater than 12KB are used.
MAX_EGL_CACHE_KEY_SIZE := 12*1024

# Maximum GLES shader cache size for each app to store the compiled shader
# binaries. Decrease the size if RAM or Flash Storage size is a limitation
# of the device.
MAX_EGL_CACHE_SIZE := 2048*1024

# Use signed boot and recovery image
TARGET_BOOTIMG_SIGNED := true

TARGET_USERIMAGES_USE_EXT4 := true
BOARD_CACHEIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_PERSISTIMAGE_FILE_SYSTEM_TYPE := ext4

BOARD_EGL_CFG := device/fairphone_devices/FP2/egl.cfg

# Added to indicate that protobuf-c is supported in this build
PROTOBUF_SUPPORTED := true

TARGET_USES_ION := true
TARGET_USES_NEW_ION_API :=true

TARGET_HW_DISK_ENCRYPTION := false

# Workaround framework bluetooth dependency
BOARD_HAVE_BLUETOOTH := true
BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := device/generic/common/bluetooth
OVERRIDE_RS_DRIVER := libRSDriver_adreno.so

TARGET_INIT_VENDOR_LIB := libinit_msm
TARGET_RECOVERY_UPDATER_LIBS := librecovery_updater_msm

TARGET_LDPRELOAD := libNimsWrap.so

USE_OPENGL_RENDERER := true


TW_THEME := portrait_hdpi
TW_NO_USB_STORAGE := false
TW_TARGET_USES_QCOM_BSP := true
TW_NO_EXFAT := true
TW_NO_EXFAT_FUSE := true


RECOVERY_GRAPHICS_USE_LINELENGTH := true
