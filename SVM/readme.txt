SVM: Support Vector Machine
we use the libsvm as our library to implement the svm and you can visit the libsvm website to get detailed user guide.

1. Data Format Transfer
transfer the standard training and test data to SVM format data, the implementation is
DataFormatTransfer.py code.

2. Data Scaling
>./svm-scale train.txt > train.scale.txt 
scaling the every feature numeric between -1 and 1

3. parameter optimization
>python grid.py train.scale.txt
