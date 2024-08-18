package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.model.Role;
import com.elisiocabral.stream_link.model.User;
import com.elisiocabral.stream_link.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Check if the user exists in our database
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // If not, create a new user
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(oauth2User.getAttribute("given_name"));
                    newUser.setLastName(oauth2User.getAttribute("family_name"));
                    // Assign default role
//                    newUser.setRoles(Collections.singleton(new Role()));
                    return userRepository.save(newUser);
                });

        return oauth2User;
    }
}
