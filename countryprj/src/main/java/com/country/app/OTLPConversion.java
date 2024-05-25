package com.country.app;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;


import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OTLPConversion {
    public static void main(String[] args) throws Exception {

        String abc = "";

        //byte[] byteArray = abc.getBytes();
        try { 
           // InputStream inputStream = new ByteArrayInputStream(byteArray);
            //new OTLPConversion().parse(inputStream);

            OTLPConversion otlpObj =  new OTLPConversion();
            byte[] byteArray = otlpObj.fileToByteArray();

            InputStream inputStream = new ByteArrayInputStream(byteArray);
            otlpObj.parse(inputStream);
        } catch(Exception e) {

        }
       
    }

    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        // Span span = Span.newBuilder().build();
        // String json = JsonFormat.printer().print(span);
        // System.out.println(json);
        return "";
    }
    
        public List<ExportTraceServiceRequest> parse(InputStream inputStream) {
            try{
                List<ExportTraceServiceRequest> result = new ArrayList<>();
                System.out.println(" Hello in the parse method..");
                
                /* A Kinesis record can contain multiple `ExportMetricsServiceRequest`
                records, each of them starting with a header with an
                UnsignedVarInt32 indicating the record length in bytes:
                    ------ --------------------------- ------ -----------------------
                |UINT32|ExportMetricsServiceRequest|UINT32|ExportMetricsService...
                    ------ --------------------------- ------ -----------------------
                */
                ExportTraceServiceRequest request;request = ExportTraceServiceRequest.parseDelimitedFrom(inputStream);

                System.out.println("Parsed data.." + request.getSerializedSize());
                System.out.println("" + inputStream.toString());
                while ((request = ExportTraceServiceRequest.parseDelimitedFrom(inputStream)) != null) {
                    // Do whatever we want with the parsed message
                    result.add(request);
                }
                System.out.println(" Result " + result);
                return result;
            }catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    public  byte[] fileToByteArray(){
        // Define the path to the file
            String filePath = "/Volumes/BalajiCodebase/springprojects/countryprj/src/main/java/com/country/app/protobufsample.txt";

            // Create a File object
            File file = new File(filePath);

            // Check if the file exists
            if (!file.exists()) {
                System.err.println("File does not exist: " + filePath);
                return null;
            }

            byte[] fileBytes = null;
                // Declare a FileInputStream
                FileInputStream fis = null;
            try {
                // Create a FileInputStream for the file
                    fis = new FileInputStream(file);

                    // Get the length of the file
                    long fileSize = file.length();

                // Create a byte array with the same length as the file
                    fileBytes = new byte[(int) fileSize];


                    // Read the entire file into the byte array
                    fis.read(fileBytes);

                    // Print or process the byte array as needed
                    // For example:
                   //System.out.println("File content as byte array: " + new String(fileBytes));
                } catch (IOException e) {
                       e.printStackTrace();
                } finally {
                    // Close the FileInputStream in a finally block to ensure it's always closed
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            return fileBytes;
        }
    
        public static void protoBufHandler(byte[] protobufData) {
            // Assume you received a byte array containing the Protocol Buffers data from the HTTP POST message
           // byte[] protobufData = ...; // Retrieve the protobuf data from the HTTP POST request
    
            try {
                // Parse the Protocol Buffers data into a message object
                ExportTraceServiceRequest protoMessage = ExportTraceServiceRequest.parseFrom(protobufData);
    
                // Serialize the message object into JSON format
                String json = JsonFormat.printer().print(protoMessage);
    
                // Now you can use the JSON data as needed
                System.out.println("JSON: " + json);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
                // Handle parsing errors
            } catch (Exception e) {
                e.printStackTrace();
                // Handle other exceptions
            }
        }
}


