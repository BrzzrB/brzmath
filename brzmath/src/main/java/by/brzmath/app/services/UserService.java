package by.brzmath.app.services;

import by.brzmath.app.repositories.UserRepository;
import by.brzmath.app.utils.SecurityUtil;
import by.brzmath.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;


    @Autowired
    public UserService(UserRepository userRepository, SecurityUtil securityUtil) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public void add(User user){
        userRepository.save(user);
    }

    public void addUserByToken(OAuth2AuthenticationToken token){
        User user = new User();
        user.setSocial(token.getAuthorizedClientRegistrationId());
        user.setUsername((String) token.getPrincipal().getAttributes().get(securityUtil.
                keyBySocial(token.getAuthorizedClientRegistrationId())));
        user.setBlocked(false);
        user.setFirstDate(new Date());
        user.setLastDate(new Date());
        user.setUserIdPrincipal((String) token.getPrincipal().getName());
        add(user);
    }

    public boolean isContainsByToken(OAuth2AuthenticationToken token){
        return !userRepository.findAllByUsername(
                (String) token.getPrincipal().getAttributes()
                        .get(securityUtil.keyBySocial(token.getAuthorizedClientRegistrationId()))
        ).isEmpty();
    }

    public void setLastDateByUsername(OAuth2AuthenticationToken token){
        userRepository.findAllByUsername(
                (String) token.getPrincipal().getAttributes()
                        .get(securityUtil.keyBySocial(token.getAuthorizedClientRegistrationId()))
        ).forEach(e->{
            e.setLastDate(new Date());
            userRepository.save(e);
        });
    }

    public List<User> findAllByOrderByIdAsc(){
        return userRepository.findAllByOrderByIdAsc();
    }

    public long countUsers(){
        return userRepository.count();
    }

    public long countAllBySocial(String social){
        return userRepository.countAllBySocial(social);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public void lockById(long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setBlocked(true);
            userRepository.save(user);
        }
    }

    public void unlockById(long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setBlocked(false);
            userRepository.save(user);
        }
    }

    public List<User> findAllByToken(OAuth2AuthenticationToken token){
        String social = token.getAuthorizedClientRegistrationId();
        String username = (String) token.getPrincipal().getAttributes()
                .get(securityUtil.keyBySocial(social));
        return userRepository.findAllByUsernameAndSocial(username,social);
    }
}
