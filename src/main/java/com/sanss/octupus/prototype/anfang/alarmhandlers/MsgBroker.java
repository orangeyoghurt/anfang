package com.sanss.octupus.prototype.anfang.alarmhandlers;

import com.sanss.alarmPreProc.info.AlarmInfo;

/**
 * Implement this interface to parse incoming alarm body.
 *
 * Created by simon on 2014/12/1.
 */
public interface MsgBroker {

    AlarmInfo parseAndFill(byte[] msgBody, DeviceContext deviceContext);

    String getTypeName();
}
