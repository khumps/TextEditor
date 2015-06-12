import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class BraceHighlighter {
	public final char open;
	public final char close;
	protected DefaultHighlightPainter color;

	public BraceHighlighter(char open, char close, DefaultHighlightPainter color) {
		this.open = open;
		this.close = close;
		this.color = color;
	}

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
		System.out.println("FAIL");
		return -1;
	}

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
		System.out.println("fail");
		return -1;
	}
}
