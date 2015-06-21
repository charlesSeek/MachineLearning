import numpy as np
def load_data(file):
	try:
		matrix = []
		f = open(file,'r')
		for line in f.readlines():
			x = list(map(float,line.split()))
			x.insert(0,1.0)
			matrix.append(x)
	finally:
		if f:
			f.close()
	return matrix

def sign(n):
	if n <=0:
		return -1
	else:
		return 1

def calErrRate(output,predict):
	errCount = 0
	for i in range(len(output)):
		if output[i]!=predict[i]:
			errCount = errCount + 1
	return errCount/len(output)

def minError(data):
	minError = 1
	for i in data:
		if i[1] < minError:
			minError = i[1]
			coef = i[0]
			weight = i[2]
	return coef,minError,weight

coef = [100,10,1,0.1,0.01,0.001,0.0001,0.00001,0.000001,0.0000001,0.00000001,0.000000001,0.0000000001]
data = load_data('train.txt')
train = data[:120]
validation = data[120:]
test = load_data('test.txt')
colNum = len(data[0])

trainx =[]
trainy=[]
for i in train:
	trainx.append(i[:colNum-1])
	trainy.append(i[colNum-1])
validationx = []
validationy = []
for i in validation:
	validationx.append(i[:colNum-1])
	validationy.append(i[colNum-1])
testx =[]
testy=[]
for i in test:
	testx.append(i[:colNum-1])
	testy.append(i[colNum-1])

dataIn= []
for p in coef:
	weight = np.dot(np.dot(np.linalg.inv(np.add(np.dot(np.transpose(trainx),trainx),np.dot(p,np.identity(colNum-1)))),np.transpose(trainx)),trainy)
	coefIn = []
	coefIn.append(p)
	coefIn.append(calErrRate(trainy,list(map(sign,np.dot(weight,np.transpose(trainx))))))
	coefIn.append(weight)
	dataIn.append(coefIn)
coef,rate,w = minError(dataIn)
print('the coef:',coef)
print('the error rate in train:',rate)
print('the error rate in validation:',calErrRate(validationy,list(map(sign,np.dot(w,np.transpose(validationx))))))
print('the error rate in test:',calErrRate(testy,list(map(sign,np.dot(w,np.transpose(testx))))))


