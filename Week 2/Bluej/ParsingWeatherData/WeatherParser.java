import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of class WeatherParser here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WeatherParser
{
    private class RowNumber {
        long number;
    }
    
    private class HumidityObj {
        CSVRecord driestSoFar;
    }
    
    private CSVRecord findHottestHour(CSVParser parser, RowNumber row) {
        CSVRecord hottestSoFar = null;
        
        for (CSVRecord currentRow : parser) {
            if (hottestSoFar == null) {
                hottestSoFar = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double largestTemp = Double.parseDouble(hottestSoFar.get("TemperatureF"));
                
                if (currentTemp > largestTemp) {
                    hottestSoFar = currentRow;
                    row.number = parser.getCurrentLineNumber();
                }
            }
        }
        
        return hottestSoFar;
    }
    
    private CSVRecord findColdestHour(CSVParser parser, RowNumber row) {
        CSVRecord coldestSoFar = null;
        
        for (CSVRecord currentRow : parser) {
            if (coldestSoFar == null && Double.parseDouble(currentRow.get("TemperatureF")) > -500) {
                coldestSoFar = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                
                if (currentTemp < lowestTemp && currentTemp > -500) {
                    coldestSoFar = currentRow;
                    row.number = parser.getCurrentLineNumber();
                }
            }
        }
        
        return coldestSoFar;
    }
    
    private File fileWithColdestTemperature() {
        RowNumber row = new RowNumber();
        DirectoryResource weatherResource = new DirectoryResource();
        File coldestFile = null;
        CSVRecord coldestRecord = null;
        String coldestFileName = null;
        row.number = 0;
        
        for (File currentFile : weatherResource.selectedFiles()) {
            FileResource weatherFileResource = new FileResource(currentFile);
            CSVRecord currentRecord = findColdestHour(weatherFileResource.getCSVParser(), row);
            
            if (coldestRecord == null) {
                coldestFile = currentFile;
                coldestRecord = currentRecord;
            } else {
                double currentTemp = Double.parseDouble(currentRecord.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(coldestRecord.get("TemperatureF"));
                
                if (currentTemp < lowestTemp) {
                    coldestRecord = currentRecord;
                    coldestFile = currentFile;
                }
            }
        }
        
        return coldestFile;
    }
    
    private CSVRecord lowestHumidityInFile(CSVParser parser, HumidityObj humidityObj) {
        boolean firstRun = true;
        for (CSVRecord currentRow : parser) {
            String currentHumidity = currentRow.get("Humidity");
            
            if (humidityObj.driestSoFar == null && !currentHumidity.equals("N/A")) {
                humidityObj.driestSoFar = currentRow;
                //System.out.println("humidityObj initialized, driestSoFar: " + humidityObj.driestSoFar.get("Humidity"));
            } else {
                if (!currentHumidity.equals("N/A")) {
                    int humidityInt = Integer.parseInt(currentHumidity);
                    int lowestHumidity = Integer.parseInt(humidityObj.driestSoFar.get("Humidity"));
                    //System.out.println("HumidityInt: " + humidityInt + " Lowest Humidity: " + lowestHumidity);
                    
                    if (humidityInt < Integer.parseInt(humidityObj.driestSoFar.get("Humidity"))) {
                        //System.out.println("Current humidity " + humidityInt + " is less than driestSoFar " + humidityObj.driestSoFar.get("Humidity"));
                        humidityObj.driestSoFar = currentRow;
                        lowestHumidity = humidityInt;
                    }
                }
            }
        }
        System.out.println("LHIF() Driest So Far: " + humidityObj.driestSoFar.get("Humidity"));
        
        return humidityObj.driestSoFar;
    }
    
    private CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource directoryResource = new DirectoryResource();
        HumidityObj dailyHumidity = new HumidityObj();
        CSVRecord driestRecord = null;
        dailyHumidity.driestSoFar = null;
        CSVRecord driestOverall = null;
        
        for (File file : directoryResource.selectedFiles()) {
            FileResource weatherResource = new FileResource(file);
            driestRecord = lowestHumidityInFile(weatherResource.getCSVParser(), dailyHumidity);
        }
        
        return driestRecord;
    }
    
    private double averageTemperatureInFile(CSVParser parser) {
        int count = 0;
        double runningTemp = 0;
        double averageTemp = 0;
        
        for (CSVRecord row : parser) {
            double currentTemp = Double.parseDouble(row.get("TemperatureF"));
            if (currentTemp > -500) {
                runningTemp += currentTemp;
                count++;
            }
        }
        
        averageTemp = runningTemp / count;
        return averageTemp;
    }
    
    private double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        int count = 0;
        int humidity = 0;
        double runningTemp = 0;
        double averageTemp = 0;
        
        for (CSVRecord row : parser) {
            double currentTemp = Double.parseDouble(row.get("TemperatureF"));
            int currentHumidity = Integer.parseInt(row.get("Humidity"));
            
            if (currentTemp > -500 && currentHumidity > value) {
                runningTemp += currentTemp;
                count++;
            }
        }
        
        averageTemp = runningTemp / count;
        return averageTemp;
    }
    
    public void testFindHottestHour() {
        RowNumber row = new RowNumber();
        FileResource weatherFile = new FileResource();
        row.number = 0;
        
        CSVRecord hottestHour = findHottestHour(weatherFile.getCSVParser(), row);
        System.out.println("The hottest temperature was " + hottestHour.get("TemperatureF") + " on " + hottestHour.get("DateUTC") + " from row number " + row.number);
    }
    
    public void testFindHottestHourMulti() {
        RowNumber row = new RowNumber();
        DirectoryResource weatherResource = new DirectoryResource();
        row.number = 0;
        
        for (File weatherFile : weatherResource.selectedFiles()) {
            FileResource weatherFileResource = new FileResource(weatherFile);
            CSVRecord hottestHour = findHottestHour(weatherFileResource.getCSVParser(), row);
            System.out.println("The hottest temperature was " + hottestHour.get("TemperatureF") + " on " + hottestHour.get("DateUTC") + " from row number " + row.number);
        }
        
    }
    
    public void testFindColdestHour() {
        RowNumber row = new RowNumber();
        FileResource weatherFile = new FileResource();
        row.number = 0;
        
        CSVRecord coldestHour = findColdestHour(weatherFile.getCSVParser(), row);
        System.out.println("The coldest temperature was " + coldestHour.get("TemperatureF") + " on " + coldestHour.get("DateUTC") + " from row number " + row.number);
    }
    
    public void testFindColdestHourMulti() {
        RowNumber row = new RowNumber();
        DirectoryResource weatherResource = new DirectoryResource();
        row.number = 0;
        
        for (File weatherFile : weatherResource.selectedFiles()) {
            FileResource weatherFileResource = new FileResource(weatherFile);
            CSVRecord coldestHour = findColdestHour(weatherFileResource.getCSVParser(), row);
            System.out.println("The coldest temperature was " + coldestHour.get("TemperatureF") + " on " + coldestHour.get("DateUTC") + " from row number " + row.number);
        }
    }
    
    public void testFileWithColdestTemperature() {
        RowNumber row = new RowNumber();
        row.number = 0;
        File coldestFile = fileWithColdestTemperature();
        FileResource coldestFileResource = new FileResource(coldestFile);
        CSVRecord coldestRecord = findColdestHour(coldestFileResource.getCSVParser(), row);
        System.out.println("Coldest day was in file " + coldestFile.getName());
        System.out.println("Coldest temperature on that day was " + coldestRecord.get("TemperatureF") + " in row " + row.number);
        System.out.println("All the temperatures from the coldest day are:");
        
        for (CSVRecord currentRow : coldestFileResource.getCSVParser()) {
            System.out.println(currentRow.get("DateUTC") + ": " + currentRow.get("TemperatureF"));
        }
        
    }
    
    public void testLowestHumidityInFile() {
        HumidityObj humidityObj = null;
        FileResource weatherFileResource = new FileResource();
        CSVParser parser = weatherFileResource.getCSVParser();
        CSVRecord driestRecord = lowestHumidityInFile(parser, humidityObj);
        
        System.out.println("Lowest humidity was " + driestRecord.get("Humidity") + " at " + driestRecord.get("DateUTC"));
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord driestRecord = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + driestRecord.get("Humidity") + " at " + driestRecord.get("DateUTC"));
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fileResource = new FileResource();
        double averageTemp = averageTemperatureInFile(fileResource.getCSVParser());
        System.out.println("Average temperature in file is " + averageTemp);
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fileResource = new FileResource();
        int value = 80;
        double averageTemp = averageTemperatureWithHighHumidityInFile(fileResource.getCSVParser(), value);
        if (averageTemp == 0) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + averageTemp);
        }
    }
}
