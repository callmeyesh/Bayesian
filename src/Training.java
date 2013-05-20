import java.io.IOException;
import java.util.*;
import java.util.regex.*;

/**
 * Class for Training the model.
 * 
 * @author YeshwanthVenkatesh
 * */
public class Training {

	String regex;
	Pattern splitWord;

	int spamTotal;// Total bad words
	int goodTotal;// Total good words

	public Training() {
		regex = "\\W";
		splitWord = Pattern.compile("\\w+");
		spamTotal = 0;
		goodTotal = 0;
	}

	/**
	 * @param file
	 *            marked as "spms" and the hash-map to store the attributes
	 * @return null
	 * */
	public void trainSpam(String file, HashMap<String, Attribute> attr)
			throws IOException {
		FileReader reader = new FileReader(file);

		// Read the content and put each word in a string array
		String emailContent = reader.getContent();
		String[] stringList = emailContent.split(regex);

		// For every word in the array
		for (int i = 0; i < stringList.length; i++) {
			String s = stringList[i].toLowerCase();
			Matcher m = splitWord.matcher(s);
			// We ignore the word subject
			if (m.matches() && !s.equals("subject")) {
				spamTotal++;
				// If it exists in the HashMap already
				// Increment the count
				if (attr.containsKey(s)) {
					Attribute w = (Attribute) attr.get(s);
					w.incrementCountBad();
					// Otherwise add it
				} else {
					Attribute w = new Attribute(s);
					w.incrementCountBad();
					attr.put(s, w);
				}
			}
		}
	}

	/**
	 * @param file
	 *            marked as "legit" and the hash-map to store the attributes
	 * @return null
	 * */
	public void trainGood(String file, HashMap<String, Attribute> attr)
			throws IOException {
		FileReader reader = new FileReader(file);

		// Read the content and put each word in a string array
		String emailContent = reader.getContent();
		String[] stringList = emailContent.split(regex);

		// For every word in the array
		for (int i = 0; i < stringList.length; i++) {
			String s = stringList[i].toLowerCase();
			Matcher m = splitWord.matcher(s);
			// We ignore the word subject
			if (m.matches() && !s.equals("subject")) {
				goodTotal++;
				// If it exists in the HashMap already
				// Increment the count
				if (attr.containsKey(s)) {
					Attribute w = (Attribute) attr.get(s);
					w.incrementCountGood();
					// Otherwise add it
				} else {
					Attribute w = new Attribute(s);
					w.incrementCountGood();
					attr.put(s, w);
				}
			}
		}
	}

	/**
	 * This method calculates are the spam probability of each word in the hash
	 * map
	 * 
	 * @param hash
	 *            -map which contains all the words.
	 * @return null
	 * */
	public void finalizeTraining(HashMap<String, Attribute> words) {
		Iterator<Attribute> a = words.values().iterator();
		while (a.hasNext()) {
			Attribute w = (Attribute) a.next();
			w.calcGoodProb(goodTotal);
			w.calcBadProb(spamTotal);
			w.calcSpamProb();
		}
	}
}
