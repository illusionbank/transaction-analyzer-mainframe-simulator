package io.illusionbank.sec.transaction.analyzer.repository;

import java.util.Arrays;
import java.util.List;

public class ClientRepositoty {
	private List<String> clients;
	
	public ClientRepositoty() {
		init();
	}
	
	public List<String> getClients() {
		return clients;
	}
	
	private void init() {
		clients = Arrays.asList(
			"78984725739",
			"42392678438",
			"79688423923",
			"96626789539",
			"97477472344",
			"75873249659",
			"63237472266",
			"39752493968",
			"92459859677",
			"75442468446",
			"75598565523"
		);
	}
	
	
}
