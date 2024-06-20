package dev.batyrnosquare.auth_ustudy.service;


import dev.batyrnosquare.auth_ustudy.data.Role;
import dev.batyrnosquare.auth_ustudy.data.User;
import dev.batyrnosquare.auth_ustudy.dto.LoginInput;
import dev.batyrnosquare.auth_ustudy.dto.RefreshTokenInput;
import dev.batyrnosquare.auth_ustudy.dto.RegisterInput;
import dev.batyrnosquare.auth_ustudy.dto.ReqRes;
import dev.batyrnosquare.auth_ustudy.exceptions.IncorrectEmailException;
import dev.batyrnosquare.auth_ustudy.exceptions.UserAlreadyExistsException;
import dev.batyrnosquare.auth_ustudy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getUserByID(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> allAdmins(){
        return userRepository.findAllByRole(Role.ADMIN);
    }

    public User getAdminByID(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public ReqRes registration(RegisterInput registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            String regex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
            if (!registrationRequest.getEmail().matches(regex)){
                throw new IncorrectEmailException("Incorrect email format.");
            }

            if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()){
                throw  new UserAlreadyExistsException("User with this email already exists!");
            }
            User user = new User();
            user.setFirstName(registrationRequest.getF_name());
            user.setLastName(registrationRequest.getL_name());
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(Role.valueOf(registrationRequest.getRole()));
            User userResult = userRepository.save(user);
            if (userResult != null) {
                resp.setUser(String.valueOf(userResult));
                resp.setMessage("User Saved Successfully!");
                resp.setStatus(200);
            }
        }catch (Exception e){
            resp.setStatus(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(LoginInput loginRequest){
        ReqRes resp = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException(loginRequest.getEmail()));
            System.out.println("USER IS:" + user);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setStatus(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hr");
            resp.setMessage("Successfully Signed In!");
        }catch (Exception e){
            resp.setStatus(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }



    public List<User> allUsers(){
        return userRepository.findAll();
    }


    public ReqRes refreshToken(RefreshTokenInput refreshTokenRequest){
        ReqRes resp = new ReqRes();
        try {
            String token = refreshTokenRequest.getToken();
            String email = jwtUtils.extractUsername(token);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found."));
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                String newToken = jwtUtils.generateToken(user);
                resp.setUser(newToken);
                resp.setMessage("Token Refreshed!");
                resp.setStatus(200);
            } else {
                resp.setMessage("Invalid Token!");
                resp.setStatus(401);
            }
        }catch (Exception e){
            resp.setStatus(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

}
