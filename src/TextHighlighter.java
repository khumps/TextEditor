import java.util.HashSet;
import java.util.Iterator;

public class TextHighlighter {
	protected HashSet<Integer> locations = new HashSet<Integer>();
	protected String text;
	protected String preFormat; // Code put before text to format it
	protected String postFormat; // Code put after text to format it

	public TextHighlighter(String text, String preText, String postText) {
		this.text = text;
		preFormat = preText;
		postFormat = postText;
	}

	public void findHighlights(String toSearch) {
		int i = 0;
		while (toSearch.indexOf(text, i) != -1) {
			i = toSearch.indexOf(text, i);

			if ((!Utils.validIndex(toSearch, i - 1) || (Utils.validIndex(
					toSearch, i - 1) && isValidChar(toSearch.charAt(i - 1))))
					&& (!Utils.validIndex(toSearch, i + text.length()) || (Utils
							.validIndex(toSearch, i + text.length()) && isValidChar(toSearch
							.charAt(i + text.length())))))
				locations.add(i);

			i += text.length();
		}
	}

	public void removeHighlights(String toSearch) {
		Iterator<Integer> iterator = locations.iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			if ((Utils.validIndex(toSearch, i - 1) && !isValidChar(toSearch
					.charAt(i - 1)))
					|| (Utils.validIndex(toSearch, i + text.length()) && !isValidChar(toSearch
							.charAt(i + text.length())))) {
				iterator.remove();
			} else if (toSearch.indexOf(text, i) != i) {
				iterator.remove();
			}
		}
	}

	public String updateHighlights(String text) {
		removeHighlights(text);
		findHighlights(text);
		String temp = text
				.replaceAll(this.text, preFormat + this.text + postFormat)
				.replaceAll(preFormat + preFormat, preFormat)
				.replaceAll(postFormat + postFormat, postFormat);
		System.out.println(temp);
		return temp;
	}

	public boolean isValidChar(char c) {
		return (Character.isWhitespace(c) || c == '(' || c == ')' || c == '{' || c == '}');
	}

	public void setText(String text) {
		locations.clear();
		this.text = text;
	}
}
