import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;


public class LogisticRegression {
	public static void main(String[] args){
		SimpleMatrix trainDataSimpleMatrix = loadData("train.txt");
		SimpleMatrix testDataSimpleMatrix = loadData("test.txt");
		SimpleMatrix weight = new SimpleMatrix(1,21);
		SimpleMatrix updateWeight = weight;
		double rate = 0.001;
		for (int i=0;i<2000;i++){
			updateWeight = updateWeight.minus(gradientDescent(weight,trainDataSimpleMatrix).scale(rate));
			weight = updateWeight;
			
		}
		System.out.println(updateWeight);
	}
	public static double sign(double n){
		if (n<=0)
			return -1;
		else
			return 1;
	}
	public static SimpleMatrix gradientDescent(SimpleMatrix w,SimpleMatrix data){
		int rowNum = data.numRows();
		int colNum = data.numCols();
		SimpleMatrix xdata = data.extractMatrix(0, rowNum, 0, colNum-1);
		SimpleMatrix ydata = data.extractVector(false, colNum-1);
		SimpleMatrix weight = new SimpleMatrix(1,colNum-1);
		
		for (int i=0;i<rowNum;i++){
			double coef = 1/(1+Math.exp(-1*ydata.get(i)*w.dot(xdata.extractVector(true, i))));
			weight = weight.plus(xdata.extractVector(true, i).scale(ydata.get(i)*(-1)*coef));
		}
		
		return weight.scale((double) 1/rowNum);
		
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
