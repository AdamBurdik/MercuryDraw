package me.adamix.mercury.draw.elements.image;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.elements.MercurySizeElement;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.utils.ImageUtils;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class DrawableImage extends MercurySizeElement<DrawableImage> {
	private final @NotNull BufferedImage bufferedImage;
	private boolean resize = false;

	public DrawableImage(@NotNull Path path) throws IOException {
		this(ImageIO.read(path.toFile()));
	}

	public DrawableImage(@NotNull BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	@Override
	protected @NotNull DrawableImage self() {
		return this;
	}

	@Override
	public @NotNull DrawableImage width(@NotNull MercuryValue width) {
		this.resize = true;
		return super.width(width);
	}

	@Override
	public @NotNull DrawableImage height(@NotNull MercuryValue height) {
		this.resize = true;
		return super.height(height);
	}

	@Override
	public @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight) {
		final int finalWidth = calculateWidth(parentWidth);
		final int finalHeight = calculateHeight(parentHeight);
		final float finalX = calculateX(finalWidth, parentWidth);
		final float finalY = calculateY(finalHeight, parentHeight);

		try {
			BufferedImage image = bufferedImage;
			if (resize) {
				image = ImageUtils.resizeImage(bufferedImage, finalWidth, finalHeight);
			}

			ctx.drawImage(image, finalX, finalY, finalWidth, finalHeight, rotation);

		} catch (IOException e) {
			// ToDo Add some logging or idk
			throw new RuntimeException(e);
		}

		return new DrawResult(finalX, finalY, finalWidth, finalHeight);
	}
}