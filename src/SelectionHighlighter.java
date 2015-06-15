import java.awt.Color;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class SelectionHighlighter extends TextHighlighter {
	protected boolean refactoring = false;
	protected int initialSelection;

	public SelectionHighlighter(String text, String preFormat, String postFormat) {
		super(text, preFormat, postFormat);
		// TODO Auto-generated constructor stub
	}

	public void findHighlights(String toSearch) {
		boolean first = true;
		int i = 0;
		while (toSearch.indexOf(text, i) != -1) {

			i = toSearch.indexOf(text, i);
			locations.add(i);
			i += text.length();
		}
	}

	public void removeHighlights(String toSearch) {
		Iterator<Integer> iterator = locations.iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			if (toSearch.indexOf(text, i) != i) {
				iterator.remove();
			}
		}
	}

	public void setText(String text) {
		locations.clear();
		this.text = text;
	}

	public void setInit(int i) {
		initialSelection = i;
	}

	public void refactor(String old, String refactorTo, String toSearch,
			JEditorPane pane) {
		if (!old.isEmpty() && !refactorTo.isEmpty())
			pane.setText(toSearch.replaceAll(old, refactorTo));
		System.out.println("ran");
	}
}
