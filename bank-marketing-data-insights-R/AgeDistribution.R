file <- "bank.csv"
data <- read.csv(file, header=TRUE, sep=";")
png(filename = "AgeDistribution.png", width=480, height=480, units="px")
hist(data$Age, main="Complete Age Distribution", xlab="Age", col="red")
dev.off()