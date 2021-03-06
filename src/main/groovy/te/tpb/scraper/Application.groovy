package te.tpb.scraper

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@CompileStatic
@SpringBootApplication(scanBasePackages = ["te.tpb.scraper"])
class Application {

    static void main(String[] args) { SpringApplication.run(Application, args) }

}
