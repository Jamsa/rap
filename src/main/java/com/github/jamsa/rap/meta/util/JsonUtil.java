package com.github.jamsa.rap.meta.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jamsa.rap.meta.Constant;
import com.github.jamsa.rap.meta.model.ModelViewObjectType;
import com.github.jamsa.rap.meta.model.RapMetaModel;
import com.github.jamsa.rap.meta.model.RapMetaModelViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static Map parse(JsonNode json, RapMetaModelViewObject viewObject){
        Map result = new HashMap();
        return result;
    }

    public static Map parseRequestJson(Reader reader, RapMetaModel metaModel) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Object.class, new UntypedObjectDeserializer(){
            @Override
            public Object deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException {
                switch (jp.getCurrentToken()) {
                    case VALUE_NULL:
                        return null;
                    default:
                        return super.deserialize(jp, ctxt);
                }
            }
        });
        mapper.registerModule(module);

        Map result = mapper.readValue(reader, new TypeReference<HashMap>(){});
        convertRequestParams(metaModel,result);
        return result;
    }


    public static Map convertRequestParams(RapMetaModelViewObject viewObject,Map params){ //<String,String>
        viewObject.getViewFields().values().stream().forEach(f->{
            Object value = params.get(f.getFieldAlias());
            Object result = value;
            if(value!=null && value instanceof String && f.getDataType()!=null) {
                String obj = (String)value;
                try {
                    switch (f.getDataType()) {
                        case INTEGER:
                            result = Integer.parseInt(obj);
                            break;
                        case FLOAT:
                            result = Float.parseFloat(obj);
                            break;
                        case DOUBLE:
                            result = Double.parseDouble(obj);
                            break;
                        case DATE:
                            result = new SimpleDateFormat("yyyy-MM-dd").parse(obj);
                            break;
                        case TIMESTAMP:
                            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj);
                            break;
                    }
                    params.put(f.getFieldAlias(), result);
                }catch (Exception e){
                    //e.printStackTrace();
                    params.put(f.getFieldAlias(),null); //todo:
                }
            }
        });
        return params;
    }

    public static Map convertRequestParams(RapMetaModel metaModel, String viewAlias,Map params){ //<String,String>
        metaModel.getModelViewObjects().values().stream().filter(v->viewAlias.equals(v.getViewAlias())).forEach(v->convertRequestParams(v,params));
        return params;
    }

    public static Map convertRequestParams(RapMetaModel metaModel, Map params){
        metaModel.getModelViewObjects().values().stream().forEach(v->{
            if(v.getViewType()!=ModelViewObjectType.SUBTABLE) convertRequestParams(v,params);
            else{
                Map viewParams = (Map)params.get(v.getViewAlias());
                if(viewParams!=null) {
                    Arrays.stream(new String[]{Constant.RECORD_ADD_ROWS_KEY, Constant.RECORD_DELETE_ROWS_KEY, Constant.RECORD_UPDATE_ROWS_KEY}).forEach(s -> {
                        List<Map> records = (List<Map>) viewParams.get(s);
                        records.stream().forEach(r -> convertRequestParams(v, r));
                    });
                }
            }
        });
        return params;
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(obj);
        return json;
    }
}
