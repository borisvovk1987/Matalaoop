import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.AbstractObject;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

/**
 * 
 * @author Daniel_Vovk This is a class that takes a processed csv file of wifi
 *         points and does Filtration on some data . after that the class
 *         generates a kml file from the filtered data.
 */
public class csvToKml {

	private String pathToCsv; // what is the location of the csv file that needs to become kml file
	private String whatToFilter; // data to filter : time,date,id
	private String dataToLookFor; // what is the exact data we are looking for for instance the devise
	// "model=LG-H850" if the filter filtering ID
	private File kmlFile = new File("c:\\Users\\Boris_Vovk\\testttt\\Hello.kml");
	private ArrayList<SameTimeSample> listOfNotFilteredSamples = new ArrayList<SameTimeSample>();
	private ArrayList<SameTimeSample> listOfFilteredSamples;// this is a filtered Array List .

	public csvToKml(String pathToCsv, String whatToFilter, String dataToLookFor) throws Exception {
		this.pathToCsv = pathToCsv;
		this.whatToFilter = whatToFilter;
		this.dataToLookFor = dataToLookFor;
		readFromCsv();
		toFiltereCsv();
		toKml();

	}

	/*
	 * This function gets a csv merges file and creates a array list of same time
	 * samples arrays
	 */
	private void readFromCsv() throws IOException {
		File filecsv = new File(pathToCsv);
		FileReader fr = new FileReader(filecsv);
		BufferedReader re = new BufferedReader(fr);
		try {
			String line = re.readLine();
			line = re.readLine();
			while (line != null) {
				String[] toBreak = line.split(",");
				SameTimeSample csvrow = new SameTimeSample(toBreak[0], toBreak[2], toBreak[3], toBreak[4], toBreak[1]);
				int count = Integer.valueOf(toBreak[5]);
				int i = 0;
				int k = 6;

				while (i < count) {
					WifiPoint point = new WifiPoint(toBreak[k], toBreak[k + 1], toBreak[k + 2], toBreak[k + 3]);
					k = k + 4;
					csvrow.addPoint(point);
					i++;
				}
				listOfNotFilteredSamples.add(csvrow);
				line = re.readLine();
			}
		} catch (IOException e) {
			System.out.println("Eror in proceccing" + e);

		} finally {
			fr.close();
			re.close();
		}
	}

	/**
	 * This function filters the ArryList to a new ArryList with the desirable data
	 */
	private void toFiltereCsv() {
		Filters filters = new Filters(listOfNotFilteredSamples, whatToFilter, dataToLookFor);
		listOfFilteredSamples = filters.filterArray();

	}

	/**
	 * This function is a function that uses jaxb liberis and creates a kml file .
	 * @throws Exception
	 */
	private void toKml() throws Exception {
		Kml Kml = new Kml();
		Document document = Kml.createAndSetDocument().withName("wifiNetworks");
		for (int i = 0; i < listOfFilteredSamples.size(); i++) {
			for (int j = 0; j < listOfFilteredSamples.get(i).sameCheck.size(); j++) {


				String name = listOfFilteredSamples.get(i).sameCheck.get(j).getSsid();
				double lat = Double.parseDouble(listOfFilteredSamples.get(i).getLat());
				double lon = Double.parseDouble(listOfFilteredSamples.get(i).getLon());
				document.createAndAddPlacemark().withName(name).withOpen(true).createAndSetPoint().addToCoordinates(lon,
						lat);
				Kml.marshal(new File("C:\\Users\\Boris_Vovk\\testttt\\HeloKml.kml"));

			}

		}

	}

}
