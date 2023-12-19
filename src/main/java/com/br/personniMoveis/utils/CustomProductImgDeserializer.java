package com.br.personniMoveis.utils;

import com.br.personniMoveis.model.product.ProductImg;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomProductImgDeserializer extends StdDeserializer<Set<ProductImg>> {

    public CustomProductImgDeserializer() {
        this(null);
    }

    public CustomProductImgDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<ProductImg> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode imgNode = node.get("img");

        Set<ProductImg> secondaryImages = new HashSet<>();
        if (imgNode != null && imgNode.isArray()) {
            for (JsonNode imgElement : imgNode) {
                String base64Data = imgElement.asText();
                ProductImg img = new ProductImg();
                img.setImg(base64Data);
                // Se necessário, configure a relação bidirecional
                secondaryImages.add(img);
            }
        }
        return secondaryImages;
    }
}
