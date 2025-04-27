package com.angelozero.task.management.usecase.services.pokemon;

import com.angelozero.task.management.entity.Pokemon;
import com.angelozero.task.management.usecase.exception.BusinessException;
import com.angelozero.task.management.usecase.gateway.PokemonGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPokemonByNameUseCase {

    private final PokemonGateway pokemonGateway;

    public Pokemon execute(String name){
        if (StringUtils.isBlank(name)) {
            log.error("No Pokemon name was informed to be found");
            throw new BusinessException("No Pokemon name was informed to be found");
        }

        log.info("Getting a Pokemon by name: {}", name);

        var pokemon = pokemonGateway.findByName(name);

        if (pokemon == null) {
            log.info("The Pokemon {} was not found", name);
            return null;
        }

        log.info("The Pokemon {} was found with success", name);
        return pokemon;
    }
}
