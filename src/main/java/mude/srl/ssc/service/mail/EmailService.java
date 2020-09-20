/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.mail;

import mude.srl.ssc.entity.Channel;
import mude.srl.ssc.entity.Email;
import mude.srl.ssc.entity.utils.ResponseRequest;

/**
 *
 * @author Jack
 */
public interface EmailService {

    public ResponseRequest<Email> getAccountsByChannerl(Channel c);

    public ResponseRequest<Email> getDefault();
    
}
