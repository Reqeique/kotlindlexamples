import org.jetbrains.kotlinx.dl.api.inference.objectdetection.DetectedObject
import org.jetbrains.kotlinx.dl.api.inference.posedetection.DetectedPose
import org.jetbrains.kotlinx.dl.dataset.image.ColorMode
import org.jetbrains.kotlinx.dl.dataset.image.ImageConverter
import org.jetbrains.kotlinx.dl.dataset.image.imageType
import org.jetbrains.kotlinx.dl.dataset.preprocessor.image.InterpolationType
import org.jetbrains.kotlinx.dl.dataset.preprocessor.image.RenderingSpeed
import java.awt.*
import java.awt.image.BufferedImage
import kotlin.math.abs

fun BufferedImage.drawPoses(detectedPoses: List<DetectedPose>): BufferedImage {
    val size = this
//    val g = graphics as Graphics2D
//    g.stroke = BasicStroke(2f)
//    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    detectedPoses.forEach { detectedPose ->
        detectedPose.poseLandmarks.forEach {
            val xLM = (size.width) * (it.x)
            val yLM = (size.height) * (it.y)

            val g = graphics as Graphics2D
            val stroke1: Stroke = BasicStroke(3f)
            g.color = (Color.RED)
            g.stroke = stroke1
            graphics.drawOval(xLM.toInt(), yLM.toInt(), 3, 3)
        }

        val onePoseEdges = detectedPose.edges
        for (j in onePoseEdges.indices) {
            val x1 = (size.width) * (onePoseEdges[j].start.x)
            val y1 = (size.height) * (onePoseEdges[j].start.y)
            val x2 = (size.width) * (onePoseEdges[j].end.x)
            val y2 = (size.height) * (onePoseEdges[j].end.y)

            val g = graphics as Graphics2D
            val stroke1: Stroke = BasicStroke(2f)
            g.color = (Color.MAGENTA)
            g.stroke = stroke1
            graphics.drawLine(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }
    }
    graphics.dispose()
    return this
}
fun BufferedImage.resize(outputHeight: Int, outputWidth: Int, interpolation: InterpolationType = InterpolationType.BILINEAR,
                         renderingSpeed: RenderingSpeed = RenderingSpeed.MEDIUM,
                         enableAntialiasing: Boolean = true): BufferedImage {
    val resizedImage = BufferedImage(outputWidth, outputHeight, this.type)
    val graphics2D = resizedImage.createGraphics()

    val renderingHint = when (interpolation) {
        InterpolationType.BILINEAR -> RenderingHints.VALUE_INTERPOLATION_BILINEAR
        InterpolationType.NEAREST -> RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        InterpolationType.BICUBIC -> RenderingHints.VALUE_INTERPOLATION_BICUBIC
    }

    val renderingSpeed = when (renderingSpeed) {
        RenderingSpeed.FAST -> RenderingHints.VALUE_RENDER_SPEED
        RenderingSpeed.SLOW -> RenderingHints.VALUE_RENDER_QUALITY
        RenderingSpeed.MEDIUM -> RenderingHints.VALUE_RENDER_DEFAULT
    }

    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, renderingHint)
    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, renderingSpeed)

    if (enableAntialiasing) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    }

    graphics2D.drawImage(this, 0, 0, outputWidth, outputHeight, null)
    graphics2D.dispose()

    return resizedImage
}

fun BufferedImage.swapColorMode(colorMode: ColorMode): BufferedImage{

    //   if (this.type != 0 || this.colorMode() == colorMode) return this
    val outputType = colorMode.imageType()
    val result = BufferedImage(width, height, outputType)
    val graphics = result.createGraphics()

    graphics.drawImage(this, 0, 0, null)
    graphics.dispose()
    return result

}

fun BufferedImage.drawBoxesForOD(detections: List<DetectedObject>): BufferedImage{
    val image = this
    val g: Graphics2D = this.graphics as Graphics2D
    val stroke = 2
    g.stroke = BasicStroke(stroke.toFloat())
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    val imageWidth: Int = image.width
    val imageHeight: Int = image.height
    detections.forEach {
        val className: String = it.classLabel

        g.paint = Color.BLACK
        val top = it.yMin * image.height
        val left = it.xMin * image.width
        val bottom = it.yMax * image.height
        val right = it.xMax * image.width
        //   if (abs(top - bottom) > 300 || abs(right - left) > 300) return@forEach
        val y = top
        val x = left

        g.drawRect(
            x.toInt(), y.toInt(), abs(right - left).toInt(), abs(top - bottom).toInt()
        )

        drawText(g, className, x.toInt(), y.toInt(), stroke, 4)

    }

    g.dispose()
    return this
}
private fun drawText(g: Graphics2D, text: String, x0: Int, y0: Int, stroke: Int, padding: Int) {
    {
        222.867+
                183.971700+
                235.461100+
                167.993600+
                188.250600+
                173.749200+
                191.492600+
                187.426200+
                168.887700+
                167.564300

    }
    var x = x0
    var y = y0
    val metrics = g.fontMetrics
    x += stroke / 2
    y += stroke / 2
    val width = metrics.stringWidth(text) + padding * 2 - stroke / 2
    val height = metrics.height + metrics.descent
    val ascent = metrics.ascent
    val background = Rectangle(x, y, width, height)
    g.fill(background)
    g.paint = Color.WHITE
    g.drawString(text, x + padding, y + ascent)
}

fun BufferedImage.toFloatArray(colorMode: ColorMode, dims: Pair<Int, Int>) = ImageConverter.toRawFloatArray(this.swapColorMode(colorMode).resize(dims.first, dims.second))