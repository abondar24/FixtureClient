import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.abondar.experimental.fixtureclient.SinkBody;
import org.junit.Test;

public class SinkTest {

    @Test
    public  void convertTest() throws JsonProcessingException {
        SinkBody sinkBody = new SinkBody("joined","0091abd505c6f48a764e9210d88ea33e");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());

        String json = mapper.writeValueAsString(sinkBody);
        System.out.println(json);
    }
}
