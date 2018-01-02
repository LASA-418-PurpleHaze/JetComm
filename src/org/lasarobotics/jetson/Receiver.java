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
public class Receiver implements Runnable {
    private static Socket subscriber;
    private static Context context = ZMQ.context(1);
    String message;
    private static Receiver receiver;
    
    Receiver () {
        subscriber = context.socket(ZMQ.SUB);
        System.out.println("subscriber connecting...");
        subscriber.connect("tcp://10.4.18.3:5804");
        System.out.println("successful subscriber connect");
        subscriber.subscribe("".getBytes());
    }

    @Override
    public void run() {
        message = subscriber.recvStr();
        System.out.println("Received: " + message);
    }
    
    public static Receiver getReceiver() {
        if (receiver == null) 
            receiver = new Receiver();
        
        return receiver;
        
    }
    
}
