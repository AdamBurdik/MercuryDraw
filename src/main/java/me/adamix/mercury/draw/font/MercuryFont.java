package me.adamix.mercury.draw.font;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Path;


public class MercuryFont {
	private final Font font;

	public MercuryFont(@NotNull Path path) {
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, path.toFile());
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public MercuryFont(@NotNull String name) {
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, Path.of("assets", "fonts", name).toFile());
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public @NotNull Font font() {
		return font;
	}

	public @NotNull Dimension getTextSize(@NotNull String text, float fontSize) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D bounds = font.deriveFont(fontSize).createGlyphVector(frc, text).getVisualBounds();
		return new Dimension((int) bounds.getWidth(), (int) bounds.getHeight());
	}
}
