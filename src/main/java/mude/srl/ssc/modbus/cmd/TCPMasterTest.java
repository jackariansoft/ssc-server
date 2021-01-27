/***
 * Copyright 2002-2013 jamod development team
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

import java.net.InetAddress;

import mude.srl.ssc.modbus.Modbus;
import mude.srl.ssc.modbus.ModbusCoupler;
import mude.srl.ssc.modbus.io.ModbusTCPTransaction;
import mude.srl.ssc.modbus.msg.*;
import mude.srl.ssc.modbus.net.TCPMasterConnection;
import mude.srl.ssc.modbus.procimg.Register;


/**
 * Sample of a Modbus/TCP master. Intended to connect
 * to the TCPSlaveTest.  Just runs for a bit and makes
 * a few requests, then exits.
 * 
 * @author Charles Hache
 * @version @version@ (@date@)
 */
public class TCPMasterTest {

	private static int requestNumber = 0;
	
	public static void main(String[] args) {
		int port = Modbus.DEFAULT_PORT;
		int unitId = 1; //Same as TCPSlaveTest.java
		try {
			if (args != null && args.length == 1) {
				port = Integer.parseInt(args[0]);
			}
			InetAddress addy = InetAddress.getByName("192.168.2.192");
			TCPMasterConnection connection = new TCPMasterConnection(addy);
			connection.setTimeout(3000);
			connection.setPort(port);
			System.out.println("Trying to connect to "+addy.getCanonicalHostName()+" on port "+port);
			connection.connect();
			
			ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
			
			ModbusRequest request;
			if((request = getNextRequest()) != null) {
				request.setUnitID(unitId);
				transaction.setRequest(request);
				transaction.execute();
				ModbusResponse response = transaction.getResponse();
				gotResponse(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void gotResponse(ModbusResponse response) {
		System.out.println("Got response: "+response.toString());
	}

	private static ModbusRequest getNextRequest() {
		//Note: simple process image uses 0-based register addresses
		switch (requestNumber) {
		case 0:
			return new WriteCoilRequest(0X00,false);
		case 1:
			return new ReadCoilsRequest(4025, 1);
		case 2:
			return new ReadInputDiscretesRequest(0,4);
		case 3:
			return new ReadInputRegistersRequest(0,1);
		case 4:
			return new ReadMultipleRegistersRequest(0,1);
		case 5:
			Register r = ModbusCoupler.getReference().getProcessImageFactory().createRegister();
			r.setValue(420);
			return new WriteSingleRegisterRequest(0,r);
		case 6:
			return new ReadMultipleRegistersRequest(0,1);
		default:
			return null;
		}
	}
	
}
