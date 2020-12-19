/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import mude.srl.ssc.entity.Energymesure;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jack
 */
@Repository
public class EnergyServiceImpl extends AbstractService<Energymesure> implements EnergyService {

    @Autowired
    PlcService plcService;


    @Transactional
    @Override
    public void saveEnergyConsuption(String[] consum) throws Exception {

        /**
         * Entiity manager transazionale
         * 
         */
        EntityManager em1 =null;
        EntityTransaction tx = null;
        if (consum != null && consum.length == 3) {
            //Estrazione valori
            String plc = consum[0];
            String grad = consum[1];
            String value = consum[2];

            Integer volue_to_update = Integer.valueOf(grad);

            Plc plcByUID = plcService.getPlcByUID(plc);
            try {
                if (plcByUID != null) {

                    Energymesure energy = null;

                    Collection<Energymesure> energymesureCollection = plcByUID.getEnergymesureCollection();
                    if (energymesureCollection == null || energymesureCollection.isEmpty()) {

                        if (energymesureCollection == null) {
                            energymesureCollection = new ArrayList<>();
                            plcByUID.setEnergymesureCollection(energymesureCollection);
                        }

                        energy = new Energymesure();
                        energy.setPlc(plcByUID);
                        energy.setSsc(plcByUID.getSsc());
                        energymesureCollection.add(energy);
                        super.constraintValidationsDetected(energy);

                    } else {

                        energy = energymesureCollection.iterator().next();

                    }
                    energy.setDins(new Date(System.currentTimeMillis()));
                    impostaValoriEnergia(energy, volue_to_update, new BigDecimal(value));

                    em1 = getEmForTransaction();
                    em1.getTransaction().begin();

                    em1.merge(plcByUID);
                    em1.flush();
                    em1.getTransaction().commit();

                }

            } catch (Exception ex) {
                throw ex;
            }

        }
    }

    /**
     * 0: :Voltage:%04f$r$n'), REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 1:
     * :Current:%04f$r$n', REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 2:
     * :ActivePwr:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 3:
     * :ApparentPwr:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 4:
     * :ReactivePwr:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 5:
     * :PwrFactor:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 6:
     * :Frequency:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 7:
     * :IActiveEn:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 8:
     * :EActiveEn:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer)); 9:
     * :TActiveEn:%04f$r$n',REAL_TYPE,ADR(MdbCfg[MIDx].@Buffer));
     *
     * @param mesure
     * @param grandezza grandezza fisica da salvare
     * @param value valore della grandezza fisica
     */
    public void impostaValoriEnergia(Energymesure mesure, Integer grandezza, BigDecimal value) {
        switch (grandezza) {
            case 0:
                mesure.setVoltage(value);
                break;
            case 1:
                mesure.setCurrent(value);
                break;
            case 2:
                mesure.setActivePower(value);
                break;

            case 3:
                mesure.setApparentPower(value);
                break;
            case 4:
                mesure.setReactivePower(value);
                break;
            case 5:
                mesure.setPowerFactor(value);
                break;
            case 6:
                mesure.setFrequency(value);
                break;
            case 7:
                mesure.setIActiveEn(value);
                break;
            case 8:
                mesure.setEActveEn(value);
                break;
            case 9:
                mesure.setTActvieEn(value);
                break;
        }
    }

}
