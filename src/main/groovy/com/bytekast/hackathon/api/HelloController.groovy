package com.bytekast.hackathon.api

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hello")
public class HelloController {

  @CrossOrigin(origins = "*")
  @RequestMapping(method = RequestMethod.GET)
  Map sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
    ["response": "Hello, ${name}!".toString()]
  }
}