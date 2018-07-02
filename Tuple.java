/**
* @author Tristan Anderson
*/

public class Tuple
{
	int key;
	String value;
	int duplicates;

	public Tuple(int keyP, String valueP)
	{
		this.key = keyP;
		this.value = valueP;
		this.duplicates = 0;
	}

	public int getKey()
	{
		return key;
	}
	
	public int getDuplicates()
	{
		return duplicates;
	}
	
	public void removeDupe()
	{
		this.duplicates -= 1;
	}
	
	public void addDupe()
	{
		this.duplicates += 1;
	}

	public String getValue()
	{
		return value;
	}

	public boolean equals(Tuple t)
	{
		if(this.key == t.key && this.value.equals(t.value)) {
			return true;
		}
		return false;
	}
}