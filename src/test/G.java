package test;

import java.util.List;

public class G implements Comparable<G>{
	
	private Integer id;
	private List<Integer> list;
	
	public G(Integer id, List<Integer> list) {
		super();
		this.id = id;
		this.list = list;
	}

	
	
	public Integer getId() {
		return id;
	}



	public List<Integer> getList() {
		return list;
	}



	@Override
	public int compareTo(G o) {
			if(this.getList().size() > o.getList().size())
				return 1;
			else if(this.getList().size() < o.getList().size())
				return -1;
			
		return 0;
	}
	
	

}
