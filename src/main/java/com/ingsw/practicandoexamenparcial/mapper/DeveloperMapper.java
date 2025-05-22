package com.ingsw.practicandoexamenparcial.mapper;

import com.ingsw.practicandoexamenparcial.dto.request.DeveloperRequest;
import com.ingsw.practicandoexamenparcial.dto.response.DeveloperResponse;
import com.ingsw.practicandoexamenparcial.model.Developer;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    public Developer toEntity(DeveloperRequest request) {
        Developer developer = new Developer();
        developer.setName(request.name());
        return developer;
    }

    public DeveloperResponse toResponse(Developer developer) {
        return new DeveloperResponse(developer.getId(), developer.getName());
    }
}
