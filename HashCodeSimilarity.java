/**
 * @author Tristan Anderson
 */

public class HashCodeSimilarity {
	int sLength;
	public HashTable S;
	public HashTable T;
	public HashTable union;
	public int firstHash = 0;
	public int key = 0;
	public int alpha = 31;

	public HashCodeSimilarity(String s1, String s2, int sLength) {
		this.sLength = sLength;
		this.S = new HashTable(sLength);
		this.T = new HashTable(sLength);
		this.union = new HashTable(sLength);
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
		return (float) unionSummation() / (float) (lengthOfS1() * lengthOfS2());
	}

	public void loadTable(int length, String s, HashTable table) {
		String s1Temp = "";
		int hashKey = 0;
		int i = 0;
		int m = length;
		key = 0;

		// make first tuple
		if (s.length() < m) {
			System.out.println("sLength is longer than the string");
			return;
		}
		s1Temp = s.substring(0, m);

		for (i = 0; i < length; i++) {
			hashKey += (int) s1Temp.charAt(i) * pow2(alpha, m - 1 - i);
		}
		firstHash = (int) s1Temp.charAt(0) * pow2(alpha, m - 1);
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
		firstHash = (int) s.charAt(0) * pow2(alpha, m - 1);
		key *= alpha;
		key += (int) s.charAt(m - 1);
		return key;
	}

	public int unionSummation() {

		int sNum = 0;
		int tNum = 0;
		int send = 0;
		int alreadyKey1 = 0;
		int alreadyKey2 = 0;

		for (int i = 0; i < union.table.size(); i++) {
			alreadyKey1 = 0;
			alreadyKey2 = 0;
			sNum = 0;
			tNum = 0;
			for (int j = 0; j < union.table.get(i).size(); j++) { // should be equal to the amount of different codes.
				Tuple t = union.table.get(i).get(j);
				if (alreadyKey1 == 0) {
					alreadyKey1 = t.key;
					sNum = S.searchKey(t);
					tNum = T.searchKey(t);
					send += (sNum * tNum);
				} else if (alreadyKey2 == 0 && t.key != alreadyKey1) {
					alreadyKey2 = t.key;
					sNum = S.searchKey(t);
					tNum = T.searchKey(t);
					send += (sNum * tNum);
				}

			}

		}
		return send;

	}

	public float getLength(HashTable table) {

		int send = 0;
		for (int i = 0; i < table.table.size(); i++) {
			if(table.table.get(i) != null && table.table.get(i).size() >0)
				for(int j = 0; j < table.table.get(i).size(); j++)
					send += (1 + table.table.get(i).get(j).duplicates) * (1 + table.table.get(i).get(j).duplicates);
		}

		return (float) Math.sqrt(send);

	}

	public int pow2(int n, int p) {
		if (p == 0)
			return 1;
		int res = pow2(n, (int) Math.floor((double) p / 2));
		if (p % 2 == 0)
			return res * res;
		else
			return n * res * res;
	}

}