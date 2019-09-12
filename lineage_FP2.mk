# Copyright (C) 2017-2018 The LineageOS Project
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

# Inherit framework first
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/product_launched_with_l.mk)

# Inherit from FP2 device
$(call inherit-product, device/fairphone/FP2/FP2.mk)

# Inherit some common Lineage stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

PRODUCT_NAME := lineage_FP2
PRODUCT_DEVICE := FP2
PRODUCT_BRAND := Fairphone
PRODUCT_MANUFACTURER := Fairphone
PRODUCT_MODEL := FP2

PRODUCT_GMS_CLIENTID_BASE := android-FP2

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="FP2-user 6.0.1 FP2-gms-18.04.1 FP2-gms-18.04.1 release-keys" \
    PRODUCT_NAME=FP2

BUILD_FINGERPRINT := Fairphone/FP2/FP2:6.0.1/FP2-gms-18.04.1/FP2-gms-18.04.1:user/release-keys
