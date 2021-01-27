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

package mude.srl.ssc.modbus.cmd;

import mude.srl.ssc.modbus.Modbus;
import mude.srl.ssc.modbus.ModbusCoupler;
import mude.srl.ssc.modbus.net.ModbusTCPListener;
import mude.srl.ssc.modbus.procimg.SimpleDigitalIn;
import mude.srl.ssc.modbus.procimg.SimpleDigitalOut;
import mude.srl.ssc.modbus.procimg.SimpleInputRegister;
import mude.srl.ssc.modbus.procimg.SimpleProcessImage;
import mude.srl.ssc.modbus.procimg.SimpleRegister;

/**
 * Class implementing a simple Modbus/TCP slave. A simple process image is
 * available to test functionality and behaviour of the implementation.
 * 
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public class TCPSlaveTest {

	public static void main(String[] args) {

		ModbusTCPListener listener = null;
		SimpleProcessImage spi = null;
		int port = Modbus.DEFAULT_PORT;

		try {
			if (args != null && args.length == 1) {
				port = Integer.parseInt(args[0]);
			}
			System.out.println("jModbus Modbus Slave (Server)");

			// 1. prepare a process image
			spi = new SimpleProcessImage();

			spi.addDigitalOut(new SimpleDigitalOut(true));
			spi.addDigitalOut(new SimpleDigitalOut(true));

			spi.addDigitalIn(new SimpleDigitalIn(false));
			spi.addDigitalIn(new SimpleDigitalIn(true));
			spi.addDigitalIn(new SimpleDigitalIn(false));
			spi.addDigitalIn(new SimpleDigitalIn(true));
			// allow checking LSB/MSB order
			spi.addDigitalIn(new SimpleDigitalIn(true));
			spi.addDigitalIn(new SimpleDigitalIn(true));
			spi.addDigitalIn(new SimpleDigitalIn(true));
			spi.addDigitalIn(new SimpleDigitalIn(true));

			spi.addRegister(new SimpleRegister(251));
			spi.addInputRegister(new SimpleInputRegister(45));

			// 2. create the coupler holding the image
			ModbusCoupler.getReference().setProcessImage(spi);
			ModbusCoupler.getReference().setMaster(false);
			ModbusCoupler.getReference().setUnitID(15);

			// 3. create a listener with 3 threads in pool
			if (Modbus.debug)
				System.out.println("Listening...");
			listener = new ModbusTCPListener(3);
			listener.setPort(port);
			
			System.out.println("Listening to "+listener.getAddress().getCanonicalHostName()+" on port "+port);
			
			listener.start();
			
			System.out.println("Press enter to exit");
			
			System.in.read();
			
			System.out.println("Exiting...");
			
			listener.stop();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// main

}// class TCPSlaveTest
