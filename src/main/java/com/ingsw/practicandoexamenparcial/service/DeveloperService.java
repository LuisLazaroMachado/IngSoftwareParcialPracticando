package com.ingsw.practicandoexamenparcial.service;

import com.ingsw.practicandoexamenparcial.dto.request.DeveloperRequest;
import com.ingsw.practicandoexamenparcial.dto.response.DeveloperResponse;
import com.ingsw.practicandoexamenparcial.exception.DuplicateResourceException;
import com.ingsw.practicandoexamenparcial.exception.ResourceNotFoundException;
import com.ingsw.practicandoexamenparcial.mapper.DeveloperMapper;
import com.ingsw.practicandoexamenparcial.model.Developer;
import com.ingsw.practicandoexamenparcial.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;

    @Transactional
    public DeveloperResponse create(DeveloperRequest request) {
        if (developerRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Ya existe un developer con ese nombre");
        }
        Developer saved = developerRepository.save(developerMapper.toEntity(request));
        return developerMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<DeveloperResponse> findAll() {
        return developerRepository.findAll()
                .stream()
                .map(developerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<DeveloperResponse> findAll(Pageable pageable) {
        return developerRepository.findAll(pageable)
                .map(developerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public DeveloperResponse findById(Long id) {
        Developer dev = developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer no encontrado"));
        return developerMapper.toResponse(dev);
    }

    @Transactional
    public DeveloperResponse update(Long id, DeveloperRequest request) {
        Developer dev = developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer no encontrado"));

        if (!dev.getName().equals(request.name()) && developerRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Ya existe otro developer con ese nombre");
        }

        dev.setName(request.name());
        return developerMapper.toResponse(developerRepository.save(dev));
    }

    @Transactional
    public void delete(Long id) {
        Developer dev = developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer no encontrado"));

        developerRepository.delete(dev);
    }
}
