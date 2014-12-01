package com.sanss.octupus.prototype.anfang.alarmhandlers;

import com.sanss.alarmPreProc.info.AlarmInfo;

/**
 * Broker for QingNiao message.
 * Created by simon on 2014/12/1.
 */
public class QingNiaoBroker extends AbstractMsgBroker {
    @Override
    public AlarmInfo parseAndFill(byte[] msgBody, DeviceContext deviceContext) {
        return null;
    }

    @Override
    public String getTypeName() {
        return "qingniao";
    }
}
