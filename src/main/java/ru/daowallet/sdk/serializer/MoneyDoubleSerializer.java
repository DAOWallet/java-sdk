package ru.daowallet.sdk.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MoneyDoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (null == aDouble) {
            jsonGenerator.writeNull();
        }
        else if(aDouble.doubleValue() % 1 == 0){
            jsonGenerator.writeNumber(aDouble.intValue());
        }
        else {
            jsonGenerator.writeNumber(aDouble);
        }
    }
}
