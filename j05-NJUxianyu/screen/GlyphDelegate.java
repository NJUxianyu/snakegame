package screen;

import asciiPanel.AsciiPanel;

/**
 * The GlyphDelegate interface represents a delegate that can print a glyph on an AsciiPanel.
 */
public interface GlyphDelegate {

    /**
     * Prints the glyph on the specified AsciiPanel with the given offset.
     *
     * @param terminal the AsciiPanel to print the glyph on
     * @param offsetX  the x-coordinate offset of the glyph
     * @param offsetY  the y-coordinate offset of the glyph
     */
    void printGlyph(AsciiPanel terminal, int offsetX, int offsetY);
}