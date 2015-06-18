import java.util.Random;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;


public class LinearRegression {
	public static void main(String[] args){
		SimpleMatrix dataSimpleMatrix = getRandomData(1000);
		SimpleMatrix traindataMatrix = dataSimpleMatrix.extractMatrix(0, 1000, 0, 3);
		SimpleMatrix outputMatrix = dataSimpleMatrix.extractVector(false, 3);
		SimpleMatrix weight = traindataMatrix.pseudoInverse().mult(outputMatrix);
		SimpleMatrix predictMatrix = traindataMatrix.mult(weight);
		int errCount = 0;
		for (int i=0;i<1000;i++){
			if (sign(predictMatrix.get(i))!=outputMatrix.get(i))
				errCount++;
		}
		System.out.println("error rate:"+(double) errCount/1000);
	}
	public static double sign(double n){
		if (n<=0)
			return -1;
		else
			return 1;
	}
	public static SimpleMatrix getRandomData(int n){
		double [] data = new double[4*n];
		Random random = new Random();
		for (int i=0;i<n;i++){
			double y;
			data[i*4] = 1;
			double x1 = -1+2*random.nextDouble();
			double x2 = -1+2*random.nextDouble();
			double flip = random.nextDouble();
			if (flip<=0.1)
				y = sign(x1*x1+x2*x2-0.6)*(-1);
			else
				y = sign(x1*x1+x2*x2-0.6);
			data[i*4+1] = x1;
			data[i*4+2] = x2;
			data[i*4+3] = y;
 		}
		DenseMatrix64F dataDenseMatrix = new DenseMatrix64F(1000,4,true,data);
		return SimpleMatrix.wrap(dataDenseMatrix);
		
	}
}
