package com.github.model;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SMS_Manager {
//    public static final String ACCOUNT_SID =
//        "ACd7f1caa65f595fa2756d072be69e11f5";
//    public static final String AUTH_TOKEN =
//        "a2154369dffd059f0b56142f15ce40bc";

    public static void sendSMS(String string){

        Properties prop = new Properties();
        try (InputStream in = SMS_Manager.class.getResourceAsStream("resources/files/sms.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String ACCOUNT_SID = prop.getProperty("accountSSID");
        final String AUTH_TOKEN = prop.getProperty("authToken");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
            .creator(new PhoneNumber("+46762119023"), // to
                new PhoneNumber("+46769449582"), // from
                string)
            .create();

        System.out.println(message.getSid());
    }
}
