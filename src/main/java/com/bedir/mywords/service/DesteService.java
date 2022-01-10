package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.User;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.service.dto.DesteDTO;
import com.bedir.mywords.service.dto.UserDTO;
import com.bedir.mywords.service.utilities.SuccessDataResult;
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

    public SuccessDataResult<List<Deste>> getUserIdAllDeste(){
        Optional<User> user =  userService.getUserWithAuthorities();
        if (user.isPresent()){
            return new SuccessDataResult<List<Deste>>( desteRepository.findAllByInternalUser_Id(user.get().getId()), "desteler listelendi");
        }

         return null;
    }

    public Deste createDeste(DesteDTO dto){
        Optional<User> user =  userService.getUserWithAuthorities();
        System.out.println(user);
        Deste d = new Deste();
        d.setName(dto.getName());
        d.setRenk(dto.getRenk());
        d.setInternalUser(user.get());
        System.out.println("deste");
        System.out.println(d);
        desteRepository.save(d);
        return d;

    }
}
