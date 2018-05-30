package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.ConfigurationParameter;
import net.mavroprovato.springcms.entity.Parameter;
import net.mavroprovato.springcms.repository.ConfigurationParameterRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The configuration parameter service.
 */
@Service
public class ConfigurationParameterService {

    /** The configuration parameter repository. */
    private final ConfigurationParameterRepository configurationParameterRepository;

    /**
     * Create the configuration parameter service.
     *
     * @param configurationParameterRepository The configuration parameter repository.
     */
    public ConfigurationParameterService(ConfigurationParameterRepository configurationParameterRepository) {
        this.configurationParameterRepository = configurationParameterRepository;
    }

    /**
     * Return all parameters as a map.
     *
     * @return All parameters as a map.
     */
    public Map<String, String> allParameters() {
        return configurationParameterRepository.findAll().stream().collect(
                Collectors.toMap(ConfigurationParameter::getName, ConfigurationParameter::getValue));
    }

    /**
     * Get a string parameter.
     *
     * @param parameter The configuration parameter.
     * @return The configuration parameter value.
     */
    public String getString(Parameter parameter) {
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
    public int getInteger(Parameter parameter) {
        Optional<ConfigurationParameter> configurationParameter = configurationParameterRepository.findOneByName(
                parameter.name());
        if (configurationParameter.isPresent()) {
            return Integer.valueOf(configurationParameter.get().getValue());
        }

        return Integer.valueOf(parameter.defaultValue().toString());
    }
}
