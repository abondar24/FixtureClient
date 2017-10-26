import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.abondar.experimental.fixtureclient.XmlId;
import org.abondar.experimental.fixtureclient.XmlResponse;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class XmlTest {

    @Test
    public void xmlParseTest() throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JaxbAnnotationModule());

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"a3887a1127ac7146a235e2392d49084a\"/></msg>";

        XmlId id = new XmlId();
        id.setValue("a3887a1127ac7146a235e2392d49084a");
        XmlResponse response = new XmlResponse(null, id);


        String x1 = mapper.writeValueAsString(response);
        System.out.println(x1);
        XmlResponse res = mapper.readValue(xml, XmlResponse.class);

        System.out.println(res);
        assertEquals(response.getId(), res.getId());
    }


    @Test
    public void xmlParseTestDone() throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JaxbAnnotationModule());

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>";

        XmlId id = new XmlId();
        id.setValue("");
        XmlResponse response = new XmlResponse(null, null);


        String x1 = mapper.writeValueAsString(response);
        System.out.println(x1);
        XmlResponse res = mapper.readValue(xml, XmlResponse.class);

        System.out.println(res);
        assertNotNull(res.getStatusObject());
    }
}
