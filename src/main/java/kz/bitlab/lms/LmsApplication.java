package kz.bitlab.lms;

import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LmsApplication.class);
        app.setBanner((env, sourceClass, out) -> {
            try {
                String appName = env.getProperty("spring.application.name", "Akseleu LMS");
                String port = env.getProperty("server.port", "8080");
                String titleStyle = "\u001B[1;3;92m";
                String subTitleStyle = "\u001B[36m";
                String resetStyle = "\u001B[0m";
                out.println(titleStyle);
                out.println(FigletFont.convertOneLine(appName));
                out.println(subTitleStyle + "   🚀 :: ЗАПУСК УСПЕШЕН :: Порт: " + port + " | PID: " + new ApplicationPid());
                out.println("   ======================================================" + resetStyle);
                out.println();
            } catch (Exception e) {
                out.println("LMS System");
            }
        });
        app.run(args);
    }
}
