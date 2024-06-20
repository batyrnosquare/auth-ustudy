package dev.batyrnosquare.auth_ustudy.controller;

import dev.batyrnosquare.auth_ustudy.data.User;
import dev.batyrnosquare.auth_ustudy.dto.LoginInput;
import dev.batyrnosquare.auth_ustudy.dto.RefreshTokenInput;
import dev.batyrnosquare.auth_ustudy.dto.RegisterInput;
import dev.batyrnosquare.auth_ustudy.dto.ReqRes;
import dev.batyrnosquare.auth_ustudy.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Controller
public class UserController implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> users(){
        return userService.allUsers();
    }

    @QueryMapping
    public User user(@Argument Long id){
        return userService.getUserByID(id);
    }

    @QueryMapping
    public List<User> admins(){
        return userService.allAdmins();
    }


    @QueryMapping
    public User admin(@Argument Long id){
        return userService.getAdminByID(id);
    }

    @MutationMapping
    public ReqRes register(@Argument RegisterInput regRequest){
        return userService.registration(regRequest);
    }


    @MutationMapping
    public ReqRes login(@Argument LoginInput loginRequest){
        return userService.login(loginRequest);
    }

    @MutationMapping
    public ReqRes refreshToken(@Argument RefreshTokenInput refreshTokenRequest){
        return userService.refreshToken(refreshTokenRequest);
    }

}
