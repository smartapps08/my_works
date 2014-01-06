package com.smartapps.helpers;

public class BijoyFontUtil {

	public String convertUnicode2BijoyString(String text) {
		text = preConversion(text);
		text = reArrangeUnicodeText(text);
		text = postConversion(text);
		return text;
	}

	private String postConversion(String text) {
		text = text.replace("।", "|");
		text = text.replace("‘", "Ô");
		text = text.replace("’", "Õ");
		text = text.replace("“", "Ò");
		text = text.replace("”", "Ó");

		// text = text.replace("স্ত", "¯Í");
		text = text.replace("স্ত", "¯—");
		text = text.replace("্র্য", "ª¨");
		text = text.replace("র‌্য", "i¨");
		text = text.replace("ক্ক", "°");
		text = text.replace("ক্ট", "±");
		text = text.replace("ক্ত", "³");
		text = text.replace("ক্ব", "K¡");
		text = text.replace("স্ক্র", "¯Œ");
		text = text.replace("ক্র", "µ");
		text = text.replace("ক্ল", "K¬");
		text = text.replace("ক্ষ", "¶");
		// text = text.replace("ক্স", "•");
		text = text.replace("গু", "¸");
		text = text.replace("গ্ধ", "»");
		text = text.replace("গ্ন", "Mœ");
		text = text.replace("গ্ম", "M¥");
		text = text.replace("গ্ল", "Mø");
		text = text.replace("গ্রু", "MÖæ");
		text = text.replace("ঙ্ক", "¼");
		text = text.replace("ঙ্ক্ষ", "•¶");
		text = text.replace("ঙ্খ", "•L");
		text = text.replace("ঙ্গ", "½");
		text = text.replace("ঙ্ঘ", "•N");
		text = text.replace("চ্চ", "”P");
		text = text.replace("চ্ছ", "”Q");
		text = text.replace("চ্ছ্ব", "”Q¡");
		text = text.replace("চ্ঞ", "”T");
		text = text.replace("জ্জ্ব", "¾¡");
		text = text.replace("জ্জ", "¾");
		text = text.replace("জ্ঝ", "À");
		text = text.replace("জ্ঞ", "Á");
		text = text.replace("জ্ব", "R¡");
		text = text.replace("ঞ্চ", "Â");
		text = text.replace("ঞ্ছ", "Ã");
		text = text.replace("ঞ্জ", "Ä");
		text = text.replace("ঞ্ঝ", "Å");
		text = text.replace("ট্ট", "Æ");
		text = text.replace("ট্ব", "U¡");
		text = text.replace("ট্ম", "U¥");
		text = text.replace("ড্ড", "Ç");
		text = text.replace("ণ্ট", "È");
		text = text.replace("ণ্ঠ", "É");
		text = text.replace("ন্স", "Ý");
		text = text.replace("ণ্ড", "Ð");
		text = text.replace("ন্তু", "š‘");
		text = text.replace("ণ্ব", "Y^");
		text = text.replace("ত্ত", "Ë");
		text = text.replace("ত্ত্ব", "Ë¡");
		text = text.replace("ত্থ", "Ì");
		text = text.replace("ত্ন", "Zœ");
		text = text.replace("ত্ম", "Z¥");
		text = text.replace("ন্ত্ব", "šÍ¡");
		text = text.replace("ত্ব", "Z¡");
		text = text.replace("থ্ব", "_¡");
		text = text.replace("দ্গ", "˜M");
		text = text.replace("দ্ঘ", "˜N");
		text = text.replace("দ্দ", "Ï");
		text = text.replace("দ্ধ", "×");
		text = text.replace("দ্ব", "Ø");
		text = text.replace("দ্ব", "Ø");
		text = text.replace("দ্ভ", "™¢");
		text = text.replace("দ্ম", "Ù");
		text = text.replace("দ্রু", "`ªæ");
		text = text.replace("ধ্ব", "aŸ");
		text = text.replace("ধ্ম", "a¥");
		text = text.replace("ন্ট", "›U");
		text = text.replace("ন্ঠ", "Ú");
		text = text.replace("ন্ড", "Û");
		text = text.replace("ন্ত্র", "š¿");
		text = text.replace("ন্ত", "šÍ");
		text = text.replace("স্ত্র", "¯¿");
		text = text.replace("ত্র", "Î");
		text = text.replace("ন্থ", "š’");
		text = text.replace("ন্দ", "›`");
		text = text.replace("ন্দ্ব", "›Ø");
		text = text.replace("ন্ধ", "Ü");
		text = text.replace("ন্ন", "bœ");
		text = text.replace("ন্ব", "š^");
		text = text.replace("ন্ম", "b¥");
		text = text.replace("প্ট", "Þ");
		text = text.replace("প্ত", "ß");
		text = text.replace("প্ন", "cœ");
		text = text.replace("প্প", "à");
		text = text.replace("প্ল", "cø");
		text = text.replace("প্স", "á");
		text = text.replace("ফ্ল", "d¬");
		text = text.replace("ব্জ", "â");
		text = text.replace("ব্দ", "ã");
		text = text.replace("ব্ধ", "ä");
		text = text.replace("ব্ব", "eŸ");
		text = text.replace("ব্ল", "eø");
		text = text.replace("ভ্র", "å");
		text = text.replace("ম্ন", "gœ");
		text = text.replace("ম্প", "¤ú");
		text = text.replace("ম্ফ", "ç");
		text = text.replace("ম্ব", "¤\\^");
		text = text.replace("ম্ভ", "¤¢");
		text = text.replace("ম্ভ্র", "¤£");
		text = text.replace("ম্ম", "¤§");
		text = text.replace("ম্ল", "¤ø");
		text = text.replace("রু", "iæ");
		text = text.replace("রূ", "iƒ");
		text = text.replace("ল্ক", "é");
		text = text.replace("ল্গ", "ê");
		text = text.replace("ল্ট", "ë");
		text = text.replace("ল্ড", "ì");
		text = text.replace("ল্প", "í");
		text = text.replace("ল্ফ", "î");
		text = text.replace("ল্ব", "j¦");
		text = text.replace("ল্ম", "j¥");
		text = text.replace("ল্ল", "jø");
		text = text.replace("শু", "ï");
		text = text.replace("শ্চ", "ð");
		text = text.replace("শ্ন", "kœ");
		text = text.replace("শ্ব", "k¦");
		text = text.replace("শ্ম", "k¥");
		text = text.replace("শ্ল", "kø");
		text = text.replace("ষ্ক", "®‹");
		text = text.replace("ষ্ক্র", "®Œ");
		text = text.replace("ষ্ট", "ó");
		text = text.replace("ষ্ঠ", "ô");
		text = text.replace("ষ্ণ", "ò");
		text = text.replace("ষ্প", "®ú");
		text = text.replace("ষ্ফ", "õ");
		text = text.replace("ষ্ম", "®§");
		text = text.replace("স্ক", "¯‹");
		text = text.replace("স্ট", "÷");
		text = text.replace("স্খ", "ö");

		text = text.replace("স্তু", "¯‘");
		text = text.replace("স্থ", "¯’");
		text = text.replace("স্ন", "mœ");
		text = text.replace("স্প", "¯ú");
		text = text.replace("স্ফ", "ù");
		text = text.replace("স্ব", "¯^");
		text = text.replace("স্ম", "¯§");
		text = text.replace("স্ল", "mø");
		text = text.replace("হু", "û");
		text = text.replace("হ্ণ", "nè");
		text = text.replace("হ্ন", "ý");
		text = text.replace("হ্ম", "þ");
		text = text.replace("হ্ল", "n¬");
		text = text.replace("হৃ", "ü");
		text = text.replace("র্", "©");
		text = text.replace("্র", "«");
		text = text.replace("্য", "¨");
		text = text.replace("্", "&");
		text = text.replace("আ", "Av");
		text = text.replace("অ", "A");
		text = text.replace("ই", "B");
		text = text.replace("ঈ", "C");
		text = text.replace("উ", "D");
		text = text.replace("ঊ", "E");
		text = text.replace("ঋ", "F");
		text = text.replace("এ", "G");
		text = text.replace("ঐ", "H");
		text = text.replace("ও", "I");
		text = text.replace("ঔ", "J");
		text = text.replace("ক", "K");
		text = text.replace("খ", "L");
		text = text.replace("গ", "M");
		text = text.replace("ঘ", "N");
		text = text.replace("ঙ", "O");
		text = text.replace("চ", "P");
		text = text.replace("ছ", "Q");
		text = text.replace("জ", "R");
		text = text.replace("ঝ", "S");
		text = text.replace("ঞ", "T");
		text = text.replace("ট", "U");
		text = text.replace("ঠ", "V");
		text = text.replace("ড", "W");
		text = text.replace("ঢ", "X");
		text = text.replace("ণ", "Y");
		text = text.replace("ত", "Z");
		text = text.replace("থ", "_");
		text = text.replace("দ", "`");
		text = text.replace("ধ", "a");
		text = text.replace("ন", "b");
		text = text.replace("প", "c");
		text = text.replace("ফ", "d");
		text = text.replace("ব", "e");
		text = text.replace("ভ", "f");
		text = text.replace("ম", "g");
		text = text.replace("য", "h");
		text = text.replace("র", "i");
		text = text.replace("ল", "j");
		text = text.replace("শ", "k");
		text = text.replace("ষ", "l");
		text = text.replace("স", "m");
		text = text.replace("হ", "n");
		text = text.replace("ড়", "o");
		text = text.replace("ঢ়", "p");
		text = text.replace("য়", "q");
		text = text.replace("ৎ", "r");
		text = text.replace("০", "0");
		text = text.replace("১", "1");
		text = text.replace("২", "2");
		text = text.replace("৩", "3");
		text = text.replace("৪", "4");
		text = text.replace("৫", "5");
		text = text.replace("৬", "6");
		text = text.replace("৭", "7");
		text = text.replace("৮", "8");
		text = text.replace("৯", "9");
		text = text.replace("া", "v");
		text = text.replace("ি", "w");
		text = text.replace("ী", "x");
		text = text.replace("ু", "y");
		text = text.replace("ূ", "~");
		text = text.replace("ৃ", "…");
		text = text.replace("ে", "‡");
		text = text.replace("ৈ", "‰");
		text = text.replace("ৗ", "Š");
		text = text.replace("ং", "s");
		text = text.replace("ঃ", "t");
		text = text.replace("ঁ", "u");
		text = text.replace("ক্স", "•");

		text = text.replace("‘", "Ô");
		text = text.replace("’", "Õ");
		text = text.replace("“", "Ò");
		text = text.replace("”", "Ó");
		return text;
	} // end function postConversion

	private String preConversion(String text) {
		text = text.replaceAll("ো", "ো");
		text = text.replaceAll("ৌ", "ৌ");
		return text;
	} // end function preConversion

	private String reArrangeUnicodeText(String str) {
		int barrier = 0;
		for (int i = 0; i < str.length(); i++) {
			// Change pre-kar to pre format suitable for ascii
			if (i < str.length() && isBanglaPreKar(str.charAt(i))) {
				int j = 1;
				while (isBanglaBanjonborno(str.charAt(i - j))) {
					if (i - j < 0)
						break;
					if (i - j <= barrier)
						break;
					if (isBanglaHalant(str.charAt(i - j - 1)))
						j += 2;
					else
						break;
				}
				String temp = str.substring(0, i - j);
				temp += str.charAt(i);
				temp += str.substring(i - j, i);
				temp += str.substring(i + 1, str.length());
				str = temp;
				barrier = i + 1;
				continue;
			}
			if (i < str.length() - 1 && isBanglaHalant(str.charAt(i))
					&& str.charAt(i - 1) == 'র'
					&& !isBanglaHalant(str.charAt(i - 2))) {
				int j = 1;
				int found_pre_kar = 0;
				while (true) {
					if (isBanglaBanjonborno(str.charAt(i + j))
							&& isBanglaHalant(str.charAt(i + j + 1)))
						j += 2;
					else if (isBanglaBanjonborno(str.charAt(i + j))
							&& isBanglaPreKar(str.charAt(i + j + 1))) {
						found_pre_kar = 1;
						break;
					} else
						break;
				}
				String temp = str.substring(0, i - 1);
				temp += str.substring(i + j + 1, i + j + found_pre_kar + 1);
				temp += str.substring(i + 1, i + j + 1);
				temp += str.charAt(i - 1);
				temp += str.charAt(i);
				temp += str.substring(i + j + found_pre_kar + 1, str.length());
				str = temp;
				i += (j + found_pre_kar);
				barrier = i + 1;
				continue;
			}

		}

		return str;
	} // end function ReArrangeUnicodeText

	private boolean isBanglaDigit(char CUni) {
		if (CUni == '০' || CUni == '১' || CUni == '২' || CUni == '৩'
				|| CUni == '৪' || CUni == '৫' || CUni == '৬' || CUni == '৭'
				|| CUni == '৮' || CUni == '৯')
			return true;

		return false;
	} // end function IsBanglaDigit

	private boolean isBanglaPreKar(char CUni) {
		if (CUni == 'ি' || CUni == 'ৈ' || CUni == 'ে')
			return true;

		return false;
	} // end function IsBanglaPreKar

	private boolean isBanglaPostKar(char CUni) {
		if (CUni == 'া' || CUni == 'ো' || CUni == 'ৌ' || CUni == 'ৗ'
				|| CUni == 'ু' || CUni == 'ূ' || CUni == 'ী' || CUni == 'ৃ')
			return true;
		return false;
	} // end function IsBanglaPostKar

	private boolean isBanglaKar(char CUni) {
		if (isBanglaPreKar(CUni) || isBanglaPostKar(CUni))
			return true;
		return false;

	} // end function IsBanglaKar

	private boolean isBanglaBanjonborno(char CUni) {
		if (CUni == 'ক' || CUni == 'খ' || CUni == 'গ' || CUni == 'ঘ'
				|| CUni == 'ঙ' || CUni == 'চ' || CUni == 'ছ' || CUni == 'জ'
				|| CUni == 'ঝ' || CUni == 'ঞ' || CUni == 'ট' || CUni == 'ঠ'
				|| CUni == 'ড' || CUni == 'ঢ' || CUni == 'ণ' || CUni == 'ত'
				|| CUni == 'থ' || CUni == 'দ' || CUni == 'ধ' || CUni == 'ন'
				|| CUni == 'প' || CUni == 'ফ' || CUni == 'ব' || CUni == 'ভ'
				|| CUni == 'ম' || CUni == 'শ' || CUni == 'ষ' || CUni == 'স'
				|| CUni == 'হ' || CUni == 'য' || CUni == 'র' || CUni == 'ল'
				|| CUni == 'য়' || CUni == 'ং' || CUni == 'ঃ' || CUni == 'ঁ'
				|| CUni == 'ৎ')
			return true;

		return false;
	} // end function IsBanglaBanjonborno

	private boolean isBanglaSoroborno(char CUni) {
		if (CUni == 'অ' || CUni == 'আ' || CUni == 'ই' || CUni == 'ঈ'
				|| CUni == 'উ' || CUni == 'ঊ' || CUni == 'ঋ' || CUni == 'ঌ'
				|| CUni == 'এ' || CUni == 'ঐ' || CUni == 'ও' || CUni == 'ঔ')
			return true;

		return false;
	} // end function IsBanglaSoroborno

	private boolean isBanglaNukta(char CUni) {
		if (CUni == 'ং' || CUni == 'ঃ' || CUni == 'ঁ')
			return true;

		return false;

	} // end function IsBanglaNukta

	private boolean isBanglaFola(char CUni) {
		if (CUni == 'য' || CUni == 'র')
			return true;

		return false;
	} // end function IsBanglaFola

	private boolean isBanglaHalant(char CUni) {
		if (CUni == '্')
			return true;

		return false;
	} // end function IsBanglaHalant

	private boolean isBangla(char CUni) {
		if (isBanglaDigit(CUni) || isBanglaKar(CUni)
				|| isBanglaBanjonborno(CUni) || isBanglaSoroborno(CUni)
				|| isBanglaNukta(CUni) || isBanglaFola(CUni)
				|| isBanglaHalant(CUni))
			return true;

		return false;
	} // end function IsBangla

	private boolean isASCII(char CH) {
		if (CH >= 0 && CH < 128)
			return true;

		return false;
	} // end function IsBangla

	private boolean isSpace(char C) {
		if (C == ' ' || C == '\t' || C == '\n' || C == '\r')
			return true;

		return false;
	} // end function IsSpace

}