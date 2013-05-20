import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for Testing the model.
 * 
 * @author YeshwanthVenkatesh
 * 
 *         Reference: http://www.paulgraham.com/spam.html
 *         http://en.wikipedia.org/wiki/Bayesian_spam_filtering
 * */
public class Testing {

	String regex;
	Pattern splitWord;

	public Testing() {
		regex = "\\W";
		splitWord = Pattern.compile("\\w+");
	}

	/**
	 * We analyze only 15 interesting words in the spam email. Words are
	 * interesting based on there difference in there spam probability from 0.5
	 * 
	 * 
	 * @param string
	 *            containing the words in the spam email and the hash-map
	 *            Containing the attributes
	 * @return boolean
	 * */
	public boolean test(String stuff, HashMap<String, Attribute> attr) {

		ArrayList<Attribute> interestingTop15 = new ArrayList<Attribute>();

		// For every word in the String to be analyzed
		String[] stringList = stuff.split(regex);

		for (int i = 0; i < stringList.length; i++) {
			String s = stringList[i].toLowerCase();
			Matcher m = splitWord.matcher(s);
			// We ignore the word subject
			if (m.matches() && !s.equals("subject")) {

				Attribute a;

				// If the String is in our HashMap get the word out
				if (attr.containsKey(s)) {
					a = (Attribute) attr.get(s);
					// Otherwise add the word with a Spam probability of
					// 0.4;
				} else {
					a = new Attribute(s);
					a.setPSpam(0.4f);
				}

				// Assign the limit of 15 interesting words to be extracted.
				int limit = 15;
				// If this list is empty add the word.
				// this happens only the first time.
				if (interestingTop15.isEmpty()) {
					interestingTop15.add(a);
				} else {
					for (int j = 0; j < interestingTop15.size(); j++) {
						// get the word from the array list.
						Attribute newAttr = (Attribute) interestingTop15.get(j);
						// If the words are same then we terminate the loop
						if (a.getWord().equals(newAttr.getWord())) {
							break;
							// If the interestingness measure is greater we add
							// it to the list.
						} else if (a.isMoreinteresting() > newAttr
								.isMoreinteresting()) {
							interestingTop15.add(j, a);
							break;
							// If we reached the end, we add the word.
						} else if (j == interestingTop15.size() - 1) {
							interestingTop15.add(a);
						}
					}
				}

				// If the list is bigger than the limit, delete rest of the
				// entries
				while (interestingTop15.size() > limit)
					interestingTop15.remove(interestingTop15.size() - 1);

			}
		}

		float wordSpamicity = 1.0f;
		float negWordSpamicity = 1.0f;
		// For every word we multiply that words spam probability to
		// wordSpamicity. We also calculate 1-spam probability and multiply it
		// with negWordSpamicity.
		for (int i = 0; i < interestingTop15.size(); i++) {
			Attribute w = (Attribute) interestingTop15.get(i);
			wordSpamicity *= w.getPSpam();
			negWordSpamicity *= (1.0f - w.getPSpam());
		}

		float pEmailSpam = wordSpamicity / (wordSpamicity + negWordSpamicity);

		// If the formula yields a value greater than 0.9, the email is spam.
		if (pEmailSpam > 0.9f)
			return true;
		else
			return false;

	}

}
