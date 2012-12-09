$(call inherit-product, device/qcom/common/common.mk)

PRODUCT_NAME := msm8974
PRODUCT_DEVICE := msm8974

# Audio configuration file
PRODUCT_COPY_FILES += \
    device/qcom/msm8974/audio_policy.conf:system/etc/audio_policy.conf \

# Bluetooth configuration files
PRODUCT_COPY_FILES += \
    system/bluetooth/data/main.le.conf:system/etc/bluetooth/main.conf \

#fstab.qcom
PRODUCT_PACKAGES += fstab.qcom
