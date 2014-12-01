package com.sanss.octupus.prototype.anfang.alarmhandlers;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Abstract class to declare some common fields
 * Created by simon on 2014/12/1.
 */
public class AbstractMsgBroker implements MsgBroker {

    protected JdbcTemplate jdbcTemplate;

}
