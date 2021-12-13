// Sai Rajuladevi, srajulad
package hw2;

public class CaseReaderFactory {
	CaseReader createReader(String filename) { // check the filetypes by splitting on the "." character
		if(filename.split("\\.")[1].equalsIgnoreCase("csv")) { // for CSVs
			CaseReader csvReader = new CSVCaseReader(filename);
			return csvReader;
		}
		if(filename.split("\\.")[1].equalsIgnoreCase("tsv")) { // for TSVs
			CaseReader tsvReader = new TSVCaseReader(filename);
			return tsvReader;
		}
		return null;
	}
}
