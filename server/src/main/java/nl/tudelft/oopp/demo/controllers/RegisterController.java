package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Quote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected.
     */
    @GetMapping("register")
    @ResponseBody
    public String getString(){
        return "testRegister";
    }

}
