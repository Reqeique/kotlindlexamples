package live_cv.pose_detection

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import drawPoses
import live_cv.ImageFrame
import modelHub

import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.api.inference.onnx.posedetection.MultiPoseDetectionModel
import org.jetbrains.kotlinx.dl.api.inference.posedetection.MultiPoseDetectionResult
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import toFloatArray
import java.awt.image.BufferedImage

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>){
    print(args.toList())

    val moveNetL = modelHub[
    ONNXModels.PoseDetection.MoveNetMultiPoseLighting]

    println(Webcam.getWebcams())
    val webcam = Webcam.getWebcams()[args.getOrNull(0)?.toIntOrNull() ?: 0] //TODO Set to default webcam
    webcam.viewSize = WebcamResolution.VGA.size
    webcam.open()
    val frame = ImageFrame(webcam.viewSize.width, webcam.viewSize.height)
    while(webcam.isOpen){

       measureTime {
           frame.showImage(webcam.image.drawPoses(moveNetL.detectPoses(webcam.image).multiplePoses.map { it.second }))
       }.run(::println)
    }

}

fun MultiPoseDetectionModel.detectPoses(buff: BufferedImage, conf: Double = 5e-1): MultiPoseDetectionResult {
    return detectPoses(buff.toFloatArray(ColorMode.BGR,  256 to 256), conf.toFloat())
}