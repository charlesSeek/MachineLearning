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
	return coef,minError

def getTrainAndValidationData(data,v,sequenceid):
	size = int(len(data)/v)
	traindata = []
	validationdata = []
	for i in range(len(data)):
		if i>=sequenceid*size and i<(sequenceid+1)*size:
			validationdata.append(data[i])
		else:
			traindata.append(data[i])
	return traindata,validationdata

def getDataXY(train,validation):
	colNum = len(train[0])
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
	return trainx,trainy,validationx,validationy

def getAverageCrossValidationErrRate(data,coef,n):
	sumErrRate = 0
	colNum = len(data[0])
	for i in range(n):
		traindata,validationdata = getTrainAndValidationData(data,n,i)
		trainx,trainy,validationx,validationy = getDataXY(traindata,validationdata)
		weight = np.dot(np.dot(np.linalg.inv(np.add(np.dot(np.transpose(trainx),trainx),np.dot(coef,np.identity(colNum-1)))),np.transpose(trainx)),trainy)
		errRate = calErrRate(validationy,list(map(sign,np.dot(weight,np.transpose(validationx)))))
		sumErrRate = sumErrRate + errRate
	return sumErrRate/n

coef = [100,10,1,0.1,0.01,0.001,0.0001,0.00001,0.000001,0.0000001,0.00000001,0.000000001,0.0000000001]
cv = 5
data = load_data('train.txt')
test = load_data('test.txt')
train,validation = getTrainAndValidationData(data,3,0)
dataVal= []
for p in coef:
	errRate = getAverageCrossValidationErrRate(data,p,cv)
	coefIn = []
	coefIn.append(p)
	coefIn.append(errRate)
	dataVal.append(coefIn)
coef,errRate = minError(dataVal)
print('the coef:',coef)
colNum = len(data[0])
trainx =[]
trainy=[]
for i in data:
	trainx.append(i[:colNum-1])
	trainy.append(i[colNum-1])
testx =[]
testy=[]
for i in test:
	testx.append(i[:colNum-1])
	testy.append(i[colNum-1])
w = np.dot(np.dot(np.linalg.inv(np.add(np.dot(np.transpose(trainx),trainx),np.dot(coef,np.identity(colNum-1)))),np.transpose(trainx)),trainy)
print('the error rate in train:',calErrRate(trainy,list(map(sign,np.dot(w,np.transpose(trainx))))))
print('the error rate in test:',calErrRate(testy,list(map(sign,np.dot(w,np.transpose(testx))))))






