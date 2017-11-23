

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

// must create an empty constructor to class Filters to run these test //

public class FiltersTest {
	
ArrayList<WifiPoint> x = new ArrayList<WifiPoint>();

	@Test
	public void test() {
		Filters test=new Filters();
		ArrayList<SameTimeSample> ans = test.filterArray();  
		assertEquals(x , ans); // put the array list you expect to get after using the filterArray method here, instead of "x" and then click run //
		
		
	}

}
