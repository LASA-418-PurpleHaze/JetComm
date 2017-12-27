/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasarobotics.frc2017;

import org.lasarobotics.hazylib.util.HazyIterative;
import org.lasarobotics.jetson.Receiver;
import org.lasarobotics.jetson.Transmitter;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQ.Context;
/**
 *
 * @author nihal
 */
public class Robot extends HazyIterative {
    Context context;
    Socket subscriber;
    Socket publisher;
    
    Transmitter transmitter;
    
    @Override
    public void teleopPeriodic() {
        transmitter.run();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disabledPeriodic() {
        super.disabledPeriodic(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void teleopInit() {
        super.teleopInit(); //To change body of generated methods, choose Tools | Templates.
        transmitter.publishKeyValue("STATE", "TELE_INIT");
    }

    @Override
    public void autonomousInit() {
        transmitter.publishKeyValue("STATE", "AUTO_INIT");
    }

    @Override
    public void disabledInit() {
        transmitter.publishKeyValue("STATE", "DISABLED_INIT");
    }

    @Override
    public void robotInit() {
        
        context = ZMQ.context(1);
        Receiver receiver = Receiver.getReceiver();
        transmitter = Transmitter.getTransmitter();
        int counter = 0;
        while (counter < 100) {
            transmitter.run();
            counter++;
        }
        new Thread(receiver).start();
        
    }
    
}
