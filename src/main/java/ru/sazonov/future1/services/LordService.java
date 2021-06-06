package ru.sazonov.future1.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sazonov.future1.enteties.Lord;
import ru.sazonov.future1.enteties.Planet;
import ru.sazonov.future1.exceptions.NotFoundEntityException;
import ru.sazonov.future1.mappers.LordMapper;
import ru.sazonov.future1.repositories.LordRepository;
import ru.sazonov.future1.requests.LordModel;

import java.util.List;

@Service
@AllArgsConstructor
public class LordService {

    private final LordRepository lordRepository;
    private final LordMapper lordMapper;

    public void createLord(LordModel lordModel) {
        Lord lord = lordMapper.toLordEntity(lordModel);
        lordRepository.save(lord);
    }

    public List<Lord> get10YoungestLords() {
        return lordRepository.findFirst10Lords(Pageable.ofSize(10)).getContent();
    }

    /**
     *
     * @param lordId lord id
     * @param lordModel lord model
     * @return updated lord
     */
    @Transactional
    public Lord updateLord(Long lordId, LordModel lordModel) {
        Lord targetLord = findLordById(lordId);
        Planet planet1 = new Planet();
        planet1.setName("plan1");
//        lordMapper.updateLord(lordModel, targetLord);
        targetLord.addPlanet(planet1);
        return targetLord;
//        return lordRepository.save(targetLord);
    }

    public Lord findLordById(Long lordId) throws NotFoundEntityException {
        return lordRepository.findById(lordId).orElseThrow(() -> new NotFoundEntityException(String.format("Lord with id: %d not found", lordId)));
    }
}
