package com.bechtle.eagl.UserMappingsService.json;


import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipTemplate;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.Result;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.SyncResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("local")
public class TestDeserialisation {

    @Test
    public void testSyncResponse(@Autowired ObjectMapper mapper) throws IOException {
        Resource file = new ClassPathResource("SyncResponse.json");
        Assertions.assertNotNull(file);
        Assertions.assertTrue(file.isFile());


        ObjectReader objectReader = mapper.readerFor(SyncResult.class);
        SyncResult o = objectReader.readValue(file.getInputStream());

        Assertions.assertNotNull(o);
        Assertions.assertNotNull(o.getRelationships());
    }


    @Test
    public void testCreateRelationshipTemplate(@Autowired ObjectMapper mapper) throws IOException {
        Resource file = new ClassPathResource("CreateRelationshipTemplateResponse.json");
        Assertions.assertNotNull(file);
        Assertions.assertTrue(file.isFile());


        ObjectReader objectReader = mapper.readerFor(RelationshipTemplate.class);
        RelationshipTemplate o = objectReader.readValue(file.getInputStream());

        Assertions.assertNotNull(o);
        Assertions.assertNotNull(o.getId());
    }

}
