package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test2 {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer>ones = new ArrayList<Integer>();
		ones.add(1);
		ones.add(1);
		ones.add(1);
		
		List<Integer>twos = new ArrayList<Integer>();
		twos.add(2);
		twos.add(2);

		G g1 = new G(1,ones);
		G g2 = new G(2,twos);
		
		Map<Integer,G>tm = new TreeMap<Integer,G>(new GComparator());
		
		
		

	}

}
