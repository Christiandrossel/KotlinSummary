import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

class Scratch {

  public static void main(String[] args) throws JsonProcessingException {
    String delimiter = "|";
    String s = "myCool|String that should be parsed|string 2|strin3|";

    final int index = s.indexOf(delimiter);


    for (int i = 0; i != s.length() - 1 ; i++){
      String result = s.substring(index+i, s.length());
      System.out.println("result: " + result);
    }

    ObjectMapper  objectMapper = jacksonObjectMapper()
        .registerModule(new JavaTimeModule())
        .setTimeZone(TimeZone.getDefault());
  }
}