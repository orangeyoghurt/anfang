package com.sanss.octupus.prototype.anfang.alarmhandlers;

import com.sanss.alarmPreProc.info.AlarmInfo;
import com.sanss.alarmPreProc.intf.AlarmSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Router class of message.
 * Created by simon on 2014/12/1.
 */
public class AlarmRouter implements InitializingBean {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<MsgBroker> brokers = new ArrayList<MsgBroker>();
    private Map<String, MsgBroker> brokerMap = new HashMap<String, MsgBroker>();

    @Override
    public void afterPropertiesSet() throws Exception {
        for(MsgBroker broker: brokers) {
            brokerMap.put(broker.getTypeName(), broker);
        }
    }


    public AlarmSender processMsg(final String deviceId, byte[] msgBody ) {
        Map<String, Object> deviceConfig = jdbcTemplate.queryForMap("select * from mosbus where deviceId="+deviceId);

        DeviceContext deviceContext = jdbcTemplate.query("select * from t_device where deviceId=?", new Object[]{deviceId}, new ResultSetExtractor<DeviceContext>() {
            @Override
            public DeviceContext extractData(ResultSet rs) throws SQLException, DataAccessException {
                DeviceContext deviceContext1 = new DeviceContext();
                deviceContext1.deviceId = deviceId;
                deviceContext1.ip = rs.getString("ip");
                // TODO fill other information from ResultSet
                return deviceContext1;
            }
        });

        String msgType = (String) deviceConfig.get("deviceType");

        AlarmInfo alarmInfo = doParse(msgType, msgBody, deviceContext);
        if (null != alarmInfo) {
//            return new AlarmSender();
        }
        return null;
    }

    public AlarmSender processMsgByType(String msgType, final String deviceId, byte[] msgBody) {
        Map<String, Object> deviceConfig = jdbcTemplate.queryForMap("select * from mosbus where deviceId="+deviceId);

        DeviceContext deviceContext = jdbcTemplate.query("select * from t_device where deviceId=?", new Object[]{deviceId}, new ResultSetExtractor<DeviceContext>() {
            @Override
            public DeviceContext extractData(ResultSet rs) throws SQLException, DataAccessException {
                DeviceContext deviceContext1 = new DeviceContext();
                deviceContext1.deviceId = deviceId;
                deviceContext1.ip = rs.getString("ip");
                // TODO fill other information from ResultSet
                return deviceContext1;
            }
        });

        AlarmInfo alarmInfo = doParse(msgType, msgBody, deviceContext);
        if (null != alarmInfo) {
//            return new AlarmSender();
        }
        return null;
    }

    private AlarmInfo doParse(String msgType, byte[] msgBody, DeviceContext deviceContext) {
        MsgBroker broker = brokerMap.get(msgType);
        if (null == broker) {
            log.error("Failed to find broker for msg type {} on {}", msgType, deviceContext.deviceId);
            return null;
        }
        AlarmInfo alarmInfo = broker.parseAndFill(msgBody, deviceContext);
        return alarmInfo;
    }

    public List<MsgBroker> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<MsgBroker> brokers) {
        this.brokers = brokers;
    }

}
