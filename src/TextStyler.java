import java.awt.Color;
import java.util.HashSet;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextStyler
	{
		private JEditorPane pane;
		private HashSet<Highlight> highlights;
		private Highlighter highlighter;

		public TextStyler(JEditorPane pane)
			{
				this.pane = pane;
				this.highlighter = pane.getHighlighter();
			}

		public TextStyler(JEditorPane pane, HashSet<Highlight> highlights)
			{
				this.pane = pane;
				this.highlights = highlights;
			}

		public void drawHighlight() {

			for (Highlight h : highlights)
				{
					int length = h.text.length() - 1;
					for (Integer i : h.locations)
						{
							try
								{
									highlighter.addHighlight(i, length, h.color);
								}
							catch (BadLocationException e)
								{
								}
						}
				}
		}

		public void newHighlight(String text, Color color) {
			highlights.add(new Highlight(pane.getText(), text, new DefaultHighlightPainter(color)));
		}
	}
