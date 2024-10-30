package vttp.batch5.sdf.task01;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;

import vttp.batch5.sdf.task01.models.BikeEntry;

// Use this class as the entry point of your program

public class Main {
	public static final String[] POSITION = {"highest", "second highest", "third highest", "fourth highest", "fifth highest"};
	public static final String KEYWORD = "weathersit";	// keyword to lookout for when reading readme
	public static void main(String[] args) {
		String csvFile = "day.csv";
		String readmeFile = "Readme.txt";
		List<String> weatherSitList = new ArrayList<>();			// to store all weather situations from readme.txt
		List<BikeEntry> bikeEntriesList = new ArrayList<>();
		List<BikeEntry> bikeEntriesTop5List = new ArrayList<>();

		// Open csv file for reading
        try(Reader reader = new FileReader(csvFile);
        	BufferedReader br = new BufferedReader(reader)) {

			String line = "";

			// Read/skip headers
			br.readLine();   
			
			while((line = br.readLine()) != null)       // read line if it exists in input file
			{ 
				if(line.isEmpty())  // skip any lines with fullyblank entry/whitespace
                	continue;  

				String[] colEntries = csvParse(line);
				
				bikeEntriesList.add(BikeEntry.toBikeEntry(colEntries));
			}
		} catch(FileNotFoundException ex) {
			System.err.println("ERROR: File not found!");
			ex.printStackTrace();
		} catch(IOException ex) {
			System.err.println("ERROR: IO error!");
			ex.printStackTrace();
		}

		// Open readme file for reading
        try(Reader reader = new FileReader(readmeFile);
        	BufferedReader br = new BufferedReader(reader)) {
				
			String line = "";
			boolean isReadingWeatherSit = false;		
			
			while((line = br.readLine()) != null)       // read line if it exists in input file
			{ 				
				if(isReadingWeatherSit)
				{
					// - 1: Clear, Few clouds, Partly cloudy, Partly cloudy
					// get second half of line.split(":") str array (after ":")
					String[] lineTokens = line.split(":");

					String charBeforeSplit = "" + lineTokens[0].trim().charAt(lineTokens[0].trim().length()-1);

					// if reached end of weatherSit (ie. last char is NaN before ":")
					try {
						int i = Integer.parseInt(charBeforeSplit);
					} catch(NumberFormatException ex) {
						break;
					}

					String weatherSit = lineTokens[1].trim();
					weatherSitList.add(weatherSit);
				}
				else
				{
					if(line.contains(KEYWORD))  
						isReadingWeatherSit = true;
				}
			}
		} catch(FileNotFoundException ex) {
			System.err.println("ERROR: File not found!");
			ex.printStackTrace();
		} catch(IOException ex) {
			System.err.println("ERROR: IO error!");
			ex.printStackTrace();
		}
		
		// Compare and sort using total cyclist
		Comparator<BikeEntry> compareTotalCyclistAsc = Comparator.comparing(be -> (be.getCasual() + be.getRegistered()));
		bikeEntriesList.sort(compareTotalCyclistAsc.reversed());
		
		// Store top 5 entries
		int count = 0;
		for (BikeEntry bikeEntry : bikeEntriesList) {
			if(count++ > 4)
				break;
			bikeEntriesTop5List.add(bikeEntry);
		}

		printInfo(bikeEntriesTop5List, weatherSitList);
	}

	private static void printInfo(List<BikeEntry> bikeEntriesTop5List, List<String> weatherSitList) {
		// FOREACH LOOP OF TOP 5 DAYS
		// POSITION = {highest, second highest, third ... } (use enum or smth and increase index to print this while going thru each loop)
		// TOTAL = sum of casual and registered cyclist 

		for(int i = 0; i < bikeEntriesTop5List.size(); i++) {
			BikeEntry be = bikeEntriesTop5List.get(i);

			// printing out according to format as requested
			/*
			System.out.printf("The %s ", POSITION[i]);
			System.out.printf("recorded number of cyclists was in %s, ", Utilities.toSeason(be.getSeason()));
			System.out.printf("on a %s ", Utilities.toWeekday(be.getWeekday()));			
			System.out.printf("in the month of %s. ", Utilities.toMonth(be.getMonth()));	
			System.out.printf("There were a total of %d cyclists. ", (be.getCasual() + be.getRegistered()));

			System.out.printf("The weather was %s. ", weatherSitList.get(be.getWeather()-1));

			if(be.isHoliday())
				System.out.printf("%s was a holiday.\n", Utilities.toWeekday(be.getWeekday()));
			else
				System.out.printf("%s was not a holiday.\n", Utilities.toWeekday(be.getWeekday()));
			*/

			// printing out according to example output
			System.out.printf("The %s (position) ", POSITION[i]);
			System.out.printf("recorded number of cyclists was in %s (season), ", Utilities.toSeason(be.getSeason()));
			System.out.printf("on a %s (day) ", Utilities.toWeekday(be.getWeekday()));			
			System.out.printf("in the month of %s (month). ", Utilities.toMonth(be.getMonth()));	
			System.out.printf("There were a total of %d (total) cyclist. ", (be.getCasual() + be.getRegistered()));

			System.out.printf("The weather was %s (weather). ", weatherSitList.get(be.getWeather()-1));

			if(be.isHoliday())
				System.out.printf("%s (day) was a holiday.\n", Utilities.toWeekday(be.getWeekday()));
			else
				System.out.printf("%s (day) was not a holiday.\n", Utilities.toWeekday(be.getWeekday()));

			// Add line break if its not last line
			if(i < (bikeEntriesTop5List.size()-1))
				System.out.println();
		}
	}

	public static String[] csvParse(String row) {
        List<String> colEntries = new ArrayList<>();    // to hold each col entries of a row
        StringBuilder sbCurrentEntry = new StringBuilder();      

        // Loop thru each ch in a row
        for(char ch : row.toCharArray()) 
        {
            // Start of next entry found
            if(ch == ',')  
            {
                colEntries.add(sbCurrentEntry.toString().trim());
                sbCurrentEntry.setLength(0);    // reset sb
            } 
            else 
                sbCurrentEntry.append(ch);
        }

        // Add the last entry as there is no comma at the end of row
        colEntries.add(sbCurrentEntry.toString().trim()); 

        return colEntries.toArray(new String[0]);
    }
}
