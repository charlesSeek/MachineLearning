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
		#print(row)
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

def calErrInRate(output,predict):
	errCount = 0
	for i in range(len(output)):
		if output[i]!=predict[i]:
			errCount = errCount + 1
	return errCount/len(output)

data = trainData(1000)
#print(data)
output = getOutput(data)
weight = getLinearRegressionWeight(data,output)
print(weight)
predict = list(map(sign,np.dot(weight,np.transpose(data))))
print(list(predict))
print(calErrInRate(output,predict))
