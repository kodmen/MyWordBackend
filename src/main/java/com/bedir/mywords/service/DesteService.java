package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.domain.User;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.service.dto.DesteDTO;
import com.bedir.mywords.service.utilities.SuccessDataResult;
import com.bedir.mywords.service.utilities.SuccessResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DesteService {


    private final DesteRepository desteRepository;
    private final KartService kartService;
    private final UserService userService;

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    public DesteService(DesteRepository desteRepository, KartService kartService, UserService userService) {
        this.desteRepository = desteRepository;
        this.kartService = kartService;
        this.userService = userService;
    }

    public SuccessDataResult<List<Deste>> getUserIdAllDeste(){
        Optional<User> user =  userService.getUserWithAuthorities();
        if (user.isPresent()){

            List<Deste> tempDeste = desteRepository.findAllByInternalUser_Id(user.get().getId());
            List<Kart> tempKart;
            for (int i = 0; i < tempDeste.size(); i++){
                tempKart = kartService.getAllKartForDeste(tempDeste.get(i).getId());
                //Set<Integer> targetSet = new HashSet<Kart>(tempKart);
                tempDeste.get(i).setKartlars(new HashSet<Kart>(tempKart));
            }

            return new SuccessDataResult<List<Deste>>( tempDeste, "desteler listelendi");
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

    public SuccessDataResult<Deste> updateDeste(DesteDTO dto){
        Deste entitiy = desteRepository.getOne(dto.getId());
        entitiy.name(dto.getName());
        entitiy.renk(dto.getRenk());
        return new SuccessDataResult<Deste>(desteRepository.save(entitiy),"deste guncellendi");

    }

    public SuccessResult deleteDeste(long id){
        Deste d = desteRepository.getOne(id);
        kartService.deleteAllKart(d.getId());
        desteRepository.delete(d);
        return new SuccessResult("silme başarılı");
    }

    public SuccessDataResult<Deste> getOneDeste(long id){
        Deste d = desteRepository.getOne(id);
        List<Kart> kartlar = kartService.getAllKartForDeste(d.getId());
        d.setKartlars(new HashSet<Kart>(kartlar));
        return new SuccessDataResult<>(d,"tek deste getirildi");
    }
}
