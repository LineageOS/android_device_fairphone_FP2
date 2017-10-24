#
# Copyright (C) 2008 The Android Open Source Project
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

LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
RES_DIR := app/src/main/res

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := android-support-v7-appcompat \
                               android-support-v4


LOCAL_RESOURCE_DIR := frameworks/support/v7/appcompat/res \
                      $(addprefix $(LOCAL_PATH)/, $(RES_DIR))

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java) $(call all-renderscript-files-under, app/src/main/java)
LOCAL_SDK_VERSION := current

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml

LOCAL_PACKAGE_NAME := ProximitySensorTools

LOCAL_CERTIFICATE := platform
LOCAL_PRIVILEGED_MODULE := true

LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.appcompat
include $(BUILD_PACKAGE)
