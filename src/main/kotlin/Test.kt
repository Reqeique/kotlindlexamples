import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.activation.Activations
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics

import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.api.core.summary.printSummary
import org.jetbrains.kotlinx.dl.api.inference.keras.loaders.ModelHub
import org.jetbrains.kotlinx.dl.api.inference.onnx.ONNXModels
import org.jetbrains.kotlinx.dl.dataset.OnHeapDataset
import org.jetbrains.kotlinx.multik.api.mk

fun main(){
//    val model = Sequential.of(
//        Input(2),
//        Dense(1, Activations.Sigmoid)
//    )
//    ONNXModels.PoseDetection.
//
//    model.compile(Adam(), Losses.BINARY_CROSSENTROPY, metric = Metrics.ACCURACY)
////    model.fit(OnHeapDataset.Companion.create())
//    model.printSummary()
//    model.predictSoftly(mk[2.5f, -2.1f].toFloatArray()).toList().apply(::println)
//
      Sequential
}