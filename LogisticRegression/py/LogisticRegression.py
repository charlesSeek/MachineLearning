import numpy as np
import math
def load_data(train):
	try:
		matrix=[]
		f = open(train,'r')
		for line in f.readlines():
			x = list(map(float,line.split()))
			x.insert(0,1.0)
			matrix.append(x)
	finally:
		if f:
			f.close()
	return matrix

def load_test(test):
	try:
		matrix=[]
		f = open(test,'r')
		for line in f.readlines():
			x = list(map(float,line.split()))
			x.insert(0,1.0)
			matrix.append(x)
	finally:
		if f:
			f.close()
	return matrix

def logistic(x):
	return 1/(1+math.exp(-x))

def sign (n):
	if n > 0:
		return 1
	else :
		return -1

def predict(row,weight):
	colNum = len(row)-1
	x = row[:colNum]
	y = row[colNum]
	if sign(np.dot(x,weight))!=y:
		return 1
	else:
		return 0	

def gradientDescent(data,w):
	update = w
	gradientupdate = w
	rowNum = len(data)
	colNum = len(data[0])
	for i in range(rowNum):
		x = data[i][:colNum-1]
		y = data[i][colNum-1]
		coef = logistic(-y*np.dot(update,np.transpose(x)))
		gradientupdate = np.add(gradientupdate,np.dot(-y*coef,x))
	return np.dot(1/rowNum,gradientupdate)

train = load_data('train.txt')
test = load_data('test.txt')
weight = []
for i in range(len(train[0])-1):
	weight.append(0)
learningRate = 0.001
for i in range(2000):
	update = gradientDescent(train,weight)
	w = np.subtract(weight,np.dot(update,learningRate))
	weight = w
	print('weight:',weight)
errCount = 0
for i in test:
	errCount = errCount + predict(i,weight)
print("out sample error rate:",errCount/len(test))


