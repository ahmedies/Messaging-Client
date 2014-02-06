/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commyprojectmessagingtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Ahmed Rezgui <ahmad.rezgui at gmail.com>
 */
public class PutMessage {
  
    private static final String CONNECTION_FACTORY = "jms/MyProjectConnectionFactory";
    private static final String CONNECTION_QUEUE = "jms/MyProjectQueue";
    private static final String CONNECTION_TOPIC = "jms/MyProjectTopic";
    private static final String TOPIC_ID = "MyProjectDurableTopic";

    /**
     *
     * @param args
     *
     */
    public static void main(String[] args) {

        ConnectionFactory connectionFactory = null;

        Connection connection = null;

//Get the JNDI Context

        try {

            Context jndiContext = new InitialContext();



//Create the Connection Factory

            connectionFactory = (ConnectionFactory) jndiContext.lookup(PutMessage.CONNECTION_FACTORY);

            connection = connectionFactory.createConnection();



//Create the session

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);



//Initialize Message Producer

            MessageProducer producer = null;



//Call methods to send publish messages

            publishToTopic(producer, jndiContext, session, args[1]);

            publishToQueue(producer, jndiContext, session, args[0]);



            System.out.println("Messages Sent!!!");

        } catch (NamingException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        } catch (JMSException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            try {

                if (connection != null) {

                    connection.close();

                }

            } catch (JMSException e) {

                e.printStackTrace();

            }

        }

    }

    private static void publishToTopic(MessageProducer producer, Context jndiContext, Session session, String message)
            throws
            NamingException, JMSException {

//Create new topic

        Topic topic = (Topic) jndiContext.lookup(PutMessage.CONNECTION_TOPIC);



//Create Message Produer

        producer = session.createProducer(topic);



//Send TextMessage

        TextMessage textMessage = session.createTextMessage();

        textMessage.setText(message);

        producer.send(textMessage);

    }

    private static void publishToQueue(MessageProducer producer, Context jndiContext, Session session, String message) throws NamingException, JMSException {

//Create new Queue

        Queue queue = (Queue) jndiContext.lookup(PutMessage.CONNECTION_QUEUE);



//Create Message Produer

        producer = session.createProducer(queue);



//Send TextMessage

        TextMessage textMessage = session.createTextMessage();

        textMessage.setText(message);

        producer.send(textMessage);

    }
}

