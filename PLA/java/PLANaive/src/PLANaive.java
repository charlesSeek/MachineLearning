/*
 * Author:SHUCHENG CUI
 * source code: implementation of machine learning PLA Algorithm
 * Description:Each line of the data set contains one (Xn,Yn) 
 * The first 4 numbers of the line contains the components of Xn orderly, 
 * the last number is Yn. 
 * Initialize our algorithm with w=0 and take sign(0) as −1
 * Implement a version of PLA by visiting examples in the naive cycle using 
 * the order of examples in the data set. Run the algorithm on the data set. 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
public class PLANaive {
	public static void main(String[] args){
		BufferedReader br = null;
		double[] labelMatrix = new double[400];
		double[] dataMatrix = new double[2000];
		int counter = 0;
		try {
				br = new BufferedReader(new FileReader("train.txt"));
				String line;
				while ((line = br.readLine()) != null) {
					String[] newLine = line.trim().split("\\s+");
					labelMatrix[counter] = Double.valueOf(newLine[newLine.length-1]);
					dataMatrix[counter*5] = 1;
					for (int i=1;i<newLine.length;i++){
						dataMatrix[counter*5+i] = Double.valueOf(newLine[i-1]);
					}
					counter++;
				}
		} catch (IOException e){
			e.printStackTrace();
		}
		DenseMatrix64F dataDenseMatrix = new DenseMatrix64F(400,5,true,dataMatrix);
		DenseMatrix64F labelDenseMatrix = new DenseMatrix64F(400,1,true,labelMatrix);
		DenseMatrix64F weightDenseMatrix = new DenseMatrix64F(1,5,true,0,0,0,0,0);
		SimpleMatrix dataSimpleMatrix = SimpleMatrix.wrap(dataDenseMatrix);
		SimpleMatrix labelSimpleMatrix = SimpleMatrix.wrap(labelDenseMatrix);
		SimpleMatrix weightSimpleMatrix = SimpleMatrix.wrap(weightDenseMatrix);
		int num = 0;
		while (true){
			SimpleMatrix weightUpdate = weightUpdate(weightSimpleMatrix,dataSimpleMatrix,labelSimpleMatrix);
			if (weightUpdate.equals(weightSimpleMatrix)){
				System.out.println("find the line "+"w:"+weightUpdate);
				break;
			}
			num++;
			weightSimpleMatrix = weightUpdate;
				
		}
		System.out.println("weight is updated "+num+" times");
	}
	public static boolean hasError(SimpleMatrix weight,SimpleMatrix data,SimpleMatrix label){
		boolean result = true;
		for (int i=0;i<400;i++){
			SimpleMatrix currentRow = data.extractVector(true, i);
			double labelValue=label.extractVector(true, i).get(0);
			if ((currentRow.dot(weight))*labelValue <=0)
				result = false;
		}
		return result;
	}
	public static SimpleMatrix weightUpdate(SimpleMatrix weight,SimpleMatrix data,SimpleMatrix label){
		SimpleMatrix lastWeight = weight;
		for (int i=0;i<400;i++){
			SimpleMatrix row = data.extractVector(true, i);
			double labelValue = label.get(i, 0);
			if (row.dot(weight)*labelValue <=0){
				lastWeight = lastWeight.plus(labelValue, row);
				break;
			}
		}
		return lastWeight;
	}

}
