package live_image_recognition_and_object_detection.object_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import drawBoxesForOD
import live_image_recognition_and_object_detection.ImageFrame
import org.jetbrains.kotlinx.dl.api.inference.loaders.ONNXModelHub
import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.api.inference.onnx.objectdetection.SSDMobileNetV1ObjectDetectionModel
import org.jetbrains.kotlinx.dl.api.inference.onnx.objectdetection.SSDObjectDetectionModel
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
    val ssd = modelHub[
            ONNXModels.ObjectDetection.SSD]

    val webcam = Webcam.getWebcams()[1]
    webcam.viewSize = WebcamResolution.VGA.size
    webcam.open()
    val frame = ImageFrame(webcam.viewSize.width, webcam.viewSize.height)
    while(webcam.isOpen){

        measureTime {

            frame.showImage(webcam.image.drawBoxesForOD(ssd.detectObjects(webcam.image)))
        }.run(::println)
    }


}


fun SSDObjectDetectionModel.detectObjects(buff: BufferedImage, topK: Int = 5): List<DetectedObject> {
    return detectObjects(buff.toFloatArray(ColorMode.BGR,  1200 to 1200), topK)
}