package hu.baxter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

@Configuration
public class XmlDataSourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(XmlDataSourceConfig.class);

    private final ResourceLoader resourceLoader;

    private static final String XML_FILE_PATH = "classpath:employee.xml";

    public XmlDataSourceConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public Document xmlDocument() {
        try {
            final Resource resource = resourceLoader.getResource(XML_FILE_PATH);
            try (final InputStream inputStream = resource.getInputStream()) {

                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(inputStream);
                document.getDocumentElement().normalize();

                return document;
            }
        } catch (Exception e) {
            LOG.error("Error loading xml", e);
            throw new RuntimeException("Datasource cannot be loaded!", e);
        }
    }

}
