# Loading emails from Enron
# Patricia Briones Yus, 735576
# 10-Enero-2021

######################################################
# Imports
######################################################

import sys
import numpy as np
import glob
import matplotlib.pyplot as plt
from sklearn.model_selection import KFold
from sklearn.metrics import confusion_matrix, f1_score
from sklearn.metrics import precision_recall_curve, accuracy_score
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.utils import shuffle

######################################################
# Functions for loading mails
######################################################

def read_folder(folder):
    mails = []
    file_list = glob.glob(folder)  # List mails in folder
    num_files = len(file_list)
    for i in range(0, num_files):
        i_path = file_list[i]
        # print(i_path)
        i_file = open(i_path, 'rb')
        i_str = i_file.read()
        i_text = i_str.decode('utf-8', errors='ignore')  # Convert to Unicode
        mails.append(i_text)  # Append to the mail structure
        i_file.close()
    return mails


def load_enron_folders(datasets):
    path =  sys.argv[1]
    ham = []
    spam = []
    for j in datasets:
        ham  = ham  + read_folder(path + 'enron' + str(j) + '\ham\*.txt')
        spam = spam + read_folder(path + 'enron' + str(j) + '\spam\*.txt')
    num_ham  = len(ham)
    num_spam = len(spam)
    print("mails:", num_ham+num_spam)
    print("ham  :", num_ham)
    print("spam :", num_spam)

    mails = ham + spam
    labels = [0]*num_ham + [1]*num_spam
    mails, labels = shuffle(mails, labels, random_state=0)
    return mails, labels

######################################################
# Functions for training
######################################################
    
def Kfold_suavizado(distr,X,y):
    # Mejor suavizado de Laplace en función de f1_score
    mejorValor = 0
    mejorScore = 0
  
    Y = np.array(y)
    for i in np.arange(0.25, 5, 0.25):
        score=0
        
        # Se separan los datos de entrenamiento (default=5)
        kfold_data = KFold(n_splits=5)                

        for train_i, test_i in kfold_data.split(X):
            # Datos entrenamiento y validación
            Xtr, Xcv = X[train_i], X[test_i]
            Ytr, Ycv = Y[train_i], Y[test_i]
            
            if (distr == "MULTI"):
                distribution=MultinomialNB(alpha=i)
            else:
                distribution=BernoulliNB(alpha=i)
            distribution.fit(Xtr,Ytr)
            
            pred = distribution.predict(Xcv)            
            score += f1_score(Ycv,pred)
            
        score /= 5
        
        if (score > mejorScore):
            mejorScore = score
            mejorValor = i
            
    #Devuelve el mejor suavizado de laplace
    return mejorValor
    
def Clasificar(modelo, distr, mails, y, mails_test):
    if (distr == "MULTI"):
        vectorizer  = CountVectorizer(ngram_range=(1, modelo))
    else:
        vectorizer  = CountVectorizer(ngram_range=(1, modelo),binary=1)
        
    X = vectorizer.fit_transform(mails)
    X_test = vectorizer.transform(mails_test)
    
    mejorValor = Kfold_suavizado(distr,X,y)
    
    # Se entrenan los datos con el vejor valor de Laplace
    if (distr == "MULTI"):
        distribution  = MultinomialNB(alpha=mejorValor)
    else:
        distribution  = BernoulliNB(alpha=mejorValor)
    distribution.fit(X,y)             

    # Se predice la probabilidad de pertenecer cada correo a cada clase
    pred1 = distribution.predict_proba(X_test)
    
    # Se predice a qué clase pertenece cada correo
    pred2 = distribution.predict(X_test)
    
    return mejorValor, pred1[:,1], pred2
    
def Entrenamiento(mails, y, mails_test):
    val_laplace = []    # Todos los mejores valores de Laplace
    prob_clase = []         # Probabilidad de pertenecer cada correo a cada clase
    pred_clase = []         # Predice a qué clase pertenece cada correo
    
 # Bolsa de palabras --> 1
    # Multinomial con distintos valores para el suavizado de Laplace
    laplace, pred1, pred2 = Clasificar(1, "MULTI", mails, y, mails_test)
    val_laplace.append(laplace)
    prob_clase.append(pred1)
    pred_clase.append(pred2)
    
    # Bernoulli con distintos valores para el suavizado de Laplace
    laplace, pred1, pred2 = Clasificar(1, "BERN", mails, y, mails_test)
    val_laplace.append(laplace)
    prob_clase.append(pred1)
    pred_clase.append(pred2)
    
 # Bigramas --> 2
    # Multinomial con distintos valores para el suavizado de Laplace
    laplace, pred1, pred2 = Clasificar(2, "MULTI", mails, y, mails_test)
    val_laplace.append(laplace)
    prob_clase.append(pred1)
    pred_clase.append(pred2)
    
    # Bernoulli con distintos valores para el suavizado de Laplace
    laplace, pred1, pred2 = Clasificar(2, "BERN", mails, y, mails_test)
    val_laplace.append(laplace)
    prob_clase.append(pred1)
    pred_clase.append(pred2)
    
    return val_laplace, prob_clase, pred_clase
    
######################################################
# Functions for evaluating
######################################################
def Evaluacion(mails_test, y_test, val_laplace, prob_clase, pred_clase):
 # Mejor clasificador
    mejorScore = 0
    mejorI = 0
    clasificador = ["MultinomialNB - bolsa de palabras",
                    "BernoulliNB - bolsa de palabras",
                    "MultinomialNB - bigrama",
                    "BernoulliNB - bigrama"]
    
    for i in range(4):
        score = f1_score(y_test,pred_clase[i])
        confusion = confusion_matrix(y_test,pred_clase[i])
        accuracy = accuracy_score(y_test,pred_clase[i])
            
        if (score > mejorScore) :
            mejorScore = score
            best_confusion = confusion
            best_accuracy = accuracy
            mejorI = i
    
    plt.clf()
    precisiones, recall, threshold = precision_recall_curve(y_test,prob_clase[mejorI])        
    plt.plot(recall,precisiones,label=clasificador[mejorI])    
    plt.xlabel('Recall')
    plt.ylabel('Precision')
    plt.ylim([0.9, 1.05])
    plt.xlim([0.9, 1.05])    
    plt.show()
    
    print("")
    print("Los mejores resultados obtenidos han sido mediante", clasificador[mejorI])
    print("Suavizado de Laplace:", val_laplace[mejorI])
    print("Accuracy score:", best_accuracy)
    print("Valor de f1_score:", mejorScore)
    print("Matriz de confusión:")
    print(best_confusion)
    
 # Ejemplos de spam y ham clasificados correcta e incorrectamente
    ham_correctos=[]
    ham_incorrectos=[]
    spam_correctos=[]
    spam_incorrectos=[]
    
    for i in range(0,len(y_test)):
        # Si el correo del test es el mismo que el predicho, predicción correcta
        if(y_test[i] == 0): # --> HAM
            if(pred_clase[mejorI][i] == 0):
                ham_correctos.append(mails_test[i])
            else:
                ham_incorrectos.append(mails_test[i])
        else:               # --> SPAM
            if(pred_clase[mejorI][i] != 0): 
                spam_correctos.append(mails_test[i])
            else:
                spam_incorrectos.append(mails_test[i])

    # Ejemplos
    print("")
    print("- EJEMPLOS CORREOS ELECTRÓNICOS -")
    print("")
    print("HAM clasificado correcto:")
    print("-------------------------")
    print("")
    print(ham_correctos[0])
    print("")
    print("HAM clasificado incorrecto:")
    print("---------------------------")
    print("")
    print(spam_incorrectos[1])
    print("")
    print("SPAM clasificado correcto:")
    print("--------------------------")
    print("")
    print(spam_correctos[2])
    print("")
    print("SPAM clasificado incorrecto:")
    print("----------------------------")
    print("")
    print(ham_incorrectos[3])

######################################################
# Main
######################################################

print("Loading files...")

print("------Loading train and validation data--------")
mails, y = load_enron_folders([1,2,3,4,5])

print("--------------Loading Test data----------------")
mails_test, y_test = load_enron_folders([6])

val_laplace, prob_clase, pred_clase = Entrenamiento(mails, y, mails_test)
Evaluacion(mails_test, y_test, val_laplace, prob_clase, pred_clase)
