import java.util.*;

/**
 * This class implements a single labeled datum, which has a list of features
 * 
 * @author Ben Holtz
 *
 */
public class Datum {

	// Instance variables
	/* A list of features, given as doubles */
	private List<Double> features;
	
	/* The label to which this datum belongs */
	private int label;
	
	/* The line of text that was used to generate this feature. Left in for 
	 * the purposes of testing, should you want it
	 */
	private String line;
	
	/**
	 * This constructor builds a new Datum to represent the datum given by line. 
	 * You do not need to modify this unless you want to change the datum class.
	 * @param line The line of text that represents the datum
	 */
	public Datum(String line) {
		this.line = line;
		String[] arr = line.split("\t");
		
		int myInt = Integer.parseInt(arr[0]);
		this.label = myInt;
		//System.out.println(myInt);
		
		this.features = new ArrayList<Double>();
		for (int i = 1; i < arr.length; i++) {
			Double myDouble = Double.parseDouble(arr[i]);
			//System.out.println(myDouble);
			this.features.add(myDouble);
		}
		//System.out.println("");
	}
	
	/**
	 * In order to properly implement kNN, we need to make sure that we have some notion
	 * of distance that allows us to gauge which neighbors are the nearest.
	 * @param otherDatum Some other datum in the data set 
	 * @return The distance by some metric between this datum and otherDatum. 
	 * If this datum and otherDatum are equal, this should return 0.
	 */
	public double distanceTo(Datum otherDatum) {
		List<Double> otherDatumFeatures = otherDatum.features;
		
		double sum = 0;
		for (int i=0; i<features.size(); i++) {
			sum += Math.pow(features.get(i)-otherDatumFeatures.get(i),2);
		}
		
		return Math.sqrt(sum);
	}
	
	public int getLabel() {
		return this.label;
	}
	
	@Override
	public String toString() {
		return label + "\t" + features;
	}
	
}
