import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class Highlighter
	{
		protected HashSet<Highlight> locations;
		protected final String text;
		protected DefaultHighlightPainter color;

		public Highlighter(String text, DefaultHighlighter highlighter, DefaultHighlightPainter color)
			{
				this.text = text;
				locations = new HashSet<Highlight>();
				this.color = color;
			}

		public void findHighlights(String toSearch) {
			int textPoint = 0;
			int i = 0;
			while (toSearch.indexOf(text, i) != -1)
				{
					i = toSearch.indexOf(text, i);
					locations.add(new Highlight(toSearch.indexOf(text, i)));
					i++;
				}
			System.out.println(locations);
		}

		public void removeHighlights(String toSearch) {
			Iterator<Highlight> iterator = locations.iterator();
			while (iterator.hasNext())
				{
					Integer spot = iterator.next().locaton;
					if (toSearch.indexOf(text, spot) != spot)
						{
							iterator.remove();
							locations.remove(spot);
						}
				}
		}

	}
