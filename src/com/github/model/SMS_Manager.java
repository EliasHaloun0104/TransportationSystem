package com.github.model;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMS_Manager {
    public static final String ACCOUNT_SID =
        "ACd7f1caa65f595fa2756d072be69e11f5";
    public static final String AUTH_TOKEN =
        "a2154369dffd059f0b56142f15ce40bc";

    public static void sendSMS(String string){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
            .creator(new PhoneNumber("+46762119023"), // to
                new PhoneNumber("+46769449582"), // from
                string)
            .create();

        System.out.println(message.getSid());
    }


}
