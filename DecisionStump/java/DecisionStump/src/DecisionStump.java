import java.util.Random;


public class DecisionStump {
	public static void main(String[] args){
		double errorInTotal = 0;
		double errorOutTotal = 0;
		double coefficient;
		double threshold;
		for (int i=0;i<5000;i++){
			double[] input = new double[20];
			double[] output = new double[20];
			double[] result = new double[3];
			input = getTrainingData(20);
			output = getOutput(input);
			result = decisionStump(input,output);
			errorInTotal = errorInTotal + result[0];
			errorOutTotal = errorOutTotal + calErrOut(result[1],result[2]);
		}
		System.out.println("the average in sample error:"+errorInTotal/5000);
		System.out.println("the average out sample error:"+errorOutTotal/5000);
	}
	public static double[] decisionStump(double[] input,double[] output){
		double[] errPositive = new double[3];
		double[] errNegative = new double[3];
		errPositive = getMinErrIn(input,output,1);
		errNegative = getMinErrIn(input,output,-1);
		if (errPositive[0] < errNegative[0]){
			return errPositive;
		} else
			return errNegative;
	}
	public static double sign (double x){
		if (x<=0)
			return -1.0;
		else
			return 1.0;
	}
	public static double[] getTrainingData(int n){
		double[] x = new double[n];
		Random random = new Random();
		for(int i=0;i<n;i++)
			x[i] = -1 + 2*random.nextDouble();
		return x;
	}
	public static double[] getOutput(double[] input){
		double[] y = new double[20];
		Random random = new Random();
		for (int i=0;i<20;i++){
			double rand = random.nextDouble();
			if (rand<=0.2)
				y[i] = -1*sign(input[i]);
			else
				y[i] = sign(input[i]);
		}
		return y;
	}
	public static double[] getMinErrIn(double[] input,double[] output,double s){
		double minErrIn = 1;
		double currErrIn;
		double coef;
		double threshold;
		double[] result = new double[3];
		for (int i=0;i<input.length-1;i++){
			coef = s;
			threshold = (input[i] + input[i+1])/2;
			currErrIn = calErrIn(input,output,coef,threshold);
			if (currErrIn < minErrIn){
				minErrIn = currErrIn;
				result[0] = minErrIn;
				result[1] = coef;
				result[2] = threshold;
			}
		}
		return result;
	}
	public static double calErrIn(double[] input,double[] output,double coef,double threshold){
		double errCount = 0;
		for (int i=0;i<input.length;i++){
			if (output[i]!=coef*sign(input[i]-threshold))
				errCount++;
		}
		return errCount/input.length;
	}
	public static double calErrOut(double coef,double threshold){
		return 0.5+0.3*(Math.abs(threshold)-1);
	}
}
