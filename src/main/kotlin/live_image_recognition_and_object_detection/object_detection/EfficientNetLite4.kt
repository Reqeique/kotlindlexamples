package live_image_recognition_and_object_detection.object_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import live_image_recognition_and_object_detection.ImageFrame
import org.jetbrains.kotlinx.dl.api.core.util.loadImageNetClassLabels
import org.jetbrains.kotlinx.dl.api.inference.imagerecognition.ImageRecognitionModel
import org.jetbrains.kotlinx.dl.api.inference.loaders.ONNXModelHub
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import toFloatArray
import java.awt.image.BufferedImage
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
val imageNetClassLabels: MutableMap<Int, String> = loadImageNetClassLabels()


@OptIn(ExperimentalTime::class)
fun main(args: Array<String>){
    val modelHub = ONNXModelHub(
        File("models\\onnx_pretrained_model")
    )
    val efficientNet4L = modelHub[ONNXModels.CV.EfficientNet4Lite()]
    val webcam = Webcam.getWebcams()[args.getOrNull(0)?.toIntOrNull() ?: 0]
    webcam.viewSize = WebcamResolution.VGA.size
    val frame = ImageFrame(webcam.viewSize.width, webcam.viewSize.height)
    while (webcam.isOpen){
        measureTime {
            println(efficientNet4L.predictObject(webcam.image))
        }
    }

}

fun ImageRecognitionModel.predictObject(buff: BufferedImage, ):String? {
   return imageNetClassLabels[predict(buff.toFloatArray(ColorMode.BGR,100 to 100))]
}