import java.util.*;

/**
 * @author Tristan Anderson
 */

public class HashTable {

	ArrayList<LinkedList<Tuple>> table;
	HashFunction function;
	private int elements;
	private int size;

	public HashTable(int size) {
		int p = findPrime(size);
		this.table = new ArrayList<LinkedList<Tuple>>(p);

		this.function = new HashFunction(p);
		this.size = p;
	}

	public int maxLoad() {
		int i = 0;
		int max = 0;
		for (i = 0; i < table.size(); i++) {
			if (table.get(i).size() > max)
				max = table.get(i).size();
		}
		return max;
	}

	public float averageLoad() {
		// check out my num elements functions
		int i = 0;
		int totalElements = 0;
		int nonNullBin = 0;
		for (i = 0; i < table.size(); i++) {
			if (table.get(i) != null && table.get(i).size() != 0) {
				totalElements += table.get(i).size();
				nonNullBin++;
			}
			// && table.get(i).size() !=0

		}
		if (nonNullBin == 0)
			return 0;

		return totalElements / (float) nonNullBin;
	}

	public int size() {
		return size;
	}

	public int numElements() {
		return elements;
	}

	public float loadFactor() {
		return elements / (float) size;
	}

	public void add(Tuple t) {
		int hashed = this.function.hash(t.getKey());
		int i = 0;
		int flag = 0;
		while (table.size() <= hashed) {
			LinkedList<Tuple> temp = new LinkedList<Tuple>();
			table.add(temp);
		}
		for (i = 0; i < table.get(hashed).size(); i++) {
			if (t.equals(table.get(hashed).get(i))) {
				flag = 1;
				break;
			}
		}
		if (flag == 1)
			table.get(hashed).get(i).addDupe();
		else {
			table.get(hashed).add(0, t);
			this.elements++;
		}

		if (loadFactor() > .7) {
			int p = findPrime(2 * size);
			HashTable tempTable = new HashTable(p);
			for (i = 0; i < table.size(); i++) {
				for (int j = 0; j < table.get(i).size(); j++) {
					tempTable.add(table.get(i).get(j));
					//System.out.println("added ");
				}
			}

			this.size = p;
			this.table = tempTable.table;
			this.function = tempTable.function;
		}
	}

	public ArrayList<Tuple> search(int k) {
		int i = 0;
		int hashed = function.hash(k);
		ArrayList<Tuple> send = new ArrayList<Tuple>();
		for (i = 0; i < table.get(hashed).size(); i++) {
			send.add(table.get(hashed).get(i));
		}
		return send;
	}

	public int search(Tuple t) {
		int i = 0;
		int hashed = function.hash(t.getKey());
		if (table.size() <= hashed)
			return 0;
		for (i = 0; i < table.get(hashed).size(); i++) {
			Tuple temp = table.get(hashed).get(i);
			if (t.equals(temp)) {
				return 1 + temp.getDuplicates();
			}
		}
		return 0;
	}

	public int searchKey(Tuple t) {
		int i = 0;
		int send = 0;
		int hashed = function.hash(t.getKey());
		if (table.size() <= hashed)
			return 0;
		for (i = 0; i < table.get(hashed).size(); i++) {

			Tuple temp = table.get(hashed).get(i);
			if (t.key == temp.key) {
				send+= 1 + temp.getDuplicates();
			}
		}

		return send;

	}

	public void remove(Tuple t) {
		int hashed = function.hash(t.getKey());
		for (int i = 0; i < table.get(hashed).size(); i++) {
			Tuple temp = table.get(hashed).get(i);
			if (t.equals(temp)) {
				if (table.get(hashed).get(i).getDuplicates() > 0)
					table.get(hashed).get(i).removeDupe();
				else
					table.get(hashed).remove(i);
				break;
			}
		}
		if (table.get(hashed).size() == 0) {
			// LinkedList<Tuple> n = null;
			table.set(hashed, null);
		}
	}

	private int findPrime(int n) {
		boolean found = false;
		int num = n;
		while (!found) {
			if (isPrime(num))
				return num;
			num++;
		}
		return -1;

	}

	private boolean isPrime(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++)
			if (n % i == 0)
				return false;
		return true;
	}

}