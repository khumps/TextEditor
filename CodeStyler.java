import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

public class CodeStyler {
	private HashSet<TextHighlighter> textHighlights;
	private Highlighter highlighter;
	private SelectionHighlighter selection;

	public HashSet<Character> getCharHighlights() {
		return charHighlights;
	}

	private HashSet<BraceHighlighter> braceHighlights;
	private HashSet<Character> charHighlights;
	private JTextPane pane;

	public CodeStyler(JTextPane pane) {
		this.pane = pane;
		this.textHighlights = new HashSet<TextHighlighter>();
		this.braceHighlights = new HashSet<BraceHighlighter>();
		this.charHighlights = new HashSet<Character>();
		this.highlighter = pane.getHighlighter();
		selection = new SelectionHighlighter("", Color.YELLOW);
	}

	public CodeStyler(JTextPane pane, HashSet<TextHighlighter> highlights) {
		this.pane = pane;
		this.textHighlights = highlights;
	}

	public void drawTextHighlights() {
		highlighter.removeAllHighlights(); // FIX
		for (TextHighlighter h : textHighlights) {
			/*
			 * Iterator<Object> iterator = h.removedHighlights.iterator();
			 * Removes the highlights that should no longer be there while
			 * (iterator.hasNext()) { Object hi = iterator.next();
			 * highlighter.removeHighlight(hi); iterator.remove(); }
			 */
			int length = h.text.length();
			/* Adds new highlights */
			for (Integer i : h.locations.keySet()) {
				try {
					highlighter.addHighlight(i, i + length, h.color);
				} catch (BadLocationException e) {
				}
			}
		}
		for (Integer i : selection.locations.keySet()) {
			int length = selection.text.length();
			try {
				highlighter.addHighlight(i, length, selection.color);
			} catch (BadLocationException e) {
			}
		}
	}

	public void updateHighlights() {
		try {
			String text = pane.getDocument().getText(0,
					pane.getDocument().getLength());
			for (TextHighlighter h : textHighlights) {
				h.updateHighlights(text);

			}
			String selected = "";
			if (pane.getSelectedText() != null) {
				selected = pane.getSelectedText();
				System.out.println("ran");
				System.out.println(selected);
				selection.setText(selected);
				selection.updateHighlights(text);
			}
		} catch (BadLocationException e) {
		}
	}

	public void addHighlight(Color color, String... text) {
		for (String s : text)
			textHighlights.add(new TextHighlighter(s, color));
	}

	public void addBraceHighlight(char open, char close, Color color) {
		braceHighlights.add(new BraceHighlighter(open, close,
				new DefaultHighlightPainter(color)));
		charHighlights.add(open);
		charHighlights.add(close);
	}

	public void drawBraceHighlights(int location, char c) {
		for (BraceHighlighter b : braceHighlights) {
			if (b.open == c)
				try {
					highlighter.addHighlight(location, b.findClose(
							pane.getDocument().getText(0,
									pane.getDocument().getLength()), location),
							b.color);
					if (b.close == c)
						highlighter.addHighlight(b.findOpen(pane.getDocument()
								.getText(0, pane.getDocument().getLength()),
								location), location, b.color);
				} catch (BadLocationException e) {
				}
		}
	}
}
