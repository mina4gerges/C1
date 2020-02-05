
package lb.edu.isae.webthymleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pascal Fares
 */
@Controller
public class HelloControler {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "Cnam Liban") String name, 
            Model model){
        model.addAttribute("user",name);
        return "hello";
    }
    
}
