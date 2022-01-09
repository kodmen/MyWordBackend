package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.User;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.service.dto.UserDTO;
import com.bedir.mywords.web.rest.AccountResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DesteService {


    private final DesteRepository desteRepository;
    private final UserService userService;

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    public DesteService(DesteRepository desteRepository, UserService userService) {
        this.desteRepository = desteRepository;
        this.userService = userService;
    }

    public List<Deste> getUserIdAllDeste(){
        Optional<User> user =  userService.getUserWithAuthorities();
    if (user.isPresent()){
        return desteRepository.findAllByInternalUser_Id(user.get().getId());
    }

     return null;
    }
}
