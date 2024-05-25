package com.movieapi;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/favorites")
public class ApiServlet {
    private static final Logger logger = LogManager.getLogger(ApiServlet.class);

    // Create artificial delay if set
    @Value("${TOGGLE_SERVICE_DELAY:0}")
    private Integer delayTime;

    // Create redis pool using Jedis client
    @Value("${REDIS_HOST:localhost}")
    private String redisHost;

    @Value("${REDIS_PORT:6379}")
    private Integer redisPort;

    //String SERVICE_NAME = System.getenv("OTEL_SERVICE_NAME");
    String SERVICE_NAME = "MyhomeService";

    private JedisPool r;

    @PostConstruct
    public void init() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(3000); // Set the maximum blocked time to 3 seconds
        poolConfig.setMaxTotal(100); // set the max total connections
        r = new JedisPool(poolConfig, redisHost, redisPort);
        System.err.println(" Redispool " + r.toString());
    }

    @GetMapping("/checkme")
    public String checkme(){
        System.out.println(" Hello there its been checked...");    
        

        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("coolchecker1").startSpan();
        Scope scope = span.makeCurrent();
       
        span.addEvent("a span event", Attributes
        .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

        span.addEvent("FirstRunner", Attributes.of(AttributeKey.longKey("25"), Long.valueOf("42"))).setAttribute("mylapore", "2").setAttribute("porur", "45");
        span.setStatus(StatusCode.OK);
        span.end();
        scope.close();

        Span span2 = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("newchecker2").startSpan();
        Scope scope2 = span2.makeCurrent();
        span2.addEvent("a span event", Attributes
        .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

        Span addEvent = span2.addEvent("SecondRunner", Attributes.of(AttributeKey.longKey("56"), Long.valueOf("200")));
        addEvent.makeCurrent();

        addEvent.setAttribute("tnagar", "8").setAttribute("adyar", "100");
        addEvent.addEvent("coolchecker3");
        span2.setStatus(StatusCode.ERROR);
        span2.end();


        scope2.close();
        

        doSomeSample();

       System.out.println("Span  name :: " + span.getSpanContext().toString());

        return " Hello there.. its checked.. new span";
    }

    public void doSomeSample(){
        System.out.println("here is the sample code...");

        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("doSomeSample1").startSpan();
        Scope scope = span.makeCurrent();
        span.addEvent("ado some sampleevent", Attributes
        .of(AttributeKey.longKey("sampleKey"), Long.valueOf(93)));
        span.addEvent("FirstRunner", Attributes.of(AttributeKey.longKey("25"), Long.valueOf("42"))).setAttribute("simei", "2").setAttribute("changi", "45");
        span.setStatus(StatusCode.OK);
        span.end();
        scope.close();

    }

    @GetMapping
    public String helloWorld(@RequestParam(required = false) String user_id) throws InterruptedException {
        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("helloWorld").startSpan();
        Scope scope = span.makeCurrent();
        String returnedString = "";

        if (user_id == null) {
            logger.info("Main request successful");
            returnedString= "Hello World!";
        } else {
            returnedString = getUserFavorites(user_id);
        }

        span.addEvent("a span event", Attributes
                .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));
        span.setStatus(StatusCode.OK);
        span.end();
        scope.close();
        System.out.println("Hello here at the hello world method..");
        return returnedString;

    }
    public void postUserFavorites(String user_id, String movieID) {
        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("Redis.Post").setSpanKind(SpanKind.CLIENT).startSpan();
        Jedis jedis = r.getResource();

        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("db.system", "redis");
            span.setAttribute("db.connection_string", redisHost);
            span.setAttribute("db.statement", "POST user_id " + user_id +" AND movie_id "+movieID);

                Long redisResponse = jedis.srem(user_id, movieID);
                if (redisResponse == 0) {
                    jedis.sadd(user_id, movieID);
                }
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, "Error while getting data from Redis");
            span.recordException(e);
        } finally {
            jedis.close();
            span.end();
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String handlePost(@RequestParam String user_id, @RequestBody String requestBody) throws InterruptedException, Exception {
        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("handlePost").startSpan();
        Scope scope = span.makeCurrent();

        handleDelay();
        logger.info("Adding or removing favorites");

        JSONObject json = new JSONObject(requestBody);
        String movieID = Integer.toString(json.getInt("id"));

       /// String movieID = Integer.toString(requestBody.getInt("id"));

        logger.info("Adding or removing favorites for user " +  user_id + ", movieID " + movieID);

        postUserFavorites(user_id,movieID);
        String favorites = getUserFavorites(user_id);
        handleCanary();

        span.addEvent("a span event", Attributes
                .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

        span.setStatus(StatusCode.OK);

        span.end();

        scope.close();
        return favorites;
    }
    public String getUserFavorites(String user_id) {
        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("Redis.Get").setSpanKind(SpanKind.CLIENT).startSpan();
        String returnedFavorites = "";
        Jedis jedis = r.getResource();

        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("db.system", "redis");
            span.setAttribute("db.connection_string", redisHost);
            span.setAttribute("db.statement", "GET user_id" + user_id);

            handleDelay();

            logger.info("Getting favorites for user " + user_id);

            List<String> favorites = new ArrayList<>(jedis.smembers(user_id));
            JSONObject favorites_json = new JSONObject();
            favorites_json.put("favorites", favorites);

            logger.info("User " + user_id + " has favorites " + favorites);

            returnedFavorites =  favorites_json.toString();

        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR, "Error while getting data from Redis");
            span.recordException(e);
        } finally {
            jedis.close();
            span.end();
        }
        return returnedFavorites;
    }



    private void handleDelay() throws InterruptedException {
        if (delayTime > 0) {
            Random random = new Random();
            double randomGaussDelay =
                    Math.max(0, random.nextGaussian() * (delayTime / 1000 / 10) + (delayTime / 1000));
            TimeUnit.MILLISECONDS.sleep((long) randomGaussDelay);
        }
    }

    private void handleCanary() throws Exception {
        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("handleCanary").startSpan();
        Scope scope = span.makeCurrent();

        Integer sleepTime =
                Integer.parseInt(Objects.requireNonNullElse(System.getenv("TOGGLE_CANARY_DELAY"), "0"));
        Random random = new Random();
        if (sleepTime > 0 && random.nextDouble() < 0.5) {
            double randomGaussDelay =
                    Math.max(0, random.nextGaussian() * (sleepTime / 1000 / 10) + (sleepTime / 1000));
            TimeUnit.MILLISECONDS.sleep((long) randomGaussDelay);
            logger.info("Canary enabled");
            Span.current().setAttribute("canary", "test-new-feature");
            Span.current().setAttribute("quiz_solution", "correlations");
            Double toggleCanaryFailure =
                    Double.parseDouble(Objects.requireNonNullElse(System.getenv("TOGGLE_CANARY_FAILURE"), "0"));
            if (random.nextDouble() < toggleCanaryFailure) {
                logger.error("Something went wrong");
                throw new Exception("Something went wrong");
            }
        }
            span.addEvent("a span event", Attributes
                    .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

                                span.setStatus(StatusCode.OK);

        span.end();

        scope.close();
    }
}
