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
	for i in data :
		print('w:',w,'row:',i[:5])
		if sign(np.dot(w,i[:5]))*i[5]<=0:
			flag = True
			scale = np.dot(i[:5],rate)
			w = np.add(w,np.dot(i[5],scale))
			break
		else :
			flag = False
	#print(flag,w)
	return flag,w

def pla_train(data):
	w =[0.0,0.0,0.0,0.0,0.0]
	flag = True
	count = 0
	while True:
		flag,nw=has_error(w,data)
		w = nw
		if flag==True:
			count = count +1
		else:
			break
	print('final weight:',w)
	return count

m=load_data('train.txt')
sum = 0
for i in range(2000):
	random.shuffle(m)
	sum = sum + pla_train(m)
print('The average update: ',sum/2000,' times')
