/*
 * Copyright (C) 2013 pablisco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.camera;

import android.util.SparseArray;

import com.android.gallery.R;

/**
 * 
 * This enum is introduced to use view id values with switch statements. This
 * is required since in later versions of ADT the id values are not constants
 * anymore.
 * 
 * @author pablisco
 * 
 */
public enum StaticId {

	ACTIONBAR_DONE(R.id.actionbar_done),
	ACTIONBAR_DISCARD(R.id.actionbar_discard),
	SAVE(R.id.save),
	DISCARD(R.id.discard);

	
	private StaticId(int id) {
		StaticIdHolder.values.put(id, this);
	}

	public static StaticId from(int id) {
		return StaticIdHolder.values.get(id);
	}
	
}

/**
 * 
 * Helper class to keep track of the {@link StaticId} instances
 *
 * @author pablisco
 *
 */
class StaticIdHolder {
	static SparseArray<StaticId> values = new SparseArray<StaticId>();
}
