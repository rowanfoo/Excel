import java.util.Date;

public class Price {
    private  Date date;
    private  double open;
    private  double high;
    private  double low;
    private  double close;
    private  double change;
    
    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }


    public double getChange() {
		return change;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public Price(Date date, double open, double high, double low, double close, double change) {
        super();
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.change=  change;
    }
}