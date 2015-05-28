import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class BraceHighlight
	{
		public final char open;
		public final char close;
		protected DefaultHighlightPainter color;

		public BraceHighlight(char open, char close, DefaultHighlightPainter color)
			{
				this.open = open;
				this.close = close;
				this.color = color;
			}

		public int findClose(String text, int open) {
			int count = 1;
			if (open >= 0) while (open < text.length() && count != 0)
				{
					char c = text.charAt(open);
					if (c == open) count++;
					if (c == close) count--;
					open++;
				}
			if (count == 0) return open;
			return -1;
		}

		public int findOpen(String text, int close) {
			int count = -1;
			if (close < text.length()) while (--close >= 0)
				{
					char c = text.charAt(close);
					if (c == open) count++;
					if (c == close) count--;
				}
			if (count == 0) return close;
			return -1;
		}
	}
