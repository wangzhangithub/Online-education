package com.wang.msm.servive;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public interface MsmService {
    public String sendAuthCodeEmail(String email, String authCode);

}
