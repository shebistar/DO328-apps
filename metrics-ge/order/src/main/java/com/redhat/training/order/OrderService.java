package com.redhat.training.order;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Metered;

import java.util.Random;

@Path("/")
@ApplicationScoped
public class OrderService {

    @GET
    @Path("/order")
    @Counted(name = "order_svc:spl50_orders_placed",
    description = "count of spl50 orders placed")
    @SimplyTimed(name = "order_svc:spl50_order_process_time",
           description = "A measure of how long it takes to process an order",
           unit = MetricUnits.MILLISECONDS)
    @Metered(name = "order_svc:orders_processed_rate",
         unit = MetricUnits.MINUTES,
         description = "Rate at which orders are placed",
         absolute = true)
    // TODO
    // 1. Add a counter to count the spl50 orders placed
    // 2. Add a simple timer to track the response time
    // 3. Add a meter to track the rate of order placement

    @Produces(MediaType.TEXT_PLAIN)
    public String processOrder() {

        try {
            Thread.sleep(getRandom(1, 3)*100); // introduce random delay
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // generate and return a random order id
        return "Thank you for your order! Your order id is " + getRandom(1, 10000) + "\n";
    }

    @GET
    @Path("/rating")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRating() {
        Integer rating = generateRandomRating(); // generate a random rating between 1-5

        return "You rated the order process " + rating + " stars. Thank you for your feedback!\n";
    }

    // TODO: Add a gauge to track the rating
    @Gauge(name = "order_svc:spl50_order_process_rating",
    unit = MetricUnits.NONE,
    description = "Overall customer rating for the order process")
    private Integer generateRandomRating() {
        return getRandom(1, 5);
    }

    private Integer getRandom(int min, int max) {
        Random random = new Random();
        Integer number = random.nextInt((max - min) + 1) + min;

        return number;
    }
}
