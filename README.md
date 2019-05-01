# Bank-Churn-Modeling-DS2FinalProject

README 
CSCI 4/6360
Omkar Acharya(oma52562@uga.edu), Zach Baker(zeb66481@uga.edu),
Akshay Mendki(akshay.mendki@uga.edu)

This submission includes an powerpoint presentation with the accuracy, 
precision, and recall, for Churn and Bank Marketing dataset.


SCALATION
The right folder to run the scalation code is  

/Bank-Churn-Modeling-DS2FinalProject/finalproj

To compile the code, first start an sbt server with the following command:

	$ sbt

Then to actaully compile the code run this command:

	sbt:FinalProj> compile

Now to run the code, use this command:

	sbt:FinalProj> run

Now following options should appear ending with a prompt to enter a number:
[1] BankNN
[2] LogitBank
[3] LogitTest
[4] NNTest
[5] PerceptronBankMarketing
[6] PerceptronTest

[3] LogitTest, [4] NNTest, [6] PerceptronTest: These three files contain code for LogisticRegression, 4L NeuralNetwork and Perceptron for Churn dataset.

[1] BankNN, [2] LogitBank, [5] PerceptronBankMarketing: These three files contain code for LogisticRegression, 4L NeuralNetwork and Perceptron for Bank Marketing dataset.

The number you enter will correspond to which file will run,
so if you wanted to run BankNN, you would type the number that
corresponds to BankNN (in this case no: 1) and then hit enter.

The datasets and links are below:
1. Churn: Bank Customer Classification (https://www.kaggle.com/shrutimechlearn/churn-modelling)
2. Bank Marketing: UCI Dataset: http://archive.ics.uci.edu/ml/datasets/Bank+Marketing

We read each of these datasets in as a csv file, which are present in the RawData folder and the code is in the 
corresponding scala file. For instance the Bank Train dataset is read 
in the BankTrain.scala file and all the preprocessing is done there.

The model files will have commented code for forward selection method. Please uncomment, compile and run the file if you want to execute the models with forward selection.
Once the file completes execution, it will print Accuracy, True Positive, False Negative, False Positive, and True Negative.

So in summary each file runs either LogisticRegression or Perceptron or NeuralNetwork for
Churn dataset and Bank Marketing dataset.
