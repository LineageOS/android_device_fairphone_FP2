$(call inherit-product, device/qcom/common/common.mk)

PRODUCT_NAME := msm8974
PRODUCT_DEVICE := msm8974

# Audio configuration file
PRODUCT_COPY_FILES += \
    device/qcom/msm8974/audio_policy.conf:system/etc/audio_policy.conf \

# audio UCM files
PRODUCT_COPY_FILES += device/qcom/msm8974/snd_soc_msm/snd_soc_msm_Taiko:system/etc/snd_soc_msm/snd_soc_msm_Taiko \
                      device/qcom/msm8974/snd_soc_msm/snd_soc_msm_Taiko_CDP:system/etc/snd_soc_msm/snd_soc_msm_Taiko_CDP \
                      device/qcom/msm8974/snd_soc_msm/snd_soc_msm_Taiko_Fluid:system/etc/snd_soc_msm/snd_soc_msm_Taiko_Fluid \
                      device/qcom/msm8974/snd_soc_msm/snd_soc_msm_Taiko_liquid:system/etc/snd_soc_msm/snd_soc_msm_Taiko_liquid \


# Bluetooth configuration files
PRODUCT_COPY_FILES += \
    system/bluetooth/data/main.le.conf:system/etc/bluetooth/main.conf \

#fstab.qcom
PRODUCT_PACKAGES += fstab.qcom

#wlan driver
PRODUCT_COPY_FILES += \
    device/qcom/msm8974/WCNSS_cfg.dat:system/etc/firmware/wlan/prima/WCNSS_cfg.dat \
    device/qcom/msm8974/WCNSS_qcom_cfg.ini:system/etc/wifi/WCNSS_qcom_cfg.ini \
    device/qcom/msm8974/WCNSS_qcom_wlan_nv.bin:system/etc/wifi/WCNSS_qcom_wlan_nv.bin
