# Kotlin DL Examples

#### This repo contains a practical example on using kotlin dl for real world application

---

- ### Live Image Recognition and Object Detection

1. #### [Pose Detection][1] ([MoveNetLighting][2])

   [1]:src/main/kotlin/live_image_recognition_and_object_detection/pose_detection
   <img alt="Watch The Video" height="420" src="assets/movenetlightingdemo.gif" width="420" />

   [2]:src/main/kotlin/live_image_recognition_and_object_detection/pose_detection/MoveNetLighting.kt

2. #### [Object Detection][3] ([SSDMobileNetV1][4], [SSD][5])

   [1]:src/main/kotlin/live_image_recognition_and_object_detection/pose_detection
   <img alt="Watch The Video" height="420" src="assets/ssdmobilenetdemo.gif" width="420"/>


   [3]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection

   [4]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection/SSDMobileNetV1.kt

   [5]:src/main/kotlin/live_image_recognition_and_object_detection/object_detection/SSD.kt
3. #### Face Detection (Coming Soon)


- ### NLP (Coming Soon)
 
### Bench 

----
- #### Intel® Core™ i5-8250U (CPU only)

| Models            | Frequency(hZ) | Frequency(hZ) |       
|-------------------|---------------|---------------|
|                   | On Battery    | AC            |
| `MoveNetLighting` | ~9.5 hZ       | ~12.5 hZ      |
| `SSDMobileNetV1`  | ~4.04 hZ      | ~5.293 hZ     |
 | `SSD`             | ~0.1 hZ       | ~0.2 hZ       |
 
### Run the Classes from Gradle CLI

-------

#### Tasks
`runMoveNetLighting`
\
`runSSD` 
\
`runSSDMobileNetV1`
#### Id
 The id that represents the current web cam
```shell
# gradle task --args id
gradle runMoveNetLighting --args 0
```