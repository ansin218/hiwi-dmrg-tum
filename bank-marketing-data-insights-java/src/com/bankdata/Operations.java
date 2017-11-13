package com.bankdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.bankdata.models.DataModel;

public class Operations {

	private ArrayList<DataModel> data = new ArrayList<DataModel>();
	private ArrayList<DataModel> allData = new ArrayList<DataModel>();
	private ArrayList<DataModel> sortedData = new ArrayList<DataModel>();

	public Operations(ArrayList<DataModel> data) {
		this.data.addAll(data);
		this.allData.addAll(data);
	}

	public void removeFailureOutcomes() {
		int j = 0;
		data.clear();
		for (DataModel model : allData) {
			if(!model.getFIELD15().equals("failure")){
				j++;
				data.add(model);
			}
		}
	}
	
	public void removeZeroDuration() {
		int j = 0;
		ArrayList<DataModel> data2 = new ArrayList<DataModel>();
		data2.addAll(data);
		for (DataModel model : data2) {
			if(model.getFIELD11().equals("0")){
				j++;
				data.remove (model);
			}
		}
	}
	
	public void removeLoanHolders() {
		int j = 0;
		ArrayList<DataModel> data2 = new ArrayList<DataModel>();
		data2.addAll(data);
		for (DataModel model : data2) {
			if(model.getFIELD5().equals("yes") || model.getFIELD6().equals("yes")
					|| model.getFIELD7().equals("yes")){
				j++;
				data.remove (model);
			}
		}
	}
	
	public void removeBelowDurationCalls() {
		int belowCount = 0;
		ArrayList<DataModel> data2 = new ArrayList<DataModel>();
		data2.addAll(data);
		for (DataModel model : data2) {
			if(Integer.parseInt(model.getFIELD11()) < 181) {
				belowCount++;
				data.remove (model);
			}
		}
	}
	
	public void removeUnemployedStudents() {
		int j = 0;
		ArrayList<DataModel> data2 = new ArrayList<DataModel>();
		data2.addAll(data);
		for (DataModel model : data2) {
			if(model.getFIELD2().equals("unemployed") && Integer.parseInt(model.getFIELD1()) < 30) {
				j++;
				data.remove(model);
			}
		}
	}
	
	public void removeBelowMeanCampaign() {
		int j = 0;
		double x = calculateMeanCampaign();
		ArrayList<DataModel> data2 = new ArrayList<DataModel>();
		data2.addAll(data);
		for (DataModel model : data2) {
			if(Integer.parseInt(model.getFIELD12()) < x) {
				j++;
				data.remove(model);
			}
		}
	}
	
	public void restoreFailureOutcomes(){
		data.clear();
		data.addAll(allData);
	}

	public double calculateMeanAge() {
		int age = 0;
		int totalCount = 0;
		for (DataModel model : data) {
			totalCount++;
			age += Integer.parseInt(model.getFIELD1());
		}
		return age / totalCount;
	}
	
	public double calculateMeanCampaign() {
		int campaign = 0;
		int totalCount = 0;
		double roundOff;
		for (DataModel model : data) {
			totalCount++;
			campaign += Integer.parseInt(model.getFIELD12());
		}
		double x = (double)campaign / totalCount;
		roundOff = (double) Math.round(x * 100) / 100;
		return roundOff;
	}
	
	public double calculateMeanDuration() {
		int duration = 0;
		int totalCount = 0;
		for (DataModel model : data) {
			totalCount++;
			duration += Integer.parseInt(model.getFIELD11());
		}
		return duration / totalCount;
	}
	
	public int belowMeanDuration(double mean) {
		int belowCount = 0;
		for (DataModel model : data) {
			if(Integer.parseInt(model.getFIELD11()) < mean) {
				belowCount++;
			}
		}
		return belowCount;
	}
	
	public int medianDuration() {
		sortData();
		int median = (int) Math.round(sortedData.size()/2);
		int belowCount = 0;
		int medianElement = Integer.parseInt(sortedData.get(median).getFIELD11());
		for (DataModel model : sortedData) {
			if(Integer.parseInt(model.getFIELD11()) >= medianElement) {
				belowCount++;
			}
		}
		return medianElement;
	}
	
	public int singleStats() {
		int j = 0;
		for (DataModel model : data) {
			if(model.getFIELD3().equals("single")){
				j++;
			}
		}
		return j;
	}
	
	public int divorcedStats() {
		int j = 0;
		for (DataModel model : data) {
			if(model.getFIELD3().equals("divorced")){
				j++;
			}
		}
		return j;
	}
	
	public int marriedStats() {
		int j = 0;
		for (DataModel model : data) {
			if(model.getFIELD3().equals("married")){
				j++;
			}
		}
		return j;
	}
	
	public int unknownMaritalStats() {
		int j = 0;
		for (DataModel model : data) {
			if(model.getFIELD3().equals("unknown")){
				j++;
			}
		}
		return j;
	}
	
	public void sortData() {
		sortedData.addAll(data);
		Collections.sort(sortedData, new Comparator<DataModel>() {
	        @Override
	        public int compare(DataModel o1, DataModel o2) {
	            return Integer.parseInt(o1.getFIELD11()) - (Integer.parseInt(o2.getFIELD11()));
	        }
	    });
	}
	
	public int aboveMeanDuration(double mean) {
		int aboveCount = 0;
		for (DataModel model : data) {
			if(Integer.parseInt(model.getFIELD11()) >= mean) {
				aboveCount++;
			}
		}
		return aboveCount;
	}
	
	public int peopleWithoutFailure() {
		return data.size();
	}
	
	public int calculateMinAge() {
		int minAge = Integer.MAX_VALUE;
		for (DataModel model : data) {
			if(minAge > Integer.parseInt(model.getFIELD1())) {
				minAge = Integer.parseInt(model.getFIELD1());
			}
		}
		return minAge;
	}
	
	public int calculateMinDuration() {
		int minDuration = Integer.MAX_VALUE;
		for (DataModel model : data) {
			if(minDuration > Integer.parseInt(model.getFIELD11())) {
				minDuration = Integer.parseInt(model.getFIELD11());
			}
		}
		return minDuration;
	}
	
	public int calculateMaxAge() {
		int maxAge = Integer.MIN_VALUE;
		for (DataModel model : data) {
			if(maxAge < Integer.parseInt(model.getFIELD1())) {
				maxAge = Integer.parseInt(model.getFIELD1());
			}
		}
		return maxAge;
	}
	
	public int calculateMaxDuration() {
		int maxDuration = Integer.MIN_VALUE;
		for (DataModel model : data) {
			if(Integer.parseInt(model.getFIELD11()) > maxDuration) {
				maxDuration = Integer.parseInt(model.getFIELD11());
			}
		}
		return maxDuration;
	}
		
}
