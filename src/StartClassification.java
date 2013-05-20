import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * We train the mode for both good and bad e-mails. Calculate the True Positive,
 * False Negative, False Positive and True Negative for evaluating the model.
 * 
 * 
 * @author YeshwanthVenkatesh
 * */
public class StartClassification {

	// Correctly Classified email's
	private static int correctlyCLASSIFIED = 0;

	// Incorrectly Classified email's
	private static int incorrectlyCLASSIFIED = 0;

	private static int TotalFILES = 0;

	private static int tp = 0; // True Positive
	private static int fn = 0; // False Negative
	private static int fp = 0; // False Positive
	private static int tn = 0; // True Negative

	public static void run(String path, int testID) {
		try {

			// Path to the input
			File inputDir = new File(path);

			// Check if the path exists
			// Quit the program if path is not found
			if (!inputDir.exists()) {
				System.out
						.println("Please enter a valid path to the directory");
				System.exit(0);
			}

			int testDataID = testID;

			// HashMap to store our attributes.
			HashMap<String, Attribute> attr = new HashMap<String, Attribute>();

			// Create an instance of both Training and Testing.
			Training trainData = new Training();
			Testing testData = new Testing();

			// Train on 9 parts and skip the one part for testing.
			for (int i = 1; i <= 10; i++) {

				// Train only if the folder is not the test folder.
				if (i != testDataID) {
					File inputTrainingDir = new File(inputDir.getAbsolutePath()
							+ "/part" + i);

					// For each file inside the training folder we test it.
					for (final File inputFile : inputTrainingDir.listFiles()) {
						if (inputFile.getName().contains("legit"))
							// Train good email
							trainData.trainGood(inputFile.getAbsolutePath(),
									attr);

						else
							// Train bad email
							trainData.trainSpam(inputFile.getAbsolutePath(),
									attr);

					}

				}

			}

			// We are finished training.
			// Now we finalize the training.
			trainData.finalizeTraining(attr);

			// Create file object for the test folder.
			File inputTestDir = new File(inputDir.getAbsolutePath() + "/part"
					+ testDataID);

			// For each file inside the test folder, we test our model.
			for (final File inputFile : inputTestDir.listFiles()) {
				FileReader fr = new FileReader(inputFile.getAbsolutePath());
				// Get the list of words in the email.
				String stuff = fr.getContent();

				// We analyze the email.
				boolean spam = testData.test(stuff, attr);

				if (inputFile.getName().contains("legit") && !spam) {
					tp++;
					correctlyCLASSIFIED++;
				} else if (inputFile.getName().contains("legit") && spam) {
					fn++;
					incorrectlyCLASSIFIED++;
				} else if (inputFile.getName().contains("spms") && !spam) {
					fp++;
					incorrectlyCLASSIFIED++;
				} else if (inputFile.getName().contains("spms") && spam) {
					tn++;
					correctlyCLASSIFIED++;
				}

			}

			TotalFILES += inputTestDir.listFiles().length;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getCorrectlyCLASSIFIED() {
		return correctlyCLASSIFIED;
	}

	public static int getIncorrectlyCLASSIFIED() {
		return incorrectlyCLASSIFIED;
	}

	public static int getTotalFILES() {
		return TotalFILES;
	}

	public static int getTP() {
		return tp;
	}

	public static int getFN() {
		return fn;
	}

	public static int getFP() {
		return fp;
	}

	public static int getTN() {
		return tn;
	}

}
