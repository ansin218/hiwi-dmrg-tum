file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
data <- data[(data$Previous_Outcome == "success") | (data$Previous_Outcome == "nonexistent"),]
data <- data[(data$Duration != "0"),]
data <- data[(data$Default != "yes"),]
data <- data[(data$Housing != "yes"),]
data <- data[(data$Loan != "yes"),]
medianDuration <- median(data$Duration)
data <- data[(data$Duration >= medianDuration),]
data <- data[!((data$Job == "unemployed") & (data$Age < 30)), ]
meanCampaign <- mean(data$Campaign)
data <- data[!(data$Campaign < meanCampaign),]
nrow(data)