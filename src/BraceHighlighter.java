import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

/**
 * Highlights matching braces
 * 
 * @author Kevin Humphreys
 *
 */
public class BraceHighlighter {
	public final char open; // The opening brace
	public final char close; // The closing brace
	protected DefaultHighlightPainter color; // Color to highlight the braces

	public BraceHighlighter(char open, char close, DefaultHighlightPainter color) {
		this.open = open;
		this.close = close;
		this.color = color;
	}

	/**
	 * Used to find the closing brace
	 * 
	 * @param text
	 *            The entire body of text to search for the closing brace
	 * @param open
	 *            the index of the opening brace
	 * @return the index of the closing brace or -1 if the matching closing
	 *         brace isn't found
	 */
	public int findClose(String text, int open) {
		System.out.println("findClose()");
		int count = 1;
		if (open >= 0)
			while (open < text.length()) {
				char c = text.charAt(open);
				if (c == this.open)
					count++;
				if (c == close)
					count--;
				open++;
				if (count == 0) {
					System.out.println(open);
					return open;
				}
			}
		return -1;
	}

	/**
	 * Used to find the opening brace
	 * 
	 * @param text
	 *            The entire body of text to search for the opening brace
	 * @param close
	 *            the index of the closing brace
	 * @return the index of the opening brace or -1 if the matching opening
	 *         brace isn't found
	 */
	public int findOpen(String text, int close) {
		System.out.println("findOpen()");
		int count = -1;
		if (close < text.length())
			while (--close >= 0) {
				char c = text.charAt(close);
				if (c == open)
					count++;
				if (c == this.close)
					count--;
				if (count == 0) {
					System.out.println(close);
					return close;
				}
			}
		return -1;
	}
}
