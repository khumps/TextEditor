import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextStyle
	{
		private JEditorPane pane;
		private HashSet<Highlight> highlights;
		private Highlighter highlighter;

		public TextStyle(JEditorPane pane)
			{
				this.pane = pane;
				this.highlights = new HashSet<Highlight>();
				this.highlighter = pane.getHighlighter();
			}

		public TextStyle(JEditorPane pane, HashSet<Highlight> highlights)
			{
				this.pane = pane;
				this.highlights = highlights;
			}

		public void drawHighlight() {
			highlighter.removeAllHighlights();
			for (Highlight h : highlights)
				{
					int length = h.text.length();
					for (Integer i : h.locations)
						{
							try
								{
									highlighter.addHighlight(i, i + length, h.color);
								}
							catch (BadLocationException e)
								{
								}
						}
				}
		}

		public void addHighlight(String text, Color color) {
			highlights.add(new Highlight(text, new DefaultHighlightPainter(color)));
		}

		public void updateHighlights() {
			for (Highlight h : highlights)
				{
					try
						{
							h.removeHighlights(pane.getDocument().getText(0,
									pane.getDocument().getLength()));
							h.findHighlights(pane.getDocument().getText(0,
									pane.getDocument().getLength()));
						}
					catch (BadLocationException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		}
	}
