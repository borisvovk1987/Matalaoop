
import java.util.ArrayList;

/**
 * 
 * @author Daniel_Vovk This class is a representation of samples at the same
 *         time,place with the same id.
 * 
 *         In every object there is a Array list of wifi points , this array
 *         represents all the wifi points that were readable at the same moment
 *         and place
 */
public class SameTimeSample {

	/*
	 * Data members
	 */
	private String time;
	private String lat;
	private String lon;
	private String alt;
	private String deviseId;
	public int Wifinetworks;

	public ArrayList<WifiPoint> sameCheck; // all the WifiPoints in each of the ArrayList have the same
	// time,location,id of the device

	/*
	 * Constructors
	 */
	public SameTimeSample(String time, String lat, String lon, String alt, String phoneId) {
		sameCheck = new ArrayList<WifiPoint>();
		this.time = time;
		this.deviseId = phoneId;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.Wifinetworks=0;

	}

	public SameTimeSample() {
		this.Wifinetworks=0;
	}

	/*
	 * Methods
	 */
	public void addPoint(WifiPoint newPoint) {
		sameCheck.add(newPoint);
		Wifinetworks++;

	}

	public String getTime() {
		return time;
	}

	public String getPhoneId() {
		return deviseId;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	public String getAlt() {
		return alt;
	}

	public String toCsv() {
		String s = time + "," + deviseId + "," + lat + "," + lon + "," + alt + ","+Integer.toString(Wifinetworks)+",";
		String k = "";
		for (int i = 0; i < sameCheck.size() && i < 10; i++) {
			k = k + sameCheck.get(i).toString();
		}
		return s + k;

	}

}
