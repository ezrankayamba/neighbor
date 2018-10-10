package tz.co.nezatech.neighbor.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tz.co.nezatech.neighbor.model.Privilege;
import tz.co.nezatech.neighbor.model.Role;
import tz.co.nezatech.neighbor.model.User;
import tz.co.nezatech.neighbor.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersApi {
    @Value("${default.user.role:App User}")
    private String defaultUserRole;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> users() {
        return userService.users();
    }

    @PostMapping("/register")
    public ApiResponse saveUser(@RequestBody User user) {
        Role role = userService.roleByName(defaultUserRole);
        user.setRole(role);
        userService.saveUser(user);
        return new ApiResponse(0, "Successfully saved a user");
    }

    @PostMapping("/sendotp")
    public ApiResponse sendOtp(@RequestBody Msisdn msisdn) {
        boolean b = userService.generateAndRecordOTP(msisdn.getMsisdn());
        return b ? new ApiResponse(0, "Successfully sent OTP") : new ApiResponse(-1, "OTP sending failed");
    }

    @PostMapping("/validateotp")
    public ApiResponse validateOtp(@RequestBody Msisdn msisdn) {
        int res = userService.validateOTP(msisdn.getMsisdn(), msisdn.getToken());
        String msg = "Invalid OTP";
        if (res == 0) {
            msg = "Valid OTP";
        } else if (res == 1) {
            msg = "OTP expired";
        }
        return new ApiResponse(res, msg);
    }

    @GetMapping("/privileges")
    public List<Privilege> privileges() {
        return userService.privileges();
    }

    @PostMapping("/privileges")
    public ApiResponse savePrivilege(@RequestBody Privilege privilege) {
        userService.savePrivilege(privilege);
        return new ApiResponse(0, "Successfully saved a privilege");
    }

    @GetMapping("/roles")
    public List<Role> roles() {
        return userService.roles();
    }

    @PostMapping("/roles")
    public ApiResponse saveRole(@RequestBody Role role) {
        userService.saveRole(role);
        return new ApiResponse(0, "Successfully saved a role");
    }
}
