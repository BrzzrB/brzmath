package by.brzmath.app.utils;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public String keyBySocial(String social){
        return social.equals("github")?"login"
                :(social.equals("google")?"email":"id");
    }
}
