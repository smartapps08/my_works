package com.banglacalendar.test;

import java.sql.Date;

public class BanglaDateConverter {
	private long timestamp; // timestamp as input
	private int morning; // when the date will change?

	private int engHour; // Current hour of English Date
	private int engDay; // Current date of English Date
	private int engMonth; // Current month of English Date
	private int engYear; // Current year of English Date

	private BanglaDate banglaDate;

	public BanglaDateConverter(long timestamp) {
		this.timestamp = timestamp;
		this.morning = 6;
		Date currentDate = new Date(timestamp);
		this.engHour = currentDate.getHours();
		this.engDay = currentDate.getDay();
		this.engMonth = currentDate.getMonth();
		this.engYear = currentDate.getYear();
		System.out.println("Day: " + engDay + " Month: " + engMonth + " Year: "
				+ engYear);
	}

	public BanglaDate calculateBanglaDate() {
		int bangDay = -1;
		int bangMonth = -1;
		int bangYear = -1;
		switch (this.engMonth) {
		case 1:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 9;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 9;
				}
			} else if (this.engDay < 14 && this.engDay > 1) {
				if (this.engHour > this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 9;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 9;
				}
			} else if (this.engDay == 14) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 13;
					bangMonth = 10;
				} else {
					bangDay = 30;
					bangMonth = 9;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 13;
					bangMonth = 10;
				} else {
					bangDay = this.engDay - 14;
					bangMonth = 10;
				}
			}
			break;
		case 2:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 18;
					bangMonth = 10;
				} else {
					bangDay = this.engDay + 17;
					bangMonth = 10;
				}
			} else if (this.engDay < 13 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 18;
					bangMonth = 10;
				} else {
					bangDay = this.engDay + 17;
					bangMonth = 10;
				}
			}

			else if (this.engDay == 13) // Date 13
			{
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 12;
					bangMonth = 11;
				} else {
					bangDay = 30;
					bangMonth = 10;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 12;
					bangMonth = 11;
				} else {
					bangDay = this.engDay - 13;
					bangMonth = 11;
				}
			}
			break;
		case 3:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					// if(this.is_leapyear())
					// bangDay = this.engDay + 17;
					// else
					// bangDay = this.engDay + 16;
					bangMonth = 11;
				} else {
					// if(this.is_leapyear())
					// bangDay = this.engDay + 16;
					// else
					// bangDay = this.engDay + 15;
					bangMonth = 11;
				}
			} else if (this.engDay < 15 && this.engDay > 1) // Date 2-13
			{
				if (this.engHour >= this.morning) {
					// if(this.is_leapyear())
					// bangDay = this.engDay + 17;
					// else
					// bangDay = this.engDay + 16;
					bangMonth = 11;
				} else {
					// if(this.is_leapyear())
					// bangDay = this.engDay + 16;
					// else
					// bangDay = this.engDay + 15;
					bangMonth = 11;
				}
			}

			else if (this.engDay == 15) // Date 14
			{
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 12;
				} else {
					bangDay = 30;
					bangMonth = 11;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 12;
				} else {
					bangDay = this.engDay - 15;
					bangMonth = 12;
				}
			}

			break;
		case 4:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 12;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 12;
				}
			} else if (this.engDay < 14 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 12;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 12;
				}
			}

			else if (this.engDay == 14) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 13;
					bangMonth = 1;
				} else {
					bangDay = 30;
					bangMonth = 12;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 13;
					bangMonth = 1;
				} else {
					bangDay = this.engDay - 14;
					bangMonth = 1;
				}
			}
			break;
		case 5:
			if (this.engDay == 1) // Date 1
			{
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 1;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 1;
				}
			} else if (this.engDay < 15 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 1;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 1;
				}
			}

			else if (this.engDay == 15) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 2;
				} else {
					bangDay = 31;
					bangMonth = 1;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 2;
				} else {
					bangDay = this.engDay - 15;
					bangMonth = 2;
				}
			}
			break;
		case 6:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 2;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 2;
				}
			} else if (this.engDay < 15 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 17;
					bangMonth = 2;
				} else {
					bangDay = this.engDay + 16;
					bangMonth = 2;
				}
			}

			else if (this.engDay == 15) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 3;
				} else {
					bangDay = 31;
					bangMonth = 2;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 3;
				} else {
					bangDay = this.engDay - 13;
					bangMonth = 3;
				}
			}
			break;
		case 7:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 3;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 3;
				}
			} else if (this.engDay < 16 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 3;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 3;
				}
			}

			else if (this.engDay == 16) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 4;
				} else {
					bangDay = 31;
					bangMonth = 3;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 4;
				} else {
					bangDay = this.engDay - 16;
					bangMonth = 4;
				}
			}
			break;
		case 8:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 4;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 4;
				}
			} else if (this.engDay < 16 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 4;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 4;
				}
			}

			else if (this.engDay == 16) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 5;
				} else {
					bangDay = 31;
					bangMonth = 4;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 5;
				} else {
					bangDay = this.engDay - 16;
					bangMonth = 5;
				}
			}
			break;
		case 9:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 5;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 5;
				}
			} else if (this.engDay < 16 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 5;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 5;
				}
			}

			else if (this.engDay == 16) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 6;
				} else {
					bangDay = 31;
					bangMonth = 5;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 6;
				} else {
					bangDay = this.engDay - 16;
					bangMonth = 6;
				}
			}
			break;
		case 10:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 15;
					bangMonth = 6;
				} else {
					bangDay = this.engDay + 14;
					bangMonth = 6;
				}
			} else if (this.engDay < 16 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 15;
					bangMonth = 6;
				} else {
					bangDay = this.engDay + 14;
					bangMonth = 6;
				}
			}

			else if (this.engDay == 16) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 7;
				} else {
					bangDay = 30;
					bangMonth = 6;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 15;
					bangMonth = 7;
				} else {
					bangDay = this.engDay - 16;
					bangMonth = 7;
				}
			}
			break;
		case 11:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 7;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 7;
				}
			} else if (this.engDay < 15 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 7;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 7;
				}
			}

			else if (this.engDay == 15) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 8;
				} else {
					bangDay = 30;
					bangMonth = 7;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 8;
				} else {
					bangDay = this.engDay - 15;
					bangMonth = 8;
				}
			}
			break;
		case 12:
			if (this.engDay == 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 8;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 8;
				}
			} else if (this.engDay < 15 && this.engDay > 1) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay + 16;
					bangMonth = 8;
				} else {
					bangDay = this.engDay + 15;
					bangMonth = 8;
				}
			}

			else if (this.engDay == 15) {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 9;
				} else {
					bangDay = 30;
					bangMonth = 8;
				}
			} else {
				if (this.engHour >= this.morning) {
					bangDay = this.engDay - 14;
					bangMonth = 9;
				} else {
					bangDay = this.engDay - 15;
					bangMonth = 9;
				}
			}
			break;
		default:
			break;
		}
		bangYear = calculateYear();
		if (bangDay > -1 && bangMonth > -1) {
			return new BanglaDate(bangDay, bangMonth, bangYear);
		}
		return null;
	}

	public boolean isLeapyear() {
		if (this.engYear % 400 == 0
				|| (this.engYear % 100 != 0 && this.engYear % 4 == 0))
			return true;
		else
			return false;
	}

	public int calculateYear() {
		int bangYear = -1;
		if (this.engMonth >= 4) {
			if (this.engMonth == 4 && this.engDay < 14) {
				bangYear = this.engYear - 594;
			} else if (this.engMonth == 4 && this.engDay == 14
					&& this.engHour <= 5) {
				bangYear = this.engYear - 594;
			} else if (this.engMonth == 4 && this.engDay == 14
					&& this.engHour >= 6) {
				bangYear = this.engYear - 593;
			} else
				bangYear = this.engYear - 593;
		} else {
			bangYear = this.engYear - 594;
		}
		return bangYear;
	}
}
