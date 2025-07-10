package com.phone.mapper;

import com.phone.dto.PhoneDTO;
import com.phone.model.Phone;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface PhoneMapper {
    PhoneDTO toDto(Phone phoneEntity);
    Phone toEntity(PhoneDTO dto);
    List<PhoneDTO> toDtoList(List<Phone> phoneEntities);
    List<Phone> toEntityList(List<PhoneDTO> phoneDTOS);
}
