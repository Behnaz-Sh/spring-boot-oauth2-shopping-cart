package com.github.behnazsh.conroller.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Behnaz Sh
 */

@Data
@MappedSuperclass
abstract class BaseDto {

    @NotNull
    private Long id;
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDto baseDto = (BaseDto) o;
        return id.equals(baseDto.id) &&
                version.equals(baseDto.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}