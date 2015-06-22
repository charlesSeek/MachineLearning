import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;


public class CrossValidation {
	public static void main(String[] args){
		double minErrRate = 1;
		double coef =0;
		SimpleMatrix dataSimpleMatrix = loadData("train.txt");
		for (double i=2;i>=-10;i--){
			double errRate = getAverageValidationErrRate(dataSimpleMatrix,5,Math.pow(10, i));
			if (errRate < minErrRate){
				minErrRate = errRate;
				coef = Math.pow(10, i);
			}
		}
		System.out.println("the coef:"+coef);
		System.out.println("the minimum cross validation error rate:"+minErrRate);
	}
	public static double getAverageValidationErrRate(SimpleMatrix data,int cv,double coef){
		int rowNum = data.numRows();
		int colNum = data.numCols();
		int size = rowNum/cv;
		double sumErrRate = 0;
		for (int i=0;i<cv;i++){
			SimpleMatrix validationdata = null;
			SimpleMatrix traindata = null;
			validationdata = data.extractMatrix(size*i, size*(i+1), 0, colNum);
			if (i==0)
				traindata = data.extractMatrix(size*1, rowNum, 0, colNum);
			else
				traindata = data.extractMatrix(0, size*i, 0, colNum).combine(size*(i+1), 0, data.extractMatrix(size*(i+1), rowNum, 0, colNum));
			int trainRowNum = traindata.numRows();
			int trainColNum = traindata.numCols();
			SimpleMatrix trainx = traindata.extractMatrix(0, trainRowNum, 0, trainColNum-1);
			SimpleMatrix trainy = traindata.extractVector(false, trainColNum-1);
			//System.out.println(trainx);
			//System.out.println(trainy);
			SimpleMatrix weight = trainx.transpose().mult(trainx).plus(SimpleMatrix.identity(trainColNum-1).scale(coef)).invert().mult(trainx.transpose()).mult(trainy);
			double errRate = calculateErrorRate(weight,validationdata);
			sumErrRate =sumErrRate + errRate;
		}
		return (double) sumErrRate/cv;
	}
	public static double sign(double n){
		if (n<=0)
			return -1;
		else
			return 1;
	}
	public static double calculateErrorRate(SimpleMatrix w,SimpleMatrix data){
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
