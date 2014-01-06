/*
    =============================================================================
 *****************************************************************************
    The contents of this file are subject to the Mozilla Public License
    Version 1.1 (the "License"); you may not use this file except in
    compliance with the License. You may obtain a copy of the License at
    http://www.mozilla.org/MPL/

    Software distributed under the License is distributed on an "AS IS"
    basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
    License for the specific language governing rights and limitations
    under the License.

    The Original Code is JAvroPhonetic

    The Initial Developer of the Original Code is
    Rifat Nabi <to.rifat@gmail.com>

    Copyright (C) OmicronLab (http://www.omicronlab.com). All Rights Reserved.


    Contributor(s): ______________________________________.

 *****************************************************************************
    =============================================================================
 */

package com.smartapps.avro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.smartapps.avro.exception.NoPhoneticLoaderException;
import com.smartapps.avro.phonetic.Data;
import com.smartapps.avro.phonetic.Match;
import com.smartapps.avro.phonetic.Pattern;
import com.smartapps.avro.phonetic.Rule;

public class PhoneticParser {

	private static volatile PhoneticParser instance = null;
	private static PhoneticLoader loader = null;
	private static List<Pattern> patterns;
	private static String vowel = "";
	private static String consonant = "";
	private static String casesensitive = "";
	private boolean initialized = false;
	private static int maxPatternLength = 0;

	// Prevent initialization
	private PhoneticParser() {
		patterns = new ArrayList<Pattern>();
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public static PhoneticParser getInstance() {
		if (instance == null) {
			synchronized (PhoneticParser.class) {
				if (instance == null) {
					instance = new PhoneticParser();
				}
			}
		}
		return instance;
	}

	public synchronized void setLoader(PhoneticLoader loader) {
		PhoneticParser.loader = loader;
	}

	public synchronized void init() throws Exception {
		if (loader == null) {
			throw new NoPhoneticLoaderException();
		}
		Data data = loader.getData();
		patterns = data.getPatterns();
		Collections.sort(patterns);

		// vowel = data.getVowel();
		// consonant = data.getConsonant();
		// casesensitive = data.getCasesensitive();
		vowel="aeiou";
		consonant="bcdfghjklmnpqrstvwxyz";
		casesensitive="oiudgjnrstyz";
		maxPatternLength = patterns.get(0).getFind().length();
		for (Pattern p : patterns) {
			Log.e("PAT", p.toString());
		}
		initialized = true;
	}

	public String parse(String input) {

		if (initialized == false) {
			try {
				this.init();
			} catch (Exception e) {
				e.printStackTrace();
				System.err
						.println("Please handle the exception by calling init mehotd");
				// System.exit(0);
			}
		}

		String fixed = "";
		for (char c : input.toCharArray()) {
			if (this.isCaseSensitive(c)) {
				fixed += c;
			} else {
				fixed += Character.toLowerCase(c);
			}
		}

		String output = "";
		for (int cur = 0; cur < fixed.length(); ++cur) {
			int start = cur, end;

			boolean matched = false;
			int len;
			for (len = maxPatternLength; len > 0; --len) {
				end = start + len;
				if (end <= fixed.length()) {
					String chunk = fixed.substring(start, end);

					// Binary Search
					int left = 0, right = patterns.size() - 1, mid;
					while (right >= left) {
						mid = (right + left) / 2;
						Pattern pattern = patterns.get(mid);
						if (pattern.getFind().equals(chunk)) {
							for (Rule rule : pattern.getRules()) {
								boolean replace = true;

								int chk = 0;

								for (Match match : rule.getMatches()) {
									if (match.getType().equals("suffix")) {
										chk = end;
									}
									// Prefix
									else {
										chk = start - 1;
									}

									// Beginning
									if (match.getScope().equals("punctuation")) {
										if (!((chk < 0 && match.getType()
												.equals("prefix"))
												|| (chk >= fixed.length() && match
														.getType().equals(
																"suffix")) || this
													.isPunctuation(fixed
															.charAt(chk)))
												^ match.isNegative()) {
											replace = false;
											break;
										}
									}
									// Vowel
									else if (match.getScope().equals("vowel")) {
										Log.e("CALL FOR VOWEL",
												"-----------------Call");
										if (!(((chk >= 0 && match.getType()
												.equals("prefix")) || (chk < fixed
												.length() && match.getType()
												.equals("suffix"))) && this
												.isVowel(fixed.charAt(chk)))
												^ match.isNegative()) {
											replace = false;
											break;
										}
									}
									// Consonant
									else if (match.getScope().equals(
											"consonant")) {
										if (!(((chk >= 0 && match.getType()
												.equals("prefix")) || (chk < fixed
												.length() && match.getType()
												.equals("suffix"))) && this
												.isConsonant(fixed.charAt(chk)))
												^ match.isNegative()) {
											replace = false;
											break;
										}
									}
									// Exact
									else if (match.getScope().equals("exact")) {
										int s, e;
										if (match.getType().equals("suffix")) {
											s = end;
											e = end + match.getValue().length();
										}
										// Prefix
										else {
											s = start
													- match.getValue().length();
											e = start;
										}
										if (!this
												.isExact(match.getValue(),
														fixed, s, e,
														match.isNegative())) {
											replace = false;
											break;
										}
									}
								}

								if (replace) {
									output += rule.getReplace();
									cur = end - 1;
									matched = true;
									break;
								}

							}

							if (matched == true)
								break;

							// Default
							output += pattern.getReplace();
							cur = end - 1;
							matched = true;
							break;
						} else if (pattern.getFind().length() > chunk.length()
								|| (pattern.getFind().length() == chunk
										.length() && pattern.getFind()
										.compareTo(chunk) < 0)) {
							left = mid + 1;
						} else {
							right = mid - 1;
						}
					}
					if (matched == true)
						break;
				}
			}

			if (!matched) {
				Log.e("PROBLEM", "-----------Problem--------------" + cur);
				output += fixed.charAt(cur);
			}

		}
		return output;
	}

	private boolean isVowel(char c) {
		Log.e("FOR VOWEL", "------------Vawel Check-----------");
		return ((vowel.indexOf(Character.toLowerCase(c)) >= 0));
	}

	private boolean isConsonant(char c) {
		return ((consonant.indexOf(Character.toLowerCase(c)) >= 0));
	}

	private boolean isPunctuation(char c) {
		return (!(this.isVowel(c) || this.isConsonant(c)));
	}

	private boolean isExact(String needle, String heystack, int start, int end,
			boolean not) {
		return ((start >= 0 && end < heystack.length() && heystack.substring(
				start, end).equals(needle)) ^ not);
	}

	private boolean isCaseSensitive(char c) {
		return (casesensitive.indexOf(Character.toLowerCase(c)) >= 0);
	}

}