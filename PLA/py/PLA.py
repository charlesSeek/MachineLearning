import numpy as np
from random import shuffle
def load_data(train):
	try:
		matrix=[]
		f = open(train,'r')
		for line in f.readlines():
			matrix.append(list(map(float,line.split())))
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
	flag = False
	for i in data :
		i.insert(0,1)
		x=i
		print('w:',w,'x:',x[:5])
		if sign(np.dot(w,x[:5]))*x[5]>0:
			flag = True
			w = np.add(w,np.dot(x[5],x[:5]))
			break
	print(flag,w)
	return flag,w

def pla_train(data):
	w =[0.0,0.0,0.0,0.0,0.0]
	flag = True
	count = 0
	while flag==True:
		flag = False
		flag,w=has_error(w,data)
		if flag==True:
			count = count +1
			print('count:',count)
	return count


m=load_data('train.txt')
#print(m)
#shuffle(m)
print(pla_train(m))
