file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
data <- data[(data$Previous_Outcome == "success") | (data$Previous_Outcome == "nonexistent"),]
data <- data[(data$Duration != "0"),]
age = data$Age
duration <- data$Duration
png(filename = "RegressionDuration.png", width=480, height=480, units="px")
plot(duration~age, main="Scatterplot & Regression for Call Duration", xlab="Age", ylab="Duration")
abline(lm(duration~age))
dev.off()