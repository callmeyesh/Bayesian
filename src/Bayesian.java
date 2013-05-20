/**
 * Main driver class. The input contains 10 folder for 10-fold cross validation.
 * Invoke the Start Classifier to perform 10-fold cross validation. Calculate
 * the Accuracy,Precision,Error Rate, Recall and Specificity of the model. Print
 * the results.
 * 
 * @author YeshwanthVenkatesh
 * */
public class Bayesian {
	public static void main(String args[]) {

		// Path to the folder which contains,
		// the 10 part data set.
		if (args.length != 1) {
			System.out.println("Please enter only the path to the data set");
			System.out.println("Eg : /pu1_encoded/stop");
			System.exit(0);
		}
		String path = args[0];

		// We perform the 10-fold validation.
		for (int i = 1; i <= 10; i++) {
			// The id of the folder which we are going
			// to test the model on.
			int testID = i;
			StartClassification.run(path, testID);
		}

		// Display the results.
		System.out.println("RESULT: ");
		System.out.println("-------------------------------");
		System.out.println("-------------------------------");
		System.out.println("Data Set : " + path);
		System.out.println("Correctly Classified   = "
				+ StartClassification.getCorrectlyCLASSIFIED());
		System.out.println("Incorrectly Classified = "
				+ StartClassification.getIncorrectlyCLASSIFIED());
		System.out.println("Total Files = "
				+ StartClassification.getTotalFILES());
		int tp = StartClassification.getTP();
		int tn = StartClassification.getTN();
		int fp = StartClassification.getFP();
		int fn = StartClassification.getFN();
		int p = tp + fn;
		int n = tn + fp;
		System.out.println("-------------------------------");
		System.out.println("Total HAM (P)  = " + p);
		System.out.println("Total SPAM (N) = " + n);
		System.out.println("True Positive  = " + StartClassification.getTP());
		System.out.println("False Negative = " + StartClassification.getFN());
		System.out.println("False Positve  = " + StartClassification.getFP());
		System.out.println("True Negative  = " + StartClassification.getTN());

		System.out.println("-------------------------------");
		System.out.println("EVALUATION: ");

		// Accuracy
		System.out.println("Accuracy   = " + ((double) (tp + tn)) / (p + n));

		// Precision
		double precision = ((double) tp) / (tp + fp);
		System.out.println("Precision   = " + precision);

		// Error Rate
		System.out.println("Error Rate  = " + ((double) (fp + fn)) / (p + n));

		// Recall
		double recall = ((double) tp) / p;
		System.out.println("Recall      = " + recall);

		// Specificity
		System.out.println("Specificity = " + (((double) tn) / n));
		System.out.println("-------------------------------");

	}
}
