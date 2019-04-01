package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.exceptions.LabelNotFoundException;
import com.adam.kiss.jbugger.exceptions.LabelWithThisNameAlreadyExistsException;
import com.adam.kiss.jbugger.repositories.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    public Label getLabelById(Integer id) throws LabelNotFoundException{
        return labelRepository.findById(id).orElseThrow(LabelNotFoundException::new);
    }
    public Label createLabel(Label label) throws LabelWithThisNameAlreadyExistsException {
        if(doesThisLabelNameAlreadyExist(label.getLabelName())){
            throw new LabelWithThisNameAlreadyExistsException(label.getLabelName());
        }
        return labelRepository.save(label);
    }

    public List<Label> getAllLabels(){
        return labelRepository.findAll();
    }

    public Label getLabelByName(String name) throws LabelNotFoundException {
        return labelRepository.findByLabelName(name).orElseThrow(LabelNotFoundException::new);
    }

    private boolean doesThisLabelNameAlreadyExist(String labelName){
        Optional<Label> possibleExistingLabel = labelRepository.findByLabelName(labelName);
        return possibleExistingLabel.isPresent();
    }
}
