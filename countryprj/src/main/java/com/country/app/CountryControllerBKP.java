package com.country.app;

import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Base64Util;
// import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.country.countryprj.SimpleProgram;
//import com.google.api.client.util.Base64;
import com.google.api.client.util.Charsets;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
//import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Struct.Builder;
// import com.google.protobuf.util.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporterBuilder;
import io.opentelemetry.exporter.otlp.trace.*;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.trace.v1.InstrumentationLibrarySpans;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import kotlin.io.encoding.Base64;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//import io.grpc.protobuf.ProtoInputStream;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
// @RequestMapping("/country")
public class CountryControllerBKP {
    
   // @GetMapping("/names")
    public String checkme(){
       System.out.println("hello here in the check me mehtod..");
        SimpleProgram simplObj = new SimpleProgram();
        Object obj = simplObj.doObserve();
       // String jsonString = getStringFromObj(obj);
        String jsonString = "Under Construction...";

        
        return jsonString; 
    }

   // @PostMapping("/postjson")
    public String postObserveJSON(@RequestHeader HttpHeaders headers, @RequestBody byte[] jsonBytes) {
        System.out.println(" &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  ");
        System.out.println("Headers.. " + headers.toSingleValueMap());
        
        //ProtobufHttpMessageConverter

        System.out.println("ACTUAL POST JSON POST " + jsonBytes);
        // Wrap the byte array in a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(jsonBytes);
       // byte[] jsonBytes = json.getBytes(Charsets.UTF_8);
        try {
            ExportTraceServiceRequest exportTraceServiceRequest = ExportTraceServiceRequest.parseFrom(byteBuffer);
             
            //convertProtobufMessageStreamToJsonString(jsonBytes);

            // Now you can access the ExportTraceServiceRequest object and process the traces as needed
        // System.out.println("Received OTLP traces: " + exportTraceServiceRequest.toBuilder().getAllFields());
           // exportTraceServiceRequest.toBuilder().build().getAllFields();

          // int count = exportTraceServiceRequest.toBuilder().build().getResourceSpansCount();
          int count = exportTraceServiceRequest.getResourceSpansCount();
         List<ResourceSpans> listSpan =  exportTraceServiceRequest.getResourceSpansList();
        System.out.println("Source of truth.." + listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getName());
        System.out.println("Source of truth..Span id" + listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getSpanId());
        System.out.println("Source of truth..trace id" + listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getTraceId());
        System.out.println("Source of truth..trace state" + listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getTraceState());


        //byte[] decode = java.util.Base64.getDecoder().decode(tobeconvertedString.toByteArray());
        //System.out.println("Cleaned up String " + new String(decode).toString());
        ByteString myByteSpanIdString = listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getSpanId();
        ByteString myByteTraceIdString = listSpan.get(0).getInstrumentationLibrarySpans(0).getSpans(0).getTraceId();
       // String spanId = "";
       // String traceId = "";    
        System.out.println("simple myByteSpanIdString" + myByteSpanIdString.newCodedInput().readFixed64());
        System.out.println("simple myByteTraceIdString " + myByteTraceIdString.newCodedInput().readFixed64());
            
        // String encodedString1 = new String(Base64Util.encode(myByteString.toString("UTF_8")));
        // System.out.println("encodedString " + encodedString1);


        // String encodedString2 = new String(Base64Util.encode(myByteString.toStringUtf8()));
        // System.out.println("encodedString " + encodedString2);

        // System.out.println("another try: " + com.google.protobuf.UnsafeByteOperations.unsafeWrap(myByteString.toByteArray()).toStringUtf8());



        //String encodedString = new String(base64.encode(myByteString.toByteArray()));
        //String encodedString = new String(base64.encodeAsString(myByteString.toByteArray()));
        // String encodedString = new String(base64.encodeToString(myByteString.toByteArray()));
        // System.out.println("encodedString " + encodedString);

        // String encodedString1String= new String(base64.encodeBase64(myByteString.toByteArray()));
        // System.out.println("encodedString " + encodedString1String);

        // String encodedString2 = new String(base64.decodeBase64(myByteString.toByteArray()));
        // System.out.println("encodedString " + encodedString2);

        // String encodedString3 = new String(base64.decode(myByteString.toByteArray()));
        // System.out.println("encodedString " + encodedString3);

        // String encodedString4 = BaseEncoding.base64().decode(new String(myByteString).chars());
        // System.out.println("encodedString " + encodedString4);
        
          

         for(int i=0; i < listSpan.size(); i++) {
            InstrumentationLibrarySpans instrumentationLibrarySpans = listSpan.get(i).getInstrumentationLibrarySpans(0);
            List<io.opentelemetry.proto.trace.v1.Span> spansList = instrumentationLibrarySpans.getSpansList();
            for(int j=0;j<spansList.size();j++){
                String name = spansList.get(j).getName();
                String kindNAme = spansList.get(j).getKind().name();

                System.err.println(" Total attributes.. " + spansList.get(j).getEventsList().get(0).getName());
                System.out.println("Name: " + name + " Kind name " + kindNAme);

                List<KeyValue> attributesList = spansList.get(j).getAttributesList();

                for (int q=0; q<attributesList.size(); q++){
                   // String result = attributesList.get(q).toString() + "= " + attributesList.get(q).getValue();
                    System.out.println("Attributes ::  " + attributesList.get(q).getKey());
                }

            }
         }   


          ExportTraceServiceRequest exportTraceServiceRequest2 = ExportTraceServiceRequest.getDefaultInstance();
          //System.out.println("exportTraceServiceRequest2 " + exportTraceServiceRequest2.getClass().toGenericString());
          //System.out.println(" span " + exportTraceServiceRequest.getDefaultInstance(). .getDescriptor());
           //exportTraceServiceRequest.writeDelimitedTo(System.out);
            //System.out.println("something else " + exportTraceServiceRequest.getAllFields());
          //  ExportTraceServiceRequest.newBuilder().build().writeDelimitedTo(System.out);
           //System.out.println("Lisst.... " + exportTraceServiceRequest.toBuilder().build().getResourceSpansList());

        //    Map<FieldDescriptor, Object> allFields = exportTraceServiceRequest.getAllFields();
        //    System.out.println("allFields " + allFields); 
           
          // exportTraceServiceRequest.getAllFields()
           System.out.println("All fields .. " + count);

        System.out.println("Received OTLP traces: " + count);

            // Optionally, you can export the traces or perform any other processing here

            // Send a success response
       ///     resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            // Send an error response
           // resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          ///  resp.getWriter().println("Error: " + e.getMessage());
        }
        System.out.println(" &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  ");


        postSomethingToElastic(jsonBytes);
        
        return "Out of the conversion method..";

     
    }
    
    public String getStringJson(String jsonString) {

        // Parse the JSON string into a Java object
        Gson gson = new Gson();
        Object jsonObject = gson.fromJson(jsonString, Object.class);

        // Stringify the Java object back to a JSON string
        String jsonStringified = gson.toJson(jsonObject);

        // Print the JSON string
        System.out.println(jsonStringified);

        return jsonStringified;
    }
    public String getStringFromObj(Object myObject) {
        // Initialize Gson
        Gson gson = new Gson();
        
        // Convert object to JSON string
        String jsonString = gson.toJson(myObject);

        // Print JSON string
        System.out.println(jsonString);

        return jsonString;
    }
    private String convertProtobufMessageStreamToJsonString(byte[] bytes) throws IOException {
        JsonFormat jsonFormat = new JsonFormat();
        //Course course = Course.parseFrom(protobufStream);
        InputStream protobufStream = new ByteArrayInputStream(bytes);

         ExportTraceServiceRequest exportTraceServiceRequest = ExportTraceServiceRequest.parseFrom(protobufStream);
         return jsonFormat.printToString(exportTraceServiceRequest);
       // return null;
    }
    
    public void postSomethingToElastic(byte[] jsonBytes){
        String otLPEndPoint = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
		String secretToken = "gy328vOqmYYjWLMkrY";
		System.out.println("OTLP URL " + otLPEndPoint);
		System.out.println("OTLP Token " + secretToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROTOBUF);

        headers.setBasicAuth("Authorization", "Bearer " + secretToken);

       // HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(byteData, headers);
       HttpEntity entityObj = new HttpEntity<>(jsonBytes, headers);


        RestTemplate restTemplate = new RestTemplate();
      
       // ResponseEntity<ExportTraceServiceRequest> postForEntity = restTemplate.postForEntity(otLPEndPoint, entityObj, ExportTraceServiceRequest.class);
        // restTemplate.postForObject(secretToken, request, null, null)
        URI postForLocation = restTemplate.postForLocation(otLPEndPoint, entityObj);

       // System.out.println("Response message  " + postForLocation.getStatusCode());
    }
   
}
