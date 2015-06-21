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

data = load_data('train.txt')
test = load_data('test.txt')
rowNum = len(data)
rowNum2 = len(test)
colNum = len(data[0])
colNum2 = len(test[0])
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
weight = np.dot(np.dot(np.linalg.inv(np.add(np.dot(np.transpose(trainx),trainx),np.dot(10,np.identity(colNum-1)))),np.transpose(trainx)),trainy)
trainpredict = list(map(sign,np.dot(weight,np.transpose(trainx))))
print("error rate in sample:",calErrRate(trainy,trainpredict))
testpredict = list(map(sign,np.dot(weight,np.transpose(testx))))
print("error rate out sample:",calErrRate(testy,testpredict))
