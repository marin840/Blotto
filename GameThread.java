package blotto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GameThread extends Thread{

	private List<Player> players;
	private Map<String, Integer> rezultati;
	private Map<String, Integer> resultSorted;

	
	public GameThread(List<Player> players) {
		this.players = players;
		rezultati = new HashMap<>();
		resultSorted = new HashMap<>();

	}
	
	@Override
	public void run() {
		players.forEach((p) -> rezultati.put(p.getName(), 0));
		
		for(Player p1: players)
			for(Player p2: players) {
				if(p1==p2) continue;
				//-----------
				//-----------
				String[] str1 = p1.getStrategy().split(" ");
				String[] str2 = p2.getStrategy().split(" ");
				
				for(int i = 0; i < 10; i++) {
					try {
					if(Integer.parseInt(str1[i]) > Integer.parseInt(str2[i]))
						rezultati.put(p1.getName(), rezultati.get(p1.getName()) + Kula.getValue(i));
	
					else
					if(Integer.parseInt(str1[i]) < Integer.parseInt(str2[i]))
						rezultati.put(p2.getName(), rezultati.get(p2.getName()) + Kula.getValue(i));
					
					else {
						rezultati.put(p1.getName(), rezultati.get(p1.getName()) + Kula.getValue(i)/2);
						rezultati.put(p2.getName(), rezultati.get(p2.getName()) + Kula.getValue(i)/2);
					}}catch(NumberFormatException e) {System.out.println(str1[i]);}
				}
				
			}
		rezultati.forEach((name, value) ->  rezultati.put(name, value/2));	//jer dva puta igraju svaki sa svakim
		resultSorted = rezultati.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		resultSorted.forEach((name, value) -> System.out.println(name + " : " + value));
		//prikazi rezultate
		new ResultFrame(resultSorted, players);
	}
	
	public Map<String, Integer> getRezultati(){
		System.out.println(resultSorted.size());
		return resultSorted;
	}
}
