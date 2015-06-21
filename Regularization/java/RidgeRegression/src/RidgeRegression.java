import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;


public class RidgeRegression {
	public static void main(String[] args){
		SimpleMatrix trainDataSimpleMatrix = loadData("train.txt");
		SimpleMatrix testDataSimpleMatrix = loadData("test.txt");
		SimpleMatrix weight = getRidgeRegressionWeight(trainDataSimpleMatrix);
		double errInRate = calculatErrorRate(weight,trainDataSimpleMatrix);
		double errOutRate = calculatErrorRate(weight,testDataSimpleMatrix);
		System.out.println("error rate in sample:"+errInRate);
		System.out.println("error rate out sample:"+errOutRate);
	}
	public static double sign(double n){
		if (n<=0)
			return -1;
		else
			return 1;
	}
	public static double calculatErrorRate(SimpleMatrix w,SimpleMatrix data){
		int rowNum = data.numRows();
		int colNum = data.numCols();
		SimpleMatrix xdata = data.extractMatrix(0, rowNum, 0, colNum-1);
		SimpleMatrix ydata = data.extractVector(false, colNum-1);
		int errCount = 0;
		for (int i=0;i<rowNum;i++){
			double predict = sign(xdata.extractVector(true, i).dot(w.transpose()));
			if (predict!=ydata.get(i))
				errCount++;
;		}
		return (double) errCount/rowNum;
	}
	
	public static SimpleMatrix getRidgeRegressionWeight(SimpleMatrix data){
		int rowNum = data.numRows();
		int colNum = data.numCols();
		SimpleMatrix xdata = data.extractMatrix(0, rowNum, 0, colNum-1);
		SimpleMatrix ydata = data.extractVector(false, colNum-1);
		SimpleMatrix weight = xdata.transpose().mult(xdata).plus(SimpleMatrix.identity(colNum-1).scale(10)).invert().mult(xdata.transpose()).mult(ydata);
		return weight;
	}
	public static SimpleMatrix loadData(String fileName){
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		try {
				br = new BufferedReader(new FileReader(fileName));
				String line = null;
				while((line = br.readLine())!=null){
					dataList.add(line.trim());
				}
				br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		int rowNum = dataList.size();
		int colNum = dataList.get(0).split("\\s+").length+1;
		double[] dataMatrix = new double[rowNum*colNum];
		for (int i=0;i<rowNum;i++){
			dataMatrix[i*colNum] = 1;
			String[] newLine = dataList.get(i).split("\\s+");
			//System.out.println(dataList.get(i));
			for (int j=0;j<newLine.length;j++){
				dataMatrix[i*colNum+j+1] = Double.valueOf(newLine[j]);
			}
		}
		DenseMatrix64F dataDenseMatrix = new DenseMatrix64F(rowNum,colNum,true,dataMatrix);
		SimpleMatrix dataSimpleMatrix = SimpleMatrix.wrap(dataDenseMatrix);
		return dataSimpleMatrix;
	}
}
