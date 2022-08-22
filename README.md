# Kotlin DL Example Projects

#### This repo contains a practical example on using kotlin dl for real world application

---

- ## Live Image Recongnition and Object Detection

1. ### [Pose Detection][1] ([MoveNetLightning][2])

[1]:src/main/kotlin/live_image_recognition_and_object_detection/pose_detection

[2]:src/main/kotlin/live_image_recognition_and_object_detection/pose_detection/MoveNetLightning.kt

2. ### [Object Detection][3] ([SSDMobileNetV1][4], [SSD][5])

   [3]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection

   [4]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection/SSDMobileNetV1.kt

   [5]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection/SSD.kt

### CPU Only Bench (i5-8250u)

| Models             | Frequency(hZ) | Frequency(hZ) |       
|--------------------|---------------|---------------|
|                    | On Battery    | AC            |
| `MoveNetLightning` | ~9.5 hZ       | ~ 12.5 hZ     |
| `SSDMobileNetV1`   |               |