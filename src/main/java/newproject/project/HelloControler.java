package newproject.project;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController

public class HelloControler {
    
    @RequestMapping("/hello")
    public String hello() {
        return "hello coder";
    }
    

}
