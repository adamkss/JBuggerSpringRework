package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.repositories.AttachmentRepository;
import com.adam.kiss.jbugger.repositories.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    public Label createLabel(Label label){
        return labelRepository.save(label);
    }

    public List<Label> getAllLabels(){
        return labelRepository.findAll();
    }
}
