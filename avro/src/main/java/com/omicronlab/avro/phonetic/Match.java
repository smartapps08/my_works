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

public class Match {
    private String type;
    private String scope;
    private String value;
    private boolean negative;

    public Match() {
        negative = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if(scope.charAt(0) == '!') {
            this.negative = true;
            scope = scope.substring(1);
        }
        this.scope = scope;
    }

    public boolean isNegative() {
        return negative;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
