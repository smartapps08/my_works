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

package com.smartapps.avro.phonetic;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Pattern> patterns;
	private String vowel = "";
	private String consonant = "";
	private String casesensitive = "";

	public Data() {
		this.patterns = new ArrayList<Pattern>();
	}

	public List<Pattern> getPatterns() {
		return patterns;
	}

	public void addPattern(Pattern pattern) {
		this.patterns.add(pattern);
	}

	public String getVowel() {
		return vowel;
	}

	public void setVowel(String vowel) {
		this.vowel = vowel;
	}

	public String getConsonant() {
		return consonant;
	}

	public void setConsonant(String consonant) {
		this.consonant = consonant;
	}

	public String getCasesensitive() {
		return casesensitive;
	}

	public void setCasesensitive(String casesensitive) {
		this.casesensitive = casesensitive;
	}

	public String toString() {
		String dataStr = "[" + this.vowel + "-" + this.consonant + "-"
				+ this.casesensitive;
		String pat = "";
		for (Pattern p : this.patterns) {
			pat += "\n" + p.toString();
		}

		return dataStr + "-" + pat + "]";
	}

}
