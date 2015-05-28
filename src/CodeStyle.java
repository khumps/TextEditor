import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class CodeStyle
	{
		private HashSet<TextHighlight> textHighlights;
		private Highlighter highlighter;

		public HashSet<Character> getCharHighlights() {
			return charHighlights;
		}

		private HashSet<BraceHighlight> braceHighlights;
		private HashSet<Character> charHighlights;
		private JEditorPane pane;

		public CodeStyle(JEditorPane pane)
			{
				this.pane = pane;
				this.textHighlights = new HashSet<TextHighlight>();
				this.braceHighlights = new HashSet<BraceHighlight>();
				this.charHighlights = new HashSet<Character>();
				this.highlighter = pane.getHighlighter();
			}

		public CodeStyle(JEditorPane pane, HashSet<TextHighlight> highlights)
			{
				this.pane = pane;
				this.textHighlights = highlights;
			}

		public void drawTextHighlights() {
			highlighter.removeAllHighlights();
			for (TextHighlight h : textHighlights)
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

		public void updateHighlights() {
			for (TextHighlight h : textHighlights)
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

		public void addHighlight(Color color, String... text) {
			for (String s : text)
				textHighlights.add(new TextHighlight(s, new DefaultHighlightPainter(color)));
		}

		public void addBraceHighlight(char open, char close, Color color) {
			braceHighlights
					.add(new BraceHighlight(open, close, new DefaultHighlightPainter(color)));
			charHighlights.add(open);
			charHighlights.add(close);
		}

		public void drawBraceHighlights(int location, char c) {
			for (BraceHighlight b : braceHighlights)
				{
					if (b.open == c)
						try
							{
								highlighter.addHighlight(
										location,
										b.findClose(
												pane.getDocument().getText(0,
														pane.getDocument().getLength()), location),
										b.color);
								if (b.close == c)
									highlighter.addHighlight(b.findOpen(
											pane.getDocument().getText(0,
													pane.getDocument().getLength()), location),
											location, b.color);
							}
						catch (BadLocationException e)
							{
							}
				}
		}
	}
