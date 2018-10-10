package tz.co.nezatech.neighbor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tz.co.nezatech.neighbor.model.*;
import tz.co.nezatech.neighbor.repository.*;
import tz.co.nezatech.neighbor.util.RandomUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepos;
    @Autowired
    private RoleRepository roleRepos;
    @Autowired
    private PrivilegeRepository privilegeRepos;
    @Autowired
    private RoleHasPrivilegeRepository rhpRepos;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private SMSService smsService;

    public void savePrivilege(Privilege privilege) {
        privilegeRepos.save(privilege);
    }

    public void saveRole(Role role) {
        List<Privilege> list = role.getPrivileges();
        role.setPrivileges(new ArrayList<>());
        Role save = roleRepos.save(role);
        rhpRepos.deleteByRole(save);
        list.forEach(privilege -> rhpRepos.save(new RoleHasPrivilege(save, privilege)));
    }

    public void saveUser(User user) {
        userRepos.save(user);
    }

    public List<User> users() {
        return userRepos.findAll();
    }

    public List<Privilege> privileges() {
        return privilegeRepos.findAll();
    }

    public List<Role> roles() {
        return roleRepos.findAll();
    }

    public Role roleByName(String name) {
        return roleRepos.findByName(name).orElse(null);
    }

    public boolean generateAndRecordOTP(String msisdn) {
        try {
            int expiryInMinutes = 3;
            String token = RandomUtil.randomDigits(6);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime now3 = now.plusMinutes(expiryInMinutes);
            ZonedDateTime zdt = now3.atZone(ZoneId.systemDefault());
            Date expiryDate = Date.from(zdt.toInstant());
            Token tkn = new Token(token, msisdn, expiryDate);
            tokenRepository.save(tkn);
            smsService.recordAndSendSMS(new SMSMessage(String.format("%s is one time password. Use this within %d minutes", token, expiryInMinutes), msisdn));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param msisdn
     * @param otp
     * @return 0=Success, 1=Expired, -1=No token
     */
    public int validateOTP(String msisdn, String otp) {
        Optional<Token> byToken = tokenRepository.findByToken(otp);
        if(byToken.isPresent()){
            Token token = byToken.get();
            LocalDateTime expiryDate = token.getExpiryDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(expiryDate)){
                return 0;
            }else {
                return 1;//expired
            }
        }
        return -1;
    }
}
