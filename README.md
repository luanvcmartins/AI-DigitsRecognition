# Digits Recognition
Simple Java Swing app that allows the user to draw a single digit between 0 and 9 and then uses the KNN algorithm to identify what the digit is. It utilizes an PNG version of the benchmark MNIST dataset. The user may choose between 3 main distance functions: Euclidean, Manhattan and Data Time Warping.

This is a simple project I worked on just to start the implementation of KNN. It doesn't always provide good results as there is no image processing of the dataset or the image the user drew. 

## MNIST Dataset
This application uses a custom PNG version of the MNIST database of digits. This version may be obtained [here](https://github.com/myleott/mnist_png). The user can select the dataset with the button "Select" or just copy and paste the folder location, it will be loaded when the user presses enter.

![Loading](https://i.imgur.com/MHPbSkW.gif)

## Drawing
By clicking and dragging the mouse on the gray panel the user may draw a digit he wants to be detected. An "Erase" option is also available.

![Loading](https://i.imgur.com/i96ZZci.gif)

## Digit Detection
This is a rather simple application. It doesn't execute any type image processing on the dataset or on the image drawn by the user and its' results may not be precise depending on how the image is drawn. 

There are 4 types of distance function that can be used, but 2 of them (euclidean and manhattan) produces very similar results. The cosine distance isn't appropriate for this task (but it was already implemented, so I added it) and never generates good results. The Data Time Warping is the most precise of them (often getting the right results even if euclidean/manhattan doesn't), but the implemented version is the first concept of the algorithm and it doesn't have any optimization methods in place, because of that (as well as the size of the database, and the fact that there is no image processing technic been applied), it takes a long time to complete.

![Loading](https://i.imgur.com/8WiQUXs.gif)