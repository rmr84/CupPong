package com.example.cuppong.netutil;

import com.example.cuppong.objects.Cup;

public class CupPongServer {
    private static volatile CupPongServer instance;
    private static Object lockobj = new Object();

    private CupPongServer() {

    }

    public static CupPongServer getInstance() {
        CupPongServer result = instance;
        if(result==null) {
            synchronized (lockobj) {
                result = instance;
                if (result==null) {
                    instance = result = new CupPongServer();
                }
            }
        }
        return result;
    }
}
