package com.bedir.mywords.service;

import com.bedir.mywords.domain.Kart;
import com.bedir.mywords.repository.KartRepository;
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

    public List<Kart> getAllKartForUser(long id){
        return kartRepository.findAllByTekDeste_Id(id);
    }
}
