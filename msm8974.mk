$(call inherit-product, device/qcom/common/common.mk)

PRODUCT_NAME := msm8974
PRODUCT_DEVICE := msm8974

DEVICE_PACKAGE_OVERLAYS += device/qcom/msm8974/overlay

# Bluetooth configuration files
PRODUCT_COPY_FILES += \
    system/bluetooth/data/main.le.conf:system/etc/bluetooth/main.conf \

#fstab.qcom
PRODUCT_PACKAGES += fstab.qcom
