


dat = read.csv("/Users/zachbaker/Documents/Senior Year/Spring Semester/DS2/Final Project/finalproj/RawData/Churn_Modelling.csv")

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

