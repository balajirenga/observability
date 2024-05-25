package com.country.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.country.countryprj.SimpleProgram;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
// import com.google.protobuf.util.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.trace.v1.InstrumentationLibrarySpans;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

@RestController
@RequestMapping("/country")
public class CountryController {
    
    @GetMapping("/names")
    public String checkme(){
       System.out.println("hello here in the check me mehtod..");
        SimpleProgram simplObj = new SimpleProgram();
        Object obj = simplObj.doObserve();
       // String jsonString = getStringFromObj(obj);
        String jsonString = "Under Construction...";
     
        return jsonString; 
    }

    @PostMapping("/postjson")
    public String postObserveJSON(@RequestHeader HttpHeaders headers, @RequestBody byte[] jsonBytes) {
        System.out.println(" ---------------------------------------------------  ");
        //System.out.println("Headers.. " + headers.toSingleValueMap());
        System.out.println("ACTUAL POST JSON POST " + jsonBytes);
       
        // Wrap the byte array in a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(jsonBytes);

        try {
            ExportTraceServiceRequest exportTraceServiceRequest = ExportTraceServiceRequest.parseFrom(byteBuffer);
            extractedInstrumentDetails(exportTraceServiceRequest);   
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println(" ---------------------------------------------------  ");


        //postSomethingToElastic(jsonBytes);

        newRestClientPost(jsonBytes);
        
        return "Out of the conversion method..";

     
    }

    private void extractedInstrumentDetails(ExportTraceServiceRequest exportTraceServiceRequest) throws IOException {

        int count = exportTraceServiceRequest.getResourceSpansCount();
        System.out.println("Count of Resource Spans : " + count);
        List<ResourceSpans> resourceSpanList =  exportTraceServiceRequest.getResourceSpansList();
        for(int a=0;a <resourceSpanList.size(); a++) {
            // System.out.println("[Span Name].." + resourceSpanList.get(a).getInstrumentationLibrarySpans(0).getSpans(0).getName());
            // System.out.println("[Span Id].." + resourceSpanList.get(a).getInstrumentationLibrarySpans(0).getSpans(0).getSpanId());
            // System.out.println("[Trace Id].." + resourceSpanList.get(a).getInstrumentationLibrarySpans(0).getSpans(0).getTraceId());
            

            extractedSpanDetails(resourceSpanList);
        }
 
    }

    private void extractedSpanDetails(List<ResourceSpans> resourceSpanList) {
        for(int i=0; i < resourceSpanList.size(); i++) {
            InstrumentationLibrarySpans instrumentationLibrarySpans = resourceSpanList.get(i).getInstrumentationLibrarySpans(0);
            System.out.println("Instrument span count " + resourceSpanList.get(i).getInstrumentationLibrarySpansCount());
            List<io.opentelemetry.proto.trace.v1.Span> spansList = instrumentationLibrarySpans.getSpansList();
            for(int j=0;j<spansList.size();j++){
                String spanName = spansList.get(j).getName();
                String kindName = spansList.get(j).getKind().name();
                ByteString myByteSpanIdString = spansList.get(j).getSpanId();
                ByteString myByteTraceIdString = spansList.get(j).getTraceId();

                System.err.println(" [In Span {Span Name }].. " + spanName);
                System.err.println(" [In Span {Kind Name }].. " + kindName);
      
                try {
                    System.out.println("[Encoded Span Id].." + myByteSpanIdString.newCodedInput().readFixed64());
                    System.out.println("[Encoded Trace Id].." + myByteTraceIdString.newCodedInput().readFixed64());
                } catch (IOException e) {
                    e.printStackTrace();
                }
  
                extractedAttributeDetails(spanName, spansList, j);
            }
         }
    }

    private void extractedAttributeDetails(String spanName, List<io.opentelemetry.proto.trace.v1.Span> spansList, int j) {
        List<KeyValue> attributesList = spansList.get(j).getAttributesList();
   
        for (int q=0; q<attributesList.size(); q++){
            System.err.println(" [In Span {Span Name "+ spanName + "} for the Attributes].. " + attributesList.get(q).getKey());
        }
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

    public void newRestClientPost(byte[] jsonBytes){
        // set service name on all OTel signals

        BalajiRestClient obj = new BalajiRestClient();
        //obj.makeAuthenticatedRequest(new String(jsonBytes), "");
        obj.makeAuthenticatedRequest(jsonBytes, "");
    }
   
}
