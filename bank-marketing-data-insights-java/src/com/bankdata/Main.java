package com.bankdata;

import java.util.*;
import com.bankdata.models.*;
import com.google.gson.*;
import java.io.*;
public class Main {

	public static void main(String[] args) throws IOException {
		 int i = 0;
		 double x, roundOff;
		 int a,b;
		 DataModel[] data;
		 Gson gson = new Gson();
		 File file = new File("data.json");
		 String dataStr = null;
		 BufferedReader br = new BufferedReader(new FileReader(file));
		 try {
		     StringBuilder sb = new StringBuilder();
		     String line = br.readLine();

		     while (line != null) {
		         sb.append(line);
		         sb.append(System.lineSeparator());
		         line = br.readLine();

		     }
		     dataStr = sb.toString().trim();
		     br.close();
		 } catch(Exception e) {
		     e.printStackTrace();
		 }

		 
		 data = gson.fromJson(dataStr, DataModel[].class);
		 if(data == null)
			 System.out.println("nulll");
		 else
			 System.out.println("Total number of records : " + data.length + "\n");

		 ArrayList<DataModel> dataList = new ArrayList<DataModel>(Arrays.asList(data));
		 Operations op = new Operations(dataList);
		 System.out.println("Mean age with failure: " + op.calculateMeanAge() + "\n");
		 op.removeFailureOutcomes();
		 System.out.println("Insights for AGE after removing people who would not opt for the scheme based on "
		 		+ "previous failure outcomes");
		 System.out.println("Total number of people now: " + op.peopleWithoutFailure());
		 System.out.println("Mean Age: " + op.calculateMeanAge() + "\n");
		 System.out.println("We now remove those records where the duration of the call was 0 seconds");
		 op.removeZeroDuration();
		 System.out.println("Total number of people now: " + op.peopleWithoutFailure() + "\n");
		 System.out.println("We now remove those records where a customer has a loan or credit "
		 		+ "because the bank might not be totally fine with someone signing up for a scheme "
		 		+ "with money being owed to someone");
		 op.removeLoanHolders();
		 System.out.println("Total number of people now: " + op.peopleWithoutFailure());
		 System.out.println("Mean Duration: " + op.calculateMeanDuration());
		 System.out.println("Minimum Duration: " + op.calculateMinDuration());
		 System.out.println("Maximum Duration: " + op.calculateMaxDuration());
		 System.out.println("Below Mean Duration Count: " + op.belowMeanDuration(op.calculateMeanDuration()));
		 System.out.println("Above Mean Duration Count: " + op.aboveMeanDuration(op.calculateMeanDuration()));
		 System.out.println("Median Element of Duration Data: " + op.medianDuration() + "\n");
		 System.out.println("We now assume that it takes atleast 181 seconds to convert a call to a successful one");
		 op.removeBelowDurationCalls();
		 System.out.println("We now see the insights of MARITAL STATUS of the remaining people");
		 System.out.println("Total number of people now: " + op.peopleWithoutFailure());
		 System.out.println("Number of Single people: " + op.singleStats());
		 System.out.println("Number of Married people: " + op.marriedStats());
		 System.out.println("Number of Widowed people: " + op.divorcedStats());
		 System.out.println("Number of people with Unknown Marital Status: " + op.unknownMaritalStats() + "\n");
		 System.out.println("We now assume that anyone below 30 have completed their studies and "
		 		+ "are still unemployed, so we remove them as they may not have savings for a deposit");
		 op.removeUnemployedStudents();
		 System.out.println("Number of people Unemployed below the age of 30: " + op.peopleWithoutFailure() + "\n");
		 System.out.println("We now check the average number for calls from previous campaign");
		 System.out.println("Average number of calls per customer from previous campaign: " 
		 + op.calculateMeanCampaign());
		 op.removeBelowMeanCampaign();
		 System.out.println("Number of people who received more than average "
		 		+ "calls per customer: " + op.peopleWithoutFailure() + "\n");
		 
		 a = op.peopleWithoutFailure();
		 b = data.length;
		 x = ((double)a/b)*100;
		 roundOff = (double) Math.round(x * 100) / 100;
		 System.out.println("So, the number of people who are likely to sign up for a deposit is " + 
				 op.peopleWithoutFailure() + " which is " + roundOff + " percent of the total number of people "
				 		+ "which is " + data.length);
	}
}
