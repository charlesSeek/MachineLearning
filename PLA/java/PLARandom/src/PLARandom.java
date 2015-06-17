import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
public class PLARandom {
	public static void main(String[] args){
			int sum = 0;
			for (int i=0;i<2000;i++){
				SimpleMatrix data = loadTrainingdata("train.txt");
				SimpleMatrix dataSimpleMatrix = data.extractMatrix(0, 400, 0, 5);
				SimpleMatrix labelSimpleMatrix = data.extractVector(false, 5);
				DenseMatrix64F weightDenseMatrix = new DenseMatrix64F(1,5,true,0,0,0,0,0);
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
				sum = sum + num;
			}
			
			System.out.println("weight is updated "+sum/2000+" times");
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
		public static SimpleMatrix loadTrainingdata(String fileName){
			SimpleMatrix mydata = null;
			BufferedReader br = null;
			double[] dataMatrix = new double[2400];
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
			counter = 0;
			for (String str:dataList){
				String[] newLine = str.trim().split("\\s+");
				for (int i=0;i<newLine.length;i++){
					dataMatrix[counter*6+i] = Double.valueOf(newLine[i]);
				}
				counter++;
			}
			
			DenseMatrix64F dataDenseMatrix = new DenseMatrix64F(400,6,true,dataMatrix);
			SimpleMatrix mydataMatrix = SimpleMatrix.wrap(dataDenseMatrix);
			return mydataMatrix;
		}

	}
