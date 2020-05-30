package clientserver;

public class ShopResponse {
	private int value;
	private int occurence;
	
	public ShopResponse(int value) {
		this.value = value;
		this.occurence = 0;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getOccurence() {
		return occurence;
	}
	
	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
}
