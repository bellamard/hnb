package com.b2la.hnb.util;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptUtil {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
