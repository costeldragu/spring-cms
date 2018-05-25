package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.ConfigurationParameter;
import net.mavroprovato.springcms.repository.ConfigurationParameterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The configuration parameter service.
 */
@Service
public class ConfigurationParameterService {

    /** The configuration parameter repository. */
    private final ConfigurationParameterRepository configurationParameterRepository;

    public ConfigurationParameterService(ConfigurationParameterRepository configurationParameterRepository) {
        this.configurationParameterRepository = configurationParameterRepository;
    }

    /**
     * Get a string parameter.
     *
     * @param parameter The configuration parameter.
     * @return The configuration parameter value.
     */
    public String getString(ConfigurationParameter.Parameter parameter) {
        Optional<ConfigurationParameter> configurationParameter = configurationParameterRepository.findOneByName(
                parameter.name());
        if (configurationParameter.isPresent()) {
            return configurationParameter.get().getValue();
        }

        return parameter.defaultValue().toString();
    }

    /**
     * Get an integer parameter.
     *
     * @param parameter The configuration parameter.
     * @return The configuration parameter value.
     */
    public int getInteger(ConfigurationParameter.Parameter parameter) {
        Optional<ConfigurationParameter> configurationParameter = configurationParameterRepository.findOneByName(
                parameter.name());
        if (configurationParameter.isPresent()) {
            return Integer.valueOf(configurationParameter.get().getValue());
        }

        return Integer.valueOf(parameter.defaultValue().toString());
    }
}
