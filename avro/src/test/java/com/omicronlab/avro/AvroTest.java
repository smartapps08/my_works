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

    Contributor(s): Mehdi Hasan <mhasan@omicronlab.com>.

 *****************************************************************************
    =============================================================================
 */

package test.java.com.omicronlab.avro;

import main.java.com.omicronlab.avro.PhoneticParser;
import main.java.com.omicronlab.avro.PhoneticXmlLoader;

/*
 Note:   If all tests fail, make sure the source file encoding is utf8.
 In Eclipse IDE: Edit menu>Set Encoding>utf8

 */

public class AvroTest {
	private PhoneticParser avro;

	public static void main(String[] args) {
		AvroTest test = new AvroTest();
		test.avro = PhoneticParser.getInstance();
		test.avro.setLoader(new PhoneticXmlLoader());

		System.out.println(test.avro.parse("ami banglai gan gai"));
		// avro.setLoader(new PhoneticXmlLoader(this));
	}

}