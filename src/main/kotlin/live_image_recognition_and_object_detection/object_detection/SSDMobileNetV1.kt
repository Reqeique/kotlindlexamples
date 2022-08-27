package live_image_recognition_and_object_detection.object_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamDriver
import com.github.sarxos.webcam.WebcamResolution
import com.github.sarxos.webcam.WebcamUtils
import drawBoxesForOD
import live_image_recognition_and_object_detection.ImageFrame
import org.jetbrains.kotlinx.dl.api.inference.loaders.ONNXModelHub
import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.api.inference.onnx.objectdetection.SSDMobileNetV1ObjectDetectionModel
import org.jetbrains.kotlinx.dl.api.inference.onnx.posedetection.MultiPoseDetectionModel
import org.jetbrains.kotlinx.dl.api.inference.posedetection.MultiPoseDetectionResult
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import toFloatArray
import java.awt.image.BufferedImage
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(){
    val modelHub = ONNXModelHub(
        File("models\\onnx_pretrained_model")
    )

    val ssdMNV1 = modelHub[
            ONNXModels.ObjectDetection.SSDMobileNetV1]



    println(Webcam.getWebcams().map { it.name })
    val webcam = Webcam.getWebcams()[0]
    webcam.viewSize = WebcamResolution.VGA.size
    webcam.open()
    val frame = ImageFrame(webcam.viewSize.width, webcam.viewSize.height)
    while(webcam.isOpen){

        measureTime {

            frame.showImage(webcam.image.drawBoxesForOD(ssdMNV1.detectObjects(webcam.image)))
        }.run(::println)
    }

}


fun SSDMobileNetV1ObjectDetectionModel.detectObjects(buff: BufferedImage,topK: Int = 5): List<DetectedObject> {
    return detectObjects(buff.toFloatArray(ColorMode.BGR,  1000 to 1000), topK)
}