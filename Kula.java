package blotto;

public class Kula {

	public static int getValue(int n) {
		if(n==0 || n==9) return 25;
		if(n==1 || n==8) return 13;
		if(n==2 || n==7) return 7;
		if(n==3 || n==6) return 4;
		return 1;
	}
}
