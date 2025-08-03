package me.adamix.mercury.draw.elements.shapes;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.elements.MercurySizeElement;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Rectangle extends MercurySizeElement<Rectangle> {
	private @NotNull Color color = Color.BLACK;
	private int arcWidth = 0;
	private int arcHeight = 0;
	private boolean antialiasing = true;

	public Rectangle(@NotNull MercuryValue width, @NotNull MercuryValue height) {
		width(width);
		height(height);
	}

	public @NotNull Rectangle color(@NotNull Color color) {
		this.color = color;
		return this;
	}

	public @NotNull Rectangle color(int r, int g, int b) {
		return color(r, g, b, 255);
	}

	public @NotNull Rectangle color(int r, int g, int b, int a) {
		return color(new Color(r, g, b, a));
	}

	public @NotNull Rectangle rounded(int arcWidth, int arcHeight) {
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		return this;
	}

	public @NotNull Rectangle antialiasing(boolean value) {
		this.antialiasing = value;
		return this;
	}

	@Override
	protected @NotNull Rectangle self() {
		return this;
	}

	@Override
	public @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight) {
		final int finalWidth = calculateWidth(parentWidth);
		final int finalHeight = calculateHeight(parentHeight);
		final float finalX = calculateX(finalWidth, parentWidth);
		final float finalY = calculateY(finalHeight, parentHeight);

		ctx.setHint(RenderingHints.KEY_ANTIALIASING, antialiasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		ctx.drawRoundedRect(finalX, finalY, finalWidth, finalHeight, rotation, color, arcWidth, arcHeight);
		return new DrawResult(finalX, finalY, finalWidth, finalHeight);
	}
}
