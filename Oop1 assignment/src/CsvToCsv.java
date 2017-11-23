import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Daniel _Vovk This Class is the main part of the program , all the
 *         benching happens here. The main idea of the program is to take csv
 *         file from a application that called wigle wifi and create a new csv
 *         file with new order of data .
 *
 */
public class CsvToCsv {

	private static final int INDEX_OF_SSID_IN_CSV_FILE = 1;
	private static final int INDEX_OF_MAC_IN_CSV_FILE = 0;
	private static final int INDEX_OF_LAT_IN_CSV_FILE = 6;
	private static final int INDEX_OF_LON_IN_CSV_FILE = 7;
	private static final int INDEX_OF_ALT_IN_CSV_FILE = 8;
	private static final int INDEX_OF_TIME_IN_CSV_FILE = 3;
	private static final int INDEX_OF_SIGNAL_IN_CSV_FILE = 5;
	private static final int INDEX_OF_CHANNEL_IN_CSV_FILE = 4;
	private static final int INDEX_OF_PHONEID_IN_CSV_FILE = 2;
	private static final String HEADER_OF_FILE = "TIME,ID,LAT,LON,ALT,WIFI networks,SSID1,MAC1,FREQUENCY1,Signal1,"
			+ "SSID2,MAC2,FREQUENCY2,Signal2," + "SSID3,MAC3,FREQUENCY3,Signal3," + "SSID4,MAC4,FREQUENCY4,Signal4,"
			+ "SSID5,MAC5,FREQUENCY5,Signal5," + "SSID6,MAC6,FREQUENCY6,Signal6," + "SSID7,MAC7,FREQUENCY7,Signal7,"
			+ "SSID8,MAC8,FREQUENCY8,Signal8," + "SSID9,MAC9,FREQUENCY9,Signal9," + "SSID10,MAC10,FREQUENCY10,Signal10";

	private ArrayList<SameTimeSample> rowsOfSameCheck = new ArrayList<SameTimeSample>(); // Arraylist
	// of
	// same
	// time
	// samples

	//// Constructors ////
	public CsvToCsv() {

	}

	/**
	 * This is the cunstractor of the program , from here the program does all the
	 * processing .
	 * 
	 * @param directoryPath
	 * @param newFile
	 */
	public CsvToCsv(String directoryPath, String newFile) {

		try {
			processFile(directoryPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		toWrite(newFile);

	}

	//// Methods////

	/**
	 * This method creates one csv file that contains all the csv files in the
	 * directory folder
	 * 
	 * @param csvFolder
	 * @return
	 * @throws FileNotFoundException
	 */
	private void processFile(String directoryPath) throws Exception {
		File csvFolder = new File(directoryPath);
		File[] csvFolderFiles = csvFolder.listFiles();

		for (int i = 0; i < csvFolderFiles.length; i++) {
			FileReader fr = new FileReader(csvFolderFiles[i]);
			if (isCsv(csvFolderFiles[i])) { // checks if the file is a .csv file or not
				BufferedReader re = new BufferedReader(fr);

				try {
					String[] dataPoints = re.readLine().split(",");
					String ID = dataPoints[INDEX_OF_PHONEID_IN_CSV_FILE]; // the id of the devise
					re.readLine();
					String[] firstPoint = re.readLine().split(",");
					WifiPoint wifiPoint = new WifiPoint(firstPoint[INDEX_OF_SSID_IN_CSV_FILE],
							firstPoint[INDEX_OF_MAC_IN_CSV_FILE], firstPoint[INDEX_OF_CHANNEL_IN_CSV_FILE],
							firstPoint[INDEX_OF_SIGNAL_IN_CSV_FILE]);
					SameTimeSample sameTimeSample = new SameTimeSample(firstPoint[INDEX_OF_TIME_IN_CSV_FILE],
							firstPoint[INDEX_OF_LAT_IN_CSV_FILE], firstPoint[INDEX_OF_LON_IN_CSV_FILE],
							firstPoint[INDEX_OF_ALT_IN_CSV_FILE], ID);
					sameTimeSample.addPoint(wifiPoint);
					rowsOfSameCheck.add(sameTimeSample);
					boolean flag = true; // this flag checks if there is more then one line in the file .
					while (re.read() != -1) {
						flag = false;
						String[] nextPoint = re.readLine().split(",");
						wifiPoint = new WifiPoint(nextPoint[INDEX_OF_SSID_IN_CSV_FILE],
								nextPoint[INDEX_OF_MAC_IN_CSV_FILE], nextPoint[INDEX_OF_CHANNEL_IN_CSV_FILE],
								nextPoint[INDEX_OF_SIGNAL_IN_CSV_FILE]);
						if (sameTimeSample.getTime().equals(nextPoint[3])
								&& sameTimeSample.getLat().equals(nextPoint[6])
								&& sameTimeSample.getLon().equals(nextPoint[7])
								&& sameTimeSample.getAlt().equals(nextPoint[8])) {
							sameTimeSample.addPoint(wifiPoint);

						} else {

							sortBySignal(sameTimeSample); // sorting the signals from the strongest to the weakest
							if (sameTimeSample.sameCheck.size() > 10) { // resizing the number of Wifi points to 10 in
								// each sample
								sameTimeSample.sameCheck = resize(sameTimeSample.sameCheck);
								sameTimeSample.Wifinetworks = 10;

							}
							rowsOfSameCheck.add(sameTimeSample);
							sameTimeSample = new SameTimeSample(nextPoint[INDEX_OF_TIME_IN_CSV_FILE],
									nextPoint[INDEX_OF_LAT_IN_CSV_FILE], nextPoint[INDEX_OF_LON_IN_CSV_FILE],
									nextPoint[INDEX_OF_ALT_IN_CSV_FILE], ID);
							sameTimeSample.addPoint(wifiPoint);
							rowsOfSameCheck.add(sameTimeSample);

						}

					}
					if (flag)
						rowsOfSameCheck.add(sameTimeSample);

				} catch (IOException exception) {
					System.out.println("Error in processing file" + exception);
				} finally {
					fr.close();
					re.close();
				}

			}

		}
	}

	/**
	 * 
	 * @param writingPath
	 */
	private void toWrite(String writingPath) {
		FileWriter Write = null;
		try {
			int i = 0;
			Write = new FileWriter(writingPath);
			Write.write(HEADER_OF_FILE);
			Write.append("\n");
			Write.write(rowsOfSameCheck.get(i).toCsv()); ////
			Write.append("\n");
			for (i = 2; i < rowsOfSameCheck.size(); i++) {
				Write.write(rowsOfSameCheck.get(i).toCsv());
				i++;
				Write.write("\n");

			}

		} catch (Exception e) {
			System.out.println("Eror in CsvFileWriter");
			e.printStackTrace();

		} finally {
			try {
				Write.flush();
				Write.close();

			} catch (IOException e) {
				System.out.println("Eror while flushing /closing file write");
				e.printStackTrace();

			}
		}
		System.out.println(rowsOfSameCheck.size());
		System.out.println(rowsOfSameCheck.get(27).sameCheck.size());

	}

	/**
	 * 
	 * @param sameCheck
	 * @return
	 */
	private ArrayList<WifiPoint> resize(ArrayList<WifiPoint> sameCheck) {
		ArrayList<WifiPoint> newSameCheack = new ArrayList<WifiPoint>();
		for (int i = 0; i < 10; i++) {
			newSameCheack.add(sameCheck.get(i));
		}

		return newSameCheack;
	}

	/**
	 * This function takes a SampleTimeSample object and sorts the first 10 Wifi by
	 * the strongest signal.
	 * 
	 * @param sample
	 */
	private void sortBySignal(SameTimeSample sample) {
		int max;
		WifiPoint temp = new WifiPoint();
		for (int i = 0; i < 10 && i < sample.sameCheck.size(); i++) {
			max = Integer.valueOf(sample.sameCheck.get(i).getSignal());
			int j = i + 1;
			for (; j < sample.sameCheck.size() - 1; j++) {
				if (Integer.valueOf(sample.sameCheck.get(j).getSignal()) > max) {
					temp = sample.sameCheck.get(j);
					sample.sameCheck.add(j, sample.sameCheck.get(i));
					sample.sameCheck.add(i, temp);
					max = Integer.valueOf(sample.sameCheck.get(i).getSignal());
				}
			}
		}

	}

	/**
	 * This function checks if the file is csv or not
	 * 
	 * @param checkIfCsvFile
	 * @return
	 */
	private boolean isCsv(File checkIfCsvFile) {
		boolean ans = false;
		String name = checkIfCsvFile.getName();
		if (name.endsWith("csv"))
			ans = true;
		return ans;
	}

}
