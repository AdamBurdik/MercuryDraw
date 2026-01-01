package me.adamix.mercury.draw.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class DrawContext {
	private final BufferedImage image;
	private @Nullable Graphics2D drawable;

	public DrawContext(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public void beginDrawing() {
		this.drawable = image.createGraphics();
	}

	public @NotNull BufferedImage image() {
		return image;
	}

	private @Nullable Graphics2D getDrawable() {
		return drawable;
	}

	public Dimension getTextSize(@NotNull String text) {
		Dimension dimension = new Dimension();
		draw(drawable -> {
			Rectangle2D bounds = drawable.getFont().createGlyphVector(drawable.getFontRenderContext(), text).getVisualBounds();
			dimension.setSize((int) bounds.getWidth(), (int) bounds.getHeight());
		});
		return dimension;
	}

	public void draw(@NotNull Consumer<Graphics2D> consumer) {
		Graphics2D drawable = getDrawable();
		if (drawable == null) {
			throw new IllegalStateException("DrawContext is not active. Call 'beginDrawing()' before drawing.");
		}
		consumer.accept(getDrawable());
	}

	public void drawBackground(@NotNull Color color) {
		draw(drawable -> {
			drawable.setColor(color);
			drawable.fillRect(0, 0, image.getWidth(), image.getHeight());
		});
	}

	public void drawRect(float x, float y, int width, int height, int rotation, @NotNull Color color) {
		draw(drawable -> {
			drawable.rotate(Math.toRadians(rotation), x + (double) width / 2, y + (double) height / 2);
			drawable.setColor(color);
			drawable.fillRect(Math.round(x), Math.round(y), width, height);
		});
	}

	public void drawRoundedRect(float x, float y, int width, int height, int rotation, @NotNull Color color, int arcWidth, int arcHeight) {
		draw(drawable -> {
			drawable.rotate(Math.toRadians(rotation), x + (double) width / 2, y + (double) height / 2);
			drawable.setColor(color);
			drawable.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			drawable.fillRoundRect(Math.round(x), Math.round(y), width, height, arcWidth, arcHeight);
		});
	}

	public void drawImage(@NotNull BufferedImage image, float x, float y, int width, int height, int rotation) {
		draw(drawable -> {
			drawable.rotate(Math.toRadians(rotation), x + (double) width / 2, y + (double) height / 2);
			drawable.setComposite(AlphaComposite.SrcOver);
			drawable.drawImage(image, Math.round(x), Math.round(y), null);
		});
	}

	public void setFont(@NotNull Font font) {
		draw(drawable -> {
			drawable.setFont(font);
		});
	}

	public void setFontSize(float fontSize) {
		draw(drawable -> {
			drawable.setFont(drawable.getFont().deriveFont(fontSize));
		});
	}

	public void setHint(@NotNull RenderingHints.Key hintKey, @NotNull Object hintValue) {
		draw(drawable -> {
			drawable.setRenderingHint(hintKey, hintValue);
		});
	}

	public void drawText(@NotNull String text, float x, float y, float fontSize, int rotation, @NotNull Color color) {
		draw(drawable -> {
			drawable.setFont(drawable.getFont().deriveFont(fontSize));
			FontMetrics metrics = drawable.getFontMetrics();
			metrics.stringWidth(text);

			drawable.setColor(color);
			drawable.rotate(Math.toRadians(rotation), x + (double) metrics.stringWidth(text) / 2, y + (double) metrics.getHeight() / 2);

			drawable.drawString(text, x, y + metrics.getAscent() - metrics.getDescent() + metrics.getLeading());
		});
	}

    public void applyClip(int x, int y, int width, int height) {
        draw(drawable -> drawable.setClip(x, y, width, height));
    }

    public void applyRoundedClip(int x, int y, int width, int height, int aw, int ah) {
        Shape roundedRect = new java.awt.geom.RoundRectangle2D.Float(x, y, width, height, aw, ah);
        draw(drawable -> drawable.setClip(roundedRect));
    }

	public void endDrawing() {
		if (drawable != null) {
			drawable.dispose();
		}
		drawable = null;
	}
}
