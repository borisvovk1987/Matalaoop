import java.util.ArrayList;

/**
 * 
 * @author Daniel_Vovk This calss is a filter that gets the information about
 *         what to sort , time,location or ID .
 * 
 * 
 * 
 */
public class Filters {

	public String dataToFilterBy;
	public String formatType;
	private ArrayList<SameTimeSample> noFilteredList; // what to put in to make compares.
	private ArrayList<SameTimeSample> filteredList = new ArrayList<SameTimeSample>();;

	public Filters(ArrayList<SameTimeSample> noFilteredList, String dataToFilterBy, String formatType) {
		this.noFilteredList = noFilteredList;
		this.dataToFilterBy = dataToFilterBy;
		this.formatType = formatType;


	}

	/**
	 * This is a function that filters a ArrayLIst of SametimeSamplesy by the required data.
	 * @return
	 */
	public ArrayList<SameTimeSample> filterArray() {

		if (dataToFilterBy == null) {
			return noFilteredList;
		} else if (dataToFilterBy == "Time") {
			for (int i = 0; i < noFilteredList.size(); i++) {
				if (noFilteredList.get(i).getTime().equals(formatType)) {
					filteredList.add(noFilteredList.get(i));
				}
			}

		} else if (dataToFilterBy == "Location") {
			for (int i = 0; i < noFilteredList.size(); i++) {
				String LocationS = noFilteredList.get(i).getAlt() + " " + noFilteredList.get(i).getLat() + " "
						+ noFilteredList.get(i).getAlt();
				if (formatType.equals(LocationS)) {
					filteredList.add(noFilteredList.get(i));

				}
			}
		} else if (dataToFilterBy == "ID") {
			for (int i = 0; i < noFilteredList.size(); i++) {
				if (noFilteredList.get(i).getPhoneId().equals(formatType)) {
					filteredList.add(noFilteredList.get(i));
				}
			}
		}
		return filteredList;
		
	
	}

}
