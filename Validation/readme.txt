The implementation of machine learning validation

1. Validation:
split the given training examples in to the first 120 examples for train and 80 for val. Ideally, you should randomly do the 120/80 split. Because the given examples are already randomly permuted, however, we would use a fixed split for the purpose of this problem. Run the algorithm on train to get g−λ, and validate g−λ with val.
Among log10λ={2,1,0,−1,…,−8,−9,−10}. What is the λ with the minimum Etrain(g−λ)? Compute λ and the corresponding Etrain(g−λ), Eval(g−λ) and Eout(g−λ) then select the closet answer. Break the tie by selecting the largest λ.

2.Validation2
Among log10λ={2,1,0,−1,…,−8,−9,−10}. What is the λ with the minimum Eval(g−λ)? Compute λ and the corresponding Etrain(g−λ), Eval(g−λ) and Eout(g−λ) then select the closet answer. Break the tie by selecting the largest λ.

3. CrossValidation
split the given training examples in D to five folds, the first 40 being fold 1, the next 40 being fold 2, and so on. Again, we take a fixed split because the given examples are already randomly permuted. Among log10λ={2,1,0,−1,…,−8,−9,−10}. What is the λ with the minimum Ecv, where Ecv comes from the five folds defined above? Compute λ and the corresponding Ecv then select the closet answer. Break the tie by selecting the largest λ.