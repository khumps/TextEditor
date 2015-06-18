import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

/**
 * Takes care of all highlighting that is done on the editor pane whether it be
 * highlighting the selection, matching words, keywords, or braces
 * 
 * @author Kevin Humphreys
 *
 */
public class CodeStyler {
	protected HashSet<TextHighlighter> textHighlights; // Stores all of the
														// Strings that should
														// be highlighted on the
														// screen
	private Highlighter highlighter; // Reference to pane.getHighlighter() for
										// cleaner code
	protected SelectionHighlighter selection; // Takes care of highlighting the
												// text that the user has
												// selected as well as any
												// occurrences of that text
	private HashSet<BraceHighlighter> braceHighlights; // Stores all of the
														// different types of
														// braces to be
														// highlighted and
														// highlights them when
														// selected
	private HashSet<Character> charHighlights;
	protected JTextPane pane; // The Text Pane that all of the highlighting will
								// show on

	public CodeStyler(JTextPane pane) {
		this.pane = pane;
		this.textHighlights = new HashSet<TextHighlighter>();
		this.braceHighlights = new HashSet<BraceHighlighter>();
		this.charHighlights = new HashSet<Character>();
		this.highlighter = pane.getHighlighter();
		selection = new SelectionHighlighter("", Color.YELLOW, Color.LIGHT_GRAY);
	}

	public void drawTextHighlights() // Draws all text highlights
	{
		highlighter.removeAllHighlights();
		int length = selection.text.length();
		for (Integer i : selection.locations) {
			try {
				if (i == selection.initialSelection)
					highlighter.addHighlight(i, i + length,
							selection.initHighlighter);
				else
					highlighter.addHighlight(i, i + length, selection.color);
			} catch (BadLocationException e) {
			}
		}
		for (TextHighlighter h : textHighlights) {
			length = h.text.length();
			/* Adds new highlights */
			for (Integer i : h.locations) {
				try {
					highlighter.addHighlight(i, i + length, h.color);
				} catch (BadLocationException e) {
				}
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
			if (!selection.refactoring) {
				String selected = "";
				selection.setText(selected);
				if (pane.getSelectedText() != null) {
					selected = pane.getSelectedText();
					// System.out.println("ran");
					// System.out.println(selected);
					selection.setInit(pane.getCaretPosition());
					selection.setText(selected);
					selection.updateHighlights(text);
				}
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
					String text = pane.getDocument().getText(0,
							pane.getDocument().getLength());
					int temp = b.findClose(text, location);
					if (Utils.validIndex(text, temp))
						highlighter.addHighlight(location, temp, b.color);
					if (b.close == c)
						highlighter.addHighlight(location, location, b.color);
					highlighter.addHighlight(b.findOpen(pane.getDocument()
							.getText(0, pane.getDocument().getLength()),
							location), location, b.color);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
		}
	}

	public SelectionHighlighter getSelection() {
		return selection;
	}

	public HashSet<Character> getCharHighlights() {
		return charHighlights;
	}
}
