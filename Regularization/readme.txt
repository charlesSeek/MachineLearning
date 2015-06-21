The implementation of regularization for machine learning algorithm

1. ridge regression(weight decay,L2):
The ridge regression is the implementation of the regularization of the linear regression. The formula is: W(reg) = (Z.transpose*Z+λI).inverse*z.transpose*y or use the gradient descent: 
Eaug(w)=Ein(w)+λ/N*w.transpose*w
w(t+1)⟵(1−2ηλ/N)*w(t)−η∇Ein(w(t)).
example:
Consider regularized linear regression (also called ridge regression) for classification.
Run the algorithm on the following data set as
Because the data sets are for classification, please consider only the 0/1 error for all the problems below.Let λ=10, which of the followings is the corresponding Ein and Eout?

2. ridge regression with different coef:
example:
Among log10λ={2,1,0,−1,…,−8,−9,−10}. What is the λ with the minimum Ein? Compute λ and its corresponding Ein and Eout then select the closest answer. Break the tie by selecting the largest λ.
