package test;

import java.util.Comparator;

public class GComparator implements Comparator<G> {

	@Override
	public int compare(G o1, G o2) {
		return o1.compareTo(o2);
		
	}

}
