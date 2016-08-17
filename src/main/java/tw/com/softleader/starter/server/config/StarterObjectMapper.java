package tw.com.softleader.starter.server.config;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tw.com.softleader.commons.json.jackson.JacksonObjectMapper;

@SuppressWarnings("serial")
public class StarterObjectMapper extends JacksonObjectMapper {

  public StarterObjectMapper() {
    super();
    addBooleanModule();
  }

  public StarterObjectMapper(Collection<Module> modules) {
    super(modules);
    addBooleanModule();
  }

  public StarterObjectMapper(Module module, Module... rest) {
    super(module, rest);
    addBooleanModule();
  }

  private void addBooleanModule() {

    JsonSerializer<Boolean> booleanSerializer = new JsonSerializer<Boolean>() {
      @Override
      public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException, JsonProcessingException {
        if (value != null) {
          gen.writeNumber((value) ? 1 : 0);
        }
      }
    };
    JsonDeserializer<Boolean> booleanDeserializer = new JsonDeserializer<Boolean>() {

      @Override
      public Boolean deserialize(JsonParser p, DeserializationContext ctxt)
          throws IOException, JsonProcessingException {
        return p.getIntValue() == 1;
      }
    };

    SimpleModule booleanModule = new SimpleModule();
    booleanModule.addSerializer(boolean.class, booleanSerializer);
    booleanModule.addSerializer(Boolean.class, booleanSerializer);
    booleanModule.addDeserializer(boolean.class, booleanDeserializer);
    booleanModule.addDeserializer(Boolean.class, booleanDeserializer);

    registerModule(booleanModule);
  }

}
