package me.adamix.mercury.draw.layout;

import me.adamix.mercury.draw.context.DrawContext;
import me.adamix.mercury.draw.elements.MercuryElement;
import me.adamix.mercury.draw.elements.MercurySizeElement;
import me.adamix.mercury.draw.elements.image.DrawableImage;
import me.adamix.mercury.draw.elements.shapes.Rectangle;
import me.adamix.mercury.draw.elements.text.MercuryText;
import me.adamix.mercury.draw.font.MercuryFont;
import me.adamix.mercury.draw.result.DrawResult;
import me.adamix.mercury.draw.value.MercuryValue;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
public class MercuryContainer<T extends MercuryContainer<T>> extends MercurySizeElement<T> {
	protected final @NotNull List<MercuryElement<?>> children = new LinkedList<>();
	protected @NotNull Color background = new Color(0, 0, 0, 0);
    protected boolean clip = false;
    protected int arcWidth = 0;
    protected int arcHeight = 0;

	public MercuryContainer(@NotNull MercuryValue width, @NotNull MercuryValue height, boolean clip, int arcWidth, int arcHeight) {
		width(width);
		height(height);
        this.clip = clip;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
	}

    public MercuryContainer(@NotNull MercuryValue width, @NotNull MercuryValue height) {
        width(width);
        height(height);
    }

    @SuppressWarnings("unchecked")
	@Override
	protected @NotNull T self() {
		return (T) this;
	}

	public @NotNull T elemenet(@NotNull MercuryElement<?> element) {
		children.add(element);
		return self();
	}

	public @NotNull T background(int r, int g, int b) {
		return background(new Color(r, g, b, 255));
	}

	public @NotNull T background(int r, int g, int b, int a) {
		return background(new Color(r, g, b, a));
	}

	public @NotNull T background(@NotNull Color color) {
		this.background = color;
		return self();
	}

	public @NotNull GridContainer grid(@NotNull MercuryValue width, @NotNull MercuryValue height, int rowCount, int columnCount) {
		var grid = new GridContainer(width, height, rowCount, columnCount);
		children.add(grid);
		return grid;
	}

	public @NotNull FlexboxContainer flexbox(@NotNull MercuryValue width, @NotNull MercuryValue height) {
		var flexbox = new FlexboxContainer(width, height);
		children.add(flexbox);
		return flexbox;
	}

	public @NotNull MercuryContainer<?> container(@NotNull MercuryValue width, @NotNull MercuryValue height) {
		var container = new MercuryContainer<>(width, height);
		children.add(container);
		return container;
	}

	public @NotNull T clip(boolean clip) {
		this.clip = clip;
		return self();
	}

	public @NotNull T rounded(int arcWidth, int arcHeight) {
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		return self();
	}

	public @NotNull Rectangle rect(@NotNull MercuryValue width, @NotNull MercuryValue height) {
		var rect = new Rectangle(width, height);
		children.add(rect);
		return rect;
	}

	public @NotNull DrawableImage image(@NotNull Path path) throws IOException {
		var image = new DrawableImage(path);
		children.add(image);
		return image;
	}
	
	public @NotNull DrawableImage image(@NotNull DrawableImage image) {
		children.add(image);
		return image;
	}

	public @NotNull MercuryText text(@NotNull String text, @NotNull MercuryFont font, float fontSize) {
		var textElement = new MercuryText(text, font, fontSize);
		children.add(textElement);
		return textElement;
	}

	@Override
	public @NotNull DrawResult draw(@NotNull DrawContext ctx, float parentX, float parentY, int parentWidth, int parentHeight) {
		final int finalWidth = calculateWidth(parentWidth);
		final int finalHeight = calculateHeight(parentHeight);
		final float finalX = calculateX(finalWidth, parentWidth);
		final float finalY = calculateY(finalHeight, parentHeight);

		DrawContext drawContext = new DrawContext(finalWidth, finalHeight);
		drawContext.beginDrawing();

        if (this.clip) {
            if (arcWidth > 0 || arcHeight > 0) {
                // Clip using a rounded rectangle shape
                drawContext.applyRoundedClip(0, 0, finalWidth, finalHeight, arcWidth, arcHeight);
            } else {
                // Standard rectangular clip
                drawContext.applyClip(0, 0, finalWidth, finalHeight);
            }
        }

		drawContext.drawBackground(background);

		for (MercuryElement<?> child : children) {
			child.draw(drawContext, finalX, finalY, finalWidth, finalHeight);
		}

		drawContext.endDrawing();
		ctx.drawImage(drawContext.image(), finalX, finalY, finalWidth, finalHeight, rotation);

		return new DrawResult(finalX, finalY, finalWidth, finalHeight);
	}
}
