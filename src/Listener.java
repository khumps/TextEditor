import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Listener extends MouseAdapter implements ActionListener
    {
	protected Editor editor;

	public Listener(Editor editor)
	    {
		this.editor = editor;
	    }

	@Override
	public void actionPerformed(ActionEvent arg0)
	    {
		// TODO Auto-generated method stub

	    }

    }
