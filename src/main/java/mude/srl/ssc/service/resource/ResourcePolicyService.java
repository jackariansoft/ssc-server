package mude.srl.ssc.service.resource;

import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.payload.exception.HourOutOfLimitException;
import mude.srl.ssc.service.payload.exception.ReservetionPolicyException;

public interface ResourcePolicyService {

	public  void checkPolicy(Resource r,RequestCommandResourceReservation request) throws ReservetionPolicyException,HourOutOfLimitException;
}
