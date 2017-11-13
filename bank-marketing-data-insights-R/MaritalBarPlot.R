library(MASS)
file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
data <- data[(data$Previous_Outcome == "success") | (data$Previous_Outcome == "nonexistent"),]
data <- data[(data$Duration != "0"),]
data <- data[(data$Default != "yes"),]
data <- data[(data$Housing != "yes"),]
data <- data[(data$Loan != "yes"),]
medianDuration <- median(data$Duration)
data <- data[(data$Duration >= medianDuration),]
maritalStatus = data$Marital
maritalStatus.freq = table(maritalStatus)
png(filename = "MaritalBarPlot.png", width=480, height=480, units="px")
colors = c("red", "green", "blue", "yellow")
barplot(maritalStatus.freq, main="Marital Status Bar Plot", col=colors)
dev.off()