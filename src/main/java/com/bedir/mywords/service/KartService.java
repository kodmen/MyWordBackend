package com.bedir.mywords.service;

import com.bedir.mywords.domain.Deste;
import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.repository.KartRepository;
import com.bedir.mywords.service.utilities.SuccessDataResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KartService {

    private final KartRepository kartRepository;

    public KartService(KartRepository kartRepository) {
        this.kartRepository = kartRepository;
    }

    public List<Kart> getAllKartForDeste(long id){
        return kartRepository.findAllByTekDeste_Id(id);
    }

    public SuccessDataResult<List<Kart>> getAllKartforDestId(long id){
        //return kartRepository.findAllByTekDeste_Id(id);
        return new SuccessDataResult<List<Kart>>( kartRepository.findAllByTekDeste_Id(id), "kartlar listelendi");
    }
}
