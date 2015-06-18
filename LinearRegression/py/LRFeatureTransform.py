#transform the training data into the following nonlinear feature vector:
#(1,x1,x2,x1x2,x21,x22)
#Find the vector w~ that corresponds to the solution of Linear Regression, 
#and take it for classification.
#Which of the following hypotheses is closest to the one you find using 
#Linear Regression on the transformed input? 

import numpy as np
import random
from random import shuffle

def trainData(n):
	data = []
	for i in range(n):
		row = []
		x1 = random.uniform(-1,1)
		x2 = random.uniform(-1,1)
		row.append(1)
		row.append(x1)
		row.append(x2)
		row.append(x1*x2)
		row.append(x1*x1)
		row.append(x2*x2)
		data.append(row)
	return data

def sign(n):
	if n <=0:
		return -1
	else:
		return 1

def getOutput(data):
	y = []
	for i in data:
		x1 = i[1]
		x2 = i[2]
		value = sign(x1*x1+x2*x2-0.6)
		rand = random.uniform(0,1)
		if rand<=0.1:
			value = -1*value
		y.append(value)
	return y

def getLinearRegressionWeight(data,ouput):
	weight = np.dot(np.linalg.pinv(data),output)
	return weight

data = trainData(1000)
output = getOutput(data)
weight = getLinearRegressionWeight(data,output)
print(weight)