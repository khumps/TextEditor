import java.util.HashSet;
import java.util.Iterator;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextHighlight
    {
	protected HashSet<Integer> locations;
	protected final String text;
	protected DefaultHighlightPainter color;

	public TextHighlight(String text, DefaultHighlightPainter color)
	    {
		this.text = text;
		locations = new HashSet<Integer>();
		this.color = color;
	    }

	public void findHighlights(String toSearch)
	    {
		int i = 0;
		while (toSearch.indexOf(text, i) != -1)
		    {
			i = toSearch.indexOf(text, i);
			locations.add(i);
			i += text.length();
		    }
	    }

	public void removeHighlights(String toSearch)
	    {
		char c = ' ';
		Iterator<Integer> iterator = locations.iterator();
		while (iterator.hasNext())
		    {
			Integer next = iterator.next();
			/*
			 * if (toSearch.indexOf(text, next) != next) {
			 * iterator.remove(); }
			 */
			if (next - 1 >= 0)
			    {
				c = toSearch.charAt(next - 1);
				if (!Character.isWhitespace(c) && c != '(' && c != ')' && c != '{'
					&& c != '}')
				    {
					iterator.remove();
				    }
			    } else if (next + text.length() - 1 < toSearch.length())
			    {
				c = toSearch.charAt(next + text.length() - 1);
				if (!Character.isWhitespace(c) && c != '(' && c != ')' && c != '{'
					&& c != '}')
				    {
					iterator.remove();
				    }

			    }

		    }

	    }
    }
