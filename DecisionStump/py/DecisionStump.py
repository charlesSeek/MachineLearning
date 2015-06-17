import random
def sign(n):
	if n <= 0:
		return -1
	else:
		return 1
def traindata(n):
	data = []
	for i in range(0,n):
		rand = random.uniform(-1,1)
		data.append(rand)
	return data

def flipSign(n):
	return -1*n

def getOutputData(input):
	output = []
	for i in input:
		rand = random.uniform(0,1)
		if rand <=0.2:
			output.append(flipSign(sign(i)))
		else:
			output.append(sign(i))
	return output

def getMinErrInSample(input,output,bound):
	minErrInSample = 1.0
	result = 0
	for i in list(range(19)):
		threshold = (input[i] + input[i+1])/2
		currentErrInSample = calErrInSample(input,output,bound,threshold)
		if currentErrInSample < minErrInSample:
			minErrInSample = currentErrInSample
			result = threshold
	return minErrInSample,bound,result

def getErrOutSample(bound,threshold):
	result = 0.5 + 0.3*bound*(abs(threshold-1))
	return result

def calErrInSample(input,output,bound,threshold):
	errCount = 0
	for i in list(range(20)):
		if output[i]!=bound*sign(input[i]-threshold):
			errCount = errCount + 1
	return errCount/20

def decisionstump(input,output):
	errPositive,bound,threshold = getMinErrInSample(input,output,1)
	errNegative,bound,threshold = getMinErrInSample(input,output,-1)
	if errPositive < errNegative:
		return errPositive,bound,threshold
	else:
		return errNegative,bound,threshold

errInTotal = 0
errOutTotal = 0
for i in range(5000):
	input = traindata(20)
	output = getOutputData(input)
	err,bound,threshold = decisionstump(input,output)
	errInTotal = errInTotal + err
	errOutTotal = errOutTotal + getErrOutSample(bound,threshold)
print('average in sample error:',errInTotal/5000)
print('average out sample error:',errOutTotal/5000)


