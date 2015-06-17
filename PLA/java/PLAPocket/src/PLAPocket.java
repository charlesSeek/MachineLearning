import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
public class PLAPocket {
	public static void main(String[] args){
		double rate = 0.5;
		double errorRate = 0;
		for (int i=0;i<2000;i++){
			SimpleMatrix data = loadData("train_pocket.txt");
			int row = data.numRows();
			int col = data.numCols()-1;
			SimpleMatrix dataSimpleMatrix = data.extractMatrix(0, row, 0, col);
			SimpleMatrix labelSimpleMatrix = data.extractVector(false, col);
			DenseMatrix64F weightDenseMatrix = new DenseMatrix64F(1,5,true,0,0,0,0,0);
			SimpleMatrix weightSimpleMatrix = SimpleMatrix.wrap(weightDenseMatrix);
			int counter = 0;
			int minError = 1000;
			SimpleMatrix optimizedWeight = weightSimpleMatrix;
			while (true && counter<50){
				SimpleMatrix weightUpdate = weightUpdate(weightSimpleMatrix,dataSimpleMatrix,labelSimpleMatrix,rate);
				int errCount = errorCalculate(weightSimpleMatrix,dataSimpleMatrix,labelSimpleMatrix);
				//System.out.println("error:"+errCount);
				if (errCount < minError) {
					minError = errCount;
					optimizedWeight = weightUpdate;
				}
				if (weightUpdate.equals(weightSimpleMatrix)){
					System.out.println("find the line "+"w:"+weightUpdate);
					break;
				}
				counter++;
				weightSimpleMatrix = weightUpdate;
					
			}
			SimpleMatrix test = loadData("test_pocket.txt");
			int testRow = test.numRows();
			int testCol = test.numCols()-1;
			int errors = 0;
			SimpleMatrix testSimpleMatrix = test.extractMatrix(0, testRow, 0, testCol);
			SimpleMatrix testLabelSimpleMatrix = test.extractVector(false, testCol);
			SimpleMatrix result = optimizedWeight.mult(testSimpleMatrix.transpose());
			for (int j=0;j<testRow;j++){
				if (sign(result.get(j)) != testLabelSimpleMatrix.get(j))
					errors++;
			}
			errorRate = errorRate + (double) errors/testRow;
		
		}
		System.out.println("the average test error rate:"+(double) errorRate/2000);
	}
	public static int errorCalculate(SimpleMatrix weight,SimpleMatrix data,SimpleMatrix label){
		int counter = 0;
		for (int i=0;i<data.numRows();i++){
			SimpleMatrix row = data.extractVector(true, i);
			double labelValue = label.get(i, 0);
			if (sign(row.dot(weight))*labelValue < 0){
				counter = counter + 1;
			}
		}
		return counter;
	}
	public static SimpleMatrix weightUpdate(SimpleMatrix weight,SimpleMatrix data,SimpleMatrix label,double rate){
		SimpleMatrix lastWeight = weight;
		for (int i=0;i<data.numRows();i++){
			SimpleMatrix row = data.extractVector(true, i);
			double labelValue = label.get(i, 0);
			if (sign(row.dot(weight))*labelValue < 0){
				lastWeight = lastWeight.plus(labelValue*rate, row);
				break;
			}
		}
		return lastWeight;
	}
	@SuppressWarnings("resource")
	public static SimpleMatrix loadData(String fileName){
		BufferedReader br = null;
		int counter = 0;
		List<String> dataList = new ArrayList<String>();
		try {
			String line;
			br = new BufferedReader(new FileReader(fileName));
			while((line = br.readLine()) !=null){
				String newline = "1 "+line;
				dataList.add(newline);
				
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		Collections.shuffle(dataList);
		int row = dataList.size();
		int col = dataList.get(0).trim().split("\\s+").length;
		int length = row*col;
		double[] dataMatrix = new double[length];
		counter = 0;
		for (String str:dataList){
			String[] newLine = str.trim().split("\\s+");
			for (int i=0;i<newLine.length;i++){
				dataMatrix[counter*col+i] = Double.valueOf(newLine[i]);
			}
			counter++;
		}
		
		DenseMatrix64F dataDenseMatrix = new DenseMatrix64F(row,col,true,dataMatrix);
		SimpleMatrix mydataMatrix = SimpleMatrix.wrap(dataDenseMatrix);
		return mydataMatrix;

	}

	public static double sign(double n){
		if (n>0)
			return 1.0;
		else
			return -1.0;
	}
}
