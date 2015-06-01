import java.awt.Color;
import java.util.HashSet;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

public class CodeStyler
    {
	private HashSet<TextHighlighter> textHighlights;
	private Highlighter highlighter;

	public HashSet<Character> getCharHighlights()
	    {
		return charHighlights;
	    }

	private HashSet<BraceHighlighter> braceHighlights;
	private HashSet<Character> charHighlights;
	private JTextPane pane;

	public CodeStyler(JTextPane pane)
	    {
		this.pane = pane;
		this.textHighlights = new HashSet<TextHighlighter>();
		this.braceHighlights = new HashSet<BraceHighlighter>();
		this.charHighlights = new HashSet<Character>();
		this.highlighter = pane.getHighlighter();
	    }

	public CodeStyler(JTextPane pane, HashSet<TextHighlighter> highlights)
	    {
		this.pane = pane;
		this.textHighlights = highlights;
	    }

	public void drawTextHighlights()
	    {
		highlighter.removeAllHighlights();
		for (TextHighlighter h : textHighlights)
		    {
			int length = h.text.length();
			for (Integer i : h.locations)
			    {
				try
				    {
					highlighter.addHighlight(i, i + length, h.color);
				    } catch (BadLocationException e)
				    {
				    }
			    }
		    }
	    }

	public void updateHighlights()
	    {
		for (TextHighlighter h : textHighlights)
		    {
			try
			    {
				h.updateHighlights(pane.getDocument().getText(0,
					pane.getDocument().getLength()));
			    } catch (BadLocationException e)
			    {
			    }
			;
		    }
	    }

	public void addHighlight(Color color, String... text)
	    {
		for (String s : text)
		    textHighlights.add(new TextHighlighter(s, new DefaultHighlightPainter(color)));
	    }

	public void addBraceHighlight(char open, char close, Color color)
	    {
		braceHighlights.add(new BraceHighlighter(open, close, new DefaultHighlightPainter(
			color)));
		charHighlights.add(open);
		charHighlights.add(close);
	    }

	public void drawBraceHighlights(int location, char c)
	    {
		for (BraceHighlighter b : braceHighlights)
		    {
			if (b.open == c)
			    try
				{
				    highlighter.addHighlight(location, b.findClose(
					    pane.getDocument().getText(0,
						    pane.getDocument().getLength()), location),
					    b.color);
				    if (b.close == c)
					highlighter.addHighlight(b.findOpen(pane.getDocument()
						.getText(0, pane.getDocument().getLength()),
						location), location, b.color);
				} catch (BadLocationException e)
				{
				}
		    }
	    }
    }
