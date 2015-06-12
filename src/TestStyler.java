import javax.swing.JTextPane;


public class TestStyler extends CodeStyler {

	public TestStyler(JTextPane pane) {
		super(pane);
		// TODO Auto-generated constructor stub
	}
	
	public void drawHighlights()
	{
		for(TextHighlighter t: super.textHighlights)
		{
			for(Integer i: t.locations)
			{
				super.pane.setText(pane.);
			}
		}
	}

}
