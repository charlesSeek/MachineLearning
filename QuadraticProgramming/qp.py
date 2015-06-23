from cvxopt import matrix
from cvxopt import solvers
import numpy as np
def load_data(train):
	try:
		matrix=[]
		f = open(train,'r')
		for line in f.readlines():
			x = list(map(float,line.split()))
			matrix.append(x)
	finally:
		if f:
			f.close()
	return matrix
def svmQP(data):
	colNum = len(data[0])
	rowNum = len(data)
	xdata = []
	ydata = []
	for i in data:
		xdata.append(i[:colNum-1])
		ydata.append(i[colNum-1])
	pMatrix = []
	for i in range(colNum):
		row = []
		for j in range(colNum):
			if j==i and i<colNum-1:
				row.append(1.0)
			else:
				row.append(0.0)
		pMatrix.append(row)
	qMatrix = []
	for i in range(colNum):
		qMatrix.append(0.0)
	gMatrix = []
	for i in range(colNum-1):
		row = []
		for j in range(rowNum):
			row.append((-1.0)*xdata[j][i]*ydata[j])
		gMatrix.append(row)
	row = [] 
	for i in range(rowNum):
		row.append((-1.0)*ydata[i])
	gMatrix.append(row)
	hMatrix = []
	for i in range(rowNum):
		hMatrix.append(-1.0)
	return pMatrix,qMatrix,gMatrix,hMatrix

data = load_data('train.txt')
print('train data:',data)
p,q,g,h = svmQP(data)
print('P:',p)
print('q:',q)
print('G:',g)
print('h:',h)
P = matrix(p)
q = matrix(q)
G = matrix(g)
h = matrix(h)
sol = solvers.qp(P,q,G,h)
print(sol['x'])






