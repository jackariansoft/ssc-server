/***
 * Copyright 2002-2010 jamod development team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***/

package mude.srl.ssc.modbus.util;

/**
 * A cleanroom implementation of the Observer interface for the Observable
 * design pattern.
 * <p/>
 * 
 * @author Dieter Wimberger (wimpi)
 * @version @version@ (@date@)
 */
public interface Observer {

	/**
	 * Updates the state of this <tt>Observer</tt> to be in synch with an
	 * <tt>Observable</tt> instance. The argument should usually be an
	 * indication of the aspects that changed in the <tt>Observable</tt>.
	 * 
	 * @param o
	 *            an <tt>Observable</tt> instance.
	 * @param arg
	 *            an arbitrary argument to be passed.
	 */
	public void update(Observable o, Object arg);

}// interface Observer
