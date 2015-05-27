import java.util.HashSet;
import java.util.Iterator;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class Highlight
	{
		protected HashSet<Integer> locations;
		protected final String text;
		protected DefaultHighlightPainter color;

		public Highlight(String text, DefaultHighlightPainter color)
			{
				this.text = text;
				locations = new HashSet<Integer>();
				this.color = color;
			}

		public void findHighlights(String toSearch) {
			int i = 0;
			while (toSearch.indexOf(text, i) != -1)
				{
					i = toSearch.indexOf(text, i);
					locations.add(toSearch.indexOf(text, i));
					i += text.length();
				}
		}

		public void removeHighlights(String toSearch) {
			Iterator<Integer> iterator = locations.iterator();
			while (iterator.hasNext())
				{
					Integer next = iterator.next();
					if (toSearch.indexOf(text, next) != next)
						{
							iterator.remove();
						}
				}

		}
	}
