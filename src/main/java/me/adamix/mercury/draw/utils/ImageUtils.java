package me.adamix.mercury.draw.utils;

import org.jetbrains.annotations.NotNull;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ImageUtils {
	public static @NotNull InputStream toInputStream(@NotNull Image image) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) image, "png", out);
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static @NotNull BufferedImage resizeImage(@NotNull BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}

	public static @NotNull BufferedImage makeSquareAndClampSize(@NotNull BufferedImage image, int maxSize) throws IOException {
		Integer newSize = null;
		if (image.getHeight() > image.getWidth()) {
			newSize = image.getWidth();
		} else if (image.getWidth() > image.getHeight()) {
			newSize = image.getHeight();
		}
		if (image.getWidth() > 512) {
			newSize = 512;
		}
		final BufferedImage resizedImage;
		if (newSize != null) {
			return resizedImage = resizeImage(image, newSize, newSize);
		}

		return image;
	}
}
