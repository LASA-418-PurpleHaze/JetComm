package org.lasarobotics.frc2017;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.lasarobotics.hazylib.util.HazyIterative;
import org.lasarobotics.jetson.Receiver;
import org.lasarobotics.jetson.Transmitter;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQ.Context;

public class Robot extends HazyIterative {
    Context context;
    Socket subscriber;
    Socket publisher;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    Transmitter transmitter;
    
    @Override
    public void teleopPeriodic() {
        transmitter.run();
    }

    @Override
    public void autonomousPeriodic() {
        transmitter.run();
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void teleopInit() {
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
        ScheduledFuture<?> recieverHandle = scheduler.scheduleAtFixedRate(receiver, 0, 33, TimeUnit.MILLISECONDS);
        
    }
    
}
