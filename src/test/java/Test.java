import me.adamix.mercury.draw.MercuryCanvas;
import me.adamix.mercury.draw.font.MercuryFont;
import me.adamix.mercury.draw.geometry.MercuryAlignment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static me.adamix.mercury.draw.value.MercuryValue.px;

public class Test {
	public static void main(String[] args) throws IOException {

		MercuryFont font = new MercuryFont(Paths.get("C:/Users/AdamIx/Java/Jda/RebelBot/assets/fonts/roboto-regular.ttf"));

		MercuryCanvas canvas = new MercuryCanvas(800, 800);

		canvas.rect(px(200), px(200))
				.x(px(0))
				.y(px(0))
				.color(255, 0, 0, 255)
				.antialiasing(false)
				.align(MercuryAlignment.BOTTOM_RIGHT)
				.rounded(50, 50);

		canvas.text("hello", font, 40f)
				.color(0, 0, 0, 255)
				.align(MercuryAlignment.BOTTOM_RIGHT);

		ImageIO.write(canvas.create().image(), "png", new File("out.png"));

//		showImage(canvas.create().image());

	}

	public static void showImage(RenderedImage image) {
		JFrame frame = new JFrame("Image Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(image.getWidth(), image.getHeight());
		frame.setResizable(false);

		if (image instanceof BufferedImage buffered) {
			frame.add(new JLabel(new ImageIcon(buffered)));
		} else {
			// Convert to BufferedImage if not already
			BufferedImage copy = new BufferedImage(
					image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB
			);
			copy.createGraphics().drawRenderedImage(image, null);
			frame.add(new JLabel(new ImageIcon(copy)));
		}

		frame.setVisible(true);
	}


}
