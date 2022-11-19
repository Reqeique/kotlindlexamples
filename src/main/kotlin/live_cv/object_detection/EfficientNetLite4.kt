package live_cv.object_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import live_cv.ImageFrame
import modelHub

import org.jetbrains.kotlinx.dl.api.inference.imagerecognition.ImageRecognitionModel

import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import toFloatArray
import java.awt.image.BufferedImage

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime



@OptIn(ExperimentalTime::class)
fun main(args: Array<String>){

    val efficientNet4L = modelHub[ONNXModels.CV.EfficientNet4Lite()]
    val webcam = Webcam.getWebcams()[args.getOrNull(0)?.toIntOrNull() ?: 2]
    print(Webcam.getWebcams())
    webcam.viewSize = WebcamResolution.VGA.size
    webcam.open()
    val frame = ImageFrame(webcam.viewSize.width, webcam.viewSize.height)
    while (webcam.isOpen){
        measureTime {
            frame.showImage(webcam.image)
            println(efficientNet4L.predictObject(webcam.image))
        }
    }

}

fun ImageRecognitionModel.predictObject(buff: BufferedImage, ):String? {
   return imageNetClassLabels[predict(buff.toFloatArray(ColorMode.BGR,380 to 380))]
}