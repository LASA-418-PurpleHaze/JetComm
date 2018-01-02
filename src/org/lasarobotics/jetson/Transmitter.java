/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasarobotics.jetson;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;
/**
 *
 * @author nihal
 */


public class Transmitter implements Runnable {
    private static final String TAG = "ROBORIO";
    private static Context context = ZMQ.context(1);
    private static Socket publisher;
    private static Transmitter transmitter;
    private static String state;
    private Transmitter() { 
        publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5804");
        
    }

    @Override
    public void run() {
        publishKeyValue("TEST_KEY", "TEST_VALUE");
        publishKeyValue("TEST_KEY2", "TEST_VALUE2");
    }
    
    public void publishKeyValue(String key, String value) {
        publisher.send(TAG + "." + key + ":" + value);
    }
    
    public static Transmitter getTransmitter() {
         if (transmitter == null) 
            transmitter = new Transmitter();
        
        return transmitter;
    }
    
    private long getAbsoluteTime() {
        return System.currentTimeMillis();
    }
    
    private static void setState(){
        
    }
}
