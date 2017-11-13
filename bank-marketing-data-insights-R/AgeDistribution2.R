file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
data <- data[(data$Previous_Outcome == "success") | (data$Previous_Outcome == "nonexistent"),]
data <- data[(data$Duration != "0"),]
png(filename = "AgeDistribution2.png", width=480, height=480, units="px")
hist(data$Age, main="Age Distribution for people without 0 Duration Calls", xlab="Age", col="red")
dev.off()