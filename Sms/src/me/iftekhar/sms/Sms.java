package me.iftekhar.sms;

public class Sms {
	private long number;
	private String text;
	private String date;
	private int read_flag;
	private int serial_no;
	private String ds;
	Sms()
	{
		
	}
	
	Sms(long number,String text)
	{
		
		super();
		this.number = number;
		this.text = text;	
	}
	@Override
	public String toString() {
		return "Sms [number=" + number + ", text=" + text + ", date=" + date
				+ ", read_flag=" + read_flag + "]";
	}
	///inbox///
	public Sms(long number, String text,String date, int read_flag,int serial_no) {
		super();
		this.number = number;
		this.text = text;
		this.date=date;
		this.read_flag=read_flag;
		this.serial_no=serial_no;
		
	}
	
	
	public long getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	
	//draft//
	public Sms(String text,String date) {
		super();
		this.text = text;
		this.date=date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public int getRead_flag() {
		return read_flag;
	}
	public int getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(int serial_no) {
		this.serial_no = serial_no;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}
//set items
	public Sms(long number, String text, String date, String ds) {
		super();
		this.number = number;
		this.text = text;
		this.date = date;
		this.ds = ds;
	}

	public void setRead_flag(Integer read_flag) {
		this.read_flag = read_flag;
	}
	

}
