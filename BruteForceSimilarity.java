import java.util.*;

/**
* @author Tristan Anderson
*/

public class BruteForceSimilarity
{
	int sLength;
	public ArrayList<String> S;
	public ArrayList<String> T;

	public BruteForceSimilarity(String s1, String s2, int sLength)
	{
		this.S = makeList(sLength, s1);
		this.T = makeList(sLength, s2);
		this.sLength = sLength;
	}
	
	public float lengthOfS1()
	{
		return getLength(S);
	}
	
	public float lengthOfS2()
	{
		return getLength(T);
	}
	

	public float similarity()
	{
		ArrayList<String> union = makeUnion();
		String element = "";
		float send = 0;
		int tNum = 0;
		int sNum = 0;
		
		for(int i=0; i<union.size(); i++) {
			element = union.get(i);
			sNum = 0;
			tNum = 0;
			for(int j = 0; j<T.size(); j++) {
				if(element.equals(T.get(j))) {
					tNum++;
				}
			}
			for(int j = 0; j<S.size(); j++) {
				if(element.equals(S.get(j))) {
					sNum++;
				}
			}
			send += sNum*tNum;
		}
		
		float bottom = getLength(S) * getLength(T);
		
		return send/bottom;
	}

	public ArrayList<String> makeList(int length, String s)
	{
		ArrayList<String> send = new ArrayList<String>();
		int stringCounter = 0;
		String s1Temp = "";
		if(s.length() >= length) {
			s1Temp = s.substring(0, length);
			send.add(s1Temp);
			stringCounter = length;
		}
		while(s.length() > stringCounter) {
			s1Temp = s1Temp.substring(1);
			s1Temp += s.charAt(stringCounter);
			send.add(s1Temp);
			stringCounter++;
		}
		return send;
	}
	
	public float getLength(ArrayList<String> in)
	{
		ArrayList<String> list = new ArrayList<>(in);
		String element = "";
		int count = 0;
		int send = 0;
		
		while(list.size() > 0) {
			element = list.get(0);
			count = 1;
			for(int i = 1; i<list.size(); i++) {
				if(element.equals(list.get(i))) {
					count++;
					list.remove(i);
					i--;
				}
			}
			list.remove(0);
			send += count*count;
		}
		return (float)Math.sqrt(send);
	}
	
	public ArrayList<String> makeUnion(){
	
				ArrayList<String> union = new ArrayList<String>();
				String element = "";
				int flag = 0;
				for(int i=0; i<S.size(); i++) {
					element = S.get(i);
					for(int j = 0; j<union.size(); j++) {
						if(element.equals(union.get(j))) {
							flag = 1;
							break;
						}
					}
					if(flag == 0) {
						union.add(element);
					}
				}
				for(int i=0; i<T.size(); i++) {
					element = T.get(i);
					flag = 0;
					for(int j = 0; j<union.size(); j++) {
						if(element.equals(union.get(j))) {
							flag = 1;
							break;
						}
					}
					if(flag == 0) {
						union.add(element);
					}
				}
			return union;
	}
	
}