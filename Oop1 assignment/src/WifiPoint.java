/**
 * 9
 * @author Daniel_Vovk
 *
 */

/*
 * This is a class that represents object of wifi points . Every object is a
 * representation of a specific wifi point that was read at a certain moment and
 * place with the Wigle_Wifi application .
 * 
 */

//// Datat members////
public class WifiPoint {
	private String ssid;
	private String mac;
	private String frequence;
	private String signal;

	//// constructors////
	public WifiPoint(String ssid, String mac, String frequence, String signal) {
		this.ssid = ssid;
		this.mac = mac;
		this.frequence = frequence;
		this.signal = signal;

	}

	public WifiPoint() {
		this.ssid = null;
		this.mac = null;
		this.frequence = null;
		this.signal = null;
	}

	public WifiPoint(WifiPoint copyPoint) { // copy constructor
		this.ssid = copyPoint.ssid;
		this.mac = copyPoint.mac;
		this.frequence = copyPoint.frequence;
		this.signal = copyPoint.signal;
	}
	/*
	 * Methods
	 */

	public String getSsid() {
		return ssid;
	}

	public String getMac() {
		return mac;
	}

	public String getFrequence() {
		return frequence;
	}

	public String getSignal() {
		return signal;
	}

	public String toString() {
		return ssid + "," + mac + "," + frequence + "," + signal + ",";
	}

}
