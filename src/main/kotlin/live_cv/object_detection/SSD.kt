package live_cv.object_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import drawBoxesForOD
import live_cv.ImageFrame
import modelHub
import org.jetbrains.kotlinx.dl.api.inference.loaders.ONNXModelHub
import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.api.inference.onnx.objectdetection.SSDObjectDetectionModel
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import toFloatArray
import java.awt.image.BufferedImage
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>){

    val ssd = modelHub[
          ONNXModels.ObjectDetection.SSD]

    val webcam = Webcam.getWebcams()[args.getOrNull(0)?.toIntOrNull() ?: 0]
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