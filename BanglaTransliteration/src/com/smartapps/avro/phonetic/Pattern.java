package com.smartapps.avro.phonetic;

import java.util.List;
import java.util.ArrayList;

public class Pattern implements Comparable<Pattern> {

	private String find;
	private String replace;
	private List<Rule> rules;

	public Pattern() {
		rules = new ArrayList<Rule>();
	}

	public String getFind() {
		return find;
	}

	public void setFind(String find) {

		this.find = find;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	@Override
	public int compareTo(Pattern p) {
		if (this.find.length() < p.getFind().length()) {
			return 1;
		} else if (this.find.length() == p.getFind().length()) {
			return this.find.compareTo(p.getFind());
		} else {
			return -1;
		}
	}

	public String toString() {
		String dataStr = this.find + "\n" + this.replace;
		String ru = "";
		for (Rule r : this.rules) {
			ru += r.toString();
		}

		if (find.equals("a")) {
			return dataStr + "\n" + ru;
		} else {
			return "";
			// return dataStr + "\n" + ru;
		}
	}

}
