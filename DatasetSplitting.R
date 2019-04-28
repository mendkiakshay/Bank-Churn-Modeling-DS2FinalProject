
library(fastDummies)

dat = read.csv("/Users/zachbaker/Documents/Senior Year/Spring Semester/DS2/Bank-Churn-Modeling-DS2FinalProject/finalproj/RawData/Churn_Modelling.csv")

set.seed(124)

sampInd = sample(1:nrow(dat),9000)

dat.train = dat[sampInd,]

sampVal = sample(1:nrow(dat.train),1000)

dat.validate = dat.train[sampVal,]

dat.train = dat.train[-sampVal,]

dat.test = dat[-sampInd,]

sum(dat.train$Exited)/8000

sum(dat.validate$Exited)/1000

sum(dat.test$Exited)/1000

sum(dat$Exited)/10000

setwd("/Users/zachbaker/Documents/Senior Year/Spring Semester/DS2/Final Project/finalproj/RawData/")

write.csv(dat.train,"Churn.train.csv")
write.csv(dat.test,"Churn.test.csv")
write.csv(dat.validate,"Churn.validate.csv")


datB = read.csv("/Users/zachbaker/Documents/Senior Year/Spring Semester/DS2/Bank-Churn-Modeling-DS2FinalProject/finalproj/RawData/bank.csv")

dumCols = dummy_cols(datB)[,c(15:25,27:28,30:32,34:36)]

y = datB[,14]

datB = cbind(datB[,c(1,5:12)],dumCols,y)

set.seed(14)

sampIndB = sample(1:nrow(datB),4069)

datB.train = datB[sampIndB,]

sampValB = sample(1:nrow(datB.train),452)

datB.validate = datB.train[sampValB,]

datB.train = datB.train[-sampValB,]

datB.test = datB[-sampIndB,]

sum(datB.test$y)/452

sum(datB.train$y)/3617

sum(datB.validate$y)/452

sum(datB$y)/4521

setwd("/Users/zachbaker/Documents/Senior Year/Spring Semester/DS2/Bank-Churn-Modeling-DS2FinalProject/finalproj/RawData")

write.csv(datB,"Bank.Full.csv")
write.csv(datB.train,"Bank.train.csv")
write.csv(datB.test,"Bank.test.csv")
write.csv(datB.validate,"Bank.validate.csv")



##Data Visualization

a = dat + rnorm(10000,mean = 0, sd = .04)
a$Exited = dat$Exited + rnorm(10000,mean = 0, sd = .01)
a %>%
  gather(-Exited, key = "some_var_name", value = "Value") %>%
  ggplot(aes(x = Value, y = Exited)) +
  geom_point() +
  facet_wrap(~ some_var_name, scales = "free")


b = datB + rnorm(4521,mean = 0, sd = .04)
b$y = datB$y + rnorm(4521,mean = 0, sd = .01)
b %>%
  gather(-y, key = "some_var_name", value = "Value") %>%
  ggplot(aes(x = Value, y = y)) +
  geom_point() +
  facet_wrap(~ some_var_name, scales = "free")


