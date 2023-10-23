package net.thumbtack.school.hiring.dto.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;

@Provider
public class JsonWriterInterceptor implements WriterInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWriterInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        OutputStream originalStream = context.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        context.setOutputStream(byteArrayOutputStream);
        try {
            context.proceed();
        } finally {
            BufferedReader reader =
                    new BufferedReader
                            (new InputStreamReader(new ByteArrayInputStream
                                    (byteArrayOutputStream.toByteArray())));
            String json = reader.readLine();
            if (json != null) {
                LOGGER.debug("Json output " + json);
            }
            byteArrayOutputStream.writeTo(originalStream);
            byteArrayOutputStream.close();
            reader.close();
            context.setOutputStream(originalStream);
        }
    }

}
