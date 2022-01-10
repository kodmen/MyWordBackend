package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.repository.DesteRepository;
import com.bedir.mywords.repository.KartRepository;
import com.bedir.mywords.service.dto.KartDTO;
import com.bedir.mywords.service.utilities.SuccessDataResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KartService {

    private final KartRepository kartRepository;
    private final DesteRepository desteRepository;

    public KartService(KartRepository kartRepository, DesteRepository desteRepository) {
        this.kartRepository = kartRepository;
        this.desteRepository = desteRepository;
    }

    public List<Kart> getAllKartForDeste(long id){
        return kartRepository.findAllByTekDeste_Id(id);
    }

    public SuccessDataResult<List<Kart>> getAllKartforDestId(long id){
        //return kartRepository.findAllByTekDeste_Id(id);
        return new SuccessDataResult<List<Kart>>( kartRepository.findAllByTekDeste_Id(id), "kartlar listelendi");
    }

    public SuccessDataResult<Kart> createKart(KartDTO dto){
        Kart entity = new Kart();
        Deste deste = desteRepository.getOne(dto.getDesteId());
        entity.setArkaYuz(dto.getArkaYuz());
        entity.setOnYuz(dto.getOnYuz());
        entity.setTekDeste(deste);
        entity.setOnemSira(0);
        return new SuccessDataResult<Kart>(kartRepository.save(entity),"yeni kart desteye eklendi");
    }

    public SuccessDataResult<Kart> updateKart(KartDTO dto){
        Kart k = kartRepository.getOne(dto.getId());
        k.setOnYuz(dto.getOnYuz());
        k.setArkaYuz(dto.getArkaYuz());
        return new SuccessDataResult<Kart>(kartRepository.save(k),"kart guncellendi");
    }


}
