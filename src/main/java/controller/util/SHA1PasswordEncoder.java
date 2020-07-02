package controller.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA1PasswordEncoder implements PasswordEncoder{
    @Override
    public String encode(String password) {
        return DigestUtils.sha1Hex(password);
    }
}