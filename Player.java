package blotto;

public class Player {

	private String name;
	private String strategy;
	
	public Player(String name, String strategy) {
		this.name = name;
		this.strategy = strategy;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getStrategy() {
		return this.strategy;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Player)) return false;
		Player other = (Player) o;
		return other.name.equals(this.name);
	}
}
