package nlp.sentiment_analysis

import Math.sigmoid
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import org.jetbrains.kotlinx.dl.api.core.shape.TensorShape
import org.jetbrains.kotlinx.multik.api.math.argMax
import org.jetbrains.kotlinx.multik.api.toNDArray
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.LongBuffer
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


private const val distilBertUncasedFineTunedSST2EN =
    "https://huggingface.co/optimum/distilbert-base-uncased-finetuned-sst-2-english/resolve/main/model.onnx"

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {

    val msg = ("""type any word to get it's negativity or positivity in the form of [[x,y]] respectively
           |type `:quit:` or `:q:` to exit
       """.trimMargin())
    println(msg)
    downloadModel()
    while (true) {


        measureTime mtp@{
            val r = readLine().toString()

            if (r in listOf(":q:", ":quit:")) return@mtp

            main0(r)
        }.apply(::println)

    }


}

private fun downloadModel() {
    val name = "distilbert-sst2-en"
    val path = Paths.get("models\\onnx_pretrained_model\\models\\onnx\\sentimentanalysis")

    if (!path.exists()) path.toFile().mkdir()
    if (!path.toFile().listFiles().map { it.nameWithoutExtension }.contains(name)) {
        print(path.toFile().listFiles().map { it.nameWithoutExtension }.toList().contains("f"))
        val url = URL(distilBertUncasedFineTunedSST2EN)
        val size = getFileSize(url)
        print("Downloading from the internet into path ${path.toAbsolutePath()} with content size of $size bytes")

        val inputStream =
            url.openStream()


        val s =
            Files.copy(inputStream, Paths.get("$path\\$name.onnx"), StandardCopyOption.REPLACE_EXISTING)

        print(s)
        print("Downloading completed")

    }

}

fun main0(req: String) {

    val sentence = req.takeIf { it != "null" }
    //  println(req)

    val h = HuggingFaceTokenizer.newInstance(

        "distilbert-base-uncased"
    )
    CustomOnnxInferenceModel(File("models\\onnx_pretrained_model\\models\\onnx\\sentimentanalysis\\model.onnx").absolutePath) { ses, env ->


        val hf = h.encode(sentence)

        val x = ses.predictRaw1D(env, mapOf("input_ids" to hf.ids, "attention_mask" to hf.attentionMask))
        val (up, down) = listOf("👍", "👎")
        val output = x.map { (it.value as Array<FloatArray>).flatMap { it.toList().map { sigmoid(it) } } }

        println(
            """ '$sentence' $output ${output.flatten().toNDArray().argMax()} ${
                if (output.flatten().toNDArray().argMax() == 1) up else down
            } """
        )


    }
}


fun CustomOnnxInferenceModel(p: String, block: (OrtSession, OrtEnvironment) -> Unit) {

    val env = OrtEnvironment.getEnvironment()
    val ses: OrtSession = env.createSession(p, OrtSession.SessionOptions())
    block(ses, env)

}

fun OrtSession.predictRaw1D(env: OrtEnvironment, input: Map<String, LongArray>): Map<String, Any> {

    val r = run(input.map {
        it.key to (OnnxTensor.createTensor(
            env,
            LongBuffer.wrap(it.value),
            TensorShape(1, it.value.size.toLong()).dims()
        ))
    }.toMap())
    val result = mutableMapOf<String, Any>()

    r.forEach {
        result[it.key] = it.value.value
    }
    return result
}

fun getFileSize(url: URL): Long {
    var conn: HttpURLConnection? = null
    return try {
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "HEAD"
        conn.contentLengthLong
    } catch (e: IOException) {
        throw RuntimeException(e)
    } finally {
        conn?.disconnect()
    }
}