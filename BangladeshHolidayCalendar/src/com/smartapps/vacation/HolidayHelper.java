package com.smartapps.vacation;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.format.DateTimeFormat;

public class HolidayHelper {
	public static final HashMap<String, ArrayList<Holiday>> holidayMap;
	public static ArrayList<Holiday> holiday;
	static {
		holidayMap = new HashMap<String, ArrayList<Holiday>>();

		// january
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("ইংরেজি নববর্ষ ", 5, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("01-01-2014"), true));
		holiday.add(new Holiday("আখেরী চাহার সোম্বা", 3, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("01-01-2014"), true));
		holiday.add(new Holiday("ঈদ-ই-মিলাদুন্নবী (সাঃ)", 1, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("14-01-2014"), true));

		holidayMap.put("1", holiday);

		// february
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("শ্রী শ্রী স্বরসতী পূজা", 4, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("04-02-2014"), false));
		holiday.add(new Holiday("ফাতেহা-ই-ইয়াজদাহম", 3, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("12-02-2014"), true));
		holiday.add(new Holiday("মাঘী পূর্ণিমা ", 6, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("14-02-2014"), true));
		holiday.add(new Holiday("শহিদ দিবস ও আন্তর্জাতিক মাতৃভাষা দিবস", 1,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"21-02-2014"), false));
		holiday.add(new Holiday("শ্রী শ্রী শিবরাত্রি ব্রত", 4, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("27-02-2014"), false));
		holidayMap.put("2", holiday);

		// march
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("ভস্ম বুধবার", 5, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("05-03-2014"), false));
		holiday.add(new Holiday("শুভ দোলযাত্রা", 4, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("16-03-2014"), false));
		holiday.add(new Holiday(
				"জাতির জনক বঙ্গবন্ধু শেখ মুজিবুর রহমানের জন্মদিবস", 1,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"17-03-2014"), false));
		holiday.add(new Holiday("স্বাধীনতা ও জাতীয় দিবস", 1, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("26-03-2014"), false));

		holiday.add(new Holiday("শ্রী শ্রী হরিচাঁদ ঠাকুরের আবির্ভাব ", 4,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"28-03-2014"), false));

		holidayMap.put("3", holiday);

		// april
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("চৈত্র সংক্রান্তি ", 6, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("13-04-2014"), false));
		holiday.add(new Holiday("নববর্ষ ", 2, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("14-04-2014"), false));
		holiday.add(new Holiday("পুণ্য বৃহস্পতিবার", 5, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("17-04-2014"), false));
		holiday.add(new Holiday("পুণ্য শুক্রবার", 5, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("18-04-2014"), false));
		holiday.add(new Holiday("পুণ্য শনিবার", 5, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("19-04-2014"), false));
		holiday.add(new Holiday("ইস্টার সানডে", 5, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("20-04-2014"), false));
		holidayMap.put("4", holiday);

		// may
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("মে দিবস", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("01-05-2014"), false));
		holiday.add(new Holiday("বুদ্ধ পূর্ণিমা (বৈশাখী পূর্ণিমা)", 1,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"13-05-2014"), true));
		holiday.add(new Holiday("শব-ই-মিরাজ", 3, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("27-05-2014"), true));
		holidayMap.put("5", holiday);

		// june
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("শব-ই-বরাত", 2, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("14-06-2014"), true));
		holidayMap.put("6", holiday);

		// july
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("আষাঢ়ী পূর্ণিমা", 6, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("11-07-2014"), true));
		holiday.add(new Holiday("জুমাতুল বিদা", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("25-07-2014"), false));
		holiday.add(new Holiday("শব-ই-ক্বদর", 2, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("26-07-2014"), true));
		holiday.add(new Holiday("ঈদ-উল-ফিতরের আগের দিন", 2, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("28-07-2014"), true));
		holiday.add(new Holiday("ঈদুল ফিতর", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("29-07-2014"), true));
		holiday.add(new Holiday("ঈদ-উল-ফিতরের পরের দিন", 2, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("30-07-2014"), true));
		holiday.add(new Holiday("ঈদ-উল-ফিতর (ঈদের পরের দ্বিতীয় দিন)", 3,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"31-07-2014"), true));
		holidayMap.put("7", holiday);

		// august
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("জাতীয় শোক দিবস", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("15-08-2014"), false));
		holiday.add(new Holiday("শুভ জন্মাষ্টমী", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("17-08-2014"), false));
		holidayMap.put("8", holiday);

		// september
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("মধু পূর্ণিমা (ভাদ্র পূর্ণিমা)", 6,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"08-09-2014"), true));
		holiday.add(new Holiday("শুভ মহালয়া", 4, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("23-09-2014"), false));
		holidayMap.put("9", holiday);

		// october
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("শ্রী শ্রী দুর্গাপূজা (নবমী)", 4,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"03-10-2014"), false));
		holiday.add(new Holiday("দুর্গাপূজা (বিজয়া দশমী)", 1, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("04-10-2014"), false));
		holiday.add(new Holiday("ঈদ-উল-আযহা (আগের দিন)", 2, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("05-10-2014"), true));
		holiday.add(new Holiday("ঈদ-উল-আযহা", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("06-10-2014"), true));
		holiday.add(new Holiday("ঈদ-উল-আযহা (পরের দিন)", 2, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("07-10-2014"), true));
		holiday.add(new Holiday("শ্রী শ্রী লক্ষ্মীপূজা", 4, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("07-10-2014"), false));
		holiday.add(new Holiday("ঈদ-উল-আযহা (ঈদের পরের দ্বিতীয় দিন)", 3,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"08-10-2014"), true));
		holiday.add(new Holiday("প্রবারণা পূর্ণিমা (আশ্বিনী পূর্ণিমা)", 6,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"08-10-2014"), true));
		holiday.add(new Holiday("শ্রী শ্রী শ্যামাপূজা", 4, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("23-10-2014"), false));
		holidayMap.put("10", holiday);

		// november
		holiday = new ArrayList<Holiday>();
		
		holiday.add(new Holiday("আশুরা", 2, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("04-11-2014"), false));
		holidayMap.put("11", holiday);

		// december
		holiday = new ArrayList<Holiday>();
		holiday.add(new Holiday("বিজয় দিবস", 1, DateTimeFormat.forPattern(
				"dd-MM-yyyy").parseDateTime("16-12-2014"), false));
		holiday.add(new Holiday("আখেরী চাহার সোম্বা", 3, DateTimeFormat
				.forPattern("dd-MM-yyyy").parseDateTime("17-12-2014"), true));
		holiday.add(new Holiday("যীশু খ্রিস্টের জন্মোৎসব (বড়দিনের আগের দিন)",
				5, DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"24-12-2014"), false));
		holiday.add(new Holiday("যীশু খ্রিস্টের জন্মদিন (বড়দিন)", 1,
				DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"25-12-2014"), false));
		holiday.add(new Holiday("যীশু খ্রিস্টের জন্মোৎসব (বড়দিনের পরের দিন)",
				5, DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(
						"26-12-2014"), false));
		holidayMap.put("12", holiday);

	}
}
