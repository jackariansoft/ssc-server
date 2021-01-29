package mude.srl.ssc.service.payload;

import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;

public interface RemoteService {

	RequestCommandResourceReservation validatePayload(String payload,String requestFrom) throws Exception;
}
