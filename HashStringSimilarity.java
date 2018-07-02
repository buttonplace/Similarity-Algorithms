/**
 * @author Tristan Anderson
 */

public class HashStringSimilarity {
	int sLength;
	public HashTable S;
	public HashTable T;
	public HashTable union;
	public int firstHash = 0;
	public int key = 0;
	public int alpha = 31;

	public HashStringSimilarity(String s1, String s2, int sLength) {
		this.S = new HashTable(3);
		this.T = new HashTable(3);
		this.union = new HashTable(3);
		this.sLength = sLength;
		loadTable(sLength, s1, S);
		loadTable(sLength, s2, T);
		loadTable(sLength, s1, union);
		loadTable(sLength, s2, union);

	}

	public float lengthOfS1() {

		 return getLength(S);

	}

	public float lengthOfS2() {

		return getLength(T);
		
	}

	public float similarity() { 

	return unionSummation()/(float)(lengthOfS1() * lengthOfS2());	
		
	}

	public void loadTable(int length, String s, HashTable table) {
		String s1Temp = "";
		int hashKey = 0;
		int i = 0;
		int m = length;
		key = 0;

		// make first tuple
		if (s.length() < m) {
			return;
		}
		s1Temp = s.substring(0, m);
		for (i = 0; i < length; i++) {
			hashKey += (int) s1Temp.charAt(i) * pow2(alpha, m-1-i);
		}
		firstHash = (int) s1Temp.charAt(0) * pow2(alpha, m-1);
		this.key = hashKey;
		Tuple a = new Tuple(hashKey, s1Temp);
		table.add(a);
		i = length + 1;
		while (s.length() >= i) {
			s1Temp = s.substring(i - length, i);
			key = makeNextKey(s1Temp, length);
			Tuple b = new Tuple(key, s1Temp);
			table.add(b);
			i++;
		}
	}

	public int makeNextKey(String s, int m) {
		key -= firstHash;
		firstHash = (int) s.charAt(0) * pow2(alpha, m-1);
		key *= alpha;
		key += (int) s.charAt(m - 1);
		return key;
	}

	public float getLength(HashTable table) {
		int dupes = 0;
		int send = 0;
		for (int i = 0; i < table.table.size(); i++) {
			
			for (int j = 0; j < table.table.get(i).size(); j++) {
				dupes = 0;
				dupes += table.table.get(i).get(j).getDuplicates() + 1;
				send += dupes * dupes;
			}
			
		}

		return (float)Math.sqrt(send);
	}
	
	public int unionSummation() {
		
			int sNum = 0;
			int tNum = 0;
			int send = 0;
	
		for (int i = 0; i < union.table.size(); i++) {
			for (int j = 0; j < union.table.get(i).size(); j++) {
				Tuple t = union.table.get(i).get(j);
				sNum = S.search(t);
				tNum = T.search(t);
				send+=sNum*tNum;
			}
			
		}
		
		return send;
		
	}
	
	public int pow2(int n, int p) {
		if(p==0) return 1;
		int res = pow2(n, (int)Math.floor((double)p/2));
		if(p % 2 == 0) return res*res;
		else return n*res*res;
	}
}