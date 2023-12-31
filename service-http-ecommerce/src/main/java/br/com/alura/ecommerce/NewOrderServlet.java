package br.com.alura.ecommerce;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
    private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>();

//    @Override
//    public void init() throws ServletException {
//        super.init();
//    }

    @Override
    public void destroy() {
        orderDispatcher.close();
        emailDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            //we are not caring about any security issues, we are only
            //showing how to use http as a starting point
            var email = req.getParameter("email");
            var amount = new BigDecimal(req.getParameter("amount"));

            var orderId = UUID.randomUUID().toString();

            var order = new Order(orderId, amount, email);
            orderDispatcher.send("ECCOMMERCE_NEW_ORDER", email, order);

            var emailCode = "Thank you for your order! We are processing your order!";
            emailDispatcher.send("ECCOMMERCE_SEND_EMAIL", email, emailCode);

            System.out.println("New order sent successfully.");
            resp.getWriter().println("New order sent successfully.");
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (ExecutionException | InterruptedException e) {
            throw new ServletException(e);
        }
    }
}
