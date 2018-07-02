/**
 * Implements a random hash function of
 * the form (ax+b)%p.
 * Integers a and b are chosen randomly from {0,1, ...p-1}
 */
import java.util.*;
public class HashFunction {
	
	private int a, b, p;
	
	public HashFunction(int range) {
		p = findPrime(range);
		Random r = new Random();
		a = 0;
		while (a==0)
			a = r.nextInt(p);
		b = 0;
		while (b==0)
			b = r.nextInt(p);
		
	}
	
	/**
	 * 
	 * @param x integer that will be hashed
	 * @return hash value of x
	 */
	public int hash(int x) {
		 x = (int) Math.abs(x);
		 return (int) Math.abs((a*x + b)%p);
		
	}
	
	private int findPrime(int n) {
		boolean found = false;
		int num = n;
		while(!found) {
			if (isPrime(num))
				return num;
			num++;
		}
		return -1;
		
	}
	
	private boolean isPrime(int n) {
		for(int i= 2; i<=Math.sqrt(n); i++)
			if (n%i==0)
				return false;
		return true;
	}

}
