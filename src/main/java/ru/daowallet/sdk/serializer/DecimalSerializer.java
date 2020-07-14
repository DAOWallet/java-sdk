package ru.daowallet.sdk.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class DecimalSerializer  extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal val, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (null == val) {
            jsonGenerator.writeNull();
        } else if (val.doubleValue() % 1 == 0) {
            jsonGenerator.writeNumber(val.intValue());
        } else {
            val = val.setScale(15, BigDecimal.ROUND_HALF_UP);
            String d = val.toPlainString();
            d = d.indexOf(".") < 0 ? d : d.replaceAll("0*$", "").replaceAll("\\.$", "");
            jsonGenerator.writeNumber(new BigDecimal(d));
        }
    }
}
