package com.demoAmarelo.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.bson.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.demoAmarelo.model.Mensaje;
import com.demoAmarelo.model.Producto;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.*;

@Component
public class MensajeListener {
   @RabbitListener(queues = "cola.Productos")
    public void recibirMensaje(Producto mensaje) throws IOException, InterruptedException, TimeoutException {

	   // Configuración de la conexión a RabbitMQ
       ConnectionFactory factory = new ConnectionFactory();
       factory.setHost("localhost"); // Cambia esto si usas un host diferente

       try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

           // Declarar la cola (asegurarse de que sea duradera)
           boolean durable = true;
           channel.queueDeclare("cola.Productos", durable, false, false, null);

           // Conexión a MongoDB
           try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
               MongoDatabase database = mongoClient.getDatabase("demoAmarelo");
               MongoCollection<Document> collection = database.getCollection("productos");

               DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                   String mensaje2 = new String(delivery.getBody(), StandardCharsets.UTF_8);
                   System.out.println(" [x] Recibido '" + mensaje + "'");

                   // Insertar el mensaje en MongoDB
                   Document doc = new Document("mensaje", mensaje2)
                                     .append("timestamp", System.currentTimeMillis());
                   collection.insertOne(doc);
                   System.out.println(" [x] Mensaje insertado en MongoDB");
               };

               // Suscribirse a la cola y esperar mensajes
               channel.basicConsume("cola.Productos", true, deliverCallback, consumerTag -> { });
               System.out.println(" [*] Esperando mensajes. Presiona CTRL+C para salir.");
              // Long time =(long) 60000;
               Thread.sleep(Long.MAX_VALUE); // Mantener la aplicación corriendo
           }
       }
    }
}