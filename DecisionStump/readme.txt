Example description:
In class, we learn about the learning model of ``positive and negative rays'' (which is simply one-dimensional perceptron) for one-dimensional data. The model contains hypotheses of the form:
hs,θ(x)=s⋅sign(x−θ).
The model is frequently named the ``decision stump'' model and is one of the simplest learning models. As shown in class, for one-dimensional data, the VC dimension of the decision stump model is 2. 

In fact, the decision stump model is one of the few models that we could easily minimize Ein efficiently by enumerating all possible thresholds. In particular, for N examples, there are at most 2N dichotomies (see page 22 of class05 slides), and thus at most 2N different Ein values. We can then easily choose the dichotomy that leads to the lowest Ein, where ties can be broken by randomly choosing among the lowest-Ein ones. The chosen dichotomy stands for a combination of some `spot' (range of θ) and s, and commonly the median of the range is chosen as the θ that realizes the dichotomy. 

In this problem, you are asked to implement such and algorithm and run your program on an artificial data set. First of all, start by generating a one-dimensional data by the procedure below: 

(a) Generate x by a uniform distribution in [−1,1]. 
[b) Generate y by f(x)=s~(x) + noise where s~(x)=sign(x) and the noise flips the result with 20% probability. 

For any decision stump hs,θ with θ∈[−1,1], express Eout(hs,θ) as a function of θ and s.