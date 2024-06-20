package dev.batyrnosquare.auth_ustudy.controller;

import dev.batyrnosquare.auth_ustudy.dto.ReqRes;
import dev.batyrnosquare.auth_ustudy.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Controller
public class UserController implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    public ReqRes register(ReqRes regRequest){
        return userService.registration(regRequest);
    }


    public ReqRes login(ReqRes loginRequest){
        return userService.login(loginRequest);
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        return userService.refreshToken(refreshTokenRequest);
    }

}
