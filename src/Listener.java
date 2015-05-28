import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Listener extends MouseAdapter implements ActionListener
	{
		protected Editor editor;

		public Listener(Editor editor)
			{
				this.editor = editor;
			}

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			if (command.equals("timer"))
				{
					braceHighlights();
					editor.style.updateHighlights();
					editor.style.drawTextHighlights();
				}

			if (command.equals("openFile")) editor.openFileChooser();
			if (command.equals("saveFile")) editor.saveFileChooser();
				{

				}

		}

		private void braceHighlights() {
			char c = '\0';
			Document d = editor.getDocument();
			try
				{
					if (editor.getCaretPosition() < d.getLength() && editor.getCaretPosition() > 0)
						c = d.getText(0, editor.getDocument().getLength()).charAt(
								editor.getCaretPosition());
				}
			catch (BadLocationException e1)
				{
				}
			if (editor.style.getCharHighlights().contains(c))
				{
					editor.style.drawBraceHighlights(editor.getCaretPosition(), c);
				}
		}
	}
