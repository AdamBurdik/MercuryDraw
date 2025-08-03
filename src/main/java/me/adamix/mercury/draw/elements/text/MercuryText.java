package me.adamix.mercury.draw.elements.text;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.elements.MercurySizeElement;
import me.adamix.mercury.draw.font.MercuryFont;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.adamix.mercury.draw.value.MercuryValue.px;

public class MercuryText extends MercurySizeElement<MercuryText> {
	private final @NotNull String text;
	private final @NotNull MercuryFont font;
	private final float fontSize;
	private @NotNull Color color = Color.WHITE;
	private boolean antialiasing = true;

	public MercuryText(@NotNull String text, @NotNull MercuryFont font, float fontSize) {
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
	}

	@Override
	protected @NotNull MercuryText self() {
		return this;
	}

	@Override
	public @NotNull MercuryText width(@NotNull MercuryValue width) {
		// ToDO Add some logging to notify that text width cannot be changed
		return this;
	}

	@Override
	public @NotNull MercuryText height(@NotNull MercuryValue height) {
		// ToDO Add some logging to notify that text height cannot be changed
		return super.height(height);
	}

	@Override
	public @NotNull MercuryValue width() {
		return px(font.getTextSize(text, fontSize).width);
	}

	@Override
	public @NotNull MercuryValue height() {
		return px(font.getTextSize(text, fontSize).height);
	}

	public @NotNull MercuryText color(@NotNull Color color) {
		this.color = color;
		return this;
	}

	public @NotNull MercuryText color(int r, int g, int b) {
		return color(r, g, b, 255);
	}

	public @NotNull MercuryText color(int r, int g, int b, int a) {
		return color(new Color(r, g, b, a));
	}

	@Override
	public @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight) {
		ctx.setFontSize(fontSize);
		Dimension dimension = ctx.getTextSize(text);
		width(px(dimension.width));
		height(px(dimension.height));

		final float finalX = calculateX(dimension.width, parentWidth);
		final float finalY = calculateY(dimension.height, parentHeight);

		ctx.setFont(font.font());
		ctx.drawText(text, finalX, finalY, fontSize, rotation, color, true);

		return new DrawResult(finalX, finalY, dimension.width, dimension.height);
	}
}
