package live_cv

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.swing.*

class ImageFrame internal constructor(width: Int, height: Int) {
    private val frame: JFrame
    private val imagePanel: ImagePanel

    init {
        var width = width
        frame = JFrame("Demo")
        imagePanel = ImagePanel()
        frame.layout = BorderLayout()
        frame.add(BorderLayout.CENTER, imagePanel)
        JOptionPane.setRootFrame(frame)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (width > screenSize.width) {
            width = screenSize.width
        }
        val frameSize = Dimension(width, height)
        frame.size = frameSize
        frame.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.isVisible = true
    }

    fun showImage(image: BufferedImage?) {
        imagePanel.setImage(image)
        SwingUtilities.invokeLater {
            frame.repaint()
            frame.pack()
        }
    }

    private class ImagePanel : JPanel() {
        private var image: BufferedImage? = null
        fun setImage(image: BufferedImage?) {
            this.image = image
        }

        public override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            if (image == null) {
                return
            }
            g.drawImage(image, 0, 0, null)
            preferredSize = Dimension(image!!.width, image!!.height)
        }
    }
}