The implementation of linear regression
example description:
I will play with linear regression and feature transforms. Consider the target function:
f(x1,x2)=sign(x21+x22−0.6)
Generate a training set of N=1000 points on =[−1,1]×[−1,1] with uniform probability of picking each x∈. Generate simulated noise by flipping the sign of the output in a random 10% subset of the generated training set.
Carry out Linear Regression without transformation, i.e., with feature vector: (1,x1,x2), to find the weight w, and use wlin directly for classification. What is the closest value to the classification (0/1) in-sample error (Ein)? Run the experiment 1000 times and take the average Ein in order to reduce variation in your results.