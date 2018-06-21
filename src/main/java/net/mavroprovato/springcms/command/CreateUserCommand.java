package net.mavroprovato.springcms.command;

import net.mavroprovato.springcms.entity.Role;
import net.mavroprovato.springcms.entity.User;
import net.mavroprovato.springcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("net.mavroprovato.springcms")
public class CreateUserCommand implements ApplicationRunner {

    /** The user service **/
    private final UserService userService;

    /**
     * Create the command.
     *
     * @param userService The user service.
     */
    @Autowired
    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(ApplicationArguments args) {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("admin");
        user.setEmail("admin@example.com");
        user.setRole(Role.ADMIN);

        userService.save(user);
    }

    /**
     * The entry point of the command.
     *
     * @param args The command line arguments.
     */
    public static void main(String... args) {
        SpringApplication command = new SpringApplication(CreateUserCommand.class);
        command.setWebApplicationType(WebApplicationType.NONE);
        command.run(args);
    }
}
