import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

public class CodeStyler {
	protected HashSet<TextHighlighter> textHighlights;
	private Highlighter highlighter;
	protected SelectionHighlighter selection;
	private HashSet<BraceHighlighter> braceHighlights;
	private HashSet<Character> charHighlights;
	protected JTextPane pane;

	public CodeStyler(JTextPane pane) {
		this.pane = pane;
		this.textHighlights = new HashSet<TextHighlighter>();
		this.braceHighlights = new HashSet<BraceHighlighter>();
		this.charHighlights = new HashSet<Character>();
		this.highlighter = pane.getHighlighter();
		selection = new SelectionHighlighter("", "<b>", "</b>");
	}

	public SelectionHighlighter getSelection() {
		return selection;
	}

	public HashSet<Character> getCharHighlights() {
		return charHighlights;
	}

	/*
	 * public void drawTextHighlights() { highlighter.removeAllHighlights(); //
	 * FIX int length = selection.text.length(); for (Integer i :
	 * selection.locations) { try { if (i == selection.initialSelection)
	 * highlighter.addHighlight(i, i + length, selection.initHighlighter); else
	 * highlighter.addHighlight(i, i + length, selection.color); } catch
	 * (BadLocationException e) { e.printStackTrace(); } } for (TextHighlighter
	 * h : textHighlights) { pane.setText(t.); } }
	 */

	public void updateHighlights() {
		try {
			String text = pane.getDocument().getText(0,
					pane.getDocument().getLength());
			for (TextHighlighter h : textHighlights) {
				text = pane.getDocument().getText(0,
						pane.getDocument().getLength());
				pane.setText(h.updateHighlights(text));

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

	public void addHighlight(String preFormat, String postFormat,
			String... text) {
		for (String s : text)
			textHighlights.add(new TextHighlighter(s, preFormat, postFormat));
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
}
