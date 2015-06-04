import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextHighlighter {
	protected HashSet<Object> removedHighlights = new HashSet<Object>();
	protected HashMap<Integer, Object> locations = new HashMap<Integer, Object>();
	protected String text;
	protected DefaultHighlightPainter color;

	public TextHighlighter(String text, Color color) {
		this.text = text;
		this.color = new DefaultHighlightPainter(color);
	}

	public void findHighlights(String toSearch) {
		int i = 0;
		while (toSearch.indexOf(text, i) != -1) {
			i = toSearch.indexOf(text, i);

			if ((!validIndex(toSearch, i - 1) || (validIndex(toSearch, i - 1) && isValidChar(toSearch
					.charAt(i - 1))))
					&& (!validIndex(toSearch, i + text.length()) || (validIndex(
							toSearch, i + text.length()) && isValidChar(toSearch
							.charAt(i + text.length())))))
				locations.put(new Integer(i), null);

			i += text.length();
		}
	}

	public void removeHighlights(String toSearch) {
		Iterator<Integer> iterator = locations.keySet().iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			if ((validIndex(toSearch, i - 1) && !isValidChar(toSearch
					.charAt(i - 1)))
					|| (validIndex(toSearch, i + text.length()) && !isValidChar(toSearch
							.charAt(i + text.length())))) {
				removedHighlights.add(locations.get(i));
				iterator.remove();
			} else if (toSearch.indexOf(text, i) != i) {
				removedHighlights.add(locations.get(i));
				iterator.remove();
			}
		}
	}

	public void updateHighlights(String text) {
		removeHighlights(text);
		findHighlights(text);
	}

	public boolean isValidChar(char c) {
		return (Character.isWhitespace(c) || c == '(' || c == ')' || c == '{' || c == '}');
	}

	public boolean validIndex(String s, int i) {
		return i >= 0 && i < s.length();
	}

	public void setText(String text) {
		locations.clear();
		this.text = text;
	}
}
