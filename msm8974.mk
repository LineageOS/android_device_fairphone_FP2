$(call inherit-product, device/qcom/common/common.mk)

PRODUCT_NAME := msm8974
PRODUCT_DEVICE := msm8974

DEVICE_PACKAGE_OVERLAYS := device/qcom/msm8974/overlay

#fstab.msm8974
PRODUCT_PACKAGES += fstab.msm8974
