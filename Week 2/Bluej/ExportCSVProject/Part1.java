import edu.duke.*;
import org.apache.commons.csv.*;

/**
 * Write a description of class Part1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Part1
{
    private String getCountryInfo(CSVParser parser, String countryOfInterest) {
        String countryInfo = "";
        
        for (CSVRecord document : parser) {
            String country = document.get("Country");
            
            if (country.contains(countryOfInterest)) {
                countryInfo = country + ": " + document.get("Exports") + ": " + document.get("Value (dollars)");
             }
        }
        
        return countryInfo;
    }
    
    private void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord document : parser) {
            String exports = document.get("Exports");
            
            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                System.out.println("Countries exporting " + exportItem1 + " and " + exportItem2 + ": " + document.get("Country"));
            }
        }
    }
    
    private int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord document : parser) {
            if (document.get("Exports").contains(exportItem)) {
                count++;
            }
        }
        
        return count;
    }
    
    private void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord document : parser) {
            if (document.get("Value (dollars)").length() > amount.length()) {
                System.out.println(document.get("Country") + " " + document.get("Value (dollars)"));
            }
        }
    }
    
    public void tester() {
        FileResource fileResource = new FileResource();
        CSVParser parser = fileResource.getCSVParser();
        String countryInfo = getCountryInfo(parser, "Nauru");
        System.out.println(countryInfo);
        parser = fileResource.getCSVParser();
        listExportersTwoProducts(parser, "cotton", "flowers");
        parser = fileResource.getCSVParser();
        int totalExporters = numberOfExporters(parser, "cocoa");
        System.out.println("Total exports of cocoa: " + totalExporters);
        parser = fileResource.getCSVParser();
        System.out.println("Big exporters:");
        bigExporters(parser, "$900,000,000,000");
        
    }
}
