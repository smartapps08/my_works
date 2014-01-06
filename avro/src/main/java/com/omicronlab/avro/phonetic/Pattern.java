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

package main.java.com.omicronlab.avro.phonetic;

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
        if(this.find.length() < p.getFind().length()) {
            return 1;
        }
        else if(this.find.length() == p.getFind().length()) {
            return this.find.compareTo(p.getFind());
        } else {
            return -1;
        }
    }

}
