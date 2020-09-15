/**
 * 
 * @author Jun Kit David Tang, Deepak George Thomas
 *
 */
public class Interval {

	public int low;
	public int high;
	
	public Interval(int low, int high) {
		this.low = low;
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public int getHigh() {
		return high;
	}
	
	public boolean overlaps(Interval i)
	{
		return this.getLow() <= i.getHigh() && this.getHigh() >= i.getLow();
	}
	
	public int compareTo(Interval o) {
        if (low > o.low) {
            return 1;
        } else if (low < o.low) {
            return -1;
        } else if (high > o.high) {
            return 1;
        } else if (high < o.high) {
            return -1;
        } else {
            return 0;
        }
    }
}
