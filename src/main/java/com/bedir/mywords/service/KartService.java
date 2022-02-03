package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.domain.User;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.repository.KartRepository;
import com.bedir.mywords.service.dto.KartDTO;
import com.bedir.mywords.service.utilities.DataResult;
import com.bedir.mywords.service.utilities.ErrorDataResult;
import com.bedir.mywords.service.utilities.SuccessDataResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KartService {

    private final KartRepository kartRepository;
    private final DesteRepository desteRepository;
    private final UserService userService;


    public KartService(KartRepository kartRepository, DesteRepository desteRepository, UserService userService) {
        this.kartRepository = kartRepository;
        this.desteRepository = desteRepository;
        this.userService = userService;
    }

    public boolean userAndExistCheck(long id){
        return check(id, desteRepository, userService);
    }

    public static boolean check(long id, DesteRepository desteRepository, UserService userService) {
        Deste dx = desteRepository.getOne(id);
        Optional<Deste> tx = desteRepository.findById(id);
        if(desteRepository.existsById(id)){
            Deste d = desteRepository.getOne(id);
            long desteId = d.getInternalUser().getId();
            Optional<User> user =  userService.getUserWithAuthorities();
            if (user.isPresent()){
                if(desteId == user.get().getId()) {return true;}
                else {return false;}
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * bu fonk kullandığım yerde kontrol yapıyorum
     * @param id
     * @return
     */
    public List<Kart> getAllKartForDeste(long id){
        return kartRepository.findAllByTekDeste_Id(id);
    }

    public DataResult<List<Kart>> getAllKartforDestId(long id){
        //return kartRepository.findAllByTekDeste_Id(id);
        if (userAndExistCheck(id)){
            return new SuccessDataResult<List<Kart>>( kartRepository.findAllByTekDeste_Id(id), "kartlar listelendi");
        }
        return new ErrorDataResult<>(null,"kartlar listelenemedi");
    }

    public DataResult<Kart> createKart(KartDTO dto){
        if (userAndExistCheck(dto.getDesteId())){
            Kart entity = new Kart();
            Deste deste = desteRepository.getOne(dto.getDesteId());
            entity.setArkaYuz(dto.getArkaYuz());
            entity.setOnYuz(dto.getOnYuz());
            entity.setTekDeste(deste);
            entity.setOnemSira(0);
            return new SuccessDataResult<Kart>(kartRepository.save(entity),"yeni kart desteye eklendi");
        }else{
            return new ErrorDataResult<>(null,"yeni kart desteye eklenmedi");
        }

    }

    public DataResult<Kart> updateKart(KartDTO dto){
        if (userAndExistCheck(dto.getDesteId())){
            Kart k = kartRepository.getOne(dto.getId());
            k.setOnYuz(dto.getOnYuz());
            k.setArkaYuz(dto.getArkaYuz());
            return new SuccessDataResult<Kart>(kartRepository.save(k),"kart guncellendi");
        }else {
            return new ErrorDataResult<>(null,"kart guncellenmedi");
        }

    }

    public boolean deleteAllKart(long desteId){
        //burda deste id var mı kontrolu yapabiliriz
        List<Kart> deletKart = kartRepository.findAllByTekDeste_Id(desteId);
        deletKart.forEach(kart -> {
            kartRepository.delete(kart);
        });
       return true;
    }


}
