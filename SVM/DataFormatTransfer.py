def load_data(file):
	try:
		matrix = []
		f = open(file,'r')
		for line in f.readlines():
			x = list(map(float,line.split()))
			matrix.append(x)
	finally:
		if f:
			f.close()
	return matrix

data = load_data('train.txt')
colNum = len(data[0])
f = open('output.txt','w')
for i in data:
	line = ''
	line = line+str(i[0])
	for j in range(colNum-1):
		line = line+' '+str(j+1)+':'+str(i[j+1])
	print(line)
	line = line +"\n"
	f.write(line)
	