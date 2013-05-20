/**
 * 
 * Class describing the word and its spam probability
 * 
 * @author YeshwanthVenkatesh
 * */
public class Attribute {
	private String word; // The String itself.
	private int numBad; // The total times it appears in "bad" messages.
	private int numGood; // The total times it appears in "good" messages.
	private float freqBad; // bad count / total bad words
	private float freqGood; // good count / total good words
	private float probSpam; // Spam probability of the word.

	public Attribute(String s) {
		word = s;
		numBad = 0;
		numGood = 0;
		freqBad = 0.0f;
		freqGood = 0.0f;
		probSpam = 0.0f;
	}

	public void incrementCountBad() {
		numBad++;
	}

	public void incrementCountGood() {
		numGood++;
	}

	public void calcBadProb(int total) {
		if (total > 0)
			freqBad = numBad / (float) total;
	}

	public void calcGoodProb(int total) {
		if (total > 0)
			freqGood = 2 * numGood / (float) total;
	}

	// Baye's rules.
	public void calcSpamProb() {
		if (freqGood + freqBad > 0)
			probSpam = freqBad / (freqBad + freqGood);
		if (probSpam < 0.01f)
			probSpam = 0.01f;
		else if (probSpam > 0.99f)
			probSpam = 0.99f;
	}

	// The "interesting" rating for a word.
	public float isMoreinteresting() {
		return Math.abs(0.5f - probSpam);
	}

	// Some getters and setters
	public float getfreqGood() {
		return freqGood;
	}

	public float getfreqBad() {
		return freqBad;
	}

	public float getPSpam() {
		return probSpam;
	}

	public void setPSpam(float f) {
		probSpam = f;
	}

	public String getWord() {
		return word;
	}

}
