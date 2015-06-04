import java.awt.Color;
import java.util.Iterator;

public class SelectionHighlighter extends TextHighlighter {

	public SelectionHighlighter(String text, Color color) {
		super(text, color);
		// TODO Auto-generated constructor stub
	}

	public void findHighlights(String toSearch) {
		int i = 0;
		while (toSearch.indexOf(text, i) != -1) {
			i = toSearch.indexOf(text, i);
			locations.put(i, null);
			i += text.length();
		}
	}

	public void removeHighlights(String toSearch) {
		Iterator<Integer> iterator = locations.keySet().iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			if (toSearch.indexOf(text, i) != i) {
				removedHighlights.add(locations.get(i));
				iterator.remove();
			}
		}
	}

	public void setText(String text) {
		locations.clear();
		this.text = text;
	}

}
