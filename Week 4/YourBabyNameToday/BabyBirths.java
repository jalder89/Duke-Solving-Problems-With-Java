/**
 * Write a description of class BabyBirths here.
 *
 * @author Jessica Alder
 * @version 01.23.2022.0.0.1
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;
import java.io.File;

public class BabyBirths
{
    class BirthRecord {
        private String name;
        private String gender;
        private int birthCount;
        private int rank;
        private int year;
        
        public BirthRecord(String name, String gender, int birthCount, int rank, int year) {
            this.name = name;
            this.gender = gender;
            this.birthCount = birthCount;
            this.rank = rank;
            this.year = year;
        }
        
        public String getName() {
            return name;
        }
        
        public String getGender() {
            return gender;
        }
        
        public int getBirthCount() {
            return birthCount;
        }
        
        public int getYear() {
            return year;
        }
        
        public int getRank() {
            return rank;
        }
        
        public void setRank(int newRank) {
            rank = newRank;
        }
        
        public void setYear(int newYear) {
            year = newYear;
        }
    }
    
    public void printData() {
        FileResource csvResource = new FileResource();
        for (CSVRecord birthRecord : csvResource.getCSVParser(false)) {
            System.out.println("Name: " + birthRecord.get(0) + "\nGender: " + 
            birthRecord.get(1) + "\nBirths: " + 
            birthRecord.get(2) + "\n-----------------");
        }
    }
    
    public void totalBirths() {
        int totalBirths = 0;
        int totalNames = 0;
        int totalFemaleBirths = 0;
        int totalFemaleNames = 0;
        int totalMaleBirths = 0;
        int totalMaleNames = 0;
        
        FileResource csvResource = new FileResource();
        
        for (CSVRecord birthRecord : csvResource.getCSVParser(false)) {
            if (birthRecord.get(1).equals("M")) {
                totalMaleBirths += Integer.parseInt(birthRecord.get(2));
                totalNames++;
                totalMaleNames++;
            } else {
                totalFemaleBirths += Integer.parseInt(birthRecord.get(2));
                totalNames++;
                totalFemaleNames++;
            }
        }
        
        totalBirths += totalMaleBirths + totalFemaleBirths;
        System.out.println("Total Births: " + totalBirths + 
        "\nTotal Female Births: " + totalFemaleBirths +
        "\nTotal Male Births: " + totalMaleBirths +
        "\nTotal Names: " + totalNames +
        "\nTotal Female Names: " + totalFemaleNames + 
        "\nTotal Male Names: " + totalMaleNames);
    }
    
    private int getRank(int year, String name, String gender) {
        /* This method returns the rank of the name in the file for the given gender,  
         * where rank 1 is the name with the largest number of births. 
         * If the name is not in the file, then -1 is returned.
         */
        
        // Get the file for the desired year
        FileResource csvResource = new FileResource("babyData/byYear/yob" + year + ".csv");
        
        // Create an ArrayList of BirthRecords
        List<BirthRecord> birthRecords = new ArrayList<>();
        
        // Add the records for the desired gender to the ArrayList
        for (CSVRecord csvRecord : csvResource.getCSVParser(false)) {
            if(gender.equals(csvRecord.get(1))) {
                birthRecords.add(new BirthRecord(csvRecord.get(0), csvRecord.get(1), Integer.parseInt(csvRecord.get(2)), -1, -1));
            }
        }
        
        // Sort the ArrayList in descending order based on the number of births
        sortArrayByCount(birthRecords);
        
        for (int i = 0; i < birthRecords.size(); i++) {
            if (birthRecords.get(i).getName().equals(name)) {
                return i + 1;
            }
        }
        
        return -1;
    }
    
    // Get the name at the given rank for the given gender in the given year
    private String getName(int year, int rank, String gender) {
        FileResource fileResource = new FileResource("babyData/byYear/yob" + year + ".csv");
        List<BirthRecord> birthRecords = new ArrayList<>();
                
        for (CSVRecord csvRecord : fileResource.getCSVParser(false)) {
            if (gender.equals(csvRecord.get(1))) {
                birthRecords.add(new BirthRecord(csvRecord.get(0), csvRecord.get(1), Integer.parseInt(csvRecord.get(2)), -1, -1));
            }
        }
        
        if (rank >= birthRecords.size()) {
            return "NoName";
        } else {
            return birthRecords.get(rank - 1).getName();
        }
    }
    
    private void whatIsNameInYear(String name, int year, int newYear, String gender) {
        // This method determines what name would have been named if they were born in a different year, based on the same popularity. 
        // That is, you should determine the rank of name in the year they were born, and then print the name born in newYear that is at the same rank and same gender. 
        // For example, using the files "yob2012short.csv" and "yob2014short.csv", notice that in 2012 Isabella is the third most popular girl's name. 
        // If Isabella was born in 2014 instead, she would have been named Sophia, the third most popular girl's name that year.
        
        int nameRank = getRank(year, name, gender);
        System.out.println(name + " in year " + year + "is ranked #" + nameRank);
        String newName = getName(newYear, nameRank, gender);
        System.out.println(name + " born in " + year + " would be " + newName + " if she was born in 2014.");
        
    }
    
    public int yearOfHighestRank(String name, String gender) {
        // This method selects a range of files to process and returns an integer, the year with the highest rank for the name and gender. 
        // If the name and gender are not in any of the selected files, it should return -1.
        DirectoryResource directoryResource = new DirectoryResource();
        List<BirthRecord> birthRecordsRanked = new ArrayList<>();
        List<BirthRecord> birthRecordsRankedByYear = new ArrayList<>();
        String fileName = "";
        int year = 0;
        int count = 0;
        
        for (File file : directoryResource.selectedFiles()) {
            List<BirthRecord> birthRecords = new ArrayList<>();
            fileName = file.getName();
            year = Integer.parseInt(fileName.substring(3, fileName.length() - 4));
            FileResource fileResource = new FileResource(file);
            
            // For each record in the selected file that matches the gender provided, add the file to an array as a birthRecord.
            for (CSVRecord csvRecord : fileResource.getCSVParser(false)) {
                if (gender.equals(csvRecord.get(1))) {
                    birthRecords.add(new BirthRecord(csvRecord.get(0), csvRecord.get(1), Integer.parseInt(csvRecord.get(2)), -1, -1));
                }
            }
            
            sortArrayByCount(birthRecords);
            
            // Get the rank of the name for the selected file year and add it to the ranked array as a birthRecord.
            for (int i = 0; i < birthRecords.size(); i++) {
                if (birthRecords.get(i).getName().equals(name)) {
                    birthRecordsRanked.add(new BirthRecord(birthRecords.get(i).getName(), birthRecords.get(i).getGender(), birthRecords.get(i).getBirthCount(), i + 1, year));
                }
            }
        }
        
        sortArrayByRank(birthRecordsRanked);
        
        for (BirthRecord birthRecord : birthRecordsRanked) {
            if (birthRecord.getBirthCount() == birthRecordsRanked.get(0).getBirthCount()) {
                birthRecordsRankedByYear.add(birthRecord);
            }
        }
        
        sortArrayByYear(birthRecordsRankedByYear);
        
        if (birthRecordsRanked.isEmpty()) {
            return -1;
        } else {
            sortArrayByRank(birthRecordsRanked);
            System.out.println("The earliest year that " + name + " was ranked the highest is: " + birthRecordsRankedByYear.get(0).getYear() + " at rank: " + birthRecordsRankedByYear.get(0).getRank());
            System.out.println(name + " was ranked " + birthRecordsRanked.get(0).getRank() + " in year " + birthRecordsRanked.get(0).getYear());
            return birthRecordsRanked.get(0).getYear();
        }
    }
    
    private double getAverageRank(String name, String gender) {
        // This method selects a range of files to process and returns a double representing the average rank of the name and gender over the selected files. 
        // It should return -1.0 if the name is not ranked in any of the selected files.
        int year = 0;
        double rankAverage = 0.0;
        double rankTotal = 0.0;
        String fileName = "";
        ArrayList<Integer> ranks = new ArrayList<>();
        
        DirectoryResource directoryResource = new DirectoryResource();
        for (File file : directoryResource.selectedFiles()) {
            FileResource fileResource = new FileResource(file);
            fileName = file.getName();
            year = Integer.parseInt(fileName.substring(3, fileName.length() - 4));
            ranks.add(getRank(year, name, gender));
        }
        
        if (ranks.isEmpty()) {
            return -1.0;
        } else {
            // Calculate rank total and average
            for (int rank : ranks) {
            rankTotal += rank;
            }
            rankAverage = rankTotal / ranks.size();
            
            return rankAverage;
        }
    }
    
    private int getTotalBirthsRankedHigher(int year, String name, String gender) {
        // This method returns an integer, the total number of births of those names with the same gender and same year who are ranked higher than name.
        // Given a name, year, and gender get the number of names ranked higher than given name in that year. Return the total birth count of the names ranked higher.
        int givenNameRank = 0;
        int totalHigherRankedBirths = 0;
        FileResource csvResource = new FileResource("babyData/byYear/yob" + year + ".csv");
        List<BirthRecord> birthRecords = new ArrayList<>();
        
        // Add the records for the desired gender to the ArrayList
        for (CSVRecord csvRecord : csvResource.getCSVParser(false)) {
            if(gender.equals(csvRecord.get(1))) {
                birthRecords.add(new BirthRecord(csvRecord.get(0), csvRecord.get(1), Integer.parseInt(csvRecord.get(2)), -1, -1));
            }
        }
        
        // Sort the array of birth records by count and then get the rank of the given name
        sortArrayByCount(birthRecords);
        
        for (int i = 0; i < birthRecords.size(); i++) {
            if (birthRecords.get(i).getName().equals(name)) {
                givenNameRank += i + 1;
            }
        }
        
        for (int i = 0; i < (givenNameRank - 1); i++) {
            totalHigherRankedBirths += birthRecords.get(i).getBirthCount();
        }
        
        return totalHigherRankedBirths;
    }
    
    private void sortArrayByRank(List<BirthRecord> birthRecords) {
        // Sort the ArrayList in descending order based on the number of births
        Collections.sort(birthRecords, (record1, record2) -> 
        record1.getRank() - record2.getRank());
    }
    
    private void sortArrayByCount(List<BirthRecord> birthRecords) {
        // Sort the ArrayList in descending order based on the number of births
        Collections.sort(birthRecords, Collections.reverseOrder((record1, record2) -> 
        record1.getBirthCount() - record2.getBirthCount()));
    }
    
    private void sortArrayByYear(List<BirthRecord> birthRecords) {
        // Sort the ArrayList in descending order based on the number of births
        Collections.sort(birthRecords, (record1, record2) -> 
        record1.getYear() - record2.getYear());
    }
    
    public void testGetRank(int year, String name, String gender) {
        int rank = 0;
        
        rank = getRank(year, name, gender);
        System.out.println("The name " + name + " was ranked #" + rank + " in " + year + ".");
    }
    
    public void testGetName(int year, int rank, String gender) {
        String name = "";
        
        name = getName(year, rank, gender);
        System.out.println("The name " + name + " was ranked #" + rank + " in " + year + ".");
    }
    
    public void testWhatIsNameInYear(int year, int newYear, String name, String gender) {
        String newName = "";
        
        whatIsNameInYear(name, year, newYear, gender);
    }
    
    public void testGetAverageRank(String name, String gender) {
        double averageRank = 0.0;
        
        averageRank = getAverageRank(name, gender);
        System.out.println("The average rank for " + name + " in the files selected is " + averageRank);
    }
    
    public void testGetTotalBirthsRankedHigher(int year, String name, String gender) {
        int totalBirthsRankedHigher = 0;
        totalBirthsRankedHigher = getTotalBirthsRankedHigher(year, name, gender);
        System.out.println("The total births for names ranked higher than " + name + " is: " + totalBirthsRankedHigher);
    }
}
