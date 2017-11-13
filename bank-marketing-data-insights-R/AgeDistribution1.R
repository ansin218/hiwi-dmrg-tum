file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
data <- data[(data$Previous_Outcome == "success") | (data$Previous_Outcome == "nonexistent"),]
png(filename = "AgeDistribution1.png", width=480, height=480, units="px")
hist(data$Age, main="Age Distribution without Rejections", xlab="Age", col="red")
dev.off()