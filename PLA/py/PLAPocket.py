import numpy as np
import random
from random import shuffle
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

def sign(n):
	if n >0:
		return 1.0
	else:
		return -1.0

def has_error(w,data):
	rate = 0.5
	errCount = 0
	for i in data:
		length = len(i)
		if sign(np.dot(w,i[:length-1]))*i[length-1]<=0:
			errCount = errCount + 1
	for i in data :
		#print('w:',w,'row:',i[:5])
		if sign(np.dot(w,i[:length-1]))*i[length-1]<=0:
			flag = True
			scale = np.dot(i[:length-1],rate)
			w = np.add(w,np.dot(i[length-1],scale))
			break
		else :
			flag = False
	#print(flag,w)
	return flag,w,errCount

def pla_train(data):
	w =[0.0,0.0,0.0,0.0,0.0]
	flag = True
	count = 0
	minErr = 1000
	while True and count<=50:
		flag,nw,err=has_error(w,data)
		w = nw
		if err < minErr:
			minErr = err
			minW = nw
		if flag==True:
			count = count +1
		else:
			break
	#print('final weight:',w)
	return minW

def pla_test(data,w):
	length = len(data)
	print('length:',length)
	err = 0
	for i in data:
		length2 = len(i)
		if sign(np.dot(w,i[:length2-1]))*i[length2-1]<=0:
			err = err + 1
	return err/length

m=load_data('train_pocket.txt')
test = load_data('test_pocket.txt')
errRate = 0
for i in range(100):
	random.shuffle(m)
	w = pla_train(m)
	rate = pla_test(test,w)
	errRate = errRate + rate
print('The average error rate: ', errRate/100)
