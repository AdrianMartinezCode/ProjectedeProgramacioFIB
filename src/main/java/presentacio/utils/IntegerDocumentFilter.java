package presentacio.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Serveix per a poder filtrar la introducció de caràcters que no siguin numèrics a un camp
 * 	de text que utilitzi aquest tipus de filtre.
 */
public class IntegerDocumentFilter extends DocumentFilter {
	
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		try {
			Integer.valueOf(text);
			super.replace(fb, offset, length, text, attrs);
		} catch (NumberFormatException e) {}
		
	}
}
